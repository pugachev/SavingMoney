
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.BuyListDAO;
import model.Product;


public class SavingMoneyRegsitAction extends Action {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {

    	int rcvItemNum = Integer.parseInt(req.getParameter("itemnum"));
    	int rcvbuymount = Integer.parseInt(req.getParameter("buymount"));

    	Product tmp = new Product();
    	tmp.setItemnum(rcvItemNum);
    	tmp.setBuyamount(rcvbuymount);

        BuyListDAO dao = new BuyListDAO();
        dao.entry(tmp);

        return mapping.findForward("success");
    }

}
