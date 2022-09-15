package com.green.board.dao;

import java.util.HashMap;
import java.util.List;

import com.green.board.vo.BoardVo;

public interface BoardDao {

	List<BoardVo> getBoardList(String menu_id);

	void boardInsert(BoardVo boardVo);

	BoardVo getBoard(HashMap<String, Object> map);

	void boardUpdate(HashMap<String, Object> map);

	void boardDelete(HashMap<String, Object> map);

}
