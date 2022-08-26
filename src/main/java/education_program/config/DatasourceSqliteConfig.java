package education_program.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "education_program.web.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DatasourceSqliteConfig {
	
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dbDataSource() {
		
		return DataSourceBuilder.create().build();
		
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory dbSqlSessionFactory(@Qualifier("dataSource") DataSource dbDataSource, ApplicationContext applicationContext) throws Exception {
		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dbDataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:education_program/web/mapper/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
		
	}

	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate dbSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory dbSqlSessionFactory) throws Exception {
		
		return new SqlSessionTemplate(dbSqlSessionFactory);
		
	}
	
}
