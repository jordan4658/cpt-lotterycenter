package com.mapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class EmptyStringIfNull implements TypeHandler<String> {

    public String getResult(ResultSet rs, String columnName) throws SQLException {
     return (rs.getString(columnName) == null) ? "" : rs.getString(columnName); 
    }

   
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
     return (rs.getString(columnIndex) == null) ? "" : rs.getString(columnIndex);
    }
    
    public String getResult(CallableStatement cs, int columnIndex)   throws SQLException {
     return (cs.getString(columnIndex) == null) ? "" : cs.getString(columnIndex);
    }
    
    public void setParameter(PreparedStatement ps, int arg1, String str, JdbcType jdbcType) throws SQLException { }
}
