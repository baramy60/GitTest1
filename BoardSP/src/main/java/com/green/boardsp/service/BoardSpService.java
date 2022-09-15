package com.green.boardsp.service;

import java.util.HashMap;
import java.util.List;

import com.green.boardsp.vo.BoardVo;

public interface BoardSpService {

	List<BoardVo> getBoardList(HashMap<String, Object> map);

	BoardVo getView(HashMap<String, Object> map);

	void updateBoard(HashMap<String, Object> map);

	void deleteBoard(HashMap<String, Object> map);

	void insertBoard(HashMap<String, Object> map);


}
