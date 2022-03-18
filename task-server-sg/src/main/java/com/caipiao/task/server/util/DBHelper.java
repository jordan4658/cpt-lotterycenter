package com.caipiao.task.server.util;//package com.caipiao.task.server.util;
//
//import com.mapper.domain.TxffcLotterySg;
//import org.apache.commons.dbcp.BasicDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//public class DBHelper {
//	private static final Logger logger = LoggerFactory.getLogger(DBHelper.class);
//
//    // MySQL 驱动
//    private static final String driver = "com.mysql.jdbc.Driver";
//    // 数据库 URL
//    private static final String url = "jdbc:mysql://47.75.199.227:3306/caipiao?useUnicode=true&characterEncoding=utf8&useSSL=false";
//    // 数据库名称
//    private static final String username = "root";
//    // 数据库密码
//    private static final String password = "CaiPiao123!@#";
//
//    public static JdbcTemplate createMysqlTemplate() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(driver);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        return new JdbcTemplate(dataSource);
//    }
//
//    public static void insertTxffc(TxffcLotterySg sg) {
//        // 期号
//        String issue = sg.getIssue();
//        logger.debug("期号：" + issue);
//        JdbcTemplate jdbcTemplate = createMysqlTemplate();
//        // 查询数据库该记录是否存在
//        Integer count = jdbcTemplate.queryForObject("select count(*) from txffc_lottery_sg g where g.issue = ?", new Object[]{issue}, Integer.class);
//        if (count.equals(0)) {
//            // 插入数据库
//            int update = jdbcTemplate.update("insert into txffc_lottery_sg (issue, wan, qian, bai, shi, ge, `date`, `time`) value (?,?,?,?,?,?,?,?)",
//                    sg.getIssue(), sg.getWan(), sg.getQian(), sg.getBai(), sg.getShi(), sg.getGe(), sg.getDate(), sg.getTime());
//            if (update == 1) {
//                logger.debug("腾讯分分彩【" + sg.getIssue() + "】数据录入成功");
//            }
//        } else {
//            logger.debug("【"+issue+"】该期号已存在！");
//        }
//    }
//}
