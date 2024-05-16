package com.ezen.www.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String email;
    private String pwd;
    private String nickName;
    private String reaAt;
    private String lastLogin;
    private List<AuthVO> authList;
}
