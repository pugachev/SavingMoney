
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import dao.BuyListDAO;
import model.Product;


public class SavingMoneyRegistAction extends DispatchAction {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {

    	String rcvTargetDate = req.getParameter("targetdate");
    	int rcvItemNum = Integer.parseInt(req.getParameter("selecteditem"));
    	int rcvPrice = Integer.parseInt(req.getParameter("price"));

    	System.out.println("rcvTargetDate="+rcvTargetDate+" rcvItemNum=" + rcvItemNum + " rcvPrice=" + rcvPrice);

    	Product tmp = new Product();
    	tmp.setItemnum(rcvItemNum);
    	tmp.setPrice(rcvPrice);
    	tmp.setBuydate(rcvTargetDate);

        BuyListDAO dao = new BuyListDAO();
        dao.entry(tmp);

        return mapping.findForward("success");
    }

}
