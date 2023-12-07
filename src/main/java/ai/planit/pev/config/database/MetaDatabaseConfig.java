package ai.planit.pev.config.database;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        value = "ai.planit.pev.domain.meta.**.dao",
        sqlSessionFactoryRef = "MetaSqlSessionFactory",
        sqlSessionTemplateRef = "MetaSqlSessionTemplate"
)
public class MetaDatabaseConfig {

    @Bean("MetaDataSource")
    @ConfigurationProperties(prefix = "spring.meta.datasource.hikari")
    public DataSource MetaDataSource() {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public SqlSessionFactory MetaSqlSessionFactory(@Qualifier("MetaDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/meta/*.xml");
        bean.setMapperLocations(res);

        bean.setTypeAliasesPackage("ai.planit.pev.domain.meta.**.dto");

        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate MetaSqlSessionTemplate(@Qualifier("MetaSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean
    public DataSourceTransactionManager MetaTransactionManager(@Qualifier("MetaDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
