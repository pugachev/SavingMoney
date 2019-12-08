
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


public class SavingMoneyListOutputFileAjaxAction extends Action {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
    	String userid = (String)req.getSession(true).getAttribute("rcvmail");
    	int targetMonth=0;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	if(req.getParameter("targetmonth")!=null)
    	{
        	//画面からポストしてくる対象月
        	targetMonth =  Integer.parseInt(req.getParameter("targetmonth"));

    	}
    	else
    	{
        	//システムから取得した対象月
        	targetMonth =  LocalDate.now().getMonthValue();
    	}
    	req.getSession(true).setAttribute("month", targetMonth);


    	//対象月の末尾
    	int targetMonthLastDay = YearMonth.of(LocalDate.now().getYear(), 7).lengthOfMonth();
    	//対象月の開始日
    	String targetStart = String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,1);
        //対象月の終了日
    	String targetEnd= String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,targetMonthLastDay);


        BuyListDAO dao = new BuyListDAO();
        dao.outPutFle(userid,targetStart,targetEnd);

        req.getSession(true).setAttribute("targetMonth", String.valueOf(targetMonth));

//        res.setCharacterEncoding("UTF-8");
//        PrintWriter writer = res.getWriter();
//        writer.print("success");
//        writer.close();

        return mapping.findForward("success");
    }

}
