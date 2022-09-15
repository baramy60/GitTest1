package com.green.board.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.board.dao.BoardDao;
import com.green.board.service.BoardService;
import com.green.board.vo.BoardVo;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	private  BoardDao   boardDao;
	
	@Override
	public List<BoardVo> getBoardList(String menu_id) {
		
		List<BoardVo>  boardList =  boardDao.getBoardList( menu_id );		
		return         boardList;
		
	}

	@Override
	public void boardInsert(BoardVo boardVo) {
		
		boardDao.boardInsert( boardVo );
		
	}

	@Override
	public BoardVo getBoard(HashMap<String, Object> map) {
		
		BoardVo  boardVo  =  boardDao.getBoard( map );
		return   boardVo;
		
	}

	@Override
	public void boardUpdate(HashMap<String, Object> map) {
		
		boardDao.boardUpdate( map );
		
	}

	@Override
	public void boardDelete(HashMap<String, Object> map) {
		
		boardDao.boardDelete( map ) ;
		
	}

}






