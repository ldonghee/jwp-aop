package core.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionHolder {
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	public Boolean hasConnection() {
		Connection connection = threadLocal.get();

		if (Objects.isNull(connection)) {
			return false;
		}

		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			return false;
		}
	}

	public void setConnection(Connection connection) {
		threadLocal.set(connection);
	}

	public Connection getConnection() {
		return threadLocal.get();
	}
}
