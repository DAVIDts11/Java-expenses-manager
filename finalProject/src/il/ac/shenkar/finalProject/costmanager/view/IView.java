// Submitted by:
//        Tsibulsky David - 309444065
//        Haham Omri - 308428226

package il.ac.shenkar.finalProject.costmanager.view;

import il.ac.shenkar.finalProject.costmanager.model.Category;
import il.ac.shenkar.finalProject.costmanager.viewmodel.IViewModel;

import java.util.List;

public interface IView {

    /**
     * Set view model (composition)
     * @param _vm -  view model
     */
    public void setViewModel(IViewModel _vm);

    /**
     * Init all the class attributes
     */
    public void init();

    /**
     * Show the initial view
     */
    public void start();

    /**
     * Display the error message to the user
     * @param text - string
     */
    public void showErrorMessage(String text);

    /**
     * Display the information message to the user
     * @param text - string
     */
    public void showInfoMessage(String text);

    /**
     * generic function to how to the user list of items
     * in this app - we used it to show list of cost items or categories
     * @param items - it could be any item that has "toString" method
     * @param <T> - type of items in the list
     */
    public <T> void showItems(List<T> items);

    /**
     * shows all of the categories exists in our DB in the combo box.
     * @param categories - list of all categories.
     */
    public void printCategoriesPre(List<Category> categories);

}
