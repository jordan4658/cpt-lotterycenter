package com.caipiao.app.config;//package com.caipiao.app.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement(proxyTargetClass = true)
//public class HikariDataSourceConfig {
//
//    private static final Logger logger = LoggerFactory.getLogger(HikariDataSourceConfig.class);
//
//    @Value("${spring.datasource.type}")
//    private String type;
//
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;
//
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Value("${spring.datasource.hikari.minimum-idle}")
//    private int minimumIdle;
//    @Value("${spring.datasource.hikari.idle-timeout}")
//    private long idleTimeout;
//    @Value("${spring.datasource.hikari.maximum-pool-size}")
//    private int maximumPoolSize;
//    @Value("${spring.datasource.hikari.max-lifetime}")
//    private long maxLifetime;
//    @Value("${spring.datasource.hikari.auto-commit}")
//    private boolean autoCommit;
//    @Value("${spring.datasource.hikari.pool-name}")
//    private String poolName;
//    @Value("${spring.datasource.hikari.connection-test-query}")
//    private String connectionTestQuery;
//    @Value("${spring.datasource.hikari.connection-timeout}")
//    private long connectionTimeout;
//    @Value("${spring.datasource.hikari.leakDetectionThresholdMs}")
//    private long leakDetectionThresholdMs;
//
//    @Bean
//    public DataSource dataSource() {
//        logger.debug("???????????????????????????");
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName(driverClassName);
//        hikariConfig.setJdbcUrl(url);
//        hikariConfig.setUsername(username);
//        hikariConfig.setPassword(password);
//
//        hikariConfig.setMinimumIdle(minimumIdle);
//        hikariConfig.setIdleTimeout(idleTimeout);
//        hikariConfig.setMaximumPoolSize(maximumPoolSize);
//        hikariConfig.setMaxLifetime(maxLifetime);
//        hikariConfig.setAutoCommit(autoCommit);
//        hikariConfig.setPoolName(poolName);
//        hikariConfig.setConnectionTestQuery(connectionTestQuery);
//        hikariConfig.setConnectionTimeout(connectionTimeout);
//        hikariConfig.setLeakDetectionThreshold(leakDetectionThresholdMs);
//
//        hikariConfig.addDataSourceProperty("cachePrepStmts", "true"); //???????????????????????????true??????????????????????????????
//        hikariConfig.addDataSourceProperty("prepStmtCacheSize", maximumPoolSize); //?????????????????????25???????????????250-500
//        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); //??????????????????????????????256???????????????2048
//        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true"); //?????????MySQL???????????????????????????????????????????????????????????????
//        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
//        hikariConfig.addDataSourceProperty("useLocalTransactionState", "true");
//        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
//        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
//        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
//        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
//        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");
//
//        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
//
//        return dataSource;
//    }
//
//    @Bean()
//    public PlatformTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//}
