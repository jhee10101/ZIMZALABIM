<%--
  /**
  * @Class Name : useOutObject.jsp
  * @Description : Sample Register 화면
  * @Modification Information
  *
  *   수정일                   수정자                      수정내용
  *  -------    --------    ---------------------------
  *  2019.07.01            최초 생성
  *
  * author 실행환경 개발팀
  * since 2019.07.01
  *
  * Copyright (C) 2009 by KandJang  All right reserved.
  */
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/ZIMZALABIM/js/jquery-ui.css" >
<title>Insert title here</title>
<script src="/ZIMZALABIM/js/jquery-1.12.4.js"></script>
<script src="/ZIMZALABIM/js/jquery-ui.js"></script>
</head>
<body>
	<h2>비밀번호 확인</h2>
	<p>안전한 개인정보를 위하여 비밀번호를 입력하여 주세요~~</p>
	<form>
		<table>
			<tr>
				<td><label>비밀번호</label><br/><input type="password" id="passWd" name=""passWd""></td>
			</tr>
		</table>
		<input type="submit" value="확인" id="submit">
		<button id="cancel">취소</button>			
	</form>
	<script>		 
		$(document).ready(function(){
			
		});
	</script>
</body>
</html>