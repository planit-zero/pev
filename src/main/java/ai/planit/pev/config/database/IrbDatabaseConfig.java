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
        value = "ai.planit.pev.domain.irb.**.dao",
        sqlSessionFactoryRef = "IrbSqlSessionFactory",
        sqlSessionTemplateRef = "IrbSqlSessionTemplate"
)
public class IrbDatabaseConfig {

    @Bean("IrbDataSource")
    @ConfigurationProperties(prefix = "spring.irb.datasource.hikari")
    public DataSource OdsDataSource() {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public SqlSessionFactory IrbSqlSessionFactory(@Qualifier("IrbDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/irb/*.xml");
        bean.setMapperLocations(res);

        bean.setTypeAliasesPackage("ai.planit.pev.domain.irb.**.dto");

        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate IrbSqlSessionTemplate(@Qualifier("IrbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean
    public DataSourceTransactionManager IrbTransactionManager(@Qualifier("IrbDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
