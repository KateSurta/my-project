package dao;

import model.Basket;

public interface IBasketDao {

	public void addProduct(Long productId, Long basketId, Integer quantity);

	public void deleteProduct(Long productId, Long basketId, Integer quantity);

	public Double getTotalCost(Long basketId);

	public void createBasket(Long basketId);	

	public Basket getLastBasket();

}
