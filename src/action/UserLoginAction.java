
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import dao.BuyListDAO;


public class UserLoginAction extends DispatchAction {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {

    	String rcvmail = (String)req.getParameter("regiEmail");
    	String rcvpassword = req.getParameter("regiPassword");

    	//ユーザーの存在有無をパスワードとの一致でみる
    	if((rcvmail!=null && !rcvmail.equals(""))&&(rcvpassword!=null && !rcvpassword.equals("")))
    	{
			//DBへupdate
            BuyListDAO dao = new BuyListDAO();
            String retPassword = dao.isUser(rcvmail);
            if(!retPassword.equals(rcvpassword)) {
            	return mapping.findForward("failure");
            }
    	}

        return mapping.findForward("success");
    }

}
