<%@page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!doctype html>
<html lang="en">
  <head>
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登録画面</title>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <!-- Custom styles for this template -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css">
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script>
		function toTop(){
			document.location.href = "./index.jsp";
		}
	</script>
  </head>

  <body class="text-center">
    <form class="form-signin">
      <img class="mb-4" src="${pageContext.request.contextPath}/images/fukurou.jpg" alt="" width="72" height="72">
      <h1 class="h3 mb-3 font-weight-normal">登録画面</h1>
      <label for="inputEmail" class="sr-only">Email address</label>
      <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
      <button class="btn btn-lg btn-success btn-block" type="submit">新規登録</button>
      <button class="btn btn-lg btn-warning btn-block" onclick="toTop();">キャンセル</button>
      <p class="mt-5 mb-3 text-muted">&copy; 2019-2020</p>
    </form>
  </body>
</html>