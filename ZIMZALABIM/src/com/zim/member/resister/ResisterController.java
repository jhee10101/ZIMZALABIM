 /**
 * @Class Name : ResisterController.java
 * @Description : 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2019. 7. 29.           최초생성
 *
 * @author Zimzalabim
 * @since 2019. 7. 29. 
 * @version 1.0
 * @see
 *
 *  Copyright (C) by HR. KIM All right reserved.
 */
package com.zim.member.resister;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.zim.cmn.MessageVO;
import com.zim.cmn.StringUtil;
import com.zim.member.MemberVO;

/**
 * @author sist
 *
 */
@WebServlet(description = "회원정보조회", urlPatterns = { "/resister/resister.json" })
public class ResisterController extends HttpServlet{
	private final Logger LOG=Logger.getLogger(ResisterController.class);
	ResisterService resisterService;
	
	public ResisterController(){		
		resisterService = new ResisterService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("01 doGet()");
		LOG.debug("01.1 retrieveService:"+resisterService);
		service(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("01 doPost()");
		LOG.debug("01.1 retrieveService:"+resisterService);
		service(req,resp);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("02 service()");
		req.setCharacterEncoding("UTF-8");
		//work_div:read
    	String workDiv = StringUtil.nvl(req.getParameter("work_div"),"");
    	LOG.debug("02.1 workDiv:"+workDiv);
    	
    	switch(workDiv){
    		case "do_resister":
    			do_resister(req,resp);
    		break;    	
    	}
	}

	 /**
	 * @Method Name  : do_resister
	 * @작성일   : 2019. 7. 29.
	 * @작성자   : sist
	 * @변경이력  : 최초작성
	 * @Method 설명 :
	 * @param req
	 * @param resp
	 */
	private void do_resister(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		LOG.debug("03.1 do_resister");
		MemberVO inVO = new MemberVO();
		String userId = StringUtil.nvl(req.getParameter("resister_user_id"),"");
		String passwd = StringUtil.nvl(req.getParameter("resister_passwd"),"");
		String  name= StringUtil.nvl(req.getParameter("name"),"");
		String email = StringUtil.nvl(req.getParameter("email"),"");
		String residentNo = StringUtil.nvl(req.getParameter("resident_no"),"");
		String cellPhone = StringUtil.nvl(req.getParameter("cell_phone"),"");
		inVO.setUserId(userId);
		inVO.setPasswd(passwd);
		inVO.setName(name);
		inVO.setEmail(email);
		inVO.setResidentNo(residentNo);
		inVO.setCellPhone(cellPhone);
		inVO.setLvl("1");
		inVO.setRegId(userId);
		inVO.setModId(userId);
		//--param
		LOG.debug("03.1 param-------------------------------");
		LOG.debug(inVO);
		LOG.debug("03.1 ------------------------------------");
    	int flag = resisterService.do_resister(inVO);
    	LOG.debug("03.2 do_resister----------------------------");
    	LOG.debug("flag : "+flag);
    	LOG.debug("03.2----------------------------------------");
    	
    	Gson gson = new Gson();
    	resp.setContentType("text/html;charset=utf-8");
    	PrintWriter out = resp.getWriter();
    	String msg = "";
    	String gsonString = "";    
    	if(flag>0){
    		msg = "회원가입이 되었습니다.";
    	}else{
    		msg = "회원가입 실패.";
    	}
    	gsonString = gson.toJson(new MessageVO(String.valueOf(flag), msg));
    	LOG.debug("03.4 gsonString : "+gsonString);
    	out.println(gsonString);
	}
	
	
}
