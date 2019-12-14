
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
    	String rcvYear = (String)req.getParameter("targetyear");
    	String rcvMonth = (String)uinfo.getDispMonth();
    	int totalyearsum=0;
    	int totalsum=0;
    	if(rcvMonth==null || (rcvMonth!=null && rcvMonth.equals(""))) {
    		rcvMonth = (String)req.getParameter("targetmonth");
    	}
    	int targetMonthLastDay=0;
    	String targetStart="";
    	String targetEnd="";
    	//データ取得処理
    	if(req.getParameter("targetmonth")!=null)
    	{
        	//画面からポストしてくる対象月
        	int targetMonth =  Integer.parseInt(req.getParameter("targetmonth"));
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

            BuyListDAO dao = new BuyListDAO();
            //年別合計金額
            String yeartotaldate = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),1,1);
            String yeartotaldate2 = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),12,31);
            totalyearsum = dao.getTotalYearSum(userid, yeartotaldate, yeartotaldate2);
            //月別合計金額
            totalsum = dao.getTotalSum(userid,targetStart,targetEnd);
            uinfo.setDispMonth(String.valueOf(targetMonth));
            uinfo.setPreDispMonth(String.valueOf(targetMonth));
            uinfo.setDispYearSum(totalyearsum);
            uinfo.setDispMonthSum(totalsum);
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
        	if(targetMonth>12) {
        		targetMonth=targetMonth-1;
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
        		}
        		else
        		{
                	//対象月の末尾
                	targetMonthLastDay = YearMonth.of(Integer.parseInt(uinfo.getDispYear()), targetMonth).lengthOfMonth();
                	//対象月の開始日
                	targetStart = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),targetMonth,1);
                    //対象月の終了日
                	targetEnd= String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),targetMonth,targetMonthLastDay);
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
        		}
        		else
        		{
                	//対象月の末尾
                	targetMonthLastDay = YearMonth.of(Integer.parseInt(uinfo.getDispYear()), targetMonth).lengthOfMonth();
                	//対象月の開始日
                	targetStart = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),targetMonth,1);
                    //対象月の終了日
                	targetEnd= String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),targetMonth,targetMonthLastDay);
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
        	}

            BuyListDAO dao = new BuyListDAO();
            //年別合計金額
            String yeartotaldate = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),1,31);
            String yeartotaldate2 = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),12,31);
            totalyearsum = dao.getTotalSum(userid, yeartotaldate, yeartotaldate2);
            //月別合計金額
            totalsum = dao.getTotalSum(userid,targetStart,targetEnd);
            uinfo.setDispMonth(String.valueOf(targetMonth));
            uinfo.setPreDispMonth(String.valueOf(targetMonth));
            uinfo.setDispYearSum(totalyearsum);
            uinfo.setDispMonthSum(totalsum);
    	}

    	req.getSession(true).setAttribute("uinfo", uinfo);
        return mapping.findForward("success");
    }

}
