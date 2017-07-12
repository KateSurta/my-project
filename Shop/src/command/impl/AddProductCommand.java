package command.impl;

import command.Command;
import dao.IBasketDao;

public class AddProductCommand implements Command {

	private IBasketDao basketDao;
	private Long productId;
	private Long basketId;
	private Integer quantity;

	@Override
	public void execute() {
		basketDao.addProduct(productId, basketId, quantity);
	}

	public void setDao(IBasketDao basketDao) {
		this.basketDao = basketDao;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setBasketId(Long basketId) {
		this.basketId = basketId;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
