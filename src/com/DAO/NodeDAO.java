package com.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.VO.NodeVO;

public class NodeDAO {
	PreparedStatement pst;
	Connection conn;
	ResultSet rs;
	public void getConnection() {
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String dbid = "wcadmin";
		String dbpw = "wcadmin";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,dbid,dbpw);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

	public ArrayList<NodeVO> select(String class_id) {
		getConnection();
		ArrayList<NodeVO> nodeList = new ArrayList<NodeVO>();
		NodeVO node = null;
		try {
			pst = conn.prepareStatement("SELECT * FROM CLASS_TREE_INFO WHERE CLASS_ID=?");
			pst.setString(1, class_id);
			rs = pst.executeQuery();

			while(rs.next()) {
				nodeList.add(new NodeVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodeList;
	}

	public ArrayList<NodeVO> getElders(String classID) {
		getConnection();
		ArrayList<NodeVO> nodeList = new ArrayList<NodeVO>();
		NodeVO node = null;
		try {
			pst = conn.prepareStatement("SELECT * FROM CLASS_TREE_INFO WHERE CLASS_ID=? AND PARENT_ID='0'");
			pst.setString(1, classID);
			rs = pst.executeQuery();

			while(rs.next()) {
				nodeList.add(new NodeVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nodeList;
		
	}

	public int updateNode(String nodeID, String tempPID, String tempSID) {
		int result=0;
		getConnection();
		
		try {
			
			pst = conn.prepareStatement("update class_tree_info set parent_id = ?, sibling_id = ? where note_id = ?");
			pst.setString(1, tempPID);
			pst.setString(2, tempSID);
			pst.setString(3, nodeID);
			result = pst.executeUpdate();
			pst.close();
			conn.close();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}

}
