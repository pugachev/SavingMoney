
package action;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import dao.BuyListDAO;
import model.UserInfo;


public class UserLoginAction extends DispatchAction {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {

    	//セッション処理を開始
    	//1.セッションを生成
		res.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		//ユーザー情報を生成する
		UserInfo uinfo = new UserInfo();

		//2-1.セッションにログイン状態をセット(初期値はNG)
		uinfo.setLoginStatus("NG");
		//2-2.セッションに詳細画面の現在のページ数をセット(初期値は1)
		uinfo.setPresetPageNum(1);
		//2-3.スタート時の西暦をセット
		uinfo.setDispYear(String.valueOf(LocalDate.now().getYear()));
		//2-4.スタート時の月をセット
		uinfo.setPreDispMonth(String.valueOf(LocalDate.now().getMonthValue()));

		System.out.println(uinfo.getDispYear() + ' ' + uinfo.getPreDispMonth());

		//3.パラメータの取得
    	String rcvmail = (String)req.getParameter("regiEmail");
    	String rcvpassword = req.getParameter("regiPassword");

    	//4.ユーザーの存在有無をパスワードとの一致でみる
    	if((rcvmail!=null && !rcvmail.equals(""))&&(rcvpassword!=null && !rcvpassword.equals("")))
    	{
			//4-1.DBへパスワードの問い合わせ
            BuyListDAO dao = new BuyListDAO();
            String retPassword = dao.isUser(rcvmail);
            //4-1-1.パスワード不一致の場合
            if(!retPassword.equals(rcvpassword))
            {
            	req.getSession(true).setAttribute("uinfo", uinfo);
            	return mapping.findForward("failure");
            }
            //4-1-２.パスワード一致の場合
            else
            {
            	uinfo.setLoginStatus("OK");
            	uinfo.setUserId(rcvmail);
            	req.getSession(true).setAttribute("rcvmail", rcvmail);
            	req.getSession(true).setAttribute("uinfo", uinfo);
            	return mapping.findForward("success");
            }
    	}
    	//IDとPWが揃ってこなかった場合
    	else
    	{
    		req.getSession(true).setAttribute("uinfo", uinfo);
        	return mapping.findForward("failure");
    	}
    }
}