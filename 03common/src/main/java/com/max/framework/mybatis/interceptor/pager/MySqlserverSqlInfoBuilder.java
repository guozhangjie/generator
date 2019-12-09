package com.max.framework.mybatis.interceptor.pager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;

import com.max.framework.bean.PagerBean;

/**
 * SQL server分页器
 * @author 马青松
 */
public class MySqlserverSqlInfoBuilder implements SqlInfoBuilder {
    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(MySqlserverSqlInfoBuilder.class);

    /**
     * {@inheritDoc}
     */
    public String buildPageSql(String sql, PagerBean page) {
        return sql;
    }

    /**
     * {@inheritDoc}
     */
    public void setPageParamCount(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, PagerBean page) {
        // 检索到FROM关键字位置
        String mainSql = getRealSearchSql(sql);

        // 记录总记录数
        String countSql = "SELECT count(1) " + mainSql;
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            logger.info(countSql);
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBs = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(),
                    boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBs, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotal(totalCount);
        } catch (SQLException ex) {
            logger.error("Ignore this exception", ex);
        } finally {
        }
    }

    /**
     * 代入参数值
     * @param ps ps
     * @param mappedStatement mappedStatement
     * @param boundSql boundSql
     * @param parameterObject parameterObject
     * @throws SQLException SQLException
     */
    public void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    /**
     * 获得主SQL内容
     * @param sql SQL内容
     * @return 主SQL内容
     */
    private String getRealSearchSql(String sql) {
        // 转大写
        String newSql = sql.toUpperCase();
        // 防止死循环设置最大检索20层
        int count = 0;
        // 找到第一个SELECT之后的位置
        int index = newSql.indexOf("SELECT") + 6;
        while (true) {
            // 检索是否有成对的SELECT..FROM
            int selectIndex = newSql.indexOf("SELECT", index + 1);
            int fromIndex = newSql.indexOf("FROM", index + 1);

            if (selectIndex > fromIndex) {
                // SELECT在FROM之后，直接找到对应的from(说明是WHERE之后的子查询)
                index = fromIndex;
                break;
            } else if (selectIndex < 0) {
                // 之后没有SELECT也直接返回,说明是简单的SQL
                index = fromIndex;
                break;
            } else {
                // 成对SELECT..FROM抵消后,继续检索抵消
                index = fromIndex + 1;
            }

            // 最大支持20个子查询
            if (count++ > 20) {
                break;
            }
        }
        return sql.substring(index, sql.lastIndexOf("LIMIT"));
    }
}
