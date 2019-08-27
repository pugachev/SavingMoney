
package action;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import dao.BuyListDAO;
import model.Product;


public class UserRegistAction extends DispatchAction {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {

    	String rcvTargetId = (String)req.getParameter("targetid");
    	String rcvTargetDate = req.getParameter("targetdate");
    	int rcvItemNum = Integer.parseInt(req.getParameter("selecteditem"));
    	int rcvPrice = Integer.parseInt(req.getParameter("buyamount"));

    	//対象日時作成
    	//フォーマット
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	//システムから取得した月
    	int targetMonth =  LocalDate.now().getMonthValue();

    	String setDate = String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,Integer.parseInt(rcvTargetDate));

    	//「変更」or 「削除」の場合
    	if(rcvTargetId!=null && !rcvTargetId.equals(""))
    	{
    		//「変更」の場合
    		if(rcvPrice>0)
    		{
    			//DBへupdate
                BuyListDAO dao = new BuyListDAO();
                dao.updateItem(rcvTargetId, String.valueOf(rcvItemNum), String.valueOf(rcvPrice));
                System.out.println("(変更) rcvTargetId="+rcvTargetId + " rcvItemNum=" + rcvItemNum + " rcvPrice=" + rcvPrice);
    		}
    	}
    	//「新規作成」の場合
    	else
    	{
        	Product tmp = new Product();
        	tmp.setItemnum(rcvItemNum);
        	tmp.setPrice(rcvPrice);
        	tmp.setBuydate(setDate);

            BuyListDAO dao = new BuyListDAO();
            dao.entry(tmp);

            System.out.println("(新規作成) rcvTargetDate="+rcvTargetDate+" rcvItemNum=" + rcvItemNum + " rcvPrice=" + rcvPrice + " setDate=" + setDate);
    	}




        return mapping.findForward("success");
    }

}
