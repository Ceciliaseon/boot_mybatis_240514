package com.ezen.www.service;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;

    @Override
    public int insert(MemberVO mvo) {
        int isOk = memberMapper.insert(mvo);
        return (isOk > 0 ? memberMapper.insertAuth(mvo.getEmail()) : 0) ;
    }

    @Override
    public List<MemberVO> getList() {
        List<MemberVO> memberList = memberMapper.getList();
        for (MemberVO mvo : memberList){
            mvo.setAuthList(memberMapper.selectAuth(mvo.getEmail()));
        }
        log.info(" memberList >> {}", memberList);
        return memberList;
    }

    @Override
    public void nameUpdate(MemberVO mvo) {
        memberMapper.nameUpdate(mvo);
    }

    @Override
    public void updateAll(MemberVO mvo) {
        memberMapper.updateAll(mvo);
    }

    @Override
    public void remove(String name) {
        memberMapper.removeAuth(name);
        memberMapper.removeUser(name);
    }
}
