
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


public class UserRegistAction extends DispatchAction {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {

    	String rcvmail = (String)req.getParameter("regiEmail");
    	String rcvpassword = req.getParameter("regiPassword");

    	//対象日時作成
    	//フォーマット
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	//システムから取得した月
    	int targetMonth =  LocalDate.now().getMonthValue();
    	//システムから取得した日
    	int targetDay = LocalDate.now().getDayOfMonth();

    	String setDate = String.format("%4d-%02d-%02d",LocalDate.now().getYear(),targetMonth,targetDay);

    	//「変更」or 「削除」の場合
    	if((rcvmail!=null && !rcvmail.equals(""))&&(rcvpassword!=null && !rcvpassword.equals("")))
    	{
			//DBへupdate
            BuyListDAO dao = new BuyListDAO();
            dao.regiUser(rcvmail, rcvpassword,setDate);
    	}
    	else
    	{
    		return mapping.findForward("failure");
    	}

        return mapping.findForward("success");
    }

}
