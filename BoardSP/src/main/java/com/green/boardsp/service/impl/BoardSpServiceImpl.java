package com.green.boardsp.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.boardsp.dao.BoardSpDao;
import com.green.boardsp.service.BoardSpService;
import com.green.boardsp.vo.BoardVo;

@Service("boardSpService")
public class BoardSpServiceImpl implements  BoardSpService {

	@Autowired
	private BoardSpDao boardSpDao;
	
	@Override
	public List<BoardVo> getBoardList(HashMap<String, Object> map) {
		 List<BoardVo> boardList = boardSpDao.getBoardList( map ); 
		 return        boardList;
	}

	@Override
	public BoardVo getView(HashMap<String, Object> map) {
		
		BoardVo    boardVo  =  boardSpDao.getView( map );		
		return     boardVo;
		
	}

	@Override
	public void updateBoard(HashMap<String, Object> map) {
		
		boardSpDao.updateBoard( map );
		
	}

	@Override
	public void deleteBoard(HashMap<String, Object> map) {
		
		boardSpDao.deleteBoard( map ); 
		
	}

	@Override
	public void insertBoard(HashMap<String, Object> map) {
		
		boardSpDao.insertBoard(  map ); 
		
	}

}





