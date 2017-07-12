package factory;

public class DaoFactoryCreator
{
    private static DaoFactoryCreator instance = null;

    protected DaoFactoryCreator()
    {

    }

    public static DaoFactoryCreator getInstance()
    {
        if (instance == null)
        {
            instance = new DaoFactoryCreator();
        }
        return instance;
    }
    
    public IDaoFactory getFactory(String type){
        if ("sqlite".equals(type)) {
            return new SQLiteDaoFactory();
        } else {
            return null;
        }
    }
}
