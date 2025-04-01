package com.altkey.code.common.mybatis;

import com.altkey.code.common.enums.KeyEnum;
import com.altkey.code.common.utils.EnumUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.lang.ref.WeakReference;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-04-01
 */
public class KeyEnumTypeHandler<E extends Enum<E> & KeyEnum<E>> implements TypeHandler<E> {
    //
    private final Class<E> type;

    public KeyEnumTypeHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        final String name = new WeakReference<>(parameter.getKey().getClass().getSimpleName()).get();
        switch (Objects.requireNonNull(name)) {
            case "Character":
            case "String":
                ps.setString(i, String.valueOf(parameter.getKey()));
                break;
            case "Integer":
                ps.setInt(i, Integer.parseInt(String.valueOf(parameter.getKey())));
                break;
            case "Boolean":
                ps.setBoolean(i, Boolean.parseBoolean(String.valueOf(parameter.getKey())));
                break;
            case "Long":
                ps.setLong(i, Long.parseLong(String.valueOf(parameter.getKey())));
                break;
            case "Double":
                ps.setDouble(i, Double.parseDouble(String.valueOf(parameter.getKey())));
                break;
            case "Float":
                ps.setFloat(i, Float.parseFloat(String.valueOf(parameter.getKey())));
                break;
            case "Short":
                ps.setShort(i, Short.parseShort(String.valueOf(parameter.getKey())));
                break;
            case "Byte":
                ps.setByte(i, Byte.parseByte(String.valueOf(parameter.getKey())));
                break;
            default:
                throw new SQLException("Unsupported type: " + name);
        }
    }

    @Override
    public E getResult(ResultSet rs, String columnName) throws SQLException {
        final int index = rs.findColumn(columnName);
        final int columnType = rs.getMetaData().getColumnType(index);
        return this.getResultSet(rs, columnType, index);
    }

    @Override
    public E getResult(ResultSet rs, int columnIndex) throws SQLException {
        final int columnType = rs.getMetaData().getColumnType(columnIndex);
        return this.getResultSet(rs, columnType, columnIndex);
    }

    @Override
    public E getResult(CallableStatement cs, int columnIndex) throws SQLException {
        final int columnType = cs.getMetaData().getColumnType(columnIndex);
        return switch (columnType) {
            case java.sql.Types.VARCHAR, java.sql.Types.CHAR -> EnumUtils.getKeyEnum(type, cs.getString(columnIndex));
            case java.sql.Types.INTEGER -> EnumUtils.getKeyEnum(type, String.valueOf(cs.getInt(columnIndex)));
            case java.sql.Types.BIGINT -> EnumUtils.getKeyEnum(type, cs.getLong(columnIndex));
            case java.sql.Types.DOUBLE -> EnumUtils.getKeyEnum(type, cs.getDouble(columnIndex));
            case java.sql.Types.FLOAT -> EnumUtils.getKeyEnum(type, cs.getFloat(columnIndex));
            case java.sql.Types.SMALLINT -> EnumUtils.getKeyEnum(type, cs.getShort(columnIndex));
            case java.sql.Types.TINYINT -> EnumUtils.getKeyEnum(type, cs.getByte(columnIndex));
            case java.sql.Types.BOOLEAN -> EnumUtils.getKeyEnum(type, cs.getBoolean(columnIndex));
            default -> throw new SQLException("Unsupported type: ".concat(String.valueOf(type)));
        };
    }

    private E getResultSet(ResultSet rs, int columnType, int index) throws SQLException {
        //
        return switch (columnType) {
            case java.sql.Types.VARCHAR, java.sql.Types.CHAR -> EnumUtils.getKeyEnum(type, rs.getString(index));
            case java.sql.Types.INTEGER -> EnumUtils.getKeyEnum(type, String.valueOf(rs.getInt(index)));
            case java.sql.Types.BIGINT -> EnumUtils.getKeyEnum(type, rs.getLong(index));
            case java.sql.Types.DOUBLE -> EnumUtils.getKeyEnum(type, rs.getDouble(index));
            case java.sql.Types.FLOAT -> EnumUtils.getKeyEnum(type, rs.getFloat(index));
            case java.sql.Types.SMALLINT -> EnumUtils.getKeyEnum(type, rs.getShort(index));
            case java.sql.Types.TINYINT -> EnumUtils.getKeyEnum(type, rs.getByte(index));
            case java.sql.Types.BOOLEAN -> EnumUtils.getKeyEnum(type, rs.getBoolean(index));
            default -> throw new SQLException("Unsupported type: ".concat(String.valueOf(type)));
        };
    }
}
