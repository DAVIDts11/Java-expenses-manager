// Submitted by:
//        Tsibulsky David - 309444065
//        Haham Omri - 308428226

package il.ac.shenkar.finalProject.costmanager.model;

import java.util.List;

public interface IModel {

    /**
     * Add a new CostItem to the database
     * @param item The CostItem to be added
     * @throws CostManagerException in case of an error
     */
    public void addCostItem(CostItem item) throws CostManagerException ;


    /**
     * Create a new category in the database if category isn't exist already
     * @param newCategory the Category to be added
     * @throws CostManagerException in case of an error
     */
    public void addCategory(Category newCategory) throws CostManagerException;


    /**
     * Getting a detailed report that lists all costs in a specific period of time
     * @param filters a ReportFilters object
     * @return A list of all CostItems matching the filters
     * @throws CostManagerException in case of an error
     */
    public List<CostItem> getCostItems(ReportFilters filters) throws CostManagerException;

    /**
     * Get all the CostItems from CostItems table in DB .
     * @return  LinkedList with all the CostItems that exist in DB in CostItems table.
     * @throws CostManagerException
     */
    public List<CostItem> getCostItemTable() throws CostManagerException;


    /**
     * Get all the categories from categories table in DB .
     * @return LinkedList with all the categories that exist in DB in categories table.
     * @throws CostManagerException
     */
    public List<Category> getCategoryTable() throws CostManagerException;
}
