// Submitted by:
//        Tsibulsky David - 309444065
//        Haham Omri - 308428226

package il.ac.shenkar.finalProject.costmanager;

import il.ac.shenkar.finalProject.costmanager.model.Category;
import il.ac.shenkar.finalProject.costmanager.model.CostManagerException;
import il.ac.shenkar.finalProject.costmanager.model.DerbyDBModel;
import il.ac.shenkar.finalProject.costmanager.model.IModel;
import il.ac.shenkar.finalProject.costmanager.view.IView;
import il.ac.shenkar.finalProject.costmanager.view.ManagerView;
import il.ac.shenkar.finalProject.costmanager.viewmodel.ExManagerViewModel;
import il.ac.shenkar.finalProject.costmanager.viewmodel.IViewModel;

import javax.swing.*;

/**
 * Run our expenses manager app .
 */
    public class Program {
        public static void main(String args[]) {

            IModel model = null;
            try {
                model = new DerbyDBModel();
            } catch (CostManagerException e) {
                e.printStackTrace();
            }

            //add three basic categories to the DB , if they already exist - do nothing -    "Sport","Groceries","Home"
            try {
                model.addCategory(new Category("Sport"));
                model.addCategory(new Category("Groceries"));
                model.addCategory(new Category("Home"));
            } catch (CostManagerException e) {
                if(e.getMessage()!= "This category is already exist .")
                    e.printStackTrace();
            }

            IViewModel vm = new ExManagerViewModel();
            IView view = new ManagerView();

            view.setViewModel(vm);
            vm.setModel(model);
            vm.setView(view);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    view.init();
                    view.start();
                    vm.getItemsAndShow();
                }
            });

        }
    }

