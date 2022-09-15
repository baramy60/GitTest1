package com.green.boardsp.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.boardsp.dao.BoardSpDao;
import com.green.boardsp.vo.BoardVo;

@Repository("boardSpDao")
public class BoardSpDaoImpl implements BoardSpDao {

	@Autowired
	private  SqlSession  sqlSession;
	
	@Override
	public List<BoardVo> getBoardList(HashMap<String, Object> map) {
		// stored procedure 사용할 경우
		// List<BoardVo> boardList = sqlSession.selectList("", map);  X 결과를 받을 수 없다
		
		// stored procedure 사용시 결과 받기
		// 프로시저 실행 후 map  일 결과를 돌려 받음
		sqlSession.selectList("BoardSp.BoardList", map);  // procedure 를 실행
		List<BoardVo> boardList =  (List<BoardVo>) map.get("result"); //out 파라미터 결과
		return        boardList;
	}

	@Override
	public BoardVo getView(HashMap<String, Object> map) {
		
		sqlSession.selectList("BoardSp.BoardView", map);
		List<BoardVo> boardList  =  (List<BoardVo>) map.get("result");
		BoardVo       boardVo    =  boardList.get(0);
		return        boardVo;
		
	}

	@Override
	public void updateBoard(HashMap<String, Object> map) {
		
		sqlSession.update("BoardSp.UpdateBoard", map);
		
	}

	@Override
	public void deleteBoard(HashMap<String, Object> map) {
		
		sqlSession.delete("BoardSp.DeleteBoard", map);
		
	}

	@Override
	public void insertBoard(HashMap<String, Object> map) {
		
		sqlSession.insert("BoardSp.InsertBoard", map);
		
	}

}





