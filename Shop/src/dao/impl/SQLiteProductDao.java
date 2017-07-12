package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.IProductDao;
import dao.util.DatabaseUtil;
import model.Product;

public class SQLiteProductDao implements IProductDao {
	private static final String URL = "jdbc:sqlite:C:/sqlite/Shop_base.db3";
	private static final String DRIVER_CLASSNAME = "org.sqlite.JDBC";
	private static final String USER = "public";
	private static final String PASSWORD = "public";

	public SQLiteProductDao() {
		try {
			Class.forName(DRIVER_CLASSNAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Product getProductByCode(String qrCode) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		Product product = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = con.createStatement();
			String sql = "Select* from Product where QR_CODE = '" + qrCode + "'";
			result = stmt.executeQuery(sql);
			if (result.next()) {
				product = new Product();
				product.setId(result.getLong(1));
				product.setCost(result.getDouble(4));
				product.setName(result.getString(2));
			} else {
				throw new SQLException("Product " + sql + " not found.");
			}
		} catch (SQLException e) {
			System.err.println("Can't founnd product " + qrCode + e.getMessage());
		} finally {
			DatabaseUtil.close(result, stmt, con);
		}
		return product;
	}

	@Override
	public Product getProductByName(String name) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		Product product = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = con.createStatement();
			String sql = "Select* from Product where NAME = '" + name + "'";
			result = stmt.executeQuery(sql);
			if (result.next()) {
				product = new Product();
				product.setId(result.getLong(1));
				product.setCost(result.getDouble(4));
				product.setName(result.getString(2));
			} else {
				throw new SQLException("Product " + sql + " not found.");
			}
		} catch (SQLException e) {
			System.err.println("Product not found" + name + e.getMessage());
		} finally {
			DatabaseUtil.close(result, stmt, con);
		}
		return product;
	}

}
