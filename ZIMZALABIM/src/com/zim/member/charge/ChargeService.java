 /**
 * @Class Name : ChargeService.java
 * @Description : 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2019. 8. 9.           최초생성
 *
 * @author Zimzalabim
 * @since 2019. 8. 9. 
 * @version 1.0
 * @see
 *
 *  Copyright (C) by HR. KIM All right reserved.
 */
package com.zim.member.charge;

import org.apache.log4j.Logger;

import com.zim.cmn.DTO;
import com.zim.member.MemberDao;
import com.zim.member.MemberVO;

/**
 * @author sist
 *
 */
public class ChargeService {
	private final Logger LOG = Logger.getLogger(MemberDao.class);
	MemberDao memberDao = new MemberDao();
	
	/**
	 * 
	 * @Method Name  : do_checkPw
	 * @작성일   : 2019. 7. 19.
	 * @작성자   : sist
	 * @변경이력  : 최초작성
	 * @Method 설명 : 성공 1
	 * @return
	 */
	//포인트 충전
	public int do_point_charge(DTO dto){		
		MemberVO vo = (MemberVO) dto; //충전자 ID, 충전 금액
		MemberVO oldVO = memberDao.do_selectOne(vo); //기존  정보
		int oldPoint = Integer.parseInt(oldVO.getPoint());//기존 보유 포인트
		int newPoint = Integer.parseInt(vo.getPoint());//새로 충전할 포인트
		newPoint = oldPoint+newPoint;//새 포인트 = 기존포인트+충전포인트
		
		vo.setPoint(Integer.toString(newPoint));//새 포인트를 담아서
		
		return memberDao.do_point_update(vo);
	}
	
	//포인트 사용(결제시)
	public int do_point_payment(DTO dto, String join_Cnt){		
		MemberVO vo = (MemberVO) dto; //결제자 ID, 결제 가격
		MemberVO oldVO = memberDao.do_selectOne(vo); //기존  정보
		int oldPoint = Integer.parseInt(oldVO.getPoint());//기존 보유 포인트
		int paymentPoint = Integer.parseInt(vo.getPoint());//사용한 포인트
		int joinCnt = Integer.parseInt(join_Cnt);
		paymentPoint = paymentPoint*joinCnt; // 상품금액*구매수량		

		paymentPoint = oldPoint-paymentPoint;//결제 후 남은 포인트
	
		vo.setPoint(Integer.toString(paymentPoint));//남은 포인트를 담아서
		
		return memberDao.do_point_update(vo);//업데이트
	}
	
	//사용가능 포인트 존재여부(사용하려는 포인트만큼 잔액이 남아있는지 확인)
	public int do_point_exist(DTO dto, String join_Cnt){
		MemberVO vo = (MemberVO) dto; //결제자 ID, 결제 가격
		MemberVO oldVO = memberDao.do_selectOne(vo); //기존  정보
		int oldPoint = Integer.parseInt(oldVO.getPoint());//기존 보유 포인트
		int paymentPoint = Integer.parseInt(vo.getPoint());//사용한 포인트
		int joinCnt = Integer.parseInt(join_Cnt);
		paymentPoint = paymentPoint*joinCnt; // 상품금액*구매수량
		
		if(oldPoint >= paymentPoint){
			return 1;
		}else{
			return 0;
		}		
	}
	
	//포인트 환불 (결제 취소시 포인트 환불받음)
	public int do_point_refund(DTO dto, String join_cnt){		
		MemberVO vo = (MemberVO) dto; //유저 ID, 환불
		MemberVO oldVO = memberDao.do_selectOne(vo); //기존  정보
		int oldPoint = Integer.parseInt(oldVO.getPoint());//기존 보유 포인트
		int newPoint = Integer.parseInt(vo.getPoint());//환불 받을 포인트
		int joinCnt = Integer.parseInt(join_cnt);
		newPoint = oldPoint+(newPoint*joinCnt);//새 포인트 = 기존포인트+환불포인트
		
		vo.setPoint(Integer.toString(newPoint));//새 포인트를 담아서
		
		return memberDao.do_point_update(dto);
	}
	
}
