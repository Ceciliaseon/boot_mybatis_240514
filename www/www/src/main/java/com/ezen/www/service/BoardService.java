package com.ezen.www.service;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.PagingVO;

import java.util.List;

public interface BoardService {
    int insert(BoardDTO bdto);

    List<BoardVO> getList(PagingVO pgvo);

    BoardDTO getDetail(long bno);

    void update(BoardVO bvo);

    void remove(long bno);

    int getTotal(PagingVO pgvo);
}
