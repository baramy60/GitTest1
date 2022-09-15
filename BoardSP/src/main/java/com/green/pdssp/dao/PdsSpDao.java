package com.green.pdssp.dao;

import java.util.HashMap;
import java.util.List;

import com.green.pdssp.vo.FilesVo;
import com.green.pdssp.vo.PdsVo;

public interface PdsSpDao {

	List<PdsVo> getPdsList(HashMap<String, Object> map);

	PdsVo getPdsView(HashMap<String, Object> map);

	List<FilesVo> getFileList(HashMap<String, Object> map);

	void setWrite(HashMap<String, Object> map);


}
