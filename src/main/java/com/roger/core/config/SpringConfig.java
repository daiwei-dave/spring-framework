package com.roger.core.config;

import com.roger.core.constant.SystemConstant;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(SystemConstant.COMPONENT_SCAN_PACKAGE)
@Import(DataSourceConfig.class)
@EnableAspectJAutoProxy
public class SpringConfig {

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

}

