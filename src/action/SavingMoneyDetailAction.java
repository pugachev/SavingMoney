
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
import model.Product;
import model.UserInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class SavingMoneyDetailAction extends Action {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
    	//セッションからUserInfoを取得する
    	UserInfo uinfo = (UserInfo)req.getSession(true).getAttribute("uinfo");
    	if(uinfo==null || (uinfo!=null && uinfo.getUserId().equals(""))) {
    		return mapping.findForward("failure");
    	}
    	String userid = (String)uinfo.getUserId();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String rcvYear = (String)uinfo.getDispYear();
    	String rcvMonth = (String)uinfo.getDispMonth();
    	System.out.println("Detail rcvYear=" + rcvYear + " rcvMonth="+rcvMonth);
    	int rcvOffset = uinfo.getPresetPageNum();
    	if(req.getParameter("doffset")!=null && Integer.parseInt(req.getParameter("doffset"))>0) {
    		rcvOffset = Integer.parseInt(req.getParameter("doffset"));
    	}
    	int totalDataCnt=0;
    	int totaldetailsum=0;
    	if(rcvMonth==null || (rcvMonth!=null && rcvMonth.equals(""))) {
    		rcvMonth = (String)req.getParameter("targetmonth");
    	}

    	//データ取得処理
    	if(req.getParameter("targetmonth")!=null)
    	{
    		//画面からポストしてくる対象年
    		int targetYear = Integer.parseInt(req.getParameter("dtargeyear"));
        	//画面からポストしてくる対象月
        	int targetMonth =  Integer.parseInt(req.getParameter("targetmonth"));
        	//対象月の末尾
        	int targetMonthLastDay = YearMonth.of(LocalDate.now().getYear(), targetMonth).lengthOfMonth();
        	//対象月の開始日
        	String targetStart = String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,1);
            //対象月の終了日
        	String targetEnd= String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,targetMonthLastDay);

            BuyListDAO dao = new BuyListDAO();
            totalDataCnt = dao.getTotalDataCnt(userid,targetStart,targetEnd);
            totaldetailsum = dao.getTotalSum(userid,targetStart,targetEnd);
            int tmpoffset = (rcvOffset-1)*10;
            List<Product> rcv = dao.getShoppingList(userid,targetStart,targetEnd,tmpoffset);

        	JSONObject obj = new JSONObject();
        	JSONArray jsonArray = new JSONArray();
            for(int i=0;i<rcv.size();i++)
            {
            	JSONObject obj2 = new JSONObject();
            	//対象日をセット
            	String tday[] = rcv.get(i).getBuydate().split("-");
            	int numday = Integer.parseInt( tday[2]);
            	obj2.put("day", String.valueOf(numday));
            	//titleをセット
            	obj2.put("title",rcv.get(i).getTitle());
            	//金額をセット
            	obj2.put("price",rcv.get(i).getPrice());
            	//idをセット
            	obj2.put("id",rcv.get(i).getId());
            	//メモをセット
            	obj2.put("memo", rcv.get(i).getMemo());
            	jsonArray.add(obj2);
            }

            uinfo.setListData(jsonArray.toString());
            uinfo.setUserId(userid);
            uinfo.setDispMonth(String.valueOf(targetMonth));
            uinfo.setDispMonthDataCnt(totalDataCnt);
            uinfo.setDispMonthSum(totaldetailsum);
            uinfo.setPresetPageNum(rcvOffset);
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
        	int targetMonthLastDay = YearMonth.of(Integer.parseInt(rcvYear), 7).lengthOfMonth();
        	//対象月の開始日
        	String targetStart = String.format("%4d-%02d-%02d",Integer.parseInt(rcvYear),targetMonth,1);
            //対象月の終了日
        	String targetEnd= String.format("%4d-%02d-%02d",Integer.parseInt(rcvYear),targetMonth,targetMonthLastDay);

            BuyListDAO dao = new BuyListDAO();
            totalDataCnt = dao.getTotalDataCnt(userid,targetStart,targetEnd);
            totaldetailsum = dao.getTotalSum(userid,targetStart,targetEnd);
            int tmpoffset = (rcvOffset-1)*10;
            List<Product> rcv = dao.getShoppingList(userid,targetStart,targetEnd,tmpoffset);

        	JSONObject obj = new JSONObject();
        	JSONArray jsonArray = new JSONArray();
            for(int i=0;i<rcv.size();i++)
            {
            	JSONObject obj2 = new JSONObject();
            	//対象日をセット
            	String tday[] = rcv.get(i).getBuydate().split("-");
            	int numday = Integer.parseInt( tday[2]);
            	obj2.put("day", String.valueOf(numday));
            	//titleをセット
            	obj2.put("title",rcv.get(i).getTitle());
            	//金額をセット
            	obj2.put("price",rcv.get(i).getPrice());
            	//idをセット
            	obj2.put("id",rcv.get(i).getId());
            	//メモをセット
            	obj2.put("memo", rcv.get(i).getMemo());
            	jsonArray.add(obj2);
            }

            uinfo.setListData(jsonArray.toString());
            uinfo.setUserId(userid);
            uinfo.setDispMonth(String.valueOf(targetMonth));
            uinfo.setDispMonthDataCnt(totalDataCnt);
            uinfo.setDispMonthSum(totaldetailsum);
            uinfo.setPresetPageNum(rcvOffset);
    	}
       	req.getSession(true).setAttribute("uinfo", uinfo);
        return mapping.findForward("success");
    }

}
