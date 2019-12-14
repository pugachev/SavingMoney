
package action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.BuyListDAO;
import model.DailySum;
import model.UserInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class SavingMoneyListAjaxAction extends Action {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
    	//uinfoをセッションから取得する
    	UserInfo uinfo = (UserInfo)req.getSession(true).getAttribute("uinfo");
    	if(uinfo==null || (uinfo!=null && uinfo.getUserId().equals(""))) {
    		return mapping.findForward("failure");
    	}
    	String userid = (String)req.getSession(true).getAttribute("rcvmail");
    	int targetMonth=0;
    	int targetMonthLastDay=0;
    	String targetStart="";
    	String targetEnd="";
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    	String rcvMonth = (String)uinfo.getDispMonth();
    	if(rcvMonth==null || (rcvMonth!=null && rcvMonth.equals(""))) {
    		rcvMonth = (String)req.getParameter("targetmonth");
    	}

    	if(req.getParameter("targetmonth")!=null)
    	{
        	//画面からポストしてくる対象月
        	targetMonth =  Integer.parseInt(req.getParameter("targetmonth"));
        	if(targetMonth>12) {
        		targetMonth=1;
        	}
        	if(targetMonth==1) {
        		//年を切り替えるかを判断
        		if(uinfo.getPreDispMonth().contentEquals("12")&&targetMonth==1)
        		{
                	//対象月の末尾
                	targetMonthLastDay = YearMonth.of(Integer.parseInt(uinfo.getDispYear())+1, targetMonth).lengthOfMonth();
                	//対象月の開始日
                	targetStart = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear())+1,targetMonth,1);
                    //対象月の終了日
                	targetEnd= String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear())+1,targetMonth,targetMonthLastDay);
                	//対象年を格納
//                	uinfo.setDispYear(uinfo.getDispYear()+1);
                	uinfo.setDispYear(String.valueOf(Integer.parseInt(uinfo.getDispYear())+1));
                	//対象月を格納
                	uinfo.setDispMonth(String.valueOf(targetMonth));
        		}
        		else
        		{
                	//対象月の末尾
                	targetMonthLastDay = YearMonth.of(Integer.parseInt(uinfo.getDispYear()), targetMonth).lengthOfMonth();
                	//対象月の開始日
                	targetStart = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),targetMonth,1);
                    //対象月の終了日
                	targetEnd= String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),targetMonth,targetMonthLastDay);
                	//対象年を格納
                	//uinfo.setDispYear(String.valueOf(LocalDate.now().getYear()));
        		}
        	}
        	else if(targetMonth==12)
        	{
        		//年を切り替えるかを判断
        		if(uinfo.getPreDispMonth().contentEquals("1")&&targetMonth==12)
        		{
                	//対象月の末尾
                	targetMonthLastDay = YearMonth.of(Integer.parseInt(uinfo.getDispYear())-1, targetMonth).lengthOfMonth();
                	//対象月の開始日
                	targetStart = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear())-1,targetMonth,1);
                    //対象月の終了日
                	targetEnd= String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear())-1,targetMonth,targetMonthLastDay);
                	//対象年を格納
                	uinfo.setDispYear(String.valueOf(Integer.parseInt(uinfo.getDispYear())-1));
                	//対象月を格納
                	uinfo.setDispMonth(String.valueOf(targetMonth));
        		}
        		else
        		{
                	//対象月の末尾
                	targetMonthLastDay = YearMonth.of(Integer.parseInt(uinfo.getDispYear()), targetMonth).lengthOfMonth();
                	//対象月の開始日
                	targetStart = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),targetMonth,1);
                    //対象月の終了日
                	targetEnd= String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),targetMonth,targetMonthLastDay);
                	//対象年を格納
                	//uinfo.setDispYear(String.valueOf(LocalDate.now().getYear()));
        		}
    	    }
        	else
        	{
            	//対象月の末尾
            	targetMonthLastDay = YearMonth.of(Integer.parseInt(uinfo.getDispYear()), targetMonth).lengthOfMonth();
            	//対象月の開始日
            	targetStart = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),targetMonth,1);
                //対象月の終了日
            	targetEnd= String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),targetMonth,targetMonthLastDay);
            	//対象年を格納
            	//uinfo.setDispYear(String.valueOf(LocalDate.now().getYear()));
        	}
    	}
//    	else
//    	{
//        	//システムから取得した対象月
//        	targetMonth =  LocalDate.now().getMonthValue();
//    	}
    	req.getSession(true).setAttribute("month", targetMonth);
    	uinfo.setDispMonth(String.valueOf(targetMonth));
    	uinfo.setPreDispMonth(String.valueOf(targetMonth));


        BuyListDAO dao = new BuyListDAO();
        List<DailySum> rcv = dao.getDailySumList(userid,targetStart,targetEnd);

    	JSONObject obj = new JSONObject();
//    	obj.put("year", LocalDate.now().getYear());
    	obj.put("year", uinfo.getDispYear());
    	obj.put("month", targetMonth);
    	JSONArray jsonArray = new JSONArray();
        for(int i=0;i<rcv.size();i++)
        {
        	JSONObject obj2 = new JSONObject();
        	//対象日をセット
        	String tday[] = rcv.get(i).getBuydate().split("-");
        	int numday = Integer.parseInt( tday[2]);
        	obj2.put("day", String.valueOf(numday));
        	//合計金額をセット
        	obj2.put("price",rcv.get(i).getDailysum());
        	//idをセット
        	obj2.put("id",rcv.get(i).getId());
        	jsonArray.add(obj2);
        }

        obj.put("event", jsonArray);

        req.getSession(true).setAttribute("targetMonth", String.valueOf(targetMonth));

        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        writer.print(obj.toString());
        writer.close();

        return null;
    }

}
