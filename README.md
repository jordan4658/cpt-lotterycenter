数据反向生成说明

非主播端数据文件生成配置：resources/mybatis-gen-config.xml

主播端数据文件生成配置：resources/mybatis-gen-config-anchor.xml

使用

需要生成哪个环境的数据就在 live-common pom.xml 文件里的 mybatis-generator-maven-plugin 插件的 configurationFile 配置里指向相应的配置文件即可