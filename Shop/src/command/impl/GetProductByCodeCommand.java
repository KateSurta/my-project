package command.impl;

import command.Command;
import dao.IProductDao;
import model.Product;

public class GetProductByCodeCommand implements Command {

	private IProductDao productDao;
	private String qrCode;
	private Product result;

	@Override
	public void execute() {
		result = productDao.getProductByCode(qrCode);
	}

	public void setDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	public void setQRCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Product getResult() {
		return result;
	}

}
