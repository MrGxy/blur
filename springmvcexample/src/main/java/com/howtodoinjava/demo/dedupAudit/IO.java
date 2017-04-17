package com.howtodoinjava.demo.dedupAudit;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IO {
	 /** 
     * the traditional io way 
     *  
     * @param filename 
     * @return 
     * @throws IOException 
     * 读文件，将数据存入byte数组
     */  
  public static byte[] toByteArray(String filename) throws IOException {  
  
        File f = new File(filename);  
        if (!f.exists()) {  
            throw new FileNotFoundException(filename);  
        }  
  
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());  
        BufferedInputStream in = null;  
        try {  
            in = new BufferedInputStream(new FileInputStream(f));  
            int buf_size = 1024;  
            byte[] buffer = new byte[buf_size];  
            int len = 0;  
            while (-1 != (len = in.read(buffer, 0, buf_size))) {  
                bos.write(buffer, 0, len);  
            }  
            return bos.toByteArray();  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        } finally {  
            try {  
                in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            bos.close();  
        }  
    }  
  
   
    /** 
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能 
     *  
     * @param filename 
     * @return 
     * @throws IOException 
     */  
    public static byte[] toByteArray1(String filename) throws IOException {  
  
        FileChannel fc = null;  
        try {  
            fc = new RandomAccessFile(filename, "r").getChannel();  
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,  
                    fc.size()).load();  
            System.out.println(byteBuffer.isLoaded());  
            byte[] result = new byte[(int) fc.size()];  
            if (byteBuffer.remaining() > 0) {  
                // System.out.println("remain");  
                byteBuffer.get(result, 0, byteBuffer.remaining());  
            }  
            return result;  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        } finally {  
            try {  
                fc.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
    public static void writeByte(String path,byte[] content) throws IOException {
		// TODO Auto-generated method stub
    	 FileOutputStream fos = new FileOutputStream(path);  
    	  
         fos.write(content);  
         fos.close();  
	}	
    
     public static String readBuff(String fromPath) throws Exception {
		// TODO Auto-generated method stub
    	 	FileReader fr=new FileReader(new File(fromPath));
	    	BufferedReader br=new BufferedReader(fr);
	    	String Str="";
	    	String line="";
	    		while((line=br.readLine())!=null){
	    			Str=Str+line;
	    		}
	    	br.close();
	    	fr.close();
	    	
	    	return Str;
	}
     /**
      * 
      * @param fromPath
      * @param num	   行数， 按行来存储参数。总行数
      * @return
      * @throws Exception
      */
     public static String[] readBuff1(String fromPath,int num) throws Exception {
 		// TODO Auto-generated method stub
     	 	FileReader fr=new FileReader(new File(fromPath));
 	    	BufferedReader br=new BufferedReader(fr);
 	    	String[] Str=new String[num];
 	    	String line=null;
 	    	int i=0;
 	    		while((line=br.readLine())!=null){
 	    			Str[i]=line;
 	    			i++;
 	    		}
 	    		
 	    		
 	    	br.close();
 	    	fr.close();
 	    	
 	    	return Str;
 	}
     public static void writeBuff(String Str,String toPath) throws Exception {
		// TODO Auto-generated method stub
    	 	File file = new File(toPath);
    	 	if (!file.isDirectory()) {
				file.createNewFile();
			}
	    	FileWriter fw = new FileWriter(file);
	    	
	    	BufferedWriter bw = new BufferedWriter(fw);
	    	bw.write(Str);
	    	bw.close();
	    	fw.close();
	    	
	}
     public static void writeBuff1(String[] Str,String toPath) throws Exception {
 		// TODO Auto-generated method stub
     	 	File file = new File(toPath);

 	    	FileWriter fw = new FileWriter(file);
 	    	BufferedWriter bw = new BufferedWriter(fw);
 	    	for (int i = 0; i < Str.length; i++) {
 	    		bw.write(Str[i]);
 	    		bw.newLine();
			}
 	    	
 	    	bw.close();
 	    	fw.close();
 	    	
 	}
     /**
      * 
      * @param dir			文件块存储位置的父路径
      * @return
      */
    public static String[] listSubFile(String dir){
    	
    	
		return new File(dir).list();  
    	
    	
    }
    /**
     * 
     * @param dir		
     * @param Req		这里指一部分路径。req是挑战集。
     * @return
     */
    public static String[] listSubFileByReq(String dir,List<Long> Indexs,String fileName){
    	
    	
    	String suffix=".txt";
    	String[] subBlockPathByReq ;
    	
    	
    	subBlockPathByReq=new String[Indexs.size()];
    	for (int i = 0; i < Indexs.size(); i++) {
    		subBlockPathByReq[i]=dir+fileName+"_"+Indexs.get(i)+suffix;
		}
		
    	return subBlockPathByReq;
    	
		
    	
    	
    }
    public static void main(String[] args) throws Exception {
//		System.out.println(Arrays.toString(IO.listSubFileByReq("E:/local/file/song/", "1,2","song")));	
		
    	String[] str=new String[]{"1","2"};
    	IO.writeBuff1(str, "f:/str.txt");
	}
}
