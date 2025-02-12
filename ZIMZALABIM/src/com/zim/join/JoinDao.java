package com.zim.join;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.zim.cmn.ConnectionMaker;
import com.zim.cmn.DTO;
import com.zim.cmn.JDBCReturnReso;
import com.zim.cmn.SearchVO;
import com.zim.cmn.SearchVO2;
import com.zim.cmn.WorkDiv;
import com.zim.member.MemberVO;
import com.zim.product.ProductVO;

public class JoinDao implements WorkDiv {

	private final Logger LOG = Logger.getLogger(JoinDao.class);
	private ConnectionMaker connectionMaker;

	public JoinDao() {
		connectionMaker = new ConnectionMaker();
	}


	public JoinVO do_joinlist_selectOne(DTO dto) {
		JoinVO vo = (JoinVO) dto;
		JoinVO outVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT join_id       \n");
			sb.append("      ,product_no    \n");
			sb.append("      ,join_dt       \n");
			sb.append("      ,join_cnt      \n");
			sb.append("      ,join_status   \n");
			sb.append("FROM   joinlist      \n");
			sb.append("WHERE join_id = ?    \n");
			sb.append("AND   product_no = ? \n");
			conn = new ConnectionMaker().getConnection();
			LOG.debug("1=========================");
			LOG.debug("query:\n"+sb.toString());
			LOG.debug("1=========================");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getJoin_id());
			pstmt.setString(2, vo.getProduct_no());
			LOG.debug("2=========================");
			LOG.debug("2.param:"+vo);
			LOG.debug("2=========================");
			LOG.debug(rs);
			rs = pstmt.executeQuery();
			LOG.debug(rs);
			if(rs.next()){
				outVO = new JoinVO();				
				outVO.setJoin_id(rs.getString("join_id"));
				outVO.setProduct_no(rs.getString("product_no"));
				outVO.setJoin_dt(rs.getString("join_dt"));
				outVO.setJoin_cnt(rs.getString("join_cnt"));
				outVO.setJoin_status(rs.getString("join_status"));

			
			}
		}catch(SQLException e){
			LOG.debug("=========================");
			LOG.debug("SQLException:"+e.toString());
			LOG.debug("=========================");
		}finally{
			JDBCReturnReso.close(rs);			
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);
		}
		return outVO;
	}
	

	@Override
	public int do_insert(DTO dto) {
		JoinVO vo = (JoinVO) dto;
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" MERGE INTO joinlist J1															\n");
			sb.append(" USING (                                                                         \n");
			sb.append("         SELECT                                                                  \n");
			sb.append("               ?    AS join_id,                                                  \n");
			sb.append("               ?    AS product_no,                                               \n");
			sb.append("               ?    AS join_cnt                                                  \n");
			sb.append("         FROM dual                                                               \n");
			sb.append(" )J2                                                                             \n");
			sb.append(" ON (J1.join_id = J2.join_id AND J1.product_no = J2.product_no)                  \n");
			sb.append(" WHEN MATCHED THEN                                                               \n");
			sb.append(" UPDATE SET J1.join_cnt = J2.join_cnt,                                           \n");
			sb.append("            J1.join_dt = SYSDATE,                                                \n");
			sb.append("            J1.join_status = 10                                                  \n");
			sb.append(" WHEN NOT MATCHED THEN                                                           \n");
			sb.append(" INSERT (J1.join_id , J1.product_no, J1.join_dt, J1.join_cnt, J1.join_status)    \n");
			sb.append(" VALUES (?, ?, SYSDATE ,? ,10)													\n");

			LOG.debug("1.============================");
			LOG.debug("1.query\n" + sb.toString());
			LOG.debug("1.============================");

			conn = connectionMaker.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getJoin_id());
			pstmt.setString(2, vo.getProduct_no());
			pstmt.setString(3, vo.getJoin_cnt());
			pstmt.setString(4, vo.getJoin_id());
			pstmt.setString(5, vo.getProduct_no());
			pstmt.setString(6, vo.getJoin_cnt());

			LOG.debug("2.============================");
			LOG.debug("2.param:" + vo.toString());
			LOG.debug("2.============================");

			flag = pstmt.executeUpdate();

			LOG.debug("3.============================");
			LOG.debug("3.flag:" + flag);
			LOG.debug("3.============================");
		} catch (SQLException e) {
			LOG.debug("======================");
			LOG.debug("SQLException:" + e.toString());
			LOG.debug("======================");
		} finally {
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);
		}
		return flag;
	}

	@Override
	public int do_update(DTO dto) {
		JoinVO vo = (JoinVO) dto;
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = connectionMaker.getConnection();
			
			StringBuilder sb = new StringBuilder();
			sb.append(" UPDATE JOINLIST                    \n");
			sb.append(" SET join_status = '20',      \n");
			sb.append("        join_cnt = '0'      \n");
			sb.append(" WHERE JOIN_ID = ?	       \n");
			sb.append(" AND Product_no = ?	       \n");

			LOG.debug("1.======================");
			LOG.debug("1.query \n" + sb.toString());
			LOG.debug("1.======================");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, vo.getJoin_id());
			pstmt.setString(2, vo.getProduct_no());
			
			LOG.debug("2.======================");
			LOG.debug("2.param :"+vo);
			LOG.debug("2.======================");	
			
			LOG.debug("2.======================");
			LOG.debug("2.param :"+vo.getJoin_id()+","+vo.getProduct_no());
			LOG.debug("2.======================");				
			
			flag = pstmt.executeUpdate();
			LOG.debug("3.======================");
			LOG.debug("3.flag :" + flag);
			LOG.debug("3.======================");

		} catch (SQLException s) {
			LOG.debug("================");
			LOG.debug("SQLException=" + s.toString());
			LOG.debug("================");
		} finally {
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);
		}
		return flag;
	}
	public int do_update2(DTO dto) {
		JoinVO vo = (JoinVO) dto;
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = connectionMaker.getConnection();
			
			StringBuilder sb = new StringBuilder();
			sb.append(" UPDATE JOINLIST             \n");
			sb.append(" SET join_status = '30'     \n");
			sb.append(" WHERE JOIN_ID = ?	       \n");
			sb.append(" AND Product_no = ?	       \n");

			LOG.debug("1.======================");
			LOG.debug("1.query \n" + sb.toString());
			LOG.debug("1.======================");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, vo.getJoin_id());
			pstmt.setString(2, vo.getProduct_no());
			
			LOG.debug("2.======================");
			LOG.debug("2.param :"+vo);
			LOG.debug("2.======================");	
			
			LOG.debug("2.======================");
			LOG.debug("2.param :"+vo.getJoin_id()+","+vo.getProduct_no());
			LOG.debug("2.======================");				
			
			flag = pstmt.executeUpdate();
			LOG.debug("3.======================");
			LOG.debug("3.flag :" + flag);
			LOG.debug("3.======================");

		} catch (SQLException s) {
			LOG.debug("================");
			LOG.debug("SQLException=" + s.toString());
			LOG.debug("================");
		} finally {
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);
		}
		return flag;
	}

	@Override
	public int do_delete(DTO dto) {

		JoinVO vo = (JoinVO) dto;
		int flag = 0;
		Connection conn = null;

		PreparedStatement pstmt = null;

		StringBuilder sb = new StringBuilder();
		sb.append(" DELETE FROM JOINLIST \n");
		sb.append(" WHERE PRODUCT_NO = ? \n");

		try {
			conn = connectionMaker.getConnection();
			// transaction개발자가 관리한다.
			conn.setAutoCommit(false);

			LOG.debug("1======================");
			LOG.debug("query:\n" + sb.toString());
			LOG.debug("1======================");

			pstmt = conn.prepareStatement(sb.toString());
			// query param
			pstmt.setString(1, vo.getProduct_no());
			LOG.debug("2======================");
			LOG.debug("param =" + vo);
			LOG.debug("2======================");

			flag = pstmt.executeUpdate();
			// -transaction
			if (flag > 0) {
				LOG.debug("3======================");
				LOG.debug("transaction=" + conn);
				LOG.debug("3======================");
				// conn.rollback();
				// conn.commit();
			} else {
				// conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);
		}

		LOG.debug("3=====================");
		LOG.debug("flag:" + flag);
		LOG.debug("3=====================");

		return flag;

	}

	@Override
	public JoinVO do_selectOne(DTO dto) {
		JoinVO vo = (JoinVO) dto;
		JoinVO outVO =null;	
		Connection conn = null;//db 연결
		PreparedStatement pstmt = null;//query수행
		ResultSet rs = null;//결과처리 
	
		try{
			StringBuilder sb=new StringBuilder();
			sb.append(" SELECT                            \n");
			sb.append("  	    A.PRODUCT_NO,             \n");
			sb.append(" 	    A.JOIN_ID,                \n");
			sb.append("	    A.JOIN_DT,                    \n");
			sb.append("	    A.JOIN_CNT,                   \n");
			sb.append("	    A.JOIN_STATUS,                \n");
			sb.append("        B.DELIVERY_STATUS,         \n");
			sb.append("        B.PRODUCT_NM               \n");
			sb.append(" FROM  JOINLIST A  JOIN PRODUCT B  \n");    
			sb.append(" ON A.PRODUCT_NO = B.PRODUCT_NO    \n");
			sb.append(" WHERE A.PRODUCT_NO= ?             \n");
			
			conn = connectionMaker.getConnection();
			LOG.debug("1.=============================");
			LOG.debug("1.query \n"+sb.toString());
			LOG.debug("1.=============================");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, vo.getProductNo());
			LOG.debug("2.=============================");
			LOG.debug("2.param product_no="+vo.getProductNo());
			LOG.debug("2.=============================");
			rs = pstmt.executeQuery();
			if(rs.next()){
			outVO = new JoinVO();
			outVO.setProduct_no(rs.getString("PRODUCT_NO"));
			outVO.setJoin_id(rs.getString("JOIN_ID"));
			outVO.setJoin_dt(rs.getString("JOIN_DT"));
			outVO.setJoin_cnt(rs.getString("JOIN_CNT"));
			outVO.setJoin_status(rs.getString("JOIN_STATUS"));
			outVO.setDeliveryStatus(rs.getString("DELIVERY_STATUS"));
			outVO.setProductNm(rs.getString("PRODUCT_NM"));
			}
		}catch(SQLException e){
			LOG.debug("===============");
			LOG.debug("SQLException="+e.getMessage());
			LOG.debug("===============");
			e.printStackTrace();
		}finally{
			JDBCReturnReso.close(rs);
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);		
		}
			return outVO;
	}

	
	public List<JoinVO> do_refund_delete_retrieve(DTO dto) {
		List<JoinVO> list = new ArrayList<>();
		JoinVO vo = (JoinVO) dto;
		JoinVO outVO =null;
		
		Connection conn = null;//db 연결
		PreparedStatement pstmt = null;//query수행
		ResultSet rs = null;//결과처리 
		

		
		StringBuilder sb=new StringBuilder();
		sb.append(" SELECT                            \n");
		sb.append("  	    A.PRODUCT_NO,             \n");
		sb.append(" 	    A.JOIN_ID,                \n");
		sb.append("	    A.JOIN_DT,                    \n");
		sb.append("	    A.JOIN_CNT,                   \n");
		sb.append("	    A.JOIN_STATUS,                \n");
		sb.append("        B.DELIVERY_STATUS,         \n");
		sb.append("        B.PRODUCT_NM               \n");
		sb.append(" FROM  JOINLIST A  JOIN PRODUCT B  \n");    
		sb.append(" ON A.PRODUCT_NO = B.PRODUCT_NO    \n");
		sb.append(" WHERE A.PRODUCT_NO= ?             \n");
		
		LOG.debug("--------------------------------------------------------------");
		LOG.debug("3 do_retrieve sql \n:"+sb.toString());
		LOG.debug("--------------------------------------------------------------");
		try{
			conn = connectionMaker.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			
			//param
			pstmt.setString(1, vo.getProductNo());
			LOG.debug("3 param \n:"+vo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				outVO = new JoinVO();
				outVO.setProduct_no(rs.getString("PRODUCT_NO"));
				outVO.setJoin_id(rs.getString("JOIN_ID"));
				outVO.setJoin_dt(rs.getString("JOIN_DT"));
				outVO.setJoin_cnt(rs.getString("JOIN_CNT"));
				outVO.setJoin_status(rs.getString("JOIN_STATUS"));
				outVO.setDeliveryStatus(rs.getString("DELIVERY_STATUS"));
				outVO.setProductNm(rs.getString("PRODUCT_NM"));
				
				list.add(outVO);
			}
			
		}catch(SQLException e){
			LOG.debug("===============");
			LOG.debug("SQLException="+e.getMessage());
			LOG.debug("===============");
			e.printStackTrace();
		}finally{
			JDBCReturnReso.close(rs);
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);		
		}

		return list;
	}
	
	
	
	
	@Override
	public List<JoinVO> do_retrieve(DTO dto) {
		List<JoinVO> list = new ArrayList<>();
		SearchVO vo = (SearchVO) dto;
		JoinVO outVO =null;
		
		Connection conn = null;//db 연결
		PreparedStatement pstmt = null;//query수행
		ResultSet rs = null;//결과처리 
		

		
		StringBuilder sb=new StringBuilder();
		sb.append(" SELECT T1.*,T2.*                                       \n");                       
		sb.append("     FROM(                                              \n");                       
		sb.append(" SELECT B.rnum as num,                                  \n");                       
		sb.append("     B.JOIN_ID,                                         \n");                       
		sb.append("     C.PRODUCT_NO,                                      \n");                       
		sb.append("     C.REG_DT,                                          \n");                       
		sb.append("     C.TARGET_CNT,                                      \n");                       
		sb.append("     B.JOIN_DT,                                         \n");                       
		sb.append("     B.JOIN_CNT,                                        \n");                       
		sb.append("     B.JOIN_STATUS,                                     \n");                       
		sb.append("     C.PRODUCT_NM,                                      \n");                       
		sb.append("     C.DELIVERY_STATUS,                                 \n");                       
		sb.append("     C.HOST_STATUS                                      \n");                       
		sb.append(" FROM(                                                  \n");                       
		sb.append(" SELECT ROWNUM AS rnum,A.*                              \n");                       
		sb.append(" FROM(                                                  \n");                       
		sb.append(" SELECT *                                               \n");                       
		sb.append("     FROM JOINLIST                                      \n");                       
		sb.append(" 	 WHERE JOIN_ID = ?                                 \n");
		sb.append(" 	 AND   JOIN_STATUS IN('10')                        \n");
		sb.append("     ORDER BY PRODUCT_NO DESC                           \n");                       
		sb.append("  	)A                                                 \n");                        
		sb.append("  WHERE ROWNUM <=( ? * ( ?-1)+ ?)  )B JOIN PRODUCT C    \n");							
		sb.append("  ON B.PRODUCT_NO = C.PRODUCT_NO                        \n");                       
		sb.append("  WHERE B.rnum>= ( ? * ( ?-1)+1)                        \n");					      			
		sb.append("  )T1                                                   \n");                       
		sb.append("  CROSS JOIN                                            \n");                       
		sb.append("  (                                                     \n");                       
		sb.append("  SELECT COUNT(*) total_cnt                             \n");                       
		sb.append("  FROM JOINLIST                                         \n");
		sb.append(" 	 WHERE JOIN_ID = ?                                 \n");
		sb.append(" 	 AND   JOIN_STATUS IN('10')                        \n");
		sb.append("  )T2                                                   \n");
		
		LOG.debug("--------------------------------------------------------------");
		LOG.debug("3 do_retrieve sql \n:"+sb.toString());
		LOG.debug("--------------------------------------------------------------");
		try{
			conn = connectionMaker.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			
			//param
			pstmt.setString(1, vo.getSearchWord());
			pstmt.setInt(2, vo.getPageSize());
			pstmt.setInt(3, vo.getPageNum());
			pstmt.setInt(4, vo.getPageSize());
			pstmt.setInt(5, vo.getPageSize());
			pstmt.setInt(6, vo.getPageNum());
			pstmt.setString(7, vo.getSearchWord());
			LOG.debug("3 param \n:"+vo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				outVO = new JoinVO();
				outVO.setJoin_id(rs.getString("JOIN_ID"));
				outVO.setProduct_no(rs.getString("PRODUCT_NO"));
				outVO.setRegDt(rs.getString("REG_DT"));
				outVO.setTargetCnt(rs.getString("TARGET_CNT"));
				outVO.setJoin_dt(rs.getString("JOIN_DT"));
				outVO.setJoin_cnt(rs.getString("JOIN_CNT"));
				outVO.setJoin_status(rs.getString("JOIN_STATUS"));
				outVO.setProductNm(rs.getString("PRODUCT_NM"));
				outVO.setDeliveryStatus(rs.getString("DELIVERY_STATUS"));
				outVO.setHostStatus(rs.getString("HOST_STATUS"));
				outVO.setTotal(rs.getInt("total_cnt"));
				LOG.debug("--------------------------------------------------------------");
				LOG.debug("3 do_retrieve outVO \n:"+outVO);
				LOG.debug("--------------------------------------------------------------");
			list.add(outVO);
			}
		}catch(SQLException e){
			LOG.debug("===============");
			LOG.debug("SQLException="+e.getMessage());
			LOG.debug("===============");
			e.printStackTrace();
		}finally{
			JDBCReturnReso.close(rs);
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);		
		}

		return list;
	}
	
	
	
	public List<JoinVO> do_retrieve2(DTO dto) {
		List<JoinVO> list = new ArrayList<>();
		SearchVO vo = (SearchVO) dto;
		JoinVO outVO =null;
		
		Connection conn = null;//db 연결
		PreparedStatement pstmt = null;//query수행
		ResultSet rs = null;//결과처리 	

		//SearchVO에서 SearchWord = '아이디008'로 선지정해주어야 작동 가능.
		StringBuilder sb=new StringBuilder();
		sb.append(" SELECT T1.*,T2.*                                      \n");                
		sb.append("     FROM(                                             \n");                
		sb.append(" SELECT B.rnum as num,                                 \n");                
		sb.append("     B.JOIN_ID,                                        \n");                
		sb.append("     C.PRODUCT_NO,                                     \n");                
		sb.append("     C.REG_DT,                                         \n");                
		sb.append("     C.TARGET_CNT,                                     \n");                
		sb.append("     B.JOIN_DT,                                        \n");                
		sb.append("     B.JOIN_CNT,                                       \n");                
		sb.append("     B.JOIN_STATUS,                                    \n");                
		sb.append("     C.PRODUCT_NM,                                     \n");                
		sb.append("     C.DELIVERY_STATUS,                                \n");                
		sb.append("     C.HOST_STATUS                                     \n");                
		sb.append(" FROM(                                                 \n");                
		sb.append(" SELECT ROWNUM AS rnum,A.*                             \n");                
		sb.append(" FROM(                                                 \n");                
		sb.append(" SELECT *                                              \n");                
		sb.append("     FROM JOINLIST                                     \n");                
		sb.append(" 	 WHERE JOIN_STATUS IN('20','30')                  \n");
		sb.append("      AND JOIN_ID = ?                                  \n");
		sb.append("     ORDER BY PRODUCT_NO DESC                          \n");                
		sb.append("  	)A                                                \n");                 
		sb.append("  WHERE ROWNUM <=( ? * ( ?-1)+ ?)  )B JOIN PRODUCT C   \n");					
		sb.append("  ON B.PRODUCT_NO = C.PRODUCT_NO                       \n");                
		sb.append("  WHERE B.rnum>= ( ? * ( ?-1)+1)                       \n");			      			
		sb.append("  )T1                                                  \n");                
		sb.append("  CROSS JOIN                                           \n");                
		sb.append("  (                                                    \n");                
		sb.append("  SELECT COUNT(*) total_cnt                            \n");                
		sb.append("  FROM JOINLIST                                        \n");  
		sb.append("  WHERE JOIN_STATUS IN('20','30')                      \n");
		sb.append("  AND JOIN_ID = ?                                      \n");
		sb.append("  )T2                                                  \n");
		
		LOG.debug("--------------------------------------------------------------");
		LOG.debug("3 do_retrieve sql \n:"+sb.toString());
		LOG.debug("--------------------------------------------------------------");
		try{
			conn = connectionMaker.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			//param
			pstmt.setString(1, vo.getSearchWord());
			pstmt.setInt(2, vo.getPageSize());
			pstmt.setInt(3, vo.getPageNum());
			pstmt.setInt(4, vo.getPageSize());
			pstmt.setInt(5, vo.getPageSize());
			pstmt.setInt(6, vo.getPageNum());
			pstmt.setString(7, vo.getSearchWord());
			
			LOG.debug("3 param \n:"+vo);
			rs = pstmt.executeQuery();
			while(rs.next()){
			outVO = new JoinVO();
			outVO.setJoin_id(rs.getString("JOIN_ID"));
			outVO.setProduct_no(rs.getString("PRODUCT_NO"));
			outVO.setRegDt(rs.getString("REG_DT"));
			outVO.setTargetCnt(rs.getString("TARGET_CNT"));
			outVO.setJoin_dt(rs.getString("JOIN_DT"));
			outVO.setJoin_cnt(rs.getString("JOIN_CNT"));
			outVO.setJoin_status(rs.getString("JOIN_STATUS"));
			outVO.setProductNm(rs.getString("PRODUCT_NM"));
			outVO.setDeliveryStatus(rs.getString("DELIVERY_STATUS"));
			outVO.setHostStatus(rs.getString("HOST_STATUS"));
			outVO.setTotal(rs.getInt("total_cnt"));
			LOG.debug("--------------------------------------------------------------");
			LOG.debug("3 do_retrieve outVO \n:"+outVO);
			LOG.debug("--------------------------------------------------------------");
			list.add(outVO);
			}
		}catch(SQLException e){
			LOG.debug("===============");
			LOG.debug("SQLException="+e.getMessage());
			LOG.debug("===============");
			e.printStackTrace();
		}finally{
			JDBCReturnReso.close(rs);
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);		
		}		
		return list;
	}
}