package dao;

import model.Product;

public interface IProductDao {

	public Product getProductByCode(String qrCode);

	public Product getProductByName(String name);

}
