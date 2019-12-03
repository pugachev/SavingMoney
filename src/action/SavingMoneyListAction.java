
package action;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class SavingMoneyListAction extends Action {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
    	String loginStatus = (String)req.getSession(true).getAttribute("loginStatus");

    	String userid = (String)req.getSession(true).getAttribute("rcvmail");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String rcvMonth = (String)req.getParameter("thisMonth");
    	int totalsum=0;
    	if(rcvMonth==null || (rcvMonth!=null && rcvMonth.equals(""))) {
    		rcvMonth = (String)req.getParameter("targetmonth");
    	}
    	if((String)req.getSession(true).getAttribute("deleteMonth")!=null)
    	{
    		rcvMonth = (String)req.getSession(true).getAttribute("deleteMonth");
    		req.getSession(true).setAttribute("deleteMonth", "");
    	}

    	//データ取得処理
    	if(req.getParameter("targetmonth")!=null)
    	{
        	//画面からポストしてくる対象月
        	int targetMonth =  Integer.parseInt(req.getParameter("targetmonth"));
        	//対象月の末尾
        	int targetMonthLastDay = YearMonth.of(LocalDate.now().getYear(), targetMonth).lengthOfMonth();
        	//対象月の開始日
        	String targetStart = String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,1);
            //対象月の終了日
        	String targetEnd= String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,targetMonthLastDay);

            BuyListDAO dao = new BuyListDAO();
            List<DailySum> rcv = dao.getDailySumList(userid,targetStart,targetEnd);
            //月別合計金額
            totalsum = dao.getTotalSum(userid,targetStart,targetEnd);
        	JSONObject obj = new JSONObject();
        	obj.put("year", LocalDate.now().getYear());
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
            req.getSession(true).setAttribute("buydata", obj.toString());
            req.getSession(true).setAttribute("targetMonth", String.valueOf(targetMonth));
            req.getSession(true).setAttribute("totalsum", String.valueOf(totalsum));
    	}

    	else
    	{
    		int targetMonth =0;
    		if(rcvMonth!=null && !rcvMonth.equals(""))
    		{
            	//セッションから取得した対象月
            	targetMonth = Integer.parseInt(rcvMonth);

    		}
    		else
    		{
            	//システムから取得した対象月
            	targetMonth =  LocalDate.now().getMonthValue();

    		}


    		//対象月の末尾
        	int targetMonthLastDay = YearMonth.of(LocalDate.now().getYear(), 7).lengthOfMonth();
        	//対象月の開始日
        	String targetStart = String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,1);
            //対象月の終了日
        	String targetEnd= String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,targetMonthLastDay);

            BuyListDAO dao = new BuyListDAO();
            //月別合計金額
            totalsum = dao.getTotalSum(userid,targetStart,targetEnd);
            List<DailySum> rcv = dao.getDailySumList(userid,targetStart,targetEnd);

        	JSONObject obj = new JSONObject();
        	obj.put("year", LocalDate.now().getYear());
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
            req.getSession(true).setAttribute("buydata", obj.toString());
            req.getSession(true).setAttribute("targetMonth", String.valueOf(targetMonth));
            req.getSession(true).setAttribute("totalsum", String.valueOf(totalsum));
    	}

        return mapping.findForward("success");
    }

}
