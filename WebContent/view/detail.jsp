<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="model.Product" %>
<%@page import="java.util.*" %>
<%
	//LoginActionからもらうデータ
	String rcvID = "'"+(String)session.getAttribute("userid")+"'";
	String rcvTargetMonth = (String)session.getAttribute("targetMonth");
	System.out.println("★ detail.jsp rcvID=" + rcvID + " rcvTargetMonth=" + rcvTargetMonth);
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
$(document).ready(function()

	    {
			userid=<%= rcvID %>;
			targetmonth=<%= rcvTargetMonth %>;
			console.log('userid=' + userid + ' targetmonth='+targetmonth);
	        $("#myTable").tablesorter();
	    }
	);
function calender_func(){
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
        value: targetmonth
    }));
    $fm.appendTo(document.body);
    $fm.submit();
    $fm.remove();
}

</script>
<style>
    #myTable table {
        border-top: 1px solid #4f4d47;
        border-right: 1px solid #4f4d47;
        width: 640px;
        border-spacing: 0;
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

        /* contentでタイトルを追加 */
        #demo01 td:nth-of-type(2):before { content: データ容量}
        #demo01 td:nth-of-type(3):before { content: バージョン管理}
        #demo01 td:nth-of-type(4):before { content: 料金}
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
		        <a class="btn btn-primary" style="margin:3px;" href="#" onclick="calender_func();">詳細画面 <span class="sr-only">(current)</span></a>
		      </li>
		    </ul>
		  </div>
		</nav>
<div id="demo01">
    <table id="myTable" class="tablesorter">
        <thead>
            <tr>
                <th>プラン</th>
                <th>データ容量</th>
                <th>バージョン管理</th>
                <th>料金</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>無料プラン</td>
                <td>5GB</td>
                <td>なし</td>
                <td>無料</td>
            </tr>
            <tr>
                <td>個人事業主プラン</td>
                <td>30GB</td>
                <td>１ヶ月まで</td>
                <td>1000円/月</td>
            </tr>
            <tr>
                <td>法人プラン</td>
                <td>100GB</td>
                <td>無制限</td>
                <td>5000円/月</td>
            </tr>
        </tbody>
    </table>
</div>
</body>