package com.ezen.www.service;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentMapper commentMapper;

    @Override
    public int post(CommentVO cvo) {
        return commentMapper.post(cvo);
    }

    @Transactional
    @Override
    public PagingHandler getList(long bno, PagingVO pgvo) {
        log.info(">>> bno, pgvo >>"+ bno +"/"+pgvo);
        int totalCount = commentMapper.bnoTotalCount(bno);
        List<CommentVO> cmtlist = commentMapper.getList(bno, pgvo);
        PagingHandler ph = new PagingHandler(pgvo, totalCount, cmtlist);
        return ph;
    }

    @Override
    public int modify(CommentVO cvo) {
        return commentMapper.update(cvo);
    }

    @Override
    public int remove(long cno) {
        return commentMapper.delete(cno);
    }

}
