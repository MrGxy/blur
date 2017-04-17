<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%> 
<html>
<link href="./css/dropzone.css" type="text/css" rel="stylesheet" />
<script src="./css/dropzone.min.js"></script>
<body>
<h2>Hello java!</h2>

<form action="file/upload1" class="dropzone" method="post" enctype="multipart/form-data">
	 <div class="fallback">
    	<input name="file" type="file" multiple />
  	 </div>
  
</form>

<form action="file/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="submit" value="上传" />  
</form>
</body>
</html>
