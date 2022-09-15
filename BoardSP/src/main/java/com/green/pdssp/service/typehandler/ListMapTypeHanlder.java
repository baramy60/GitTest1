package com.green.pdssp.service.typehandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

/*
 * ListMapTypeHandler
 * 마이바티스가 파이미터를 PreparedStatement 에 세팅하거나,
 * resultSet ㅇ,로 부터ㅏ 값을 추적할때 마다, 타입=핸들러는 자바타일을  
 * 추적하기 위해 사용한다
 * 지원하지 않거나 표준타입이 아닌 것을 다루기 휘한 방법
 * 타입핸들러         <->   자바타입           <-> JDBC 타입
 * StringTypeHandler        java.lang.String       CHAR. VARCHAR 
 */

public class ListMapTypeHanlder implements TypeHandler<Object> {

	@Override
	public void setParameter(PreparedStatement ps, int i,
			Object parameter, JdbcType jdbcType) 
					throws SQLException {
		System.out.println("parameter:" + parameter);
		
		List<String>  list = (List<String>) parameter;
		
		// db연결
	/*
		CommonsDbcpNativeJdbcExtractor extractor
		  = new CommonsDbcpNativeJdbcExtractor();
		Connection  conn = extractor.getNativeConnection(
			ps.getConnection()	);
			*/
		OracleConnection conn = (ps.getConnection()).unwrap(OracleConnection.class);
		
		ArrayDescriptor  desc
		  = ArrayDescriptor.createDescriptor("FILE_ARRAY", conn);
		
		// 남어온 파일명이 하나도 없을때
		//  i 번째 파라미터 배열 방안에 null 값을 지정
		if(list == null || list.size() == 0) {
			
			ps.setArray( i,
			   new ARRAY(desc, conn, new String[] { null } ) );
			
			return;
		}
			
		// 넘어온 파일목록 처리
		// in_filename(1) = list.get(j)
		String []   strings  =  new String[list.size()];
		for (int j = 0; j < list.size(); j++) {
			strings[j]  = list.get(j);
		}
		
		parameter  = new ARRAY(desc, conn, strings);
		
		ps.setArray(i, ( oracle.sql.ARRAY ) parameter );
		
		System.out.println("ps:" + ps);
		
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		
		if( cs.wasNull() ) {
			return null;
		} else {
			return  cs.getString( columnIndex );
		}
		
	}
	
	

}
