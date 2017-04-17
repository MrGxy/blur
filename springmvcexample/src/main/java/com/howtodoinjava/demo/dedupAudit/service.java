package com.howtodoinjava.demo.dedupAudit;



public class service {
	public static void main(String[] args) throws Exception {
		
		
		String TargetFileName="E:/dedupAudit/file/song.txt";
		String FileName="song";
		String BlockParentName="e:/local/file/song/";
		
		
		String KeyParaPath="e:/local/key/keyPara.txt";
		String proofPath="e:/local/proof/song/sig.txt";
		String ChallPath="e:/local/chall/song/chall.txt";
		
		String ResProofPath="E:/local/chall/song/resProof.txt";
		
		int toatalNum=3;
		int chaNum=3;
		/*
		 * -------------本地-------------
		 */
		//用户生成公私钥
		KeyPara kp=new KeyPara();

		kp.writeKeyPara(KeyParaPath);
		
		//切分文件
		
		int blocksize=1024;		
		FileIOiml.split(TargetFileName,blocksize);
		
		//生成证据
		Proof p =new Proof(KeyParaPath,BlockParentName);
		p.ini(3);
		p.write(proofPath);
		
		
		/*
		 * -------------TPA-------------
		 */
		//发起挑战
		Chall cha=new Chall();
		cha.genCha(3, chaNum);
		cha.write(ChallPath);
		
		
		
		/*
		 * -------------Cloud-------------
		 */
		
		//响应挑战
		ResProof rp=new ResProof(ChallPath, proofPath,KeyParaPath);
		rp.reponseAuditCha( BlockParentName, chaNum, FileName);
		rp.write(ResProofPath);
		
		/*
		 * -------------TPA-------------
		 */
		//验证挑战
		
		VerProof vp=new VerProof(ChallPath, ResProofPath,KeyParaPath);
		vp.verifyProof(chaNum);
		System.out.println(vp.auditResult);
	}
}
