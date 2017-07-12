package command.impl;

import command.Command;
import dao.IBasketDao;

public class CreateBasketCommand implements Command {

	private IBasketDao basketDao;
	private Long basketId;

	@Override
	public void execute() {
		basketDao.createBasket(basketId);
	}

	public void setDao(IBasketDao basketDao) {
		this.basketDao = basketDao;
	}

	public void setBasketId(Long basketId) {
		this.basketId = basketId;
	}

}
