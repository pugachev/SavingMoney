<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="model.Product" %>
<%@page import="model.UserInfo" %>
<%@page import="java.util.*" %>
<%@ include file="/view/login.jsp" %>
<%@page import="util.ReadProperties" %>
<%@page import="java.text.NumberFormat" %>
<%
	//SavingMoneyListActionからもらうデータ
	UserInfo rcvUinfo = (UserInfo)session.getAttribute("uinfo");
	String rcvID = "'"+rcvUinfo.getUserId()+"'";
	NumberFormat nfCur = NumberFormat.getCurrencyInstance();
	int totalyearsum = rcvUinfo.getDispYearSum();
	int totalsum = rcvUinfo.getDispMonthSum();
	String rcvTargetMonth = rcvUinfo.getDispMonth();
	String rcvTargetYear = rcvUinfo.getDispYear();
	int offset = rcvUinfo.getPresetPageNum();
	boolean isLogIn=false;
	boolean isLogOut=false;
	String fileName="/view/";
	boolean isfile = ReadProperties.isPropertiesFile();
	if(isfile){
		if(ReadProperties.getProperty("addLogInButton").equals("true")){
			isLogIn=true;
		}
		if(ReadProperties.getProperty("addLogoutButton").equals("true")){
			isLogOut=true;
		}
	}
%>
<%@ include file="/view/UserStatusCheck.jsp" %>
<% if(isLogIn){ %>
<%@ include file="/view/login.jsp" %>
<% } %>
<% if(isLogOut){ %>
<%@ include file="/view/logout.jsp" %>
<% } %>
<!DOCTYPE html>
<html class="no-js">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>節約カレンダー</title>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
		<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.minicalendar.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
		<style>
			nav{
				font-size:1.2em;
			}
		</style>
		<script>
		    var tmonth = '';
		    var rcvid = '';
		    var offset ='';
			var contentsarray=[];
			$(document).ready(function(){

			});

			function save_func(){
				if($('#text1').val()==''){
					alert('金額を入力してください');
					return false;
				}

				document.myform.submit();
			}

			  function changeMonth(thisMonth){
				  console.log('clickされたー'+thisMonth);
			  }

			  function detail_func(){
			        var $fm = $('<form />', {
			            method: 'POST',
			            action: '${pageContext.request.contextPath}/SavingMoneyDetail.do'
			        });
			        $fm.append($('<input />', {
			            type: 'hidden',
			            name: 'dtargetid',
			            value: rcvid
			        }));
			        $fm.append($('<input />', {
			            type: 'hidden',
			            name: 'dtargeyear',
			            value: tyear
			        }));
			        $fm.append($('<input />', {
			            type: 'hidden',
			            name: 'dtargemonth',
			            value: tmonth
			        }));
			        $fm.append($('<input />', {
			            type: 'hidden',
			            name: 'doffset',
			            value: offset
			        }));
			        $fm.appendTo(document.body);
			        $fm.submit();
			        $fm.remove();
			  }

			  function detete_func(){
				  	if($('#text1').val()==''){
						alert('金額を入力してください');
						return false;
					}
			        var $fm = $('<form />', {
			            method: 'POST',
			            action: '${pageContext.request.contextPath}/SavingMoneyDelete.do'
			        });
			        $fm.append($('<input />', {
			            type: 'hidden',
			            name: 'dtargetid',
			            value: $('#targetid').val()
			        }));
			        $fm.append($('<input />', {
			            type: 'hidden',
			            name: 'dtargemonth',
			            value: $('#targetmonth').val()
			        }));
			        $fm.appendTo(document.body);
			        $fm.submit();
			        $fm.remove();
			  }

			  function outputfile_func(){
			        var $fm = $('<form />', {
			            method: 'POST',
			            action: '${pageContext.request.contextPath}/SavingMoneyListOutputFileAjax.do'
			        });
			        $fm.append($('<input />', {
			            type: 'hidden',
			            name: 'dtargetid',
			            value: rcvid
			        }));
			        $fm.append($('<input />', {
			            type: 'hidden',
			            name: 'dtargemonth',
			            value: tmonth
			        }));
			        $fm.appendTo(document.body);
			        $fm.submit();
			        $fm.remove();
			  }
			$(window).on('load',function(){
				tyear = <%= rcvTargetYear %>;
			    tmonth = <%= rcvTargetMonth %>;
			    rcvid = <%= rcvID %>;
			    offset = 1;
				$(document).on("click", ".targettable tr", function(){
					var td = $(this)[0];
					var tr = $(this).closest('tr')[0];
					var sitems = $('#selecteditem').children();
					for(var i=0;i<sitems.length;i++){
						if(sitems.eq(i).text()==$(this).children().eq(1).text()){
						    // 全ての選択を外す
						    $("#selecteditem option").attr("selected", false);
						    //選択項目を変更する
						    $("#selecteditem").val(sitems.eq(i).val());
						    //金額をセットする
						    $('[name="buyamount"]').val($(this).children().eq(2).text());
						    //メモをセットする
						    /* $('[name="buymemo"]').val($(this).children().eq(2).text()); */
						    //IDをセットする
						    $('#targetid').val($(this).children().eq(0).text());

							break;
						}
					}
				});

			   	/* var headarray =[' ','項目','金額']; */
			   	var headarray =['金額'];
		   		contentsarray=[];
			 	$('#mini-calendar').miniCalendar();
				$('#targetTable td').on('click',function(){
					var td = $(this)[0];
					var tr = $(this).closest('tr')[0];
					/* console.log('$(this).text()='+$(this).text()); */
					var tmparray = $(this).text().split(' ');
					console.log('tmparray='+tmparray);
					$('#targetdate').val(tmparray[0]);
					if(tmparray!=null && tmparray.length>=2){
						//for(var j=1;j<tmparray.length;j++){
							//var innertmp =tmparray[j].split(':');
							//console.log('innertmp='+innertmp);
							var rearray =[];
							//rearray.push(innertmp[0]);
 							/* rearray.push(tmparray[1]); */
							rearray.push(tmparray[2]);
							contentsarray.push(rearray);
						//}

					}

					$('#exampleModal').modal();

					if($('table.table-bordered.table-striped.targettable')!=null){
						$('table.table-bordered.table-striped.targettable').remove();
					}

					if(tmparray.length>=2){
					   	// table要素を生成
						var table = document.createElement('table');
						$('#exampleModal .modal-body table').addClass('table table-bordered table-striped targettable');
						// tr(横)部分のループ
						/* for (var i = 0; i < tmparray.length; i++) { */
						// tr要素を生成
						var tr = document.createElement('tr');
			            // th要素を生成
			            var th = document.createElement('th');
			            // th要素内にテキストを追加
			            th.textContent = headarray[0];
			            // th要素をtr要素の子要素に追加
			            tr.appendChild(th);
			            // td要素を生成
			            var td = document.createElement('td');
			            // td要素内にテキストを追加
			            td.textContent = contentsarray[0][0];
			            console.log(contentsarray[0][0]);
			            // td要素をtr要素の子要素に追加
			            tr.appendChild(td);
					    // tr要素をtable要素の子要素に追加
					    table.appendChild(tr);

						// 生成したtable要素を追加する
						$('table.table-bordered.table-striped.targettable').remove()
						$('#exampleModal .modal-body')[0].appendChild(table);
						$('#exampleModal .modal-body table').addClass('table table-bordered table-striped targettable');
					}
				});

				$("#exampleModal").on("hidden.bs.modal", function(){

					//配列クリア
					contentsarray=[];

				    // 全ての選択を外す
				    $("#selecteditem option").attr("selected", false);
				    //選択項目を変更する
				    $("#selecteditem").val(1);

				    //金額をクリアする
				    $('[name="buyamount"]').val('');
				    //メモをクリアする
				    $('[name="buymemo"]').val('');
				    //IDをクリアする
				    $("#targetid").val('');
				    $("#targetdate").val('');

					console.log('クリアしました');
				});
			});
		</script>
	</head>
	<body>
		<form action="${pageContext.request.contextPath}/UserLogout.do">
			<input type="hidden" id="hiddenID" name="hiddenID" value="<%= rcvID %>"/>
			<input type="submit" id="logoutbtn" style="display:none"/>
		</form>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
		    <a class="navbar-brand" href="#">節約カレンダー</a>
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		    <% if(isLogIn){ %>
		      <li class="nav-item active">
		        <a class="btn btn-primary" style="margin:3px;" href="#" onclick="login();">ログイン <span class="sr-only">(current)</span></a>
		      </li>
		    <% } %>
		    <% if(isLogOut){ %>
		      <li class="nav-item active">
		        <a class="btn btn-danger" style="margin:3px;" href="#" onclick="logout();">ログアウト <span class="sr-only">(current)</span></a>
		      </li>
		    <% } %>
		      <li class="nav-item active">
		        <a class="btn btn-primary" style="margin:3px;" href="#" onclick="detail_func();">詳細画面 <span class="sr-only">(current)</span></a>
		      </li>
		      <li class="nav-item active">
		        <a class="btn btn-primary" style="margin:3px;" href="#" onclick="outputfile_func();">CSV出力 <span class="sr-only">(current)</span></a>
		      </li>
		      <li class="nav-item active">
		        <a class="btn btn-primary" style="margin:3px;" href="#" onclick="detail_func();">CSV読込 <span class="sr-only">(current)</span></a>
		      </li>
		    </ul>
		  </div>
		  <span class="navbar-text">
	          年合計:<% out.print(nfCur.format(totalyearsum)); %>&nbsp;月合計:<% out.print(nfCur.format(totalsum)); %>
		  </span>
		  <span class="navbar-text">

		  </span>
		</nav>
	    <div class="container-fluid">
			<div id="wrap">
				<div id="mini-calendar"></div>
			</div>
		</div>
	</body>
	<script>
		$(window).load(function() {
		    for(var j=1;j<=31;j++){
		    	var tmpday = '#calender-id'+j+' .calender-label';
		    	/* console.log('★ tmpday='+tmpday); */
		    	if($(tmpday)[0]!=null && $(tmpday)[0].textContent!=''){
		    		var tmpCalenderArray = $(tmpday)[0].textContent.split(' ');
		    		if(parseInt(tmpCalenderArray[2])>3000){
		    			$(tmpday).css("background-color", "#FF0000");

		    		}
		    	}
		    }
		});
	</script>
</html>
<!-- モーダル部分始まり -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">タイトル</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
				<form id="regform" name="myform" action="${pageContext.request.contextPath}/SavingMoneyRegist.do" method="post">

					<div class="modal-body">
						<div class="form-group targetTable">
						</div>
						<div class="form-group">
							<label for="select1a">品目:</label>
							<select id="selecteditem" name="selecteditem" class="form-control">
							  <option value="1">食費</option>
							  <option value="2">外食費</option>
							  <option value="3">交通費</option>
							  <option value="4">交際費</option>
							  <option value="5">ガソリン代</option>
							  <option value="6">税金</option>
							  <option value="7">日用品</option>
							  <option value="8">生活費</option>
							  <option value="9">Amazon</option>
							</select>
						</div>
						<div class="form-group">
						  	<label for="text1">金額:</label>
						  	<input type="text" id="text1" name="buyamount" class="form-control">
						</div>
						<div class="form-group">
						  	<label for="text2">メモ:</label>
						  	<input type="text" id="text2" name="buymemo" class="form-control">
						</div>
						<div class="form-group">
						    <input type="hidden" id="targetid" name="targetid" value="">
						    <input type="hidden" id="targetmonth" name="targetmonth" value="<%= rcvTargetMonth %>">
							<input type="hidden" id="targetdate" name="targetdate" value="">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">閉じる</button>
<!-- 						<button type="button" class="btn btn-danger" onclick="detete_func();">削除</button> -->
						<button type="button" class="btn btn-primary" id="save_update" onclick="save_func();">保存</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- モーダル部分終わり -->
</div><!-- /container -->
