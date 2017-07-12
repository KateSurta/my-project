package facade;

import command.CommandManager;
import command.impl.AddProductCommand;
import command.impl.CreateBasketCommand;
import command.impl.DeleteProductCommand;
import command.impl.GetLastBasketCommand;
import command.impl.GetProductByCodeCommand;
import command.impl.GetProductByNameCommand;
import command.impl.GetTotalCostCommand;
import dao.IBasketDao;
import dao.IProductDao;
import factory.DaoFactoryCreator;
import model.Basket;
import model.Product;
//create ShopFacade singleton
public class ShopFacade implements IShopFacade {

    private static ShopFacade instance = null;

    protected ShopFacade()
    {

    }

    public static ShopFacade getInstance()
    {
        if (instance == null)
        {
            instance = new ShopFacade();
            instance.init();
        }
        return instance;
    }
    
	private IBasketDao basketDao;
	private IProductDao productDao;
	private CommandManager commandManager;

	
	private void init() {
		basketDao = DaoFactoryCreator.getInstance().getFactory("sqlite").getBasketDao();
		productDao = DaoFactoryCreator.getInstance().getFactory("sqlite").getProductDao();
		commandManager = new CommandManager();
	}

	@Override
	public void addProduct(Long productId, Long basketId, Integer quantity) {
		AddProductCommand addProductCommand = new AddProductCommand();
		addProductCommand.setDao(basketDao);
		addProductCommand.setBasketId(basketId);
		addProductCommand.setProductId(productId);
		addProductCommand.setQuantity(quantity);
		commandManager.setCommand(addProductCommand);
		commandManager.executeCommand();
	}

	@Override
	public void deleteProduct(Long productId, Long basketId, Integer quantity) {
		DeleteProductCommand deleteProductCommand = new DeleteProductCommand();
		deleteProductCommand.setDao(basketDao);
		deleteProductCommand.setBasketId(basketId);
		deleteProductCommand.setProductId(productId);
		deleteProductCommand.setQuantity(quantity);
		commandManager.setCommand(deleteProductCommand);
		commandManager.executeCommand();
	}

	@Override
	public Double getTotalCost(Long basketId) {
		GetTotalCostCommand getTotalCostCommand = new GetTotalCostCommand();
		getTotalCostCommand.setDao(basketDao);
		getTotalCostCommand.setBasketId(basketId);
		commandManager.setCommand(getTotalCostCommand);
		commandManager.executeCommand();
		return getTotalCostCommand.getTotalCost();
	}

	@Override
	public Product getProductByCode(String qrCode) {
		GetProductByCodeCommand getProductByCodeCommand = new GetProductByCodeCommand();
		getProductByCodeCommand.setDao(productDao);
		getProductByCodeCommand.setQRCode(qrCode);
		commandManager.setCommand(getProductByCodeCommand);
		commandManager.executeCommand();
		return getProductByCodeCommand.getResult();
	}

	@Override
	public Product getProductByName(String name) {
		GetProductByNameCommand getProductByNameCommand = new GetProductByNameCommand();
		getProductByNameCommand.setDao(productDao);
		getProductByNameCommand.setName(name);
		commandManager.setCommand(getProductByNameCommand);
		commandManager.executeCommand();
		return getProductByNameCommand.getResult();
	}

	@Override
	public void createBasket(Long basketId) {
		CreateBasketCommand createBasketCommand = new CreateBasketCommand();
		createBasketCommand.setDao(basketDao);
		createBasketCommand.setBasketId(basketId);
		commandManager.setCommand(createBasketCommand);
		commandManager.executeCommand();
	}

	@Override
	public Basket getLastBasket() {
		GetLastBasketCommand getLastBasketCommand = new GetLastBasketCommand();
		getLastBasketCommand.setDao(basketDao);
		commandManager.setCommand(getLastBasketCommand);
		commandManager.executeCommand();
		return getLastBasketCommand.getResult();
	}

}
