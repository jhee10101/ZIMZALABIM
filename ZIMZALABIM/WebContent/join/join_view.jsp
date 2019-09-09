<%@page import="com.zim.member.MemberVO"%>
<%@page import="com.zim.cmn.StringUtil"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.zim.cmn.SearchVO"%>
<%@page import="com.zim.join.JoinVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
   
<%
	Logger LOG = Logger.getLogger(this.getClass());
 	List<JoinVO> list=(List<JoinVO>)request.getAttribute("list"); 
 	List<JoinVO> list2=(List<JoinVO>)request.getAttribute("list2"); 
	MemberVO memberVO = (MemberVO)session.getAttribute("user");
	request.setAttribute("memberVO", memberVO);
	//Param
	String pageNum    = "1";
	String searchDiv  = "";
	String searchWord = "";
	String pageSize   = "10";
	
	int maxNum=0; //총글수 :sever O
	int maxNum2=0; //총글수 :sever O
	int currPageNo=1; //현재페이지 :PageNum O
	int rowPerPage=10; //rowCnt 한페이지의 게시글수 :PageSize O
	int bottomCount=10; // bottom Cnt
	String url="/ZIMZALABIM/join/join.do"; //url
	String scriptName="doSearchPage";//Javascript함수명


	
//	SearchVO paramVO = (SearchVO)request.getAttribute("paramVO");
	//out.print("paramVO:"+paramVO);
//	LOG.debug("0===========================");
//	LOG.debug("paramVO="+paramVO);
//	LOG.debug("0===========================");
//	if(null != paramVO){
 //   	pageNum    = paramVO.getNum()+"";
  //  	searchDiv  = paramVO.getSearchDiv();
   // 	searchWord = paramVO.getSearchWord();
   // 	pageSize   = paramVO.getPageSize()+"";		
   // 	LOG.debug("===========================");
   // 	LOG.debug("paramVO="+paramVO);
   // 	LOG.debug("===========================");
    	
    	
	 //	 rowPerPage = Integer.parseInt(pageSize);
	//	 currPageNo = Integer.parseInt(pageNum);
		// maxNum = (Integer)request.getAttribute("totalCnt"); 
//		 maxNum = (Integer)request.getAttribute("totalCnt2"); 

	//}

	
	

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->    
<link rel="stylesheet" href="/ZIMZALABIM/js/jquery-ui.css" >
<!-- 부트스트랩 -->
<link href="/ZIMZALABIM/css/bootstrap.min.css" rel="stylesheet">
<title>게시관리</title>
<script src="/ZIMZALABIM/js/jquery-1.12.4.js"></script>
<script src="/ZIMZALABIM/js/jquery-ui.js"></script>
<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
<script src="/ZIMZALABIM/js/bootstrap.min.js"></script>	
</head>
<body>
	<!-- 버튼 -->
	<div class="page-header">
		<h3>참여중인 상품</h3>		
	</div>
		<div class="row">
	  	  <div class="col-sm-12">
			<div class="col-md-1 col-md-offset-11">
			<input type="button" class="btn btn-warning"  value="조회" onclick="javascript:do_retrieve();" />
			</div>		
			<hr/>
			<br/>
			<!-- 버튼 -->
				<form name="searchFrm" id="searchFrm" action="/ZIMZALABIM/join/join.do" method="post" class="form-inline">
					<input type="hidden" name="work_div" />
					<input type="hidden" name="page_num"  />
					<input type="hidden" name="search_word" value="${memberVO.userId }" />
					
						<!-- 검색영역 -->
					<!--// 검색영역 -->
						<table id="listTable" class="table table-bordered table-hover">
							<thead class="table-header" style="background-color:#fbb710; color:white;">
								<tr>
									<th class="text-center " width="10%">상품번호</th>	
									<th class="text-center " width="25%">참여일</th>
									<th class="text-center " width="20%">상품이름</th>
									<th class="text-center " width="15%">주문수량</th>
									<th class="text-center " width="15%">배송상태</th>
									<th class="text-center " width="15%">비고</th>
									<th width="0%" style="display: none;">Join_id</th>
								</tr>
							</thead>
							<tbody>
						<%
							if(null != list && list.size()>0){
								for(JoinVO vo :list){
						%>
								<tr>
									<td class="text-center" ><%=vo.getProduct_no() %></td>
									<td class="text-center" ><%=vo.getJoin_dt()  %></td>
									<td class="text-center" ><%=vo.getProductNm() %></td>
									<td class="text-center" ><%=vo.getJoin_cnt() %></td>
									<td class="text-center" ><%if(vo.getDeliveryStatus().equals("100")){out.print("결제완료");}else if(vo.getDeliveryStatus().equals("200")){out.print("공구진행");}else if(vo.getDeliveryStatus().equals("300")){out.print("배송준비");}else{out.print("배송완료");} %></td>	
									<td class="text-center" ><%if(vo.getJoin_status().equals("20")){out.print("참여취소");}else if(vo.getJoin_status().equals("30")){out.print("참여완료");}else{out.print("참여진행");} %></td>		
									<td style="display: none;" <%=vo.getJoin_id()%> /></td>					
								</tr>
						<%	
							    }//--for 
							}else{  
						%>
								<tr>
									
								</tr>
						
						
						<%	}//--else %>
					</tbody>
				</table>
				<h3>참여취소/완료 상품</h3>	
				<table id="listTable2" class="table table-bordered table-hover">
					<thead class="table-header" style="background-color:#fbb710; color:white;">
						<tr>
							<th class="text-center " width="10%">상품번호</th>	
							<th class="text-center " width="25%">참여일</th>
							<th class="text-center " width="20%">상품이름</th>
							<th class="text-center " width="15%">주문수량</th>
							<th class="text-center " width="15%">배송상태</th>
							<th class="text-center " width="15%">비고</th>
							<th width="0%" style="display: none;">Join_id</th>
						</tr>
					</thead>
					<tbody>
						<%
							if(null != list2 && list2.size()>0){
								for(JoinVO vo :list2){
						%>
								<tr>
									<td class="text-center"><%=vo.getProduct_no() %></td>
									<td class="text-center"><%=vo.getJoin_dt()  %></td>
									<td class="text-center"><%=vo.getProductNm() %></td>
									<td class="text-center"><%=vo.getJoin_cnt() %></td>
									<td class="text-center"><%if(vo.getDeliveryStatus().equals("100")){out.print("결제완료");}else if(vo.getDeliveryStatus().equals("200")){out.print("공구진행");}else if(vo.getDeliveryStatus().equals("300")){out.print("배송준비");}else{out.print("배송완료");} %></td>
									<td class="text-center"><%if(vo.getJoin_status().equals("20")){out.print("참여취소");}else if(vo.getJoin_status().equals("30")){out.print("참여완료");}else{out.print("참여진행");} %></td>						
								</tr>
						<%	
							    }//--for 
							}else{  
						%>
								<tr>
									
								</tr>
						
						<%	}//--else %>
					</tbody>
				</table>	
				<!-- //date list -->
			</form>
		</div>
	</div>
	<script>
	

	
		function do_retrieve(){
			//alert('do_retrieve');
			var frm=document.searchFrm;
			frm.work_div.value='do_retrieve';
			frm.page_num.value='1';
			frm.action = '/ZIMZALABIM/join/join.do';
			
			//alert('do_retrieve:'+frm.work_div.value);
			frm.submit();
		}
		
		function do_retrieve2(){
			//alert('do_retrieve');
			var frm=document.searchFrm;
			frm.work_div.value='do_retrieve2';
			frm.page_num.value='1';
			frm.action = '/ZIMZALABIM/join/join.do';
			
			//alert('do_retrieve:'+frm.work_div.value);
			frm.submit();
		}

		
		
		   $("#listTable>tbody").on("click","tr",function(){
				//alert("취소 하시겠습니까?");
				var cTrs = $(this);
				var cTds = cTrs.children();
				console.log(cTds);
				var productno = cTds.eq(0).text();
				console.log("productno:"+productno);
				
				var frm = document.searchFrm;
				frm.work_div.value ="do_selectOne";
				frm.action = '/ZIMZALABIM/join/join.do?product_no='+productno;
				frm.submit();
			}); 
			
		$(document).ready(function(){
		});
	</script>
	



</body>
</html>