package com.howtodoinjava.demo.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") CommonsMultipartFile file,  
            HttpServletRequest request, ModelMap model)
	{
		String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"  + file.getOriginalFilename();
		filePath = filePath.replaceAll("\\\\", "/");        
		 String fileName = file.getOriginalFilename(); 
		 System.out.println("原始文件名:" + filePath);  
		return "employeesListDisplay";
		
	}
	
	@RequestMapping(value = "/upload1", method = RequestMethod.POST)
	public String upload1(@RequestParam("file") MultipartFile files[],  
            HttpServletRequest request, ModelMap model)
	{
		for (int i = 0; i < files.length; i++) {
			// 判断文件是否为空  
	        if (!files[i].isEmpty()) {  
	            try {  
	                // 文件保存路径  
	                String filePath = request.getSession().getServletContext().getRealPath("/") + "upload\\"  
	                        + files[i].getOriginalFilename();  
	                // 转存文件  
	               
	                files[i].transferTo(new File(filePath));  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            } 
			
	        }
		}
		
		return "employeesListDisplay";
		
	}
	
}
