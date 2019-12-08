
package action;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.BuyListDAO;
import model.UserInfo;


public class SavingMoneyListAction extends Action {
    @SuppressWarnings("unused")
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
    	//セッションからUserInfoを取得する
    	UserInfo uinfo = (UserInfo)req.getSession(true).getAttribute("uinfo");
    	if(uinfo==null || (uinfo!=null && uinfo.getUserId().equals(""))) {
    		return mapping.findForward("failure");
    	}
    	String loginStatus = (String)uinfo.getLoginStatus();
    	String userid = (String)uinfo.getUserId();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String rcvMonth = (String)uinfo.getDispMonth();
    	int totalsum=0;
    	if(rcvMonth==null || (rcvMonth!=null && rcvMonth.equals(""))) {
    		rcvMonth = (String)req.getParameter("targetmonth");
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
            //月別合計金額
            totalsum = dao.getTotalSum(userid,targetStart,targetEnd);
            uinfo.setDispMonth(String.valueOf(targetMonth));
            uinfo.setDispMonthSum(totalsum);
//            req.getSession(true).setAttribute("targetMonth", String.valueOf(targetMonth));
//            req.getSession(true).setAttribute("totalsum", String.valueOf(totalsum));

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
            uinfo.setDispMonth(String.valueOf(targetMonth));
            uinfo.setDispMonthSum(totalsum);
//            req.getSession(true).setAttribute("targetMonth", String.valueOf(targetMonth));
//            req.getSession(true).setAttribute("totalsum", String.valueOf(totalsum));
    	}

    	req.getSession(true).setAttribute("uinfo", uinfo);
        return mapping.findForward("success");
    }

}
