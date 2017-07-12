package command.impl;

import command.Command;
import dao.IProductDao;
import model.Product;

public class GetProductByNameCommand implements Command {

	private IProductDao productDao;
	private String name;
	private Product result;

	@Override
	public void execute() {
		result = productDao.getProductByName(name);
	}

	public void setDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Product getResult() {
		return result;
	}

}
