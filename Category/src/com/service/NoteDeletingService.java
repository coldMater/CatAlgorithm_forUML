package com.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.NodeDAO;
import com.VO.NodeVO;


@WebServlet("/NoteDeletingService")
public class NoteDeletingService extends HttpServlet {
	ArrayList<NodeVO> nodeList;
	ArrayList<NodeVO> offsprings;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//** Note Delete 의 원리 ** // 
			//삭제할 노드가 속한 classID를 받아온다. 
			//classID 를 통해 nodeList 를 불러온다. 
			//삭제할 노드의 nodeID 를 받아온다. 
			//삭제할 노드의 parentID 를 받아온다. 
			//삭제할 노드의 삭제할 nodeID를 기준으로 노드를 빼낸다.(노드 빼내기는 NoteMovingService 의 이동 전 준비과정과 동일하다.) 
				//다음 sibling을 위로 올려주는 과정이다.
					//다음 sibling 이 없을 때는 이 과정이 생략된다.
					//다음 sibling 의 sibling 값을 삭제될 노드가 갖고 있던 sibling 값으로 대체한다.(##UPDATE##) 
			//해당 노드의 parent 값을 -1 로 바꿔준다. (##UPDATE##)

		
		String classID = request.getParameter("classID");
		String nodeID = request.getParameter("nodeID"); //This node Will be deleted.

		NodeDAO nodeDAO = new NodeDAO();
		nodeList = nodeDAO.select(classID);
		
		NodeVO nextSibling = getNodeBySibling(nodeID); // nodeID 를 sibling 값으로 갖는 node 를 검색하여 반환한다. 
		NodeVO nodeVO = getNodeByID(nodeID);
		
		if(nodeVO==null) {
			System.out.println("노드(ID:"+nodeID+")가 노드가 존재하지 않는다.");
			
		} else {
			if(nextSibling!=null) {
				String tempPID = nodeVO.getParentID();
				String tempSID = nodeVO.getSiblingID();
				
				String nextSiblingID = nextSibling.getNoteID();
				//다음 형제가 있을 경우 DAO 에 접근하여 내용을 UPDATE 해줌
				//nextSibling 의 p와 s 값은 각각 tempPID, tempSID 가 되어야 한다.
				System.out.println("next Sibling 인 노드의 P, S를 변경한다.");
				System.out.println("SQL : next Sibling인 "+nextSiblingID+"의 P : "+tempPID+" S : "+tempSID+"로 변경");
				nodeDAO.updateNode(nextSiblingID,tempPID,tempSID);
				//SQL = update class_tree_info set parent_id ='tempPID', sibling_id = 'tempSID' where =  note_id='nextSiblingID';
			} else {
				System.out.println("다음 노트가 없으므로 다음 노트의 S를 바꾸지 않는다.");
			}
		
			System.out.println("ID : "+ nodeID +"인 노드의 삭제를 진행한다.");
			//노드 빼내기(삭제) : 해당 노드의 pid 변경
			System.out.println("SQL : "+nodeID+"의 P : -1, S : -1 값을 입력");
			nodeDAO.updateNode(nodeID,"-1","-1");	
		}
		
		System.out.println("=====NoteDeletingService.java/Servlet=====");
		request.setAttribute("classIDnow",classID);
		RequestDispatcher dispatcher = request.getRequestDispatcher("NoteLoadingService");
		dispatcher.forward(request, response);
		
	}
	
	//Method//
	
	public NodeVO getLastSiblingByParent(String parentID){
		NodeVO lastNode = null;
		for(int i = 0;i<nodeList.size();i++) {
			if (nodeList.get(i).getParentID().equals(parentID) && nodeList.get(i).getSiblingID().equals("0")) {				
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

	public ArrayList<NodeVO> getOffsprings(String parentID){
		
		NodeVO nodeCursor = null;
		for (int i = 0; i < nodeList.size(); i++) {
			if(nodeList.get(i).getParentID().equals(parentID)){
				nodeCursor = nodeList.get(i);
				offsprings.add(nodeCursor);
				getOffsprings(nodeCursor.getNoteID());
				while(true) {
					if(getNodeBySibling(nodeCursor.getNoteID())!=null) {
						nodeCursor = getNodeBySibling(nodeCursor.getNoteID());
						offsprings.add(nodeCursor);
						getOffsprings(nodeCursor.getNoteID());
					} else {
						break;
					}
					
				}
				
				
			} else {
				
			}
		}
		
		return offsprings;
	}
}
