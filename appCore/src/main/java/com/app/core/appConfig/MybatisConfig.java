package com.app.core.appConfig;/*package com.app.core.appConfig;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.annotation.Resource;
import javax.sql.DataSource;

*//**
 * Created by lucky on 2017/7/5.
 *//*

@Configuration
@EnableTransactionManagement
public class MybatisConfig {

    @Value("${mybatis.mapper-locations}")
    private String mapperLoctions;

    @Value("${mybatis.type-aliases-package}")
    private String entityPackge;

    @Resource(name="dataSource")
    DataSource dataSource;

    *//**
     * 可以通过这个类,详细配置mybatis
     * @return
     *//*
//    @Bean
//    public org.apache.ibatis.session.Configuration mybatisSetting(){
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//
//        return null;
//    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage(entityPackge);
        //添加插件
        //bean.setPlugins(new Interceptor[]{pageHelper});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //设置xml扫描路径
            bean.setMapperLocations(resolver.getResources(mapperLoctions));
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException("sqlSessionFactory init fail",e);
        }
    }

    *//**
     * 用于实际查询的sql工具,传统dao开发形式可以使用这个,基于mapper代理则不需要注入
     * @param sqlSessionFactory
     * @return
     *//*
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    *//**
     * 事务管理,具体使用在service层加入@Transactional注解
     *//*
    @Bean(name = "transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    
    @Bean
    public PageHelper pageHelper() {
        //分页插件,插件无非是设置mybatis的拦截器
        PageHelper  pageHelper = new PageHelper();
        Properties p = new Properties();
        //该参数默认为false
        //设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
        //和startPage中的pageNum效果一样
        p.setProperty("offsetAsPageNum", "true");
        //该参数默认为false,设置为true时，使用RowBounds分页会进行count查询
        p.setProperty("rowBoundsWithCount", "true");
        //启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
        //禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据
        p.setProperty("reasonable", "true");
        p.setProperty("dialect", "mysql");
        p.setProperty("supportMethodsArguments", "true");
        p.setProperty("returnPageInfo", "check");
        /* 增加了一个`params`参数来配置参数映射，用于从Map或ServletRequest中取值 -->
        <!-- 可以配置pageNum,pageSize,count,pageSizeZero,reasonable,不配置映射的用默认值 -->
        <!-- 不理解该含义的前提下，不要随便复制该配置
        p.setProperty("params", "count=countSql");

        pageHelper.setProperties(p);
        return pageHelper;
    }
    

}
*/