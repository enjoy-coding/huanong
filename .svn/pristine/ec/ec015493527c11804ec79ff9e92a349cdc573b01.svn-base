package com.feather.common.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class StringBigintConverter extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        Date date = DateBigintConverter.parse(parameter);
        long val = DateBigintConverter.format(date);
        ps.setLong(i, val);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        long val = rs.getLong(columnName);
        return val > 0 ? String.valueOf(val) : "";
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        long val = rs.getLong(columnIndex);
        return val > 0 ? String.valueOf(val) : "";
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        long val = cs.getLong(columnIndex);
        return val > 0 ? String.valueOf(val) : "";
    }
}
