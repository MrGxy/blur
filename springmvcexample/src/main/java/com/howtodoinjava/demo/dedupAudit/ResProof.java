package com.howtodoinjava.demo.dedupAudit;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.List;
import org.bouncycastle.util.encoders.Hex;
import it.unisa.dia.gas.jpbc.Element;


public class ResProof {
	
	public Chall cha;
	public Proof proof;
	public KeyPara keyPara;
	
	public Element R;
	public Element μ;
	public Element totalSig;
	
//	public String path="E:/local/chall/song/resProof.txt";
	
	
	public ResProof(){
		 Utils.iniPairing();
		
		
	}
	//读取响应证据的属性(R,μ,totalSig)时，调用该构造函数
	public ResProof(String path){
		 Utils.iniPairing();
		 read(path);
		
		
	}
	//计算响应证据时，调用此构造函数
	public ResProof(String chaSetPath,String proofPath,String keyParaPath){
		 Utils.iniPairing();
		 cha=new Chall(chaSetPath);
		 proof=new Proof(proofPath);		 
		 keyPara=new KeyPara(keyParaPath);
	}
	
	

	public  void reponseAuditCha(String fileBlockParentPath,int chaNum,String fileName) throws Exception {
		// TODO Auto-generated method stub
		
		
		
	
		Element r;										//随机生成
		Element w;										//公钥中读取的参数
		Element[] v,m,sig=new Element[chaNum];			//分别从	chaSetPath,fileBlockParentPath,ProofPath读取
		

		
		r=Utils.pairing.getZr().newRandomElement().getImmutable();
		
		μ=Utils.pairing.getZr().newElement().getImmutable();
		totalSig=Utils.pairing.getG1().newElement().getImmutable();
		
		w=keyPara.public_key.get("w");
		v=getChaEle();
		m=getChaBlockContent(fileBlockParentPath, cha.indexs, fileName);
		sig=getRequestProof();
		
		
		R=w.powZn(r);
		
		for (int i = 0; i < chaNum; i++) {
			
			
			μ=μ.add(m[i].duplicate().mul(v[i].duplicate()));
			totalSig=totalSig.mul(sig[i].duplicate().powZn(v[i].duplicate()));
			
			
			
		
		}
		
		μ=μ.duplicate().add(r.duplicate());
		System.out.println("计算");
		System.out.println("μ:"+μ);
		System.out.println("totalSig:"+totalSig);
		System.out.println("R:"+R);
		
		
	}
	
	
	public void write(String outPath){
		ObjectOutputStream os;
		try {
			 os = new ObjectOutputStream(new FileOutputStream(outPath));
			 os.writeObject(Utils.transferType(R));  		//G1域  
			 os.writeObject(Utils.transferType(totalSig));  //G1域  
			 os.writeObject(Utils.transferType(μ));         //Zr域  
			  
			 os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}  
		
		
	}
	
	public void read(String inPath){
		
		ObjectInputStream is;
		String hex_R,hex_totalSig,hex_μ="";
		try {
			is = new ObjectInputStream(new FileInputStream(  
					inPath));
			
			hex_R=(String)is.readObject();
			hex_totalSig=(String)is.readObject();
			hex_μ=(String)is.readObject();
			
			
			R=Utils.pairing.getG1().newElementFromBytes(Hex.decode(hex_R));
			totalSig=Utils.pairing.getG1().newElementFromBytes(Hex.decode(hex_totalSig));
			μ=Utils.pairing.getZr().newElementFromBytes(Hex.decode(hex_μ));
			
			System.out.println("读取");
			
			System.out.println(μ);
			System.out.println(totalSig);
			System.out.println(R);
			is.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		
		
	}
	
	/**
	 * 获取挑战集中的随机元素v[i]
	 * @return
	 */
	public Element[] getChaEle(){
		
		
		Element[] v=new Element[cha.indexs.size()];
	
		int i=0;
		for (Long index : cha.indexs) {
			v[i]=cha.chaSet.get(index);
			System.out.println(v[i]);
			i++;
		}
		
		return v;
		
		
		
		
	}
	
	
	
	/**
	 * 获取挑战集所对应的签名证据sig[]
	 * @return
	 */
	public Element[] getRequestProof(){
		
		
		Element[] sig=new Element[cha.indexs.size()];
		int i=0;
		for (Long index : cha.indexs) {
			sig[i]=proof.proof.get(index-1);
			i++;
	}
		
		
		
		for (int j = 0; j < sig.length; j++) {
			System.out.println(sig[j]);
		}
		
		return sig;
		
		
	}
	
	/**
	 * 获取所需的文件原文信息
	 * @param blockParentPath
	 * @param Indexs
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public  Element[] getChaBlockContent(String blockParentPath,List<Long> Indexs,String fileName) throws Exception {
		// TODO Auto-generated method stub
			
			Element[] m=new Element[Indexs.size()];
			for (int i = 0; i < m.length; i++) {
				byte[] miByte=FileIOiml.getMD5(getChaBlockPath(blockParentPath,Indexs, fileName)[i]);
				m[i]=Utils.pairing.getZr().newElement().setFromHash(miByte, 0, miByte.length);
				System.out.println(m[i]);
			}
			return m;
	}
	
	public  String[] getChaBlockPath(String blockParentPath,List<Long> Indexs,String fileName) {
		// TODO Auto-generated method stub
		String[] chaBlockPath=IO.listSubFileByReq(blockParentPath, Indexs, fileName);
		
		
		return chaBlockPath;
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		ResProof rp=new ResProof("e:/local/chall/song/chall.txt", "e:/local/proof/song/sig.txt","e:/local/key/keyPara.txt");
		rp.reponseAuditCha( "e:/local/file/song/", 2, "song");
		rp.write("");
		rp.read("");
	
	}
}
