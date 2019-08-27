<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="model.Product" %>
<%@page import="java.util.*" %>
<%
	String rcvTargetMonth = (String)session.getAttribute("targetMonth");
	System.out.println("rcvTargetMonth="+rcvTargetMonth);
%>
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
		    var tmonth = <%= rcvTargetMonth %>;
			var contentsarray=[];
			$(document).ready(function(){

			});

			  function changeMonth(thisMonth){
				  console.log('clickされたー'+thisMonth);
			  }

			  function detete_func(){
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

			$(window).on('load',function(){
				console.log('tmonth=' + tmonth);
				$(document).on("click", ".targettable tr", function(){
					var td = $(this)[0];
					var tr = $(this).closest('tr')[0];
					console.log($(this).children().eq(0).text()+'  '+$(this).children().eq(1).text()+'  '+$(this).children().eq(2).text());
					var sitems = $('#selecteditem').children();
					for(var i=0;i<sitems.length;i++){
						if(sitems.eq(i).text()==$(this).children().eq(1).text()){
							console.log(sitems.eq(i).val()+'  ' + sitems.eq(i).text());
						    // 全ての選択を外す
						    $("#selecteditem option").attr("selected", false);
						    //選択項目を変更する
						    $("#selecteditem").val(sitems.eq(i).val());

						    //金額をセットする
						    $('[name="buyamount"]').val($(this).children().eq(2).text());
						    //IDをセットする
						    $('#targetid').val($(this).children().eq(0).text());

							break;
						}
					}
				});

			   	var headarray =['','ID','項目','値段'];
		   		contentsarray=[];
			 	$('#mini-calendar').miniCalendar();
				$('#targetTable td').on('click',function(){
					var td = $(this)[0];
					var tr = $(this).closest('tr')[0];
					var tmparray = $(this).text().split(' ');
					$('#targetdate').val(tmparray[0]);
					if(tmparray!=null && tmparray.length>=2){
						for(var j=1;j<tmparray.length;j++){
							var innertmp =tmparray[j].split(':');
							var rearray =[];
							rearray.push(innertmp[0]);
							rearray.push(innertmp[1]);
							rearray.push(innertmp[2]);
							contentsarray.push(rearray);
						}
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
						for (var i = 0; i < tmparray.length; i++) {
						    // tr要素を生成
						    var tr = document.createElement('tr');
						    // th・td(縦)部分のループ
						    for (var j = 0; j <3; j++) {
						        // 1行目のtr要素の時
						        if(i === 0) {
						            // th要素を生成
						            var th = document.createElement('th');
						            // th要素内にテキストを追加
						            th.textContent = headarray[j];
						            // th要素をtr要素の子要素に追加
						            tr.appendChild(th);
						        }else{
						            // td要素を生成
						            var td = document.createElement('td');
						            // td要素内にテキストを追加
						            td.textContent = contentsarray[i-1][j];
						            console.log(contentsarray[i-1][j]);
						            // td要素をtr要素の子要素に追加
						            tr.appendChild(td);
								}
						    }
						    // tr要素をtable要素の子要素に追加
						    table.appendChild(tr);
						}

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

				    //金額をセットする
				    $('[name="buyamount"]').val('');

				    //IDをクリアする
				    $("#targetid").val('');
				    $("#targetdate").val('');

					console.log('クリアしました');
				});
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
						    <input type="hidden" id="targetid" name="targetid" value="">
							<input type="hidden" id="targetdate" name="targetdate" value="">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">閉じる</button>
						<button type="button" class="btn btn-danger" onclick="detete_func();">削除</button>
						<button type="submit" class="btn btn-primary" >保存編集</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- モーダル部分終わり -->
</div><!-- /container -->
