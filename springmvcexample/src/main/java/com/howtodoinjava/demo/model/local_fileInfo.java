package com.howtodoinjava.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="local_fileInfo")	
public class local_fileInfo {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int fileId;
	private String fileHash;
	private String fileName;
	private String spiltType;
	private int  totalNum;
	private String plainLocation;
	private String cipherLocation;
	
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFileHash() {
		return fileHash;
	}
	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSpiltType() {
		return spiltType;
	}
	public void setSpiltType(String spiltType) {
		this.spiltType = spiltType;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public String getPlainLocation() {
		return plainLocation;
	}
	public void setPlainLocation(String plainLocation) {
		this.plainLocation = plainLocation;
	}
	public String getCipherLocation() {
		return cipherLocation;
	}
	public void setCipherLocation(String cipherLocation) {
		this.cipherLocation = cipherLocation;
	}
	@Override
	public String toString() {
		return "local_fileInfo [fileId=" + fileId + ", fileHash=" + fileHash + ", fileName=" + fileName + ", spiltType="
				+ spiltType + ", totalNum=" + totalNum + ", plainLocation=" + plainLocation + ", cipherLocation="
				+ cipherLocation + "]";
	}
	
	
}
