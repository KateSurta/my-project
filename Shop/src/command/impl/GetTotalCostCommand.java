package command.impl;

import command.Command;
import dao.IBasketDao;

public class GetTotalCostCommand implements Command {

	private IBasketDao basketDao;
	private Long basketId;
	private Double totalCost;

	@Override
	public void execute() {
		totalCost = basketDao.getTotalCost(basketId);
	}

	public void setDao(IBasketDao basketDao) {
		this.basketDao = basketDao;
	}

	public void setBasketId(Long basketId) {
		this.basketId = basketId;
	}

	public Double getTotalCost() {
		return totalCost;
	}

}
