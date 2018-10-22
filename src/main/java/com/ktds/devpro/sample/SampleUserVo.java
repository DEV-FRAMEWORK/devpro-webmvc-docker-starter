package com.ktds.devpro.sample;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 *
 * 샘플 사용자 VO 클래스
 * <p>
 *
 * <pre>
 * 개정이력(Modification Information)·
 * 수정일   수정자    수정내용
 * ------------------------------------
 * 2017. 3. 16.   kt ds     최초작성
 * </pre>
 *
 * @author kt ds A.CoE(yu.chae@kt.com)
 * @since 2017. 3. 16.
 * @version 1.0.0
 * @see
 *
 */
@Data
@ToString
@Alias("sampleUserVo")
public class SampleUserVo {

    /**
     * 사용자 아이디
     */
    private String userId;

    /**
     * 비밀번호
     */
    private String password;

    /**
     * 사용자 이름
     */
    private String userName;

    /**
     * 등록일
     */
    private String createDate;
    
    private String[] roles;

	public SampleUserVo(String userName, String password,  String... roles) {
		super();
		this.password = password;
		this.userName = userName;
		this.roles = roles;
	}
    
    
    
}
