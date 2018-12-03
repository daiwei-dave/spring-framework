package com.roger;

import com.roger.core.config.DataSourceConfig;
import com.roger.core.config.SpringConfig;
import com.roger.core.config.mybatis.SqlSessionConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, SqlSessionConfig.class,SpringConfig.class})
public class SpringBaseTestSuit {

}
