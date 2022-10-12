package core.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.utils.ConnectionHolder;

public class DataSourceUtils {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceUtils.class);
	private static final ConnectionHolder connectionHolder = new ConnectionHolder();

	public static Connection getConnection(DataSource dataSource) {
		if (!connectionHolder.hasConnection()) {
			connectionHolder.setConnection(fetchConnection(dataSource));
		}
		return connectionHolder.getConnection();
	}

	private static Connection fetchConnection(DataSource dataSource) {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
}
