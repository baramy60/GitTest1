package com.green.pdssp.service.impl;

import java.io.File;

public class CheckFileName {
	
	public   String   getCheckFileName(
		String filePath, String orgFileName, String fileExt	) {
		
		boolean  isCheck         = true;
		String   returnFileName  = null;
		
		String   fullFilePath    = null;
		File     file            = null;
		
		int i = 0;
		while( isCheck ) {
			fullFilePath  = filePath + orgFileName + fileExt;
			file          = new File( fullFilePath );
			if(  file.exists() ) {
				i += 1;
				orgFileName = orgFileName + String.valueOf(i);
			} else {
				isCheck = false;
			}
		}
		
		returnFileName = orgFileName + fileExt;
		
		return  returnFileName;
	}
}




