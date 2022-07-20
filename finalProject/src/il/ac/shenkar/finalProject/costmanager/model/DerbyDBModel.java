// Submitted by:
//        Tsibulsky David - 309444065
//        Haham Omri - 308428226

package il.ac.shenkar.finalProject.costmanager.model;

import java.sql.*;
import java.util.Currency;
import java.sql.Date;
import java.util.*;

/**
 *  Model class  that implement Imodel interface
 */
public class DerbyDBModel implements IModel {

    /**
     * Class for connection to the derby DB
     * attributes: Connection , Statement , ResultSet
     */
    private static class DerbyDbConection {
        public Connection connection ;
        public Statement statement ;
        public ResultSet set ;

        /**
         * GETTER -  connection
         * @return connection
         */
        public Connection getConnection() {
            return connection;
        }

        /**
         * SETTER -> connection
         * @param connection
         */
        public void setConnection(Connection connection) {
            this.connection = connection;
        }

        /**
         * GETTER -> statement
         * @return Statement
         */
        public Statement getStatement() {
            return statement;
        }

        /**
         * SETTER ->  statement
         * @param statement
         */
        public void setStatement(Statement statement) {
            this.statement = statement;
        }

        /**
         *GETTER -> ResultSet
         * @return ResultSet
         */
        public ResultSet getSet() {
            return set;
        }

        /**
         *SETTER -> set attribute
         * @param set (ResultSet)
         */
        public void setSet(ResultSet set) {
            this.set = set;
        }

        /**
         * Constructor DerbyDbConection
         */
        public DerbyDbConection() {
            this.connection = null;
            this.statement = null;
            this.set = null;
        }
    }

    private String protocol = "jdbc:derby:";

    /**
     * Constructor  DerbyDBModel
     * @throws CostManagerException
     */
    public DerbyDBModel() throws CostManagerException {

        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        DerbyDbConection gDbConnection = this.conectDb();
        //Create categories and costItem tables in the DB
        try {
            gDbConnection.getStatement().execute("CREATE TABLE  categories ( category varchar(255) )");
            gDbConnection.getStatement().execute("CREATE TABLE  costItem (id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1),description varchar(300),sumPrice double not null,currency varchar(255) not null,category varchar(255) not null, dateCreated DATE not null)");
        } catch (SQLException e) {
            if (!e.getSQLState().equals("X0Y32")) {
                throw new CostManagerException("Couldn't create the table", e);
            }
        } finally {
            this.closeConection(gDbConnection);
        }

    }


    /**
     * Add a new CostItem to the database
     * @param item The CostItem to be added
     * @throws CostManagerException in case of an error
     */
    @Override
    public void addCostItem(CostItem item) throws CostManagerException {

        DerbyDbConection gDbConnection = this.conectDb();
        //insert new item to the DB
        try {
            PreparedStatement state;
            state = gDbConnection.getConnection().prepareStatement("insert into CostItem(description,sumPrice,currency ,category , dateCreated) values (?,?,?,?,?)");
            state.setString(1, item.getDescription());
            state.setDouble(2, item.getSum());
            state.setString(3, item.getCurrency().toString());
            state.setString(4, item.getCategory());
            state.setDate(5, item.getDate());
            state.execute();
        } catch (SQLException e) {
            throw new CostManagerException(e.getMessage());
        } finally {
            this.closeConection(gDbConnection);
        }
    }

    /**
     * Create a new category in the database if category isn't exist already
     * @param newCategory the Category to be added
     * @throws CostManagerException in case of an error
     */
    @Override
    public void addCategory(Category newCategory) throws CostManagerException {

        //check if this category is exist
        List<Category> list = this.getCategoryTable();
        for (Category category: list)
        {
            if (category.equals(newCategory))
               throw  new CostManagerException("This category is already exist .");
        }

        //add new category
        DerbyDbConection gDbConnection = this.conectDb();
        try {
            PreparedStatement state;
            state = gDbConnection.getConnection().prepareStatement("insert into categories(category) values (?)");
            state.setString(1, newCategory.getCategory());
            state.execute();
        } catch (SQLException e) {
            throw new CostManagerException(e.getMessage());
        } finally {
            this.closeConection(gDbConnection);
        }
    }


    /**
     * Getting a detailed report that lists all costs in a specific period of time
     * @param filters a ReportFilters object
     * @return A list of all CostItems matching the filters
     * @throws CostManagerException in case of an error
     */
    @Override
    public List<CostItem> getCostItems(ReportFilters filters) throws CostManagerException {
        DerbyDbConection gDbConnection = this.conectDb();
        try {
                PreparedStatement state = gDbConnection.getConnection().prepareStatement("SELECT * FROM costItem WHERE dateCreated between ? and ?");
                state.setDate(1, filters.getStart());
                state.setDate(2, filters.getEnd());
                gDbConnection.setSet(state.executeQuery());
            List<CostItem> costItems = new LinkedList<>();
            // add item into the items list
            while (gDbConnection.getSet().next()) {
                String description = gDbConnection.getSet().getString("description");
                double sum = gDbConnection.getSet().getDouble("sumPrice");
                String curr = gDbConnection.getSet().getString("currency");
                Currency currency = Currency.getInstance(curr);
                String category = gDbConnection.getSet().getString("category");
                Date date = gDbConnection.getSet().getDate("dateCreated");
                CostItem item = new CostItem(sum, description, currency, date, category);
                costItems.add(item);
            }
            gDbConnection.getConnection().close();
            return costItems;
        } catch (SQLException e) {
            throw new CostManagerException("couldn't get CostItem from the DB .",e);
        } finally {
            this.closeConection(gDbConnection);
        }
    }


    /**
     * this function uses to DROP from the DB both  categories and costItem tables
     * @throws CostManagerException
     */
    public void dropTables() throws  CostManagerException{
        DerbyDbConection gDbConnection = this.conectDb();
        try {
            gDbConnection.getStatement().execute("DROP TABLE categories");
            gDbConnection.getStatement().execute("DROP TABLE costItem");
        } catch (SQLException e) {
            throw new CostManagerException("Couldn't Drop the tables .",e);
        } finally {
            this.closeConection(gDbConnection);
        }
    }

    /**
     * Conect to the DB
     * @return DerbyDbConection (conection object)
     */
    private DerbyDbConection conectDb() {
        DerbyDbConection gDbConnection = new DerbyDbConection();
        try {
            gDbConnection.setConnection(DriverManager.getConnection(this.protocol + "CostManagerDb;create=true"));
            gDbConnection.setStatement(gDbConnection.getConnection().createStatement());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gDbConnection;
    }

    /**
     * Close connection
     * @param gDbConnection
     */
    private void closeConection(DerbyDbConection gDbConnection) {
        if (gDbConnection.getStatement() != null) try {
            gDbConnection.getStatement().close();
        } catch (Exception e) {
        }
        if (gDbConnection.getConnection() != null) try {
            gDbConnection.getConnection().close();
        } catch (Exception e) {
        }
        if (gDbConnection.getSet() != null) try {
            gDbConnection.getSet().close();
        } catch (Exception e) {
        }
    }

    /**
     * Get all the CostItems from CostItems table in DB .
     * @return  LinkedList with all the CostItems that exist in DB in CostItems table.
     * @throws CostManagerException
     */
    public List<CostItem> getCostItemTable() throws CostManagerException {

        CostItem temp;
        List<CostItem> resultList = new LinkedList<>();
        DerbyDbConection gDbConnection = this.conectDb();
        try {
            ResultSet res = gDbConnection.getStatement().executeQuery("SELECT * FROM costItem ");
            while (res.next()) {
                temp = new CostItem(res.getDouble("sumPrice"), res.getString("description")
                        , Currency.getInstance(res.getString("currency"))
                        , res.getDate("dateCreated"), res.getString("category"));
                resultList.add(temp);
            }
        } catch (SQLException e) {
            throw new CostManagerException("Couldn't select costItem table .",e);
        } finally {
            this.closeConection(gDbConnection);
        }
        return resultList;
    }


    /**
     * Get all the categories from categories table in DB .
     * @return LinkedList with all the categories that exist in DB in categories table.
     * @throws CostManagerException
     */
    public List<Category> getCategoryTable() throws CostManagerException {

        Category temp;
        List<Category> resultList = new LinkedList<>();
        DerbyDbConection gDbConnection = this.conectDb();
        try {
            ResultSet res = gDbConnection.getStatement().executeQuery("SELECT * FROM categories ");
            while (res.next()) {
                temp = new Category(res.getString("category"));
                resultList.add(temp);
            }
        } catch (SQLException e) {
            throw new CostManagerException("Couldn't select Category table .",e);
        } finally {
            this.closeConection(gDbConnection);
        }
        return resultList;
    }


}
