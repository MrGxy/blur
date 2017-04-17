package com.howtodoinjava.demo.dedupAudit;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import org.bouncycastle.util.encoders.Hex;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
/**
 * 
 * @author 
 *	密钥参数：包含公、私钥；以及对称密钥
 */
public  class KeyPara  {
	
	
	
	public  Element symmetric_key;
	public Element private_key;
	public HashMap<String,Element> public_key=new HashMap<String,Element>();
	//public String path="e:/local/key/keyPara.txt";
	
	/**
	 * 通过计算来初始化对象
	 */
	public KeyPara(){
		
		Utils.iniPairing();
		
		try {
			iniPPK();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 通过对象的序列化文件来初始化对象
	 * @param path
	 */
	public KeyPara(String path){
		
		Utils.iniPairing();
		readKeyPara(path);
		
	}
	
	

	/**
	 * 根据方案初始化公私钥！
	 * @throws Exception
	 */
	public  void iniPPK() throws Exception {
		// TODO Auto-generated method stub
		
		Element x ; 
		Element u,w,g,v;
		
		x=Utils.pairing.getZr().newRandomElement().getImmutable();
		
		u=Utils.pairing.getG1().newRandomElement().getImmutable();
		w=u.powZn(x);
		g=Utils.pairing.getG2().newRandomElement().getImmutable();
		v=g.powZn(x);
		
		
		
		private_key=x;
		public_key.put("u", u);
		public_key.put("w", w);
		public_key.put("g", g);
		public_key.put("v", v);
		
		
	}
	public void writeKeyPara(String toPath){
		
		HashMap<String,String> Hex_public_key=new HashMap<String,String>();	
		String Hex_private_key="";
		//转换Element类型为Hex
		Hex_public_key.put("u", Hex.toHexString(this.public_key.get("u").toBytes()));
		Hex_public_key.put("w", Hex.toHexString(this.public_key.get("w").toBytes()));
		Hex_public_key.put("g", Hex.toHexString(this.public_key.get("g").toBytes()));
		Hex_public_key.put("v", Hex.toHexString(this.public_key.get("v").toBytes()));
		
		Hex_private_key=Hex.toHexString(this.private_key.toBytes());
		
		//写对象
		ObjectOutputStream os;
		try {
			 os = new ObjectOutputStream(new FileOutputStream(toPath));
			 os.writeObject(Hex_public_key);  
			 os.writeObject(Hex_private_key);  
			 os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}  
	}
	
	/**
	 * 从该路径读取对象
	 * @param path
	 */
	public void readKeyPara(String path){
		
		ObjectInputStream is;
		try {
			is = new ObjectInputStream(new FileInputStream(  
					path));
			
			HashMap<String,String>  Hex_public_key = (HashMap<String,String>) is.readObject();
			String Hex_private_key=(String) is.readObject();
			
			
			Element u,w,g,v;
			u=Utils.pairing.getG1().newElementFromBytes(Hex.decode(Hex_public_key.get("u")));
			w=Utils.pairing.getG1().newElementFromBytes(Hex.decode(Hex_public_key.get("w")));
			g=Utils.pairing.getG1().newElementFromBytes(Hex.decode(Hex_public_key.get("g")));
			v=Utils.pairing.getG1().newElementFromBytes(Hex.decode(Hex_public_key.get("v")));
			
			this.public_key.put("u", u);
			this.public_key.put("w", w);
			this.public_key.put("g", g);
			this.public_key.put("v", v);
			
			
			this.private_key=Utils.pairing.getZr().newElementFromBytes(Hex.decode(Hex_private_key.toString()));
			
			
			
			is.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		
		
	}
	
	
	public static void main(String[] args) throws Exception {
		KeyPara kp=new KeyPara();
		kp.iniPPK();
		System.out.println(kp.public_key.get("") instanceof Serializable);
		System.out.println(kp.private_key instanceof Serializable);
//		
//	
		kp.writeKeyPara("");
	
		
		KeyPara kp1=new KeyPara();
		
		kp1.readKeyPara("");
	}
}
