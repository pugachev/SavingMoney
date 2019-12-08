<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="model.Product" %>
<%@page import="model.UserInfo" %>
<%@page import="java.util.*" %>
<%@page import="com.fasterxml.jackson.databind.JsonNode" %>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@page import="com.fasterxml.jackson.core.type.TypeReference" %>
<%@page import="java.text.NumberFormat" %>
<%!
Product[] itemList=null;
%>
<%

	//SavingMoneyDetailActionからもらうデータ
	UserInfo rcvUinfo = (UserInfo)session.getAttribute("uinfo");
	String rcvID = "'"+rcvUinfo.getUserId()+"'";
	NumberFormat nfCur = NumberFormat.getCurrencyInstance();
	int totalsum = rcvUinfo.getDispMonthSum();
	String rcvTargetMonth = rcvUinfo.getDispMonth();
	int offset = rcvUinfo.getPresetPageNum();

	/* String rcvJsonData = (String)session.getAttribute("buydata"); */
	String rcvJsonData = rcvUinfo.getListData();
	int totaldatacnt= rcvUinfo.getDispMonthDataCnt();
	int totaldetailsum= rcvUinfo.getDispMonthSum();
	if(rcvJsonData!=null){
        ObjectMapper mapper = new ObjectMapper();

        // JSON文字列を読み込み、JsonNodeオブジェクトに変換（Fileやbyte[]も引数に取れる）
        JsonNode root = mapper.readTree(rcvJsonData);
        itemList = new ObjectMapper().readValue(rcvJsonData, Product[].class);

	}
	int pagecnt = 0;
	if(totaldatacnt%10==0)
	{
		pagecnt = totaldatacnt/10;
	}
	else
	{
		pagecnt = (totaldatacnt/10)+1;
	}
	int presentPageNum = rcvUinfo.getPresetPageNum();

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>一覧画面</title>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<!--<head>内-->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.31.0/css/theme.default.min.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.31.0/js/jquery.tablesorter.min.js"></script>
<!-- 追加機能(widgets)を使用する場合は次も追加する -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.31.0/js/jquery.tablesorter.widgets.min.js"></script>
<script>
var userid='';
var targetmonth='';
var offset='';
var tmp='';
$(document).ready(function()
	    {
			userid=<%= rcvID %>;
			targetmonth=<%= rcvTargetMonth %>;
			console.log('userid=' + userid + ' targetmonth='+targetmonth);
	        $("#myTable").tablesorter();
	        tmp='.page'+<%= presentPageNum %>;
	    	$(tmp).addClass("active");
	    }
	);
function calender_func(offset){
    var $fm = $('<form />', {
        method: 'POST',
        action: '${pageContext.request.contextPath}/SavingMoneyList.do'
    });
    $fm.append($('<input />', {
        type: 'hidden',
        name: 'dtargetid',
        value: userid
    }));
    $fm.append($('<input />', {
        type: 'hidden',
        name: 'dtargemonth',
        value: offset
    }));
    $fm.appendTo(document.body);
    $fm.submit();
    $fm.remove();
}
function pageNation(os){

	//if(os!=1){
	//	tmp = '.page'+(os-1);
	//}
	//$(tmp).removeClass("active");
	tmp = '.page'+os;


    var $fm = $('<form />', {
        method: 'POST',
        action: '${pageContext.request.contextPath}/SavingMoneyDetail.do'
    });
    $fm.append($('<input />', {
        type: 'hidden',
        name: 'dtargetid',
        value: userid
    }));
    $fm.append($('<input />', {
        type: 'hidden',
        name: 'dtargemonth',
        value: targetmonth
    }));
    $fm.append($('<input />', {
        type: 'hidden',
        name: 'doffset',
        value: os
    }));
    $fm.appendTo(document.body);
    $fm.submit();
    $fm.remove();

}

</script>
<style>
.tablesorter-default{
	width:95%;
	text-align:center;
	margin: 10px auto;
}
	nav{
		font-size:1.2em;
	}
    #myTable table {
        border-top: 1px solid #4f4d47;
        border-right: 1px solid #4f4d47;
        width: 640px;
        border-spacing: 0;
        padding:100px;
        margin: 0 auto;
    }
    #myTable th, td {
        border-bottom: 1px solid #4f4d47;
        border-left: 1px solid #4f4d47;
        text-align: center;
        padding: 5px;
    }
    #myTable th { background-color: #61c5bb; color: #fff;}
    @media screen and (max-width: 720px){
        #demo01 table { width: 100%; border:none; padding: 10px;}
        #demo01 thead { display: none;}
        #demo01 tr {
            border-top: 1px solid #4f4d47;
            display: block;
            margin-bottom: 20px;
        }
        #demo01 td {
            border-right: 1px solid #4f4d47;
            border-bottom: none;
            display: block;
            padding: 0;
        }
        /* tdのデザイン */
        #demo01 td:nth-of-type(1) { background-color: #61c5bb; color: #fff;}
        #demo01 td:last-child { border-bottom: 1px solid #4f4d47;}

        /*td:beforeのデザイン*/
        #demo01 td:before {
            background-color: #f5f3ec;
            color: #4f4d47;
            display: block;
        }
    }
</style>
</head>
<body>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
		    <a class="navbar-brand" href="#">節約カレンダー</a>
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		      <li class="nav-item active">
		        <a class="btn btn-primary" style="margin:3px;" href="#" onclick="calender_func();">カレンダー画面 <span class="sr-only">(current)</span></a>
		      </li>
		    </ul>
		      <span class="navbar-text">
			    合計金額:<% out.print(nfCur.format(totaldetailsum)); %>
			  </span>
		  </div>
		</nav>
<div id="demo01">
    <table id="myTable" class="tablesorter">
        <thead>
            <tr>
                <th>ID</th>
                <th>項目</th>
                <th>金額</th>
                <th>日付</th>
            </tr>
        </thead>
        <tbody>
        	<% if(itemList.length>0) {%>
        		<% for (Product item : itemList) { %>
	            <tr>
	                <td><%= item.getId() %></td>
	                <td><%= item.getTitle() %></td>
	                <td><%= item.getPrice() %></td>
	                <td><%= item.getDay() %></td>
	            </tr>
            	<% } %>
        	<% } %>
        </tbody>
    </table>
    <% if(totaldatacnt>10) {%>
      <div class="row">
        <div class="col-md-6 offset-md-3 py-2">
          <nav aria-label="ページ送り">
            <div class="text-center">
              <ul class="pagination justify-content-center pagination-lg">
              	<% for(int i=0;i<pagecnt;i++){ %>
                <li class="page-item page<%= (i+1) %>"><a class="page-link" href="#" onclick="pageNation('<%= (i+1) %>')"><%= (i+1) %></a></li>
                <% } %>
              </ul>
            </div>
          </nav>
        </div>
    <% } %>
	</div>
</div>
</body>