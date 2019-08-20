
package action;

import java.io.PrintWriter;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class SavingMoneyListAjaxAction extends Action {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    	//システムから取得した対象月
    	int targetMonth =  LocalDate.now().getMonthValue();
    	//対象月の末尾
    	int targetMonthLastDay = YearMonth.of(LocalDate.now().getYear(), 7).lengthOfMonth();
    	//対象月の開始日
    	String targetStart = String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,1);
        //対象月の終了日
    	String targetEnd= String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,targetMonthLastDay);

        BuyListDAO dao = new BuyListDAO();
        List<Product> rcv = dao.getShoppingList(targetStart,targetEnd);


    	JSONObject obj = new JSONObject();
    	obj.put("year", LocalDate.now().getYear());
    	obj.put("month", targetMonth);
    	JSONArray jsonArray = new JSONArray();
        for(int i=0;i<rcv.size();i++)
        {
        	JSONObject obj2 = new JSONObject();
        	//対象日をセット
        	String tday[] = rcv.get(i).getBuydate().split("-");
        	obj2.put("day", tday[2]);
        	//titleをセット
        	obj2.put("title",rcv.get(i).getTitle());
        	//金額をセット
        	obj2.put("price",rcv.get(i).getPrice());
        	//idをセット
        	obj2.put("id",rcv.get(i).getId());
        	jsonArray.add(obj2);
        }

        obj.put("event", jsonArray);

        System.out.println("ajaxデータ " + obj.toString());

        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        writer.print(obj.toString());
        writer.close();

        return null;
    }

}
