package com.roger.core.config.mybatis;

import com.roger.core.config.DataSourceConfig;
import com.roger.core.constant.MybatisConfigConstant;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@Import(DataSourceConfig.class)
public class SqlSessionConfig {

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(dataSource);


        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(MybatisConfigConstant.MAPPER_XML_PATH));

        sqlSessionFactoryBean.setTypeAliasesPackage(MybatisConfigConstant.TYPE_ALIASES_PACKAGE);

        return sqlSessionFactoryBean;
    }


    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();

        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(MybatisConfigConstant.MAPPER_SCAN_BASE_PACKAGE);
        return  mapperScannerConfigurer;
    }
}
