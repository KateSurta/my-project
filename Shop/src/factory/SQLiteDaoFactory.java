package factory;

import dao.IBasketDao;
import dao.IProductDao;
import dao.impl.SQLiteBasketDao;
import dao.impl.SQLiteProductDao;

public class SQLiteDaoFactory implements IDaoFactory
{

    @Override
    public IBasketDao getBasketDao()
    {
        return new SQLiteBasketDao();
    }

    @Override
    public IProductDao getProductDao()
    {
        return new SQLiteProductDao();
    }

}
