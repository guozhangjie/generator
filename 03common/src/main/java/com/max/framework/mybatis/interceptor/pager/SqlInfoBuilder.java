package com.max.framework.mybatis.interceptor.pager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import com.max.framework.bean.PagerBean;

/**
 * 分页SQL生成器接口
 * @author 马青松
 * @since 1.0
 */
public interface SqlInfoBuilder {
    /** 
     * 修改原SQL为分页SQL 
     * @param sql  sql
     * @param page page
     * @return 分页内容
     */  
    String buildPageSql(String sql, PagerBean page);
    
    /** 
     * 获取总记录数 
     * @param sql sql
     * @param connection  connection
     * @param mappedStatement  mappedStatement
     * @param boundSql boundSql
     * @param page page
     */  
    void setPageParamCount(String sql, Connection connection, MappedStatement mappedStatement,  
            BoundSql boundSql, PagerBean page) ;
    
    /** 
     * 代入参数值 
     * @param ps ps
     * @param mappedStatement  mappedStatement
     * @param boundSql  boundSql
     * @param parameterObject  parameterObject
     * @throws SQLException  SQLException
     */  
    public void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,  
            Object parameterObject) throws SQLException;
}
