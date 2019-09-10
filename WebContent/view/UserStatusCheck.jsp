<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="model.Product" %>
<%@page import="java.util.*" %>
<%
	//このJSPが読み込まれた際にユーザーのログイン状態を判定する処理
	String loginStatus = (String)session.getAttribute("loginStatus");
	String isStatus="'OK'";
	if(loginStatus==null || (loginStatus!=null && !loginStatus.equals("OK")))
	{
		isStatus="'NG'";
	}
%>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script>
$(document).ready(function(){
	var sts = <%= isStatus %>
	console.log(sts);
	if(sts=="NG"){
		document.location.href = "index.jsp";
		console.log('ログインしてません');
	}
});
function login(){
	console.log('ログインボタン押下');
}
</script>
