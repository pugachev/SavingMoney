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
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<!--<head>内-->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.31.0/css/theme.default.min.css">

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.31.0/js/jquery.tablesorter.min.js"></script>
<!-- 追加機能(widgets)を使用する場合は次も追加する -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.31.0/js/jquery.tablesorter.widgets.min.js"></script>
<script>
var userid='';
var targetmonth='';
var offset='';
var tmp='';
function delete_func(){

    var $fm = $('<form />', {
        method: 'POST',
        action: '${pageContext.request.contextPath}/SavingMoneyDelete.do'
    });
    $fm.append($('<input />', {
        type: 'hidden',
        name: 'dtargetid',
        value: $('#targetid').val()
    }));

    $fm.appendTo(document.body);
    $fm.submit();
    $fm.remove();
}

function update_func(){

    var $fm = $('<form />', {
        method: 'POST',
        action: '${pageContext.request.contextPath}/SavingMoneyUpdate.do'
    });
    $fm.append($('<input />', {
        type: 'hidden',
        name: 'dtargetid',
        value: $('#targetid2').val()
    }));
    $fm.append($('<input />', {
        type: 'hidden',
        name: 'dtargetprice',
        value: $('#text11')[0].value
    }));
    $fm.append($('<input />', {
        type: 'hidden',
        name: 'dtargetmemo',
        value: $('#text22')[0].value
    }));
    $fm.appendTo(document.body);
    $fm.submit();
    $fm.remove();
}

function calender_func(offset){
    var $fm = $('<form />', {
        method: 'POST',
        action: '${pageContext.request.contextPath}/SavingMoneyList.do'
    });
    $fm.append($('<input />', {
        type: 'hidden',
        name: 'targetonth',
        value: <%= rcvTargetMonth %>
    }));
    $fm.appendTo(document.body);
    $fm.submit();
    $fm.remove();
}

function dispDeleteModal(){
	$('#deleteModal').modal();
	$('#myTable td').on('click',function(){
		var td = $(this)[0];
		var tr = $(this).closest('tr')[0];
		var id=$('#myTable > tbody > tr:nth-child(1) > td:nth-child(1)')[0].textContent;
		var item=$('#myTable > tbody > tr:nth-child(1) > td:nth-child(2)')[0].textContent;
		var price=$('#myTable > tbody > tr:nth-child(1) > td:nth-child(3)')[0].textContent;
		var memo=$('#myTable > tbody > tr:nth-child(1) > td:nth-child(4)')[0].textContent;
		$('#text1')[0].value=price;
		$('#text2')[0].value=memo;
		$('#targetid2')[0].value=id;
	});
}

function dispUpdateModal(){
	$('#updateModal').modal();
	$('#myTable td').on('click',function(){
		var td = $(this)[0];
		var tr = $(this).closest('tr')[0];
		var id=$('#myTable > tbody > tr:nth-child(1) > td:nth-child(1)')[0].textContent;
		var item=$('#myTable > tbody > tr:nth-child(1) > td:nth-child(2)')[0].textContent;
		var price=$('#myTable > tbody > tr:nth-child(1) > td:nth-child(3)')[0].textContent;
		var memo=$('#myTable > tbody > tr:nth-child(1) > td:nth-child(4)')[0].textContent;
		$('#text11')[0].value=price;
		$('#targetprice')[0].value=price;
		$('#text22')[0].value=memo;
		$('#targetmemo')[0].value=memo;
		$('#targetid2')[0].value=id;
	});
}

$(document).ready(function(){
	$("#myTable").tablesorter();
});
$(window).on('load',function(){


	$("#deleteModal").on("hidden.bs.modal", function(){

	});
});

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
    #editbtn{
	  display: inline-block;
	  border-radius: 2px;
	  background-color: green;
	  padding: 1px;
	  text-align: center;
	  color: white;
	  width: 60px;
	}
    #deletebtn{
	  display: inline-block;
	  border-radius: 2px;
	  background-color: red;
	  padding: 1px;
	  text-align: center;
	  color: white;
	  width: 60px;
	}
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
                <th>メモ</th>
                <th>日付</th>
                <th style="width:60px;">編集</th>
                <th style="width:60px;">削除</th>
            </tr>
        </thead>
        <tbody>
        	<% if(itemList.length>0) {%>
        		<% for (Product item : itemList) { %>
	            <tr>
	                <td><%= item.getId() %></td>
	                <td><%= item.getTitle() %></td>
	                <td><%= item.getPrice() %></td>
	                <td><%= item.getMemo() %></td>
	                <td><%= item.getDay() %></td>
	                <td><input type="button" id="editbtn" value="編集" onclick="dispUpdateModal();"></td>
	                <td><input type="button" id="deletebtn" value="削除" onclick="dispDeleteModal();"></td>
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
</html>
<!-- モーダル部分始まり -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">削除</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form id="deleteform" name="myform" action="${pageContext.request.contextPath}/SavingMoneyDelete.do" method="post">
				<div class="modal-body">
					<div class="form-group">
					  	<label for="text1">金額:</label>
					  	<input type="text" id="text1" name="buyamount" class="form-control" disabled="disabled">
					</div>
					<div class="form-group">
					  	<label for="text2">メモ:</label>
					  	<input type="text" id="text2" name="buymemo" class="form-control" disabled="disabled">
					</div>
					<div class="form-group">
					    <input type="hidden" id="targetid" name="targetid" value="">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">閉じる</button>
					<button type="button" class="btn btn-primary" id="delete_btn" onclick="delete_func();">削除</button>
				</div>
			</form>
	  </div>
	</div>
</div>
<!-- モーダル部分終わり -->
<!-- モーダル部分始まり -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">編集</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form id="updateform" name="myform" action="${pageContext.request.contextPath}/SavingMoneyUpdate.do" method="post">
				<div class="modal-body">
					<div class="form-group">
					  	<label for="text1">金額:</label>
					  	<input type="text" id="text11" name="buyamount" class="form-control">
					</div>
					<div class="form-group">
					  	<label for="text2">メモ:</label>
					  	<input type="text" id="text22" name="buymemo" class="form-control">
					</div>
					<div class="form-group">
					    <input type="hidden" id="targetid2" name="targetid2" value="">
					    <input type="hidden" id="targetprice" name="targetprice" value="">
					    <input type="hidden" id="targetmemo" name="targetmemo" value="">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">閉じる</button>
					<button type="button" class="btn btn-primary" id="update_btn" onclick="update_func();">編集</button>
				</div>
			</form>
	  </div>
	</div>
</div>
<!-- モーダル部分終わり -->