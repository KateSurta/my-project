package facade;

import model.Basket;
import model.Product;

public interface IShopFacade {

	public void addProduct(Long productId, Long basketId, Integer quantity);

	public void deleteProduct(Long productId, Long basketId, Integer quantity);

	public Double getTotalCost(Long basketId);

	public Product getProductByCode(String qrCode);

	public Product getProductByName(String name);

	public void createBasket(Long basketId);

	public Basket getLastBasket();

}
