package command.impl;

import command.Command;
import dao.IBasketDao;
import model.Basket;

public class GetLastBasketCommand implements Command {
	private IBasketDao basketDao;
	private Basket result;
	
	

	@Override
	public void execute() {
		result = basketDao.getLastBasket();

	}

	public void setDao(IBasketDao basketDao) {
		this.basketDao = basketDao;
	}

	public Basket getResult() {
		return result;
	}

}
