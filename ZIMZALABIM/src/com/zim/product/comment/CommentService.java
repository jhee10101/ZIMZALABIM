 /**
 * @Class Name : CommentService.java
 * @Description : 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2019. 7. 15.           최초생성
 *
 * @author Zimzalabim
 * @since 2019. 7. 15. 
 * @version 1.0
 * @see
 *
 *  Copyright (C) by HR. KIM All right reserved.
 */
package com.zim.product.comment;

import java.util.List;

import org.apache.log4j.Logger;

import com.zim.cmn.DTO;

/**
 * @author SIST
 *
 */
public class CommentService {

	// View -> Controller -> Service -> Dao

	private final Logger LOG=Logger.getLogger(CommentDao.class);
	
	private CommentDao commentDao;
	
	/**
	 * 
	 * <PRE>
	 * 1. MethodName : HrMemberService
	 * 2. ClassName  : HrMemberService
	 * 3. Comment   : 생성자의 기능 = 초기화!!!
	 * 4. 작성자    : SIST
	 * 5. 작성일    : 2019. 7. 19. 오전 9:12:53
	 * </PRE>
	 */
	public CommentService(){ // 생성자의 기능 = 초기화
		commentDao = new CommentDao();
	}
	
	
	
	//댓글삽입
	public int do_insert(DTO dto) {
		return commentDao.do_insert(dto);
	}
	
	//댓글수정
	public int do_update(DTO dto){
		return commentDao.do_update(dto);
	}
	
	
	//댓글삭제
	public int do_delete(DTO dto) {
		return commentDao.do_delete(dto);
	}
	
	
	//댓글 조회
	public List<CommentVO> do_retrieve(DTO dto){
		return (List<CommentVO>) commentDao.do_retrieve(dto);
	}



	

	
}
