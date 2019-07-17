
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


public class SavingMoneyListAction extends Action {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	//データ取得処理
    	if(req.getParameter("tmonth")!=null)
    	{
        	//画面からポストしてくる対象月
        	int targetMonth =  Integer.parseInt(req.getParameter("tmonth"));
        	//対象月の末尾
        	int targetMonthLastDay = YearMonth.of(LocalDate.now().getYear(), 7).lengthOfMonth();
        	//対象月の開始日
        	String targetStart = String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,1);
            //対象月の終了日
        	String targetEnd= String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,targetMonthLastDay);

            BuyListDAO dao = new BuyListDAO();
            List<Product> rcv = dao.getProductList(targetStart,targetEnd);

            for(int i=0;i<rcv.size();i++)
            {
            	System.out.println(rcv.get(i).getId()+ " " + rcv.get(i).getBuyamount());
            }
    	}
    	else
    	{
        	//画面からポストしてくる対象月
        	int targetMonth =  LocalDate.now().getMonthValue();
        	//対象月の末尾
        	int targetMonthLastDay = YearMonth.of(LocalDate.now().getYear(), 7).lengthOfMonth();
        	//対象月の開始日
        	String targetStart = String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,1);
            //対象月の終了日
        	String targetEnd= String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,targetMonthLastDay);

            BuyListDAO dao = new BuyListDAO();
            List<Product> rcv = dao.getProductList(targetStart,targetEnd);

            int amountmonth = dao.getAmountByMonth(targetStart,targetEnd);
            System.out.println("amountmonth="+amountmonth);
            req.getSession(true).setAttribute("amountmonth", amountmonth);

            req.getSession(true).setAttribute("buydata", rcv);
    	}


        return mapping.findForward("success");
    }

}
