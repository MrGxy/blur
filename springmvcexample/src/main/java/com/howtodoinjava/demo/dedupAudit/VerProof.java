package com.howtodoinjava.demo.dedupAudit;


import it.unisa.dia.gas.jpbc.Element;


public class VerProof {
	
	
	
	public Chall cha;
	public ResProof resProof;
	public KeyPara keyPara;
	
	public boolean auditResult; 
	
	//验证响应证据时，调用此构造函数
	public VerProof(String chaSetPath,String resProofPath,String keyParaPath){
		Utils.iniPairing();
		cha=new Chall(chaSetPath);
		resProof =new ResProof(resProofPath);	 
		keyPara=new KeyPara(keyParaPath);
	}
		
	
	
	public  void verifyProof(int chaBlockNum) throws Exception {
		
		Element Left,Right;
		Element Sig,μ,R;
		Element u,g,v;
		Element[] hi,vi;
		hi=new Element[chaBlockNum];
		
		
		Sig=resProof.totalSig;
		μ=resProof.μ;
		R=resProof.R;
		
		u=keyPara.public_key.get("u");
		g=keyPara.public_key.get("g");
		v=keyPara.public_key.get("v");
		
		
		for (int i = 0; i < chaBlockNum; i++) {
			byte[] hiByte=cha.indexs.get(i).toString().getBytes();
			hi[i]=Utils.pairing.getG1().newElement().setFromHash(hiByte,0,hiByte.length).getImmutable();
		}
		vi=getChaEle();
		
		
		
		
		Element hv;
		hv=Utils.pairing.getG1().newElement().getImmutable();
		for (int i = 0; i < chaBlockNum; i++) {
			hv=hv.mul(hi[i].duplicate().powZn(vi[i].duplicate()));
		}
		
		
		Left=Utils.pairing.getGT().newElement();
		Right=Utils.pairing.getGT().newElement();
		Left=Utils.pairing.pairing(Sig.mul(R), g).getImmutable();
		Right=Utils.pairing.pairing(hv.mul(u.powZn(μ)), v).getImmutable();
	
		this.auditResult=Left.equals(Right);
		System.out.println(Left);
		System.out.println(Right);
		
	}
	
	
	/**
	 * 获取挑战集的部分元素
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
	
	
	
	public static void main(String[] args) throws Exception {
		
		VerProof vp=new VerProof("e:/local/chall/song/chall.txt", "E:/local/chall/song/resProof.txt", "e:/local/key/keyPara.txt");
		vp.verifyProof(2);
	
	}
}
