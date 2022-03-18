package com.mapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class EmptyDoubleIfNull implements TypeHandler<Double> {

    public Double getResult(ResultSet rs, String columnName) throws SQLException {
     return (rs.getObject(columnName) == null) ? 0d : rs.getDouble(columnName); 
    }

    public Double getResult(ResultSet rs, int columnIndex) throws SQLException {
     return (rs.getObject(columnIndex) == null) ? 0d : rs.getDouble(columnIndex);
    }
    public Double getResult(CallableStatement cs, int columnIndex)   throws SQLException {
     return (cs.getObject(columnIndex) == null) ? 0d : cs.getDouble(columnIndex);
    }
    public void setParameter(PreparedStatement ps, int arg1, Double str, JdbcType jdbcType) throws SQLException { }
}
