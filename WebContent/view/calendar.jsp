<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="model.Product" %>
<%@page import="java.util.*" %>

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
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.minicalendar.js"></script>
		<style>
		</style>
		<script>
			$(document).ready(function(){

			});

			$(window).on('load',function(){
			 	$('#mini-calendar').miniCalendar();
				$('#targetTable td').on('click',function(){
					var td = $(this)[0];
					var tr = $(this).closest('tr')[0];
					//console.log('td:'+td.cellIndex);
					//console.log('tr:'+tr.rowIndex);
					//console.log($(this).text().slice( 0, 2));
					$('#exampleModal').modal();
					//var targetDay = "'#calender-id" + $(this).text() + " .calendar-labels'";
					//console.log($('#calender-id'+$(this).text().slice( 0, 2)+' .calendar-labels')[0].children.length);
					var datalen = $('#calender-id'+$(this).text().slice( 0, 2)+' .calendar-labels')[0].children.length;
					$('#regitemlist')[0].textContent='';
					for(var i=0;i<datalen;i++){
						//$('#calender-id'+$(this).text().slice( 0, 2)+' .calendar-labels')[0].children[i].textContent;
						console.log($('#calender-id'+$(this).text().slice( 0, 2)+' .calendar-labels')[0].children[i].textContent);
						$('#regitemlist').append($('#calender-id'+$(this).text().slice( 0, 2)+' .calendar-labels')[0].children[i].textContent);
						$('#regitemlist').append('\n');
					}
					//console.log($(targetDay)[0].children.length);
				});
				//$('#calender-id12 .calendar-labels')[0].children.length
				//$('#calender-id12 .calendar-labels')[0].children[0].textContent
			});
		</script>
	</head>
	<body>
		<div id="wrap">
			<div id="mini-calendar"></div>
		</div>
	</body>
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
				<form action="${pageContext.request.contextPath}/SavingMoneyRegist.do" method="post">
					<div class="modal-body">
						<div class="form-group shadow-textarea">
						  <label for="exampleFormControlTextarea6">登録データ一覧</label>
						  <textarea class="form-control z-depth-1" id="regitemlist" rows="6" placeholder="登録データ一覧"></textarea>
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
							</select>
						</div>
						<div class="form-group">
						  	<label for="text1">金額:</label>
						  	<input type="text" id="text1" name="buyamount" class="form-control">
						</div>
						<div class="form-group">
							<input type="hidden" id="targetdate" name="targetdate" value="">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">閉じる</button>
						<button type="submit" class="btn btn-primary" >保存する</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- モーダル部分終わり -->
</div><!-- /container -->
