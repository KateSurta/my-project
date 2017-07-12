package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.IBasketDao;
import dao.util.DatabaseUtil;
import model.Basket;

public class SQLiteBasketDao implements IBasketDao {
	private static final String URL = "jdbc:sqlite:C:/sqlite/Shop_base.db3";
	private static final String DRIVER_CLASSNAME = "org.sqlite.JDBC";
	private static final String USER = "public";
	private static final String PASSWORD = "public";

	public SQLiteBasketDao() {
		try {
			Class.forName(DRIVER_CLASSNAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addProduct(Long productId, Long basketId, Integer quantity) {
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = con.createStatement();
			// заменить на инсерт таблицы общей для продуктов и корзин
			String sql = " insert into Item_in_basket(Product_id, Basket_id,Quantity) values (" + productId + " ,"
					+ basketId + "," + quantity + ")";
			stmt.executeUpdate(sql);
			Double productCost = getProductCost(productId);
			Double cost = getTotalCost(basketId);
			Double totalCost = quantity * productCost + cost;
			sql = "Update Basket set Total_COST  = " + totalCost + " where id = " + basketId;
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println("Can't add product " + productId + " into basket " + basketId + e.getMessage());
		} finally {
			DatabaseUtil.close(stmt, con);
		}
	}

	@Override
	public void deleteProduct(Long productId, Long basketId, Integer quantity) {
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = con.createStatement();
			Double productCost = getProductCost(productId);
			Double cost = getTotalCost(basketId);
			Double totalCost = cost - quantity * productCost;
			String sql = "Update Basket set Total_COST  = " + totalCost + " where id = " + basketId;
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println("Can't delete product " + productId + " into basket " + basketId + e.getMessage());
		} finally {
			DatabaseUtil.close(stmt, con);
		}
	}

	@Override
	public Double getTotalCost(Long basketId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = con.createStatement();
			String sql = "Select Total_COST from Basket " + "where id = " + basketId;
			result = stmt.executeQuery(sql);
			return result.getDouble("Total_COST");
		} catch (SQLException e) {
			System.err.println("Cant get total cost" + basketId + e.getMessage());
		} finally {
			DatabaseUtil.close(result, stmt, con);
		}
		return 0.0;
	}

	@Override
	public void createBasket(Long basketId) {
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = con.createStatement();
			String sql = "insert into Basket (id, Total_COST) values (" + basketId + " ," + 0.0 + ")";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println("Can't create basket with id = " + basketId + e.getMessage());
		} finally {
			DatabaseUtil.close(stmt, con);
		}
	}

	private Double getProductCost(Long productId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = con.createStatement();
			String sql = "SELECT Cost From Product where id = " + productId;
			result = stmt.executeQuery(sql);
			return result.getDouble("cost");
		} catch (SQLException e) {
			System.err.println("Cant get product cost" + productId + e.getMessage());
		} finally {
			DatabaseUtil.close(result, stmt, con);
		}
		return 0.0;
	}

	@Override
	public Basket getLastBasket() {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		Basket basket = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = con.createStatement();
			String sql = " SELECT id,Total_cost  FROM BASKET WHERE id = (SELECT MAX(id) FROM BASKET);";
			result = stmt.executeQuery(sql);
			if (result.next()) {
				basket= new Basket();
				basket.setId(result.getLong(1));
				basket.setTotalCost(result.getDouble(2));				
			} else {
				throw new SQLException("Basket " + sql + " not found.");
			}
		} catch (SQLException e) {
			System.err.println("Can't found basket" + e.getMessage());
		} finally {
			DatabaseUtil.close(result, stmt, con);
		}
		return basket;
	}
}
