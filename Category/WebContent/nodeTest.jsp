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
	�÷����� ����� ��� <br />
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
				���� ��ȸ�ϱ�
				<form action="NoteLoadingService">
					<input type="text" name="classID" value="1" /> <input
						type="submit" value = "��ȸ"/>
				</form>

			</td>
			<td style = "width:300px;">
				��� �̵��ϱ�
				<form action="NoteMovingService">
					<input type="text" name="classID"
						value='<%=request.getAttribute("classIDnow")%>'
						placeholder="class ID" /><br /> <input type="text" name="nodeID"
						/  placeholder="Node ID"> �� <input type="text"
						name="parentID" placeholder="Parent ID"><br /> <input
						type="submit" value = "�̵�"/>
				</form>

			</td>
			<td style = "width:300px;">
				��Ʈ(���) �����ϱ�

				<form action="NoteMakingService">

					<input type="text" name="classID" id="demo-name"
						value='<%=request.getAttribute("classIDnow")%>'
						placeholder="class ID" /><br /> ����: <input type="text"
						name="note_title" placeholder="Title"><br /> ����: <input
						type="text" name="note_content" placeholder="Content"><br />

					<input type="submit" value="��Ʈ �����ϱ�" />
				</form>

			</td>
			<td style = "width:300px;">
				��Ʈ(���) �����ϱ�

				<form action="NoteDeletingService">

						<input type="text" name="classID" id="demo-name"
						value='<%=request.getAttribute("classIDnow")%>'
						placeholder="class ID" /><br /> 
					������ ��� : <input type="text"
						name="nodeID" placeholder="Node ID"><br /> 

					<input type="submit" value="����" />
				</form>

			</td>
		</tr>
	</table>



</body>
</html>