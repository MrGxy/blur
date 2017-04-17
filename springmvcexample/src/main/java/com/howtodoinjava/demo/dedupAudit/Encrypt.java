package com.howtodoinjava.demo.dedupAudit;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.catalina.tribes.util.Arrays;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import antlr.Token;

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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;  
import java.nio.MappedByteBuffer;  
import java.nio.channels.FileChannel;  
import java.nio.channels.FileChannel.MapMode;  
public class Encrypt {
	public static final String KEY_ALGORITHM="DESede"; 
	public static final String CIPHER_ALGORITHM="DESede/ECB/PKCS5Padding"; 
	
	
	/**
	 * @param filePath
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException;
	 * @throws IOException
	 *  使用DESede算法来生成二进制密钥。此处生成的密钥与文件内容相关。
	 * @throws InvalidKeyException 
	 * @throws InvalidKeySpecException 
	 */
	public byte[] iniKey(String filePath) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, InvalidKeyException, InvalidKeySpecException{
		Security.addProvider(new BouncyCastleProvider());
		KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM,"BC");
		
		//使用文件块来生成密钥参数
		SecureRandom Param= new SecureRandom(IO.toByteArray1(filePath));
		//128报错。
		kg.init(192,Param );
		SecretKey secretKey=kg.generateKey();
		

		
		return secretKey.getEncoded();
		
	}
	/**
	 * 
	 * @param key  
	 * @return
	 * @throws Exception
	 * 将二进制密钥转化为Key型。便于加解密。
	 */
	private Key toKey(byte[] key) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		
		// TODO Auto-generated method stub
		DESedeKeySpec dks=new DESedeKeySpec(key);
		SecretKeyFactory keyFactory=SecretKeyFactory.getInstance(KEY_ALGORITHM);
		
//		System.out.println(Hex.toHexString(keyFactory.generateSecret(dks).getEncoded()));
		
		return keyFactory.generateSecret(dks);
	}
	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 * 上述两个方法的合成
	 */
	private Key genKey(String filePath) throws Exception {
		// TODO Auto-generated method stub
		byte[] key=iniKey(filePath);
		return  toKey(key);
	}
	
	  	/**
	  	 * 
	  	 * @param key		密钥参数
	  	 * @param toPath	存储路径
	  	 * @throws Exception 
	  	 */
	    
	    
	    private void saveKey(byte[] key,String toPath) throws Exception {
			// TODO Auto-generated method stub
	    	System.out.println(Arrays.toString(key));
	    	
	    	String hexKey=Hex.toHexString(key);
	    	System.out.println(Arrays.toString(Hex.decode(hexKey)));
	    	
	    	IO.writeBuff(hexKey, toPath);
	    	
	    	
		}
	    /**
	     * 
	     * @param fromPath
	     * @return
	     * @throws Exception
	     * 从文件中取出密钥。
	     */
	    private Key getKey(String fromPath) throws Exception {
			// TODO Auto-generated method stub
	    	
	    	
	    	String hexKey=IO.readBuff(fromPath);
	    	byte[] key=Hex.decode(hexKey);
	    	
	    	System.out.println(Arrays.toString(toKey(key).getEncoded()));
	    	
	    	return toKey(key);
		}
	  /**
	   * 
	   * @param fromPath 文件明文路径
	   * @param toPath	  文件密文路径
	   * @param key      文件密钥
	   * @throws InvalidKeyException
	   * @throws IOException
	   * @throws NoSuchAlgorithmException
	   * @throws NoSuchProviderException
	   * @throws NoSuchPaddingException
	   */
	    public void encypt(String fromPath,String toPath,Key key) throws InvalidKeyException, IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException{
	    	Security.addProvider(new BouncyCastleProvider());
	    	Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC"); 
		    // cipher.init(Cipher.ENCRYPT_MODE, getKey()); 
		    cipher.init(Cipher.ENCRYPT_MODE, key); 
		    InputStream is = new FileInputStream(fromPath); 
		    OutputStream out = new FileOutputStream(toPath); 
		    CipherInputStream cis = new CipherInputStream(is, cipher); 
		    byte[] buffer = new byte[1024]; 
		    int r; 
		    while ((r = cis.read(buffer)) > 0) { 
		        out.write(buffer, 0, r); 
		        
		    } 
		    cis.close(); 
		    is.close(); 
		    out.close(); 

	    	
	    }
	    
	    /**
	     * 
	     * @param fromPath  文件密文路径
	     * @param toPath    解密后文件路径
	     * @param keyPath		密钥
	     * @throws Exception
	     */
	    private void decrypt(String fromPath, String toPath,String keyPath ) throws Exception{
			// TODO Auto-generated method stub
	    		Security.addProvider(new BouncyCastleProvider());
	    		Key key=getKey(keyPath);
	    		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC"); 
	    	   
	    	    cipher.init(Cipher.DECRYPT_MODE, key); 
	    	    InputStream is = new FileInputStream(fromPath); 
	    	    OutputStream out = new FileOutputStream(toPath); 
	    	    CipherOutputStream cos = new CipherOutputStream(out, cipher); 
	    	    byte[] buffer = new byte[1024]; 
	    	    int r; 
	    	    while ((r = is.read(buffer)) >= 0) { 
	    	    	System.out.println();
	    	        cos.write(buffer, 0, r); 
	    	    } 
	    	    cos.close(); 
	    	    out.close(); 
	    	    is.close(); 
	    	   
		}
	   
	    
	 
	    public static void main(String[] args) throws Exception {
			Encrypt enc=new Encrypt();
			byte[] b1=IO.toByteArray("E:/local/file/song/song_1.txt");
			byte[] b2=IO.toByteArray1("E:/local/file/song/song_1.txt");
			
			
			System.out.println(new String(Hex.encode(b1)));
			System.out.println(new String(Hex.encode(b2)));
			
//			enc.genKey("E:/song.txt");
//			enc.genKey("f:/song.txt");
			
//			Key key=enc.genKey("E:/song.txt");
			
//			enc.encypt("E:/song.txt","E:/encsong.txt",key );
			
//			enc.decrypt("E:/encsong.txt", "e:/decsong1.txt", "e:/songKey.txt");
//			byte[] key =enc.iniKey("E:/song.txt");
//			System.out.println(Arrays.toString(enc.toKey(key).getEncoded()));;
//			enc.saveKey(k/ey,"e:/songKey.txt");
//			enc.getKey("e:/songKey.txt");
			
			enc.encypt("f:/bigFile.txt", "f:/encBigFile.txt", enc.genKey("f:/bigFile.txt"));
			
		}
	    
	    

} 

