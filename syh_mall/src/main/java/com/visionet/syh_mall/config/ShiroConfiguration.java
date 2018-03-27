package com.visionet.syh_mall.config;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.visionet.syh_mall.service.UserService;
import com.visionet.syh_mall.service.adminUser.AdminUserService;
import com.visionet.syh_mall.service.adminUser.ShiroDbRealm;
import com.visionet.syh_mall.service.mobile.ShopService;

/**
 * shiro 配置
 * 
 * @author xiaofb
 * @time 2017年9月22日
 */
@Configuration
public class ShiroConfiguration {  

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(AuthorizingRealm realm) {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		dwsm.setRealm(realm);
		dwsm.setCacheManager(getEhCacheManager());
		dwsm.setSessionManager(getSessionManager());
		return dwsm;
	}

	@Bean(name = "sessionManager")
	public SessionManager getSessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(120 * 60 * 1000); // sessoin失效时长
		sessionManager.setSessionIdCookie(getSimpleCookie());
		sessionManager.setSessionDAO(getSessionDao());
		return sessionManager;
	}

	@Bean(name = "sessionDAO")
	public SessionDAO getSessionDao() {
		SessionDAO sessionDAO = new MemorySessionDAO();
		return sessionDAO;
	}

	@Bean(name = "realm")
	public AuthorizingRealm getShiroRealm(AdminUserService adminUserService, UserService userService,
			ShopService shopService) {
		ShiroDbRealm shiroDbRealm = new ShiroDbRealm();
		shiroDbRealm.setAccountService(adminUserService);
		shiroDbRealm.setUserService(userService);
		shiroDbRealm.setShopService(shopService);
		return shiroDbRealm;
	}

	@Bean(name = "sessionIdCookie")
	public Cookie getSimpleCookie() {
		String name = "SYH_COOKIE_ID";
		Cookie cookie = new SimpleCookie(name);
		cookie.setMaxAge(30 * 60); // 浏览器保存cookie有效期30分钟
		return cookie;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// shiroFilterFactoryBean.setLoginUrl("/login");
		// shiroFilterFactoryBean.setSuccessUrl("/");
		// filterChainDefinitionMap.put("/login", "authc");
		// filterChainDefinitionMap.put("/logout", "logout");
		// filterChainDefinitionMap.put("/static/**", "anon");
		// filterChainDefinitionMap.put("/api/**", "anon");
		// filterChainDefinitionMap.put("/register/**", "anon");
		// filterChainDefinitionMap.put("/admin/**", "roles[admin]");
		// filterChainDefinitionMap.put("/**", "user");
		// shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean(name = "shiroEhcacheManager")
	public EhCacheManager getEhCacheManager() {
		EhCacheManager ehCache = new EhCacheManager();
		ehCache.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");
		return ehCache;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager);
		return new AuthorizationAttributeSourceAdvisor();
	}

}
