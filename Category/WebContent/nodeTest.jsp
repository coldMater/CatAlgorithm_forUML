<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="class_assets/css/main.css" />
<style>
fieldset {
	
}
;
</style>
</head>
<body>
	플랫폼에 적용된 모습 <br />
	<iframe src="listWithStyle.jsp"
					frameborder="0"
					style="position: absolute; height: 100%; width: 23%;"></iframe>
	<table style = "position:absolute; left:400px; font-size:15px;">
		<tr>
			<td style="width:500px;"><div id="main">
					<%
						String list = (String) request.getAttribute("list");
					%>
					<%
						session.setAttribute("list_sess", list);
					%>
					<%=list%></div></td>

			<td style = "width:300px;">
				목차 조회하기
				<form action="NoteLoadingService">
					<input type="text" name="classID" value="1" /> <input
						type="submit" value = "조회"/>
				</form>

			</td>
			<td style = "width:300px;">
				노드 이동하기
				<form action="NoteMovingService">
					<input type="text" name="classID"
						value='<%=request.getAttribute("classIDnow")%>'
						placeholder="class ID" /><br /> <input type="text" name="nodeID"
						/  placeholder="Node ID"> → <input type="text"
						name="parentID" placeholder="Parent ID"><br /> <input
						type="submit" value = "이동"/>
				</form>

			</td>
			<td style = "width:300px;">
				노트(노드) 생성하기

				<form action="NoteMakingService">

					<input type="text" name="classID" id="demo-name"
						value='<%=request.getAttribute("classIDnow")%>'
						placeholder="class ID" /><br /> 제목: <input type="text"
						name="note_title" placeholder="Title"><br /> 내용: <input
						type="text" name="note_content" placeholder="Content"><br />

					<input type="submit" value="노트 생성하기" />
				</form>

			</td>
			<td style = "width:300px;">
				노트(노드) 삭제하기

				<form action="NoteDeletingService">

						<input type="text" name="classID" id="demo-name"
						value='<%=request.getAttribute("classIDnow")%>'
						placeholder="class ID" /><br /> 
					삭제할 노드 : <input type="text"
						name="nodeID" placeholder="Node ID"><br /> 

					<input type="submit" value="삭제" />
				</form>

			</td>
		</tr>
	</table>



</body>
</html>