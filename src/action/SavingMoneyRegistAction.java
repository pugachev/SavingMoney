
package action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import dao.BuyListDAO;
import model.Product;
import model.UserInfo;


public class SavingMoneyRegistAction extends DispatchAction {
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
    	//セッションからUserInfoを取得する
    	UserInfo uinfo = (UserInfo)req.getSession(true).getAttribute("uinfo");
    	if(uinfo==null || (uinfo!=null && uinfo.getUserId().equals(""))) {
    		return mapping.findForward("failure");
    	}
    	String userid = (String)req.getSession(true).getAttribute("rcvmail");

    	String rcvMonth = (String)req.getParameter("thisMonth");
    	if(rcvMonth==null || (rcvMonth!=null && rcvMonth.equals(""))) {
    		rcvMonth = (String)req.getParameter("targetmonth");
    	}
    	String rcvTargetId = (String)req.getParameter("targetid");
    	String rcvTargetDate = req.getParameter("targetdate");
    	int rcvItemNum = Integer.parseInt(req.getParameter("selecteditem"));
    	int rcvPrice = Integer.parseInt(req.getParameter("buyamount"));
    	String rcvMemo = (String)req.getParameter("buymemo");

    	//対象日時作成
    	//フォーマット
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	//システムから取得した月

    	int targetMonth = Integer.parseInt(rcvMonth);


    	String setDate = String.format("%4d-%02d-%02d",Integer.parseInt(uinfo.getDispYear()),targetMonth,Integer.parseInt(rcvTargetDate));

    	//「変更」or 「削除」の場合
    	if(rcvTargetId!=null && !rcvTargetId.equals(""))
    	{
    		//「変更」の場合
    		if(rcvPrice>0)
    		{
    			//DBへupdate
                BuyListDAO dao = new BuyListDAO();
                dao.updateItem(rcvTargetId, String.valueOf(rcvItemNum), String.valueOf(rcvPrice));
    		}
    	}
    	//「新規作成」の場合
    	else
    	{
        	Product tmp = new Product();
        	tmp.setItemnum(rcvItemNum);
        	tmp.setPrice(rcvPrice);
        	tmp.setMemo(rcvMemo);
        	tmp.setUserid(userid);
        	tmp.setBuydate(setDate);

            BuyListDAO dao = new BuyListDAO();
            dao.entry(tmp);
    	}




        return mapping.findForward("success");
    }

}
