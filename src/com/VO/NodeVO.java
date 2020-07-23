package com.VO;

public class NodeVO {
	private String NoteID;
	private String ParentID;
	private String SiblingID;
	private String ClassID;

	public NodeVO(String noteID, String parentID, String siblingID, String classID) {
		super();
		NoteID = noteID;
		ParentID = parentID;
		SiblingID = siblingID;
		ClassID = classID;
	}

	public String getNoteID() {
		return NoteID;
	}

	public void setNoteID(String noteID) {
		NoteID = noteID;
	}

	public String getParentID() {
		return ParentID;
	}

	public void setParentID(String parentID) {
		ParentID = parentID;
	}

	public String getSiblingID() {
		return SiblingID;
	}

	public void setSiblingID(String siblingID) {
		SiblingID = siblingID;
	}

	public String getClassID() {
		return ClassID;
	}

	public void setClassID(String classID) {
		ClassID = classID;
	}

	
}
