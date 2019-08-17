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
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
		<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
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
					console.log('td:'+td.cellIndex);
					console.log('tr:'+tr.rowIndex);
					console.log($(this).text());
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
