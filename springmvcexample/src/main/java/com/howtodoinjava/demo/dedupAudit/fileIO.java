package com.howtodoinjava.demo.dedupAudit;

public interface fileIO {
	public void spilt(String filePath,int blocksize);
	public int getBlockNum(String filePath);
	public String[] getBlockPath(String filePath);
	
}
