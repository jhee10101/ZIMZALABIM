 /**
 * @Class Name : ResisterService.java
 * @Description : 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2019. 7. 18.           최초생성
 *
 * @author Zimzalabim
 * @since 2019. 7. 18. 
 * @version 1.0
 * @see
 *
 *  Copyright (C) by HR. KIM All right reserved.
 */
package com.zim.member.resister;

import com.zim.cmn.DTO;
import com.zim.member.MemberDao;
import com.zim.member.MemberVO;

public class ResisterService {
	MemberDao dao = new MemberDao();
	
	public int do_resister(DTO dto){
		int flag = 0;
		MemberVO vo = (MemberVO) dto;
		flag = dao.do_insert(vo);		
		return flag;
	}
}
