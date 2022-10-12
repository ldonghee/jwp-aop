package core.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DataSourceUtilsTest {

	@Test
	@DisplayName("LocalThread 기존 Connection 연결 유지 테스트")
	public void getConnectionTest() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:mem://localhost/~/jwp-aop;MODE=MySQL;DB_CLOSE_DELAY=-1");
		ds.setUsername("sa");
		ds.setPassword("");
		Connection connection = DataSourceUtils.getConnection(ds);

		assertThat(connection).isNotNull();
		assertThat(connection).isEqualTo(getNewConnection());
	}

	private Connection getNewConnection() {
		BasicDataSource newDs = new BasicDataSource();
		newDs.setDriverClassName("org.h2.Driver");
		newDs.setUrl("jdbc:h2:mem://localhost/~/jwp-aop;MODE=MySQL;DB_CLOSE_DELAY=-1");
		newDs.setUsername("sa");
		newDs.setPassword("");
		return DataSourceUtils.getConnection(newDs);
	}


}
