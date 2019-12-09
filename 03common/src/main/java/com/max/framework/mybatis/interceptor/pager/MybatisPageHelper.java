package com.max.framework.mybatis.interceptor.pager;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.max.framework.bean.PagerBean;

/**
 * 通用分页拦截器工具类
 * @author 马青松
 * @since 1.0
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class MybatisPageHelper implements Interceptor {
    /**
     * sqlInfoBuilder
     */
    private SqlInfoBuilder sqlInfoBuilder = new MySqlserverSqlInfoBuilder();
    /**
     * 分页拦截器
     */
    public static final ThreadLocal<PagerBean> localPage = new ThreadLocal<PagerBean>();

    /**
     * 开始分页
     * @param pagerBean 分页对象
     */
    public static void startPage(PagerBean pagerBean) {
        localPage.set(pagerBean);
    }

    /**
     * 结束分页并返回结果，该方法必须被调用，否则localPage会一直保存下去，直到下一次startPage
     * @return PagerBean:Page对象
     */
    public static PagerBean endPage() {
        PagerBean page = localPage.get();
        localPage.remove();
        return page;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (localPage.get() == null) {
            return invocation.proceed();
        }
        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
            // 可以分离出最原始的的目标类)
            while (metaStatementHandler.hasGetter("h")) {
                Object object = metaStatementHandler.getValue("h");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            // 分离最后一个代理对象的目标类
            while (metaStatementHandler.hasGetter("target")) {
                Object object = metaStatementHandler.getValue("target");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            // 分页信息if (localPage.get() != null) {
            PagerBean page = localPage.get();
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            // 分页参数作为参数对象parameterObject的一个属性
            String sql = boundSql.getSql();
            Connection connection = (Connection) invocation.getArgs()[0];
            // 重设分页参数里的总页数等
            sqlInfoBuilder.setPageParamCount(sql, connection, mappedStatement, boundSql, page);
            // TODO 此处以后要考虑0件的时候不再执行分页语句
            // 重写sql
            String pageSql = sqlInfoBuilder.buildPageSql(sql, page);
            // 重写分页sql
            metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
            // 将执行权交给下一个拦截器
            return invocation.proceed();
        }
        return null;
    }

    /**
     * 只拦截这两种类型的 <br>
     * StatementHandler <br>
     * ResultSetHandler
     * @param target 拦截对象
     * @return 拦截内容
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
