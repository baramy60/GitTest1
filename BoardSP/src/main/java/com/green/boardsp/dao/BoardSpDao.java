package com.green.boardsp.dao;

import java.util.HashMap;
import java.util.List;

import com.green.boardsp.vo.BoardVo;

public interface BoardSpDao {

	List<BoardVo> getBoardList(HashMap<String, Object> map);

	BoardVo getView(HashMap<String, Object> map);

	void updateBoard(HashMap<String, Object> map);

	void deleteBoard(HashMap<String, Object> map);

	void insertBoard(HashMap<String, Object> map);

}
