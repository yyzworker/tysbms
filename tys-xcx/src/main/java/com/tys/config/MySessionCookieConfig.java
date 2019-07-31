package com.tys.config;

import javax.servlet.SessionCookieConfig;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-4-22 15:54
 */
public interface MySessionCookieConfig extends SessionCookieConfig {
    String getDomainPattern();

    void setDomainPattern(String domainPattern);

    String getJvmRoute();

    void setJvmRoute(String jvmRoute);

    boolean isUseBase64Encoding();

    void setUseBase64Encoding(boolean useBase64Encoding);
}
