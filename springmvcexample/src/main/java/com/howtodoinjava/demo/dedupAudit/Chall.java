package com.howtodoinjava.demo.dedupAudit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bouncycastle.util.encoders.Hex;
import it.unisa.dia.gas.jpbc.Element;


public class Chall {
	
	/**
	 * 
	 * @param totalNum
	 * @param challNum
	 * TPA端生成挑战集
	 * 此处示例参数为（3,2）
	 * @throws Exception 
	 * 按文件属性生成挑战集。此处挑战由<i,Vi>构成
	 * 
	 * 
	 */
	

	public int  chaNum;
	public List<Long> indexs=new ArrayList<Long>();				          //下标索引列表
	public HashMap<Long,Element> chaSet=new HashMap<Long,Element>();
	
	//public String path="e:/local/chall/song/chall.txt";
	
	
	
	public Chall(){
		
		Utils.iniPairing();
		
		
	}
	public Chall(String Path){
		
		Utils.iniPairing();
		read(Path);
		
		
	}
	
	
	public void  genCha(int totalNum,int chalNum) throws Exception {
		// TODO Auto-generated method stub
		
		Element[] v=new Element[chalNum];

		chaNum=chalNum;
		
		int i=0;
		while(i<chalNum){ 
			long randomIndex;
			
			randomIndex=Math.round(Math.random()*(totalNum-1)+1);
			
			
			if(chaSet.containsKey(randomIndex)){
				
				continue;
			}
			else{
				v[i]=Utils.pairing.getZr().newRandomElement().getImmutable();
				indexs.add(randomIndex);
				chaSet.put(randomIndex, v[i]);
				
				i++;
				
			}
			
		}
	
	}
	
	
	public void write(String outPath){
		
		try {
			ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(outPath));
			os.writeObject(this.indexs);
			os.writeObject(this.chaNum);
			os.writeObject(Utils.transferHexType(this.chaSet));
			os.close();
			
		} 
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void read(String path){
		
		HashMap<Long,String> Hex_chaSet=new HashMap<Long,String>();
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(path));
			indexs=(List)is.readObject();
			chaNum=(int) is.readObject();
			Hex_chaSet=(HashMap<Long,String>) is.readObject();
			
			
			chaSet=Utils.TransferZrType(Hex_chaSet);
			
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		
		
					
		
	}
}	
