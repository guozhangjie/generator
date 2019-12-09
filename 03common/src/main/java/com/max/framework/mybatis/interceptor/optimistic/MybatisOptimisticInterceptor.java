package com.max.framework.mybatis.interceptor.optimistic;

import java.text.MessageFormat;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.mybatis.spring.MyBatisSystemException;

import com.max.framework.core.exception.OptimisticException;
import com.max.framework.core.ognl.OgnlCache;

/**
 * 拦截器拦截mybatis的update和delete语句 查找版本号， 并进行排他处理 版本号=版本号+1 拦截insert语句，将版本号设置为 1
 * @author 马青松
 * @since 1.0
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MybatisOptimisticInterceptor implements Interceptor {
    /**
     * 版本号列名
     */
    private static final String VERNO_PROPRTY_NAME = "verNo";

    /**
     * {@inheritDoc}
     */
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (SqlCommandType.UPDATE == mappedStatement.getSqlCommandType()) {
            // 如果是update或者delete，调用下一个资源
            Object reslut = invocation.proceed();

            // 判断执行的结果是不是0条
            Integer intres = (Integer) reslut;
            if (intres == 0) {
                OptimisticException ex = new OptimisticException(MessageFormat.format("{0}由于其他用户的操作乐观排他", mappedStatement.getId()));
                throw new MyBatisSystemException(ex);
            }

            return reslut;
        } else if (SqlCommandType.INSERT == mappedStatement.getSqlCommandType()) {
            Object parameter = null;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            // 如果是insert将verNo设置为1
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            Object parameterObject = boundSql.getParameterObject();

            OgnlCache.setValue(VERNO_PROPRTY_NAME, parameterObject, 1);
        }

        return invocation.proceed();
    }

    /**
     * {@inheritDoc}
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * {@inheritDoc}
     */
    public void setProperties(Properties properties0) {
    }
}
