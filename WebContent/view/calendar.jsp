<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE html>
<html class="no-js">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Flexible Calendar</title>
		<meta name="description" content="Flexible Calendar with jQuery and CSS3" />
		<meta name="keywords" content="responsive, calendar, jquery, plugin, full page, flexible, javascript, css3, media queries" />
		<meta name="author" content="Codrops" />
		<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/calendar.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/custom_1.css" />
		<script src="${pageContext.request.contextPath}/js/modernizr.custom.63321.js"></script>


	</head>
	<body>
		<div class="container">
			<div id="icesword" class="custom-calendar-wrap custom-calendar-full">
				<div class="custom-header clearfix">
					<h2>節約カレンダー</h2>
					<h3 class="custom-month-year">
						<span id="custom-month" class="custom-month"></span>
						<span id="custom-year" class="custom-year"></span>
						<nav>
							<span id="custom-prev" class="custom-prev"></span>
							<span id="custom-next" class="custom-next"></span>
							<span id="custom-current" class="custom-current" title="Got to current date"></span>
						</nav>
					</h3>
				</div>
				<div id="calendar" class="fc-calendar-container"></div>
			</div>

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
						<div class="modal-body">これは モーダルダイアログの本文です。</div>
						<p></p>日
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">閉じる</button>
							<button type="button" class="btn btn-primary">保存する</button>
						</div>
					</div>
				</div>
			</div>
			<!-- モーダル部分終わり -->
		</div><!-- /container -->
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.calendario.js"></script>
		<script type="text/javascript">
			$(function() {
				var codropsEvents = {
						"07-20-2019": "テスト投稿1"
						}
				var cal = $( '#calendar' ).calendario( {
						onDayClick : function( $el, $contentEl, dateProperties ) {
							// for( var key in dateProperties ) {
							// 	console.log( key + ' = ' + dateProperties[ key ] );
							// 	var tmp = '.fc-item-'+dateProperties[ key ];
								if(dateProperties[ 'day' ]==$el.find('.fc-date')[0].textContent){
									var tmp = '.fc-item-'+dateProperties[ 'day' ];
									/* $(tmp)[0].append('雑草取り機 2000円'); */
/* 							        $("#overlay, #modal").addClass("active");

							        $("#close, #overlay").on("click", function() {
							            $("#overlay, #modal").removeClass("active");
							            return false;
							        }); */
									$('#exampleModal').modal();
								}
							// }
						},
						caldata : codropsEvents
					} ),
					$month = $( '#custom-month' ).html( cal.getMonthName() ),
					$year = $( '#custom-year' ).html( cal.getYear() );

				$( '#custom-next' ).on( 'click', function() {
					cal.gotoNextMonth( updateMonthYear );
				} );
				$( '#custom-prev' ).on( 'click', function() {
					cal.gotoPreviousMonth( updateMonthYear );
				} );
				$( '#custom-current' ).on( 'click', function() {
					cal.gotoNow( updateMonthYear );
				} );

				function updateMonthYear() {
					$month.html( cal.getMonthName() );
					$year.html( cal.getYear() );
				}

				// you can also add more data later on. As an example:
				/*
				someElement.on( 'click', function() {

					cal.setData( {
						'03-01-2013' : '<a href="#">testing</a>',
						'03-10-2013' : '<a href="#">testing</a>',
						'03-12-2013' : '<a href="#">testing</a>'
					} );
					// goes to a specific month/year
					cal.goto( 3, 2013, updateMonthYear );

				} );
				*/
			});
		</script>
	</body>

</html>
