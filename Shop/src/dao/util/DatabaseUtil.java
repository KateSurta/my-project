package dao.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseUtil {

	private DatabaseUtil() {

	}

	
	public static void close(Statement statement, Connection connection) {

		if (null != statement) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Can't close statement " + e.getMessage());
			}
		}

		if (null != connection) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection " + e.getMessage());
			}
		}
	}

	
	public static void close(ResultSet resultSet, Statement statement, Connection connection) {

		if (null != resultSet) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Can't close result set " + e.getMessage());
			}
		}

		close(statement, connection);
	}
}
