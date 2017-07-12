package factory;

import dao.IBasketDao;
import dao.IProductDao;

public interface IDaoFactory
{
    public IBasketDao getBasketDao();
    
    public IProductDao getProductDao();
}
