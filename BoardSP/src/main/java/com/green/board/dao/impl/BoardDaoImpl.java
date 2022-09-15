package com.green.board.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.board.dao.BoardDao;
import com.green.board.vo.BoardVo;

@Repository("boardDao")
public class BoardDaoImpl implements BoardDao {

	@Autowired
	private  SqlSession   sqlSession;
	
	@Override
	public List<BoardVo> getBoardList(String menu_id) {
		
		// All 클릭 -> BoardDaoImpl getBoardList() menu_id:null
		List<BoardVo>  boardList  
		   =  sqlSession.selectList("Board.BoardList", menu_id);
		return         boardList;
	}

	@Override
	public void boardInsert(BoardVo boardVo) {
		
		int bnum = boardVo.getBnum();
		if(bnum == 0) { // 새글쓰기
			sqlSession.insert("Board.BoardInsert", boardVo);
		} else {        // 답글 쓰기
			sqlSession.update("Board.UpdateRef",   boardVo);
			sqlSession.insert("Board.BoardReply",  boardVo);
		}
		
	}

	@Override
	public BoardVo getBoard(HashMap<String, Object> map) {
		// 조회수 증가
		sqlSession.update("Board.ReadCountUpdate", map );
		
		// idx 로 게시물 조회
		BoardVo  boardVo = sqlSession.selectOne("Board.Board", map); 
		map.put("totCnt", 12345);
		return   boardVo;
		
	}

	@Override
	public void boardUpdate(HashMap<String, Object> map) {
		
		sqlSession.update("Board.BoardUpdate", map );
		
	}

	@Override
	public void boardDelete(HashMap<String, Object> map) {
		
		sqlSession.delete("Board.BoardDelete", map);
		
	}

}






