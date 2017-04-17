package com.howtodoinjava.demo.dedupAudit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.bouncycastle.util.encoders.Hex;

import com.sun.tools.javac.util.Pair;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class Utils {
	public  static Pairing pairing;
	
	
	public static void iniPairing(){

		pairing = PairingFactory.getPairing("a.properties");
		PairingFactory.getInstance().setUsePBCWhenPossible(true);
		
		
		
	}
	
	public static HashMap<Long,String> transferHexType(HashMap<Long,Element> map){
		
		HashMap<Long,String> Hex_Map=new HashMap<Long,String>();
		Set<Long> set=map.keySet();
		Iterator<Long> ite = set.iterator();
		
		while(ite.hasNext()){
			long index=ite.next();
			Hex_Map.put(index, Hex.toHexString(map.get(index).toBytes()));
			System.out.println(Hex_Map.get(index));	
		}
		
		return Hex_Map;
	}
	
	
	//返回G域的元素
	public static HashMap<Long,Element> TransferG1Type(HashMap<Long,String> hex_Map){
		iniPairing();
		HashMap<Long,Element> map=new HashMap<Long,Element>();
		
		
		Set<Long> set=hex_Map.keySet();
		Iterator<Long> ite = set.iterator();
		while(ite.hasNext()){
			
			long index=ite.next();
			System.out.println(index);
			map.put(index,pairing.getG1().newElementFromBytes(Hex.decode(hex_Map.get(index))));
			
			
		}
		
		
		
		return map;
		
		
		
	}
	//返回Zr域的元素
		public static HashMap<Long,Element> TransferZrType(HashMap<Long,String> hex_Map){
			iniPairing();
			HashMap<Long,Element> map=new HashMap<Long,Element>();
			
			
			Set<Long> set=hex_Map.keySet();
			Iterator<Long> ite = set.iterator();
			while(ite.hasNext()){
				
				long index=ite.next();
				System.out.println(index);
				map.put(index,pairing.getZr().newElementFromBytes(Hex.decode(hex_Map.get(index))));
				
				
			}
			
			
			
			return map;
			
			
			
		}
	
	
	
	public static String transferType(Element e){
		
		
		return Hex.toHexString(e.toBytes());
		
		
	}
	
	
	/**
	 * 返回G1域
	 * @param e
	 * @return
	 */
	public static Element transferType(String e){
		
		iniPairing();
		
		return pairing.getG1().newElementFromBytes(Hex.decode(e));
		
	
	
	
	}
	/**
	 * 返回Zr域
	 * @param e
	 * @return
	 */
	public static Element transferType_(String e){
		
		iniPairing();
		
		return pairing.getZr().newElementFromBytes(Hex.decode(e));
		
	
	
	
	}
	
}
