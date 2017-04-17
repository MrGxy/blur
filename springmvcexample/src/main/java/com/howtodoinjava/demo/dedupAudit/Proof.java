package com.howtodoinjava.demo.dedupAudit;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.howtodoinjava.demo.dedupAudit.IO;

import it.unisa.dia.gas.jpbc.Element;



public class Proof {
	
	
	//证据存放在Map中，由<块索引，对应的块签名>构成。
	public HashMap<Long,Element> proof=new HashMap<Long,Element>();
	
	
	
	public KeyPara keyPara;
	public String blockParentPath;
	
	
	/*
	 * 通过计算得出文件证据。
	 * KeyParaPath：初始化密钥对象。
	 */
	public Proof(String KeyParaPath,String blockParentPath){
		
		Utils.iniPairing();
		keyPara=new KeyPara(KeyParaPath);			//初始化密钥参数
		this.blockParentPath=blockParentPath;
	
	}
	
	
	/*
	 * 通过序列化文件得读取文件证据。
	 * 
	 */
	public Proof(String path){
		Utils.iniPairing();
		read(path);
		
	}
	
	/**
	 * 
	 * @param blockNum					文件块总数
	 * @param blockParentPath			文件块的父路径
	 * @throws Exception
	 */
	public  void ini(int blockNum) throws Exception {
		// TODO Auto-generated method stub
		
		Element x ; 
		Element u;
		
		
		
		Element[] h,m,sig;
		h=new Element[blockNum];
		m=new Element[blockNum];
		sig=new Element[blockNum];
		
		
		
		x=keyPara.private_key.getImmutable();	
		u=keyPara.public_key.get("u").getImmutable();
		
		
		
		String[] blockDir=IO.listSubFile(blockParentPath);
		
		int j;
		for (int i = 0; i <blockNum ; i++) {
			j=i+1;
			byte[] hByte=String.valueOf(j).getBytes();
			//这里是用setFromHash来生成。可能有问题
			h[i]=Utils.pairing.getG1().newElement().setFromHash(hByte,0,hByte.length).getImmutable();
			
			System.out.println(h[i]);
			byte[] mByte=FileIOiml.getMD5(blockParentPath+blockDir[i]);
			m[i]=Utils.pairing.getZr().newElement().setFromHash(mByte, 0, mByte.length).getImmutable();
			
			
			
			sig[i]=(h[i].mul(u.powZn(m[i]))).powZn(x);
			proof.put(Long.valueOf(i), sig[i]);
			System.out.println(sig[i]);
		}
		
		
		
	}
	/**
	 * 
	 * @param path
	 * 
	 * 将对象的proof属性写出至文件
	 */
	public void write(String path){
		
		
		try {
		ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(path));
		os.writeObject(Utils.transferHexType(this.proof));
	
		
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
		
		
	}
	/**
	 * 
	 * @param path
	 * 从文件中读入对象的proof属性。
	 */
	public void read(String path){
		
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(path));
			this.proof=Utils.TransferG1Type((HashMap<Long,String>)is.readObject());
			
			
			System.out.println(proof);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	
	
	}
	

	
	public static void main(String[] args) throws Exception {
	

		Proof p =new Proof("e:/local/key/keyPara.txt","e:/local/file/song/");
		p.ini(3);
		p.write("e:/local/proof/song/sig.txt");
		p.read("e:/local/proof/song/sig.txt");
		
	}
	
	
}
