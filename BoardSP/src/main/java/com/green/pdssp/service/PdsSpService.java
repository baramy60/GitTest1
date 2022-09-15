package com.green.pdssp.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.green.pdssp.vo.FilesVo;
import com.green.pdssp.vo.PdsVo;

public interface PdsSpService {

	List<PdsVo> getPdsList(HashMap<String, Object> map);

	PdsVo getPdsView(HashMap<String, Object> map);

	List<FilesVo> getFileList(HashMap<String, Object> map);

	void setWrite(HashMap<String, Object> map, HttpServletRequest request);



}
