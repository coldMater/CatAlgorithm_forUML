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

@WebServlet("/NoteMovingService")
public class NoteMovingService extends HttpServlet {
	ArrayList<NodeVO> nodeList;
	ArrayList<NodeVO> offsprings;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=euc-kr");
	//////////////////////////
	// **** 자식으로 추가하기  ****//
	//////////////////////////

		
		//현재 작업중인 클래스
		String classID = (String)request.getParameter("classID");
		//이동할 노드 아이디 
		String nodeID = (String)request.getParameter("nodeID");
		//이동시킬 부모노드 아이디 
		String parentID = (String)request.getParameter("parentID");
		
		System.out.println(nodeID+"를 "+parentID+"를 부모로 갖는 그룹으로 이동하는 프로세스 시작");
		
		NodeDAO nodeDAO = new NodeDAO();
		nodeList = nodeDAO.select(classID);
		
		//이동할 노드(객체)
		NodeVO nodeVO = null;
		nodeVO = getNodeByID(nodeID);
		
		String tempPID = "0";
		String tempSID = "0";
				
		
		if(nodeVO != null) {
			tempPID = nodeVO.getParentID();
			tempSID = nodeVO.getSiblingID();
		}
			
//////////이동 전 검사(이동하려는 곳이 해당 노드의 자손 노드 중 하나인지)
		offsprings = new ArrayList<NodeVO>();
		
		boolean isOffspring = false;
		ArrayList<NodeVO> offspring = getOffsprings(nodeID);
		for (int i = 0; i < offspring.size(); i++) {
			if(parentID.equals(offspring.get(i).getNoteID())) {
				//이동하려는 곳이 내 자식의 노드일 때
				isOffspring = true;
			}
		}


//////////이동 전 세팅
		if(isOffspring) {
			System.out.println("입력오류 : 이동하려는 곳이 내 자식이다.");
		} else if (nodeVO==null && !parentID.equals("0")){
			System.out.println(nodeVO==null);
			System.out.println(parentID!="0");
			System.out.println("입력오류 : 이동을 위해 입력한 ID 를 갖는 Node가 없다.");
		} else if ((getNodeByID(parentID)==null && !parentID.equals("0"))){
			System.out.println(getNodeByID(parentID)==null);
			System.out.println(parentID!="0");
			System.out.println((getNodeByID(parentID)==null && !parentID.equals("0")));
			System.out.println("입력오류 : 입력한 Parent ID 를 갖는 Node가 없다.");
		} else if (nodeID.equals(parentID)){
			System.out.println("입력오류 : 입력한 ID 와 Parent ID 가 동일하다. ");
		} else {
			//이동하기 전 이동 전 위치에서 이동할 노트의 다음 노트가 있는지
			NodeVO nextSibling = getNodeBySibling(nodeID);
			if(nextSibling!=null) {
				String nextSiblingID = nextSibling.getNoteID();
				//다음 형제가 있을 경우 DAO 에 접근하여 내용을 UPDATE 해줌
				//nextSibling 의 p와 s 값은 각각 tempPID, tempSID 가 되어야 한다.
				System.out.println("next Sibling인 "+nextSiblingID+"의 P : "+tempPID+" S : "+tempSID+"로 변경");
				nodeDAO.updateNode(nextSiblingID,tempPID,tempSID);
				//SQL = update class_tree_info set parent_id ='tempPID', sibling_id = 'tempSID' where =  note_id='nextSiblingID';
			} else {
				System.out.println("다음 노트가 없으므로 다음 노트의 S를 바꾸지 않는다.");
			}
			
			//현재 노드를 이동시키기
			//이동 될 노드의 부모 
			NodeVO groupLastSibling = getLastSiblingByParent(parentID);
			
			if(groupLastSibling!=null) {
				//자식이 있다. 그 자식을 검색한다. 
				System.out.println("마지막 노드 ID : "+groupLastSibling.getNoteID());
				//마지막 노드를 형으로 갖도록 이동될 노드의 Sibling 값을 세팅
					//노트에 혼자 있었을 때는 변경이 이루어지면 안된다. 
					System.out.println("이동할 노드 : " + nodeID + "  비교할 노드 : " + groupLastSibling.getNoteID());	
					if(nodeID.equals(groupLastSibling.getNoteID())) {
						//이동할 노드가 혼자 있으므로 이동할 노드와 비교할 노드가 같다.
						System.out.println("교환 대상 노드가 자신이기 때문에 노드번호가 같다. 따라서 변경하지 않는다.");	
					} else {
						System.out.println(nodeVO.getNoteID()+"의  Parent는 0 Sibling을"+groupLastSibling.getNoteID()+"로 변경");		
						nodeDAO.updateNode(nodeVO.getNoteID(), "0", groupLastSibling.getNoteID());
					}
			} else {
				//마지막 자식이 Null 이므로 해당 부모는 자식이 없다.   
				System.out.println("해당 부모 "+parentID+"는 자식이 없다.");
				System.out.println(nodeID+"의 P : "+parentID+"  S : 0 로 변경");
				nodeDAO.updateNode(nodeID, parentID, "0");
				//현재 노드의 Parent 를 현재 Parent 값으로, Sibling 을 0으로 세팅한다. 
			}

		}
		
		System.out.println("=====NoteMovingService.java/Servlet=====");
		request.setAttribute("classIDnow",classID);
		RequestDispatcher dispatcher = request.getRequestDispatcher("NoteLoadingService");
		dispatcher.forward(request, response);

		
		//////////////////////////
		// **** 동생으로 추가하기  ****//
		//////////////////////////
		
		//형으로 추가하기(추후 구현)
	}
	
	
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
