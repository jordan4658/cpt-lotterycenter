package com.caipiao.app.config;//package com.caipiao.app.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
///**
// * 阿里的druid数据源
// */
//@Configuration
//@EnableTransactionManagement(proxyTargetClass = true)
//public class DruidDataSourceConfig {
//
//	private static final Logger logger = LoggerFactory.getLogger(DruidDataSourceConfig.class);
//	@Value("${druid.datasource.type}")
//	private String type;
//
//	@Value("${druid.datasource.driver-class-name}")
//	private String driverClassName;
//
//	@Value("${druid.datasource.url}")
//	private String url;
//
//	@Value("${druid.datasource.username}")
//	private String username;
//
//	@Value("${druid.datasource.password}")
//	private String password;
//
//	@Value("${druid.datasource.initialSize}")
//	private int initialSize;
//
//	@Value("${druid.datasource.minIdle}")
//	private int minIdle;
//
//	@Value("${druid.datasource.maxActive}")
//	private int maxActive;
//
//	@Value("${druid.datasource.maxWait}")
//	private long maxWait;
//
//	@Value("${druid.datasource.timeBetweenEvictionRunsMillis}")
//	private long timeBetweenEvictionRunsMillis;
//
//	@Value("${druid.datasource.minEvictableIdleTimeMillis}")
//	private long minEvictableIdleTimeMillis;
//
//	@Value("${druid.datasource.validationQuery}")
//	private String validationQuery;
//
//	@Value("${druid.datasource.testWhileIdle}")
//	private boolean testWhileIdle;
//
//	@Value("${druid.datasource.testOnBorrow}")
//	private boolean testOnBorrow;
//
//	@Value("${druid.datasource.testOnReturn}")
//	private boolean testOnReturn;
//
//	@Value("${druid.datasource.poolPreparedStatements}")
//	private boolean poolPreparedStatements;
//
//	@Value("${druid.datasource.maxPoolPrepStatementPerConnSize}")
//	private int maxPoolPrepStatementPerConnSize;
//
//	@Value("${druid.datasource.filters}")
//	private String filters;
//
//	@Value("${druid.datasource.connectionProperties}")
//	private String connectionProperties;
//
////	@Value("${druid.datasource.validationQueryTimeout}")
////	private int validationQueryTimeout;
////
////	@Value("${druid.datasource.removeAbandonedTimeout}")
////	private int removeAbandonedTimeout;
////
////	@Value("${druid.datasource.removeAbandoned}")
////	private boolean removeAbandoned;
////
////	@Value("${druid.datasource.logAbandoned}")
////	private boolean logAbandoned;
////
////	@Value("${druid.datasource.keepAlive}")
////	private boolean keepAlive;
//
//	@Bean(name = "dataSource", destroyMethod = "close", initMethod = "init")
//	public DataSource targetDataSource() {
//		DruidDataSource datasource = new DruidDataSource();
//		try {
//			datasource.setUrl(url);
//			datasource.setDriverClassName(driverClassName);
//			datasource.setUsername(username);
//			datasource.setPassword(password);
//			datasource.setMaxActive(maxActive);
//			datasource.setInitialSize(initialSize);
//			datasource.setMinIdle(minIdle);
//			datasource.setMaxWait(maxWait);
//			datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//			datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//			datasource.setValidationQuery(validationQuery);
//			datasource.setTestWhileIdle(testWhileIdle);
//			datasource.setTestOnBorrow(testOnBorrow);
//			datasource.setTestOnReturn(testOnReturn);
//			datasource.setPoolPreparedStatements(poolPreparedStatements);
//			datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPrepStatementPerConnSize);
//			datasource.setFilters(filters);
//			datasource.setConnectionProperties(connectionProperties);
////			datasource.setValidationQueryTimeout(validationQueryTimeout);
////			datasource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
////			datasource.setRemoveAbandoned(removeAbandoned);
////			datasource.setLogAbandoned(logAbandoned);
////			datasource.setKeepAlive(keepAlive);
//		} catch (Exception e) {
//			logger.error("异常：",e);
//		}
//		return datasource;
//	}
//
//	@Bean
//	public DataSource dataSource() {
//		return new LazyConnectionDataSourceProxy(targetDataSource());
//	}
//
//	@Bean()
//	public PlatformTransactionManager transactionManager() {
//		return new DataSourceTransactionManager(dataSource());
//	}
//}
