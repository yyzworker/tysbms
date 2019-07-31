package com.tys.interceptor;

import com.tys.security.util.AuthenticationUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * @Author haoxu
 * @Date 2019/3/25 15:25
 **/
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,Integer.class})})
@Component
public class BaseEntityInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaObject = MetaObject
                    .forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                            new DefaultReflectorFactory());
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql();
            String msql = null;
            String userId = AuthenticationUtil.getUserId();
            if(userId != null){
                if(SqlCommandType.INSERT.equals(sqlCommandType)){
                    int offset = sql.indexOf(")");
                    if(offset > -1){
                        msql = sql.substring(0,offset)+",create_user_id,update_user_id"+ sql.substring(offset,sql.length() -1)+","+userId+","+userId+")";
                    }
                }else if(SqlCommandType.UPDATE.equals(sqlCommandType)){
                    int offset = sql.indexOf("where");
                    if(offset < 0){
                        offset = sql.indexOf("WHERE");
                    }
                    if(offset > -1){
                        msql = sql.substring(0,offset)+" ,update_user_id="+userId+" "+sql.substring(offset);
                    }
                }
            }
            if(msql != null){
                metaObject.setValue("delegate.boundSql.sql", msql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
