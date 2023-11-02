package ai.planit.pev.config.database;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "ai.planit.pev.domain.irb")
public class IrbDatabaseConfig {
}
