
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import dao.BuyListDAO;


public class SavingMoneyRegistAction extends DispatchAction {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {

    	String rcvTargetId = (String)req.getParameter("targetids");
    	String targetDeleteId[] = rcvTargetId.split(",");

    	//「変更」or 「削除」の場合
    	if(rcvTargetId!=null && !rcvTargetId.equals(""))
    	{
    		//「削除」の場合
    		//DBへdelete(論理削除か物理削除かは未定)
            BuyListDAO dao = new BuyListDAO();
            for(int i=0;i<targetDeleteId.length;i++)
            {
                dao.deleteItem(targetDeleteId[i]);
                System.out.println("(削除) rcvTargetId="+targetDeleteId[i]);
            }
    	}

        return mapping.findForward("success");
    }

}
