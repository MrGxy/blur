package com.howtodoinjava.demo.dedupAudit;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;


public class FileIOiml  {
	
	
	
	private final static String spiltFilePath="E:/local/file/";				//本地文件分块后存放地址
	/**
	 * 
	 * @param TargetFileName
	 * @param fileNames
	 * @throws IOException
	 * 分块算法---均分，可设置分块大小
	 */
	public  static void ini() {
		// TODO Auto-generated method stub

	}
	public static  void split(String TargetFilePath,int blocksize) throws IOException{
		
			//构造文件块输出的路径
			String[] fileNames=genSubPath(TargetFilePath,blocksize);
		 	FileInputStream in = new FileInputStream(new File(TargetFilePath));// 指定要读取的
		 	int n = 0;// 每次读取的字节长度
	        byte[] bb = new byte[blocksize];// 存储每次读取的内容
	        for (int i = 0; i < fileNames.length; i++) {
	        	 File fout = new File(fileNames[i]);
	        	 if(!fout.getParentFile().exists()) {  
	                 //如果目标文件所在的目录不存在，则创建父目录  
	                  
	        		 fout.getParentFile().mkdirs() ;
	                      
	                     
	                 
	             } 
	       		
	        	 FileOutputStream out = new FileOutputStream(fout);
	        	
	        	 while((n = in.read(bb)) != -1){
	        		 out.write(bb, 0, n);// 将读取的内容，写入到输出流当中
	        		 break;
	        	 }
	        	 out.close();
			}
	        
	        System.out.println(n);
	        in.close();
	      
		
	}
	
	/**
	 * 将分块内容整合至一个文件中。
	 * @param blockParentDir					文件块父目录
	 * @param TargetFileName					整合后的文件路径
	 * @throws IOException
	 */
	public static void mergeFile(String blockParentDir, String TargetFileName) throws IOException{
		  String[] blockDir;
		  blockDir=IO.listSubFile(blockParentDir);
		  File fin = null;
		  // 构建文件输出流
		  File fout = new File(TargetFileName);
		  if (!fout.exists()) {
			   fout.createNewFile();
		  }
		  FileOutputStream out = new FileOutputStream(fout);
		  
		  for (int i = 0; i < blockDir.length; i++) {
			  // 打开文件输入流
			  fin = new File(blockDir[i]);
			  FileInputStream in = new FileInputStream(fin);
			  // 从输入流中读取数据，并写入到文件数出流中
			  int c;
			  while ((c = in.read()) != -1) {
				  out.write(c);
			  }
			  in.close();
		  }
		  out.close();
		  System.out.println("合并文件" + TargetFileName + "中的内容如下：");
		 }
	
	/**
	 * 
	 * @param filePath
	 * @param blockSize
	 * @return
	 * @throws IOException
	 * 获取文件块数量
	 */
	 public static int getBlockNum(String filePath,int blockSize) throws IOException{
		File file =new File(filePath); 
		FileInputStream fin = new FileInputStream(file);
		int blockNum;
		if(fin.available()%blockSize!=0){
			
			blockNum=fin.available()/blockSize+1;
		} 
		else{
			blockNum=fin.available()/blockSize;
		}
		return blockNum;
	}
	 
	 /**
	  * 
	  * @param TargetFileName
	  * @param blocksize			
	  * @return
	  * @throws IOException
	  * 生成所有分块文件的绝对路径。
	  */
	public static String[] genSubPath(String TargetFileName,int blocksize) throws IOException{
		
		
		//主要参数 beforePath:路径；fileNameWithsuffix：文件名+文件后缀；fileName：文件名；suffix：文件后缀
		String fileName,suffix,fileNameWithsuffix;
		int blockNum;
		//中间参数
		int  _location,dotLocation;
		
		_location=TargetFileName.lastIndexOf("/");
		fileNameWithsuffix =TargetFileName.substring(_location+1);
		
		
		
		
		dotLocation=fileNameWithsuffix.indexOf(".");
		fileName=fileNameWithsuffix.substring(0, dotLocation);
		suffix=fileNameWithsuffix.substring(dotLocation);
		
		
		
		
		blockNum=getBlockNum(TargetFileName,blocksize);
		System.out.println(blockNum);
		String[] subFilePath = new String[blockNum];
		for (int i = 0; i <blockNum; i++) {
			int j=i;
			System.out.println(i);
			subFilePath[i]=spiltFilePath+fileName+"/"+fileName+"_"+(++j)+suffix;
			System.out.println(subFilePath[i]);
		}
				
		
		return subFilePath;
		
	}
	
	
	/**
	 * 
	 * @param fielPath
	 * @return
	 * @throws Exception
	 * 生成文件摘要：MD5算法
	 */
	public  static byte[]  getMD5(String fielPath) throws Exception {
		// TODO Auto-generated method stub
		Security.addProvider(new BouncyCastleProvider()); 		
		InputStream fis =  new FileInputStream(fielPath);          //<span style="color: rgb(51, 51, 51); font-family: arial; font-size: 13px; line-height: 20px;">将流类型字符串转换为String类型字符串</span>  
		  
		byte[] buffer = new byte[1024];  
		MessageDigest Md5 = MessageDigest.getInstance("MD5"); //如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256  
		int numRead;  
		  
		do {  
		      numRead = fis.read(buffer);    //从文件读到buffer，最多装满buffer  
		      if (numRead > 0) {  
		    	  Md5.update(buffer, 0, numRead);  //用读到的字节进行MD5的计算，第二个参数是偏移量  
		      }  
		   } while (numRead != -1);  
		  
		   fis.close(); 
		   byte[] Md5Byte=Md5.digest();
		   
		   return Md5Byte;
	}	
	/**
	 * 
	 * @param fielPath
	 * @return
	 * @throws Exception 
	 */
	public static String getMD5hex(String fielPath) throws Exception{
		Security.addProvider(new BouncyCastleProvider()); 	
		String Md5Hex;
		Md5Hex=Hex.toHexString(getMD5(fielPath));
		
		   
		return Md5Hex;  
		 
	}
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		String TargetFileName="E:/dedupAudit/file/song.txt";
		String MergeFileName="e:/song.txt";
		String[] fileNames={"f:/song_0.txt", "f:/song_1.txt",
			    "f:/song_2.txt"};
		int blocksize=1024;
		FileIOiml.split(TargetFileName,blocksize);
//		System.out.println(Arrays.toString(fileIO.GensubPath(TargetFileName)));;
//		fileIO.mergeFile(fileNames, MergeFileName);
		
//		fileIO.GensubPath("F:/song.txt");
//		File file=new File("E:/local/file/hh/song.txt");
//		String s=FileIOiml.getMD5hex("f:/song.txt");
//		System.out.println(s);
//		String s1=FileIOiml.getMD5hex("e:/song.txt");
//		System.out.println(s1);
	}


	


	


	
	
}
