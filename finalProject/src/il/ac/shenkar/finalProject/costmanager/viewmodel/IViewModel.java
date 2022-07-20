// Submitted by:
//        Tsibulsky David - 309444065
//        Haham Omri - 308428226

package il.ac.shenkar.finalProject.costmanager.viewmodel;

import il.ac.shenkar.finalProject.costmanager.model.Category;
import il.ac.shenkar.finalProject.costmanager.model.CostItem;
import il.ac.shenkar.finalProject.costmanager.model.IModel;
import il.ac.shenkar.finalProject.costmanager.model.ReportFilters;
import il.ac.shenkar.finalProject.costmanager.view.IView;

public interface IViewModel {

    /**
     * set the model - (composition)
     * @param _model
     */
    public void setModel(IModel _model);


    /**
     * set the view - (composition)
     * @param view
     */
    public void setView(IView view);

    /**
     * Add item to the DB and display massages to the user
     * - info message in case of success  and error message in case of failure
     * @param item - cost item
     */
    public void addItem(CostItem item);

    /**
     * Add new category to the DB and display massages to the user
     * - info message in case of success  and error message in case of failure
     * and update the category box in the view
     * @param category
     */
    public void addCategory(Category category);

    /**
     * get all the cost items from the DB according to the filter (date start and date end) ,
     * shows it to the user on the main textarea  ,  and display massages to the user
     * - info message in case of success  and error message in case of failure
     * @param filter
     */
    public void getCostsReport(ReportFilters filter);

    /** get all the cost items from the DB , shows them to the user on the main textarea
     * - info message in case of success  and error message in case of failure
     * and update the category box in the view
     */
    public void getItemsAndShow();

    /** get all the categories from the DB , shows them to the user on the main textarea
     * - info message in case of success  and error message in case of failure
     * and update the category box in the view
     */
    public void getAndShowAllCategories();

    /**
     * This function calls the function in the model that get a return value of a list holds all of the categories.
     * and  calls the function in view to put all this categories to the combo box
     */
    public void printCategories();
}
