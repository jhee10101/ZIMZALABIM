package com.zim.cmn;

import java.awt.Image;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.zim.code.CodeVO;
import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiUtils;
import com.zim.cmn.StringUtil;


public class StringUtil {
    private static Logger LOG = Logger.getLogger(StringUtil.class);
	
    /**
     * 
     * @Method Name  : renderPaing
     * @작성일   : 2019. 7. 24.
     * @작성자   : SIST
     * @변경이력  : 최초작성
     * @Method 설명 :
     * @param maxNum:총글수
     * @param currPageNo: 현재페이지
     * @param rowPerPage:한페이지에 보여질 글수
     * @param bottomCount:바닥에 보여질 페이지수
     * @param url:호출url
     * @param scriptName:호출 javascript	
     * @return
     */
    public static String renderPaing(int maxNum,int currPageNo,int rowPerPage,int bottomCount
            ,String url, String scriptName){
         /*  총글수 : 21 
          *  현재페이지                   1
           총글수                       0
           바닥에 보여질 페이지수          10 
           한페이지에 보여질 글수          10 
           호출url         
           호출 javascript
           << < 1 2 3 4 5 6 7 8 9 10 > >>
           총글수 : 21,1
          */
         int maxPageNo  = ((maxNum-1)/rowPerPage)+1;//총페이지
         int startPaeNo = ((currPageNo-1)/bottomCount) * bottomCount+1;
         int endPageNo  = ((currPageNo-1)/bottomCount+1)*bottomCount;
         int nowBlockNo = ((currPageNo-1)/bottomCount)+1;
         int maxBlockNo = ((maxNum-1)/bottomCount)+1;
         
         int inx  = 0;
         StringBuilder html=new StringBuilder();
         if(currPageNo>maxPageNo){
            return "";
         }
         
         //<table><tr><td></td></tr></table>
     	html.append("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"     >\n");
     	html.append("<tr> \n");
     	html.append("<td align=\"center\"> \n"); 
     	html.append("<ul class=\"pagination pg-amber\"> \n");
         
    	if(nowBlockNo>1 && nowBlockNo <=maxBlockNo){
    		html.append("<li  class=\"disabled\"><a  href=\"javascript:"+scriptName+"('"+url+"',1);\"  > ");
    		html.append("&laquo; ");
    		html.append("</a></li> \n");
    	}
    	//<
    	if(startPaeNo>bottomCount){
    		html.append("<li  class=\"disabled\"><a  href=\"javascript:"+scriptName+"('"+url+"',"+(startPaeNo-1)+");\"  > ");
    		html.append("< ");
    		html.append("</a></li> \n");    		
    	}
    	//1 2  .... 10
    	for(inx = startPaeNo;inx<=maxPageNo && inx<=endPageNo;inx++){
    		if( inx == currPageNo){//현재 page
    			html.append("<li  class=\"active\" 	>");
    			html.append("<a  href=\"javascript:#\"  > ");
    			html.append(inx);
    			html.append("</a> \n");
    			html.append("</li>");    
    		}else{
    			html.append("<li  class=\"disabled\">");
        		html.append("<a  href=\"javascript:"+scriptName+"('"+url+"',"+inx+");\"  > ");
        		html.append(inx);
        		html.append("</a> \n");  
        		html.append("</li>");
    		}
    	
    	}
    	//>
    	if(maxPageNo>=inx){
    		html.append("<li  class=\"disabled\">");
    		html.append("<a  href=\"javascript:"+scriptName+"('"+url+"',"+((nowBlockNo*bottomCount)+1)+");\"  > ");
    		html.append("> ");
    		html.append("</a> \n"); 
    		html.append("</li>");
    		
    	}
    	//>> &raquo;	오른쪽 꺾인 괄호
    	if(maxPageNo >=inx){
    		html.append("<li  class=\"disabled\">");
    		html.append("<a  href=\"javascript:"+scriptName+"('"+url+"',"+maxPageNo+");\"  > ");
    		html.append("&raquo; ");
    		html.append("</a> \n");   
    		html.append("</li>");
    	}
    	html.append("</ul> \n");
    	//--paging-----------------
    	html.append("</td> \n");
    	html.append("</tr> \n");
    	html.append("</table>");
    	
    	
    	LOG.debug("===========================");
    	LOG.debug("html.toString()=\n"+html.toString());
    	LOG.debug("===========================");
    	return html.toString();
    }
    
    
    
    /**
     * 
     * @Method Name  : makeSelectBox
     * @작성일   : 2019. 7. 22.
     * @작성자   : SIST
     * @변경이력  : 최초작성
     * @Method 설명 :
     * @param list: select에 필용한 코드정보
     * @param selectBoxNm : <select name="lvl" id="lvl">
     * @param selectedNm  : <option selected
     * @param allYN       : 전체 표시 
     * @return            : <select name="lvl" id="lvl">
     * 						  <option value="">전체</option>
    			              <option value="1" selected>일반사용자</option>
    			              <option value="9">관리자</option>
    		                </select>
     */
	public static String makeSelectBox(List<CodeVO> list,
			                           String selectBoxNm,
			                           String selectedNm,
			                           boolean allYN){
		StringBuilder sb=new StringBuilder();
		//<select name="lvl" id="lvl">
		sb.append("<select  class=\"form-control\" name='"+selectBoxNm+"' id='"+selectBoxNm+"' > \n");
		//전체
		if(allYN==true){
			sb.append("<option value=''>전체</option> \n");
		}
		
		//<option value="1" selected>일반사용자</option>
		for(CodeVO dto  :list){
			CodeVO vo =  dto;
			sb.append("\t<option value='"+vo.getCodeId()+"' ");
			if(selectedNm.equals(vo.getCodeId())){
				sb.append("selected='selected' ");
			}
			
			sb.append(">");
			sb.append(vo.getCodeNm());
			sb.append("</option>\n");
		}
		sb.append("</select> \n");
		LOG.debug("------------------------");
		LOG.debug(sb.toString());
		LOG.debug("------------------------");
		return sb.toString();
	}
	
	
	
	
	public static String nvl(String str, String defVal){
		
		String retStr = "";
		if(StringUtils.isEmpty(str)){//apache.commons null
			retStr = defVal.trim();
		}else{
			retStr = str.toString().trim();
		}
		
//		if(null == str || str.equals("")){
//			retStr = defVal;
//		}else{
//			retStr = str.toString().trim();
//		}
		
		return retStr;
	}
	
	
	
	public static String cut(String str){
		
		String retStr = "";
		if(str.length()>10){//apache.commons null
			retStr = str.substring(0,8)+"...";
		}else{
			retStr = str;
		}
				
		return retStr;
	}
	
	
	
	
	
	
	
	
	public static String escapeHtml(String html){
		return StringEscapeUtils.escapeHtml4(html);
	}
	
	//첨부된 이미지의 원본파일명
	public static List<String> originalFileName(String content){		 
	    String[] contentArray = content.split(" title=\"");
	    String orgNm = "";
	    
	    List<String> orgNameList = new ArrayList<>();
	    for (int i = 1; i < contentArray.length; i++) {
	    	orgNm = contentArray[i].substring(0,contentArray[i].indexOf("\""));
	    	orgNameList.add(orgNm);
	    }
	    
	    return orgNameList;
	}
	
	//첨부된 이미지의 확장자명
	public static List<String> extName(String content){		 
	    String[] contentArray = content.split(" title=\"");
	    String extNm = "";
	    
	    List<String> extNameList = new ArrayList<>();
	    for (int i = 1; i < contentArray.length; i++) {
	    	extNm = contentArray[i].substring(0,contentArray[i].indexOf("\""));
	    	extNm = extNm.substring(extNm.indexOf(".")+1);
	    	extNameList.add(extNm);
	    }
	    return extNameList;
	}
	
	//첨부된 이미지의 경로
	public static List<String> imageSavePath(String content){		 
	    String[] contentArray = content.split(" src=\"");
	    String savePath = "";	
	    String ip = getIP();
	    
    
	    
	    List<String> savePathList = new ArrayList<>();
	    for (int i = 1; i < contentArray.length; i++) {
	    	savePath = contentArray[i].substring(0,contentArray[i].indexOf("\""));
	    	if(savePath.indexOf("h") == 0){//http로 시작하면
	    		savePath= savePath.substring(savePath.indexOf("8080")+4);
	    	}else{//아니면
	    		savePath = savePath.substring(0, savePath.indexOf("&"));
	    	}
	    	savePath = "http://"+ip+":8080"+savePath;
	    	savePathList.add(savePath);
	    }
	    return savePathList;
	}
	
	public static String contentsReplace (String contents){ 
		String ip = getIP();
		String replaceText = "src=\"http://"+ip+":8080/ZIMZALABIM";
		if(contents.contains("src=\"/ZIMZALABIM")){
	    	contents = contents.replace("src=\"/ZIMZALABIM", replaceText);
	    }
	    return contents;
	}
	public static String getIP (){
		String ip = "";
	    //IP구하기
	    InetAddress local;
		try {
			local = InetAddress.getLocalHost();
			ip = local.getHostAddress();
//			System.out.println("local ip : "+ip);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}	
		return ip;
	}
	
	
//	//썸네일 생성
//	public static String makeThumb (String fullPath){
//		fullPath = webPathToRealPath(fullPath);
//		String fileName = fullPath.substring(fullPath.indexOf("multiupload\\")+12);
//	    String filePath = fullPath.substring(0,fullPath.indexOf(fileName));
//	    String thumbName = fileName.replace(".", "_thumb.");
//		
//		// 원본 이미지
//	    String orgFile = filePath+fileName;	 
//	    // 썸네일 이미지
//	    String thumbFile = filePath+thumbName;
//	 
//	    // 썸네일 이미지 가로크기(단위 : px)
//	    int thumbWidth = 200 ;	 
//	    // 썸네일 이미지 세로크기(단위 : px)
//	    int thumbHeight = 180 ;
//	 
//	    try{	 
//	        // 썸네일 설정
//	        Image thumbnail = JimiUtils.getThumbnail(orgFile, thumbWidth, thumbHeight, Jimi.IN_MEMORY);	 
//	        // 썸네일 생성
//	        Jimi.putImage(thumbnail, thumbFile);	 
//	    }catch(Exception e){
//	        e.printStackTrace();
//	    }
//	    
//	    thumbFile = realPathToWebPath(thumbFile);
//	    
//	    return thumbFile;
//	}
//	
//	//오라클에 저장된 이미지 저장주소를 썸네일 라이브러리에 맞게 변경
//	public static String webPathToRealPath(String databaseSaveName){
//		String real = "F:\\Java_20190415\\02_ORACLE\\workspace_web\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\ZIMZALABIM\\SE2\\multiupload\\";
//	    
//	    databaseSaveName = databaseSaveName.substring(databaseSaveName.indexOf("/multiupload")+13).replace("/", "\\");
//	    databaseSaveName = real+databaseSaveName;
//	    
//	    return databaseSaveName;
//	}
//	
//	public static String realPathToWebPath(String realPath) {
//		String webPath = "http://211.238.142.127:8080";
//		realPath = webPath+realPath.substring(realPath.indexOf("\\ZIMZALABIM\\")).replace("\\", "/");
//		
//		return realPath;
//		
//	}
}
