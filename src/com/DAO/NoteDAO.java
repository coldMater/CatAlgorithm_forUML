package com.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteDAO {
	PreparedStatement pst;
	PreparedStatement pst2;
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
	public void insertNote(String title, String content, String siblingID, String classID) {
		getConnection();
		
		try {
			System.out.println(title);
			System.out.println(content);
			pst = conn.prepareStatement("insert into note values(SEQ_NOTE.NEXTVAL,?,?,sysdate)");
			pst.setString(1, title);
			pst.setString(2, content);
			int cnt = pst.executeUpdate();
			
			pst2 = conn.prepareStatement("insert into class_tree_info values(SEQ_NOTE.CURRVAL,0,?,?)");
			pst2.setString(1, siblingID);
			pst2.setString(2, classID);
			int cnt2 = pst2.executeUpdate();
			
			System.out.println(cnt);
			System.out.println(cnt2);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
