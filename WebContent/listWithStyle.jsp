<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="class_assets/css/main.css" />
</head>
<body>
	<div id="warpper">
		<div id="sidebar" class="inactive">
			<div class="inner">
				<nav id="menu"> 
				<%String list = (String) session.getAttribute("list_sess");%>
				<%=list%> </nav>

			</div>
		</div>
	</div>
	<script src="class_assets/js/jquery.min.js"></script>
	<script src="class_assets/js/skel.min.js"></script>
	<script src="class_assets/js/util.js"></script>
	<script src="class_assets/js/main.js"></script>
</body>
</html>