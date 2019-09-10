
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import dao.BuyListDAO;


public class SavingMoneyDeleteAction extends DispatchAction {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {

    	String rcvdTargetId = (String)req.getParameter("dtargetid");

    	//「変更」or 「削除」の場合
    	if(rcvdTargetId!=null && !rcvdTargetId.equals(""))
    	{
			//DBへdelete(論理削除か物理削除かは未定)
            BuyListDAO dao = new BuyListDAO();
            dao.deleteItem(rcvdTargetId);
    	}
        return mapping.findForward("success");
    }

}
