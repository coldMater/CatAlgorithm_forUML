package com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.NodeDAO;
import com.DAO.NoteDAO;
import com.VO.NodeVO;

@WebServlet("/NoteMakingService")
public class NoteMakingService extends HttpServlet {
	ArrayList<NodeVO> nodeList;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=euc-kr");
		
		String classID = (String)request.getParameter("classID");
		String title = (String)request.getParameter("note_title");
		String content = (String)request.getParameter("note_content");
		
		NodeDAO dao = new NodeDAO();
		nodeList = dao.getElders(classID);
		
		
		NoteDAO noteDao = new NoteDAO();
		noteDao.insertNote(title,content,getLastSibling().getNoteID(), classID);
		
		System.out.println("=====NoteMakingService.java/Servlet=====");
		request.setAttribute("classIDnow",classID);
		RequestDispatcher dispatcher = request.getRequestDispatcher("NoteLoadingService");
		dispatcher.forward(request, response);
	}

	public NodeVO getLastSibling(){
		NodeVO lastNode = null;
		for(int i = 0;i<nodeList.size();i++) {
			if (nodeList.get(i).getSiblingID().equals("0")) {
				lastNode = nodeList.get(i);
				String brotherID = lastNode.getNoteID();
				//해당 노트의 ID 를 가지는 노드가 있는지
				while (true) {
					if (getNodeBySibling(brotherID) != null) {
						lastNode = getNodeBySibling(brotherID);
						brotherID = getNodeBySibling(brotherID).getNoteID();
					} else {
						break;
					}
				}
			}
	
		}
		return lastNode;
	}
	
	public NodeVO getNodeBySibling(String siblingID) {
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).getSiblingID().equals(siblingID)) {
				return nodeList.get(i);
			}
		}
		return null;
	}

	public NodeVO getNodeByID(String ID) {
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).getNoteID().equals(ID)) {
				return nodeList.get(i);
			}
		}
		return null;
	}
}
