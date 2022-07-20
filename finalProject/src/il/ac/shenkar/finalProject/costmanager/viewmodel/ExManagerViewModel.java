// Submitted by:
//        Tsibulsky David - 309444065
//        Haham Omri - 308428226

package il.ac.shenkar.finalProject.costmanager.viewmodel;

import il.ac.shenkar.finalProject.costmanager.model.*;
import il.ac.shenkar.finalProject.costmanager.view.IView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExManagerViewModel implements IViewModel {
    private IModel model;
    private IView view ;

    private ExecutorService pool;

    public ExManagerViewModel() {
        pool = Executors.newFixedThreadPool(3);
    }

    /**
     * set the model - (composition)
     * @param _model
     */
    public void setModel(IModel _model){
        this.model = _model;
    }

    /**
     * set the view - (composition)
     * @param _view
     */
    public void setView(IView _view){
        this.view =_view ;
    }

    /**
     * Add item to the DB and display massages to the user
     * - info message in case of success  and error message in case of failure
     * @param item - cost item
     */
    @Override
    public void addItem(CostItem item) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addCostItem(item);
                    view.showInfoMessage("item was added");
                } catch(CostManagerException e) {
                    view.showErrorMessage(e.getMessage());
                }

                //showing the up-to-date items
                getItemsAndShow();

            }
        });
    }

    /**
     * Add new category to the DB and display massages to the user
     * - info message in case of success  and error message in case of failure
     * and update the category box in the view
     * @param category
     */
    @Override
    public void addCategory(Category category) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addCategory(category);
                    printCategories();
                    view.showInfoMessage("Category was added");
                } catch(CostManagerException e) {
                    view.showErrorMessage(e.getMessage());
                }

            }
        });

    }


    /**
     * get all the cost items from the DB according to the filter (date start and date end) ,
     * shows it to the user on the main textarea  ,  and display massages to the user
     * - info message in case of success  and error message in case of failure
     * and update the category box in the view
     * @param filter
     */
    @Override
    public void getCostsReport(ReportFilters filter) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CostItem> items  = model.getCostItems(filter) ;
                    view.showInfoMessage("getting report succeeded");
                    view.showItems(items);
                } catch(CostManagerException e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        });
    }

    /** get all the cost items from the DB , shows them to the user on the main textarea
     * - info message in case of success  and error message in case of failure
     * and update the category box in the view
     */
    @Override
    public void getItemsAndShow() {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CostItem> items  = model.getCostItemTable() ;
                    view.showInfoMessage("getting items succeeded");
                    view.showItems(items);
                } catch(CostManagerException e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        });
    }

    /** get all the categories from the DB , shows them to the user on the main textarea
     * - info message in case of success  and error message in case of failure
     * and update the category box in the view
     */
    @Override
    public void getAndShowAllCategories() {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Category> categories  = model.getCategoryTable() ;
                    view.showInfoMessage("getting categories succeeded");
                    view.showItems(categories);
                } catch(CostManagerException e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * This function calls the function in the model that get a return value of a list holds all of the categories.
     * and  calls the function in view to put all this categories to the combo box
     */
    @Override
    public void printCategories(){
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Category> categories = model.getCategoryTable();
                    view.printCategoriesPre(categories);
                } catch (CostManagerException e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        });
    }

}
