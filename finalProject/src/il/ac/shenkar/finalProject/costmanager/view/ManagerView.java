// Submitted by:
//        Tsibulsky David - 309444065
//        Haham Omri - 308428226

package il.ac.shenkar.finalProject.costmanager.view;

import il.ac.shenkar.finalProject.costmanager.model.Category;
import il.ac.shenkar.finalProject.costmanager.model.CostItem;
import il.ac.shenkar.finalProject.costmanager.model.ReportFilters;
import il.ac.shenkar.finalProject.costmanager.viewmodel.IViewModel;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.Currency;
import java.util.List;

/**
 * View of my expenses manager app .
 * implements IView interface
 */
public class ManagerView implements IView {
    private IViewModel vm;
    private JFrame frame;
    private JPanel allCostPanel, addPanel, getPanel, addCategoryPanel, addCostPanel, massagePanel,showAllPanel ,southPanel;
    private JButton getCostsBt, addCategoryBt, addCostBt , showAllItemsBt, showAllCategoriesBt;
    private JTextArea listOfCostsTa;
    private JTextField dateBeginningTf, dateEndTf, newCategoryTf,
            sumTf, descriptionTf, dateTf, errorMessageTf, infoMassageTf;
    private JComboBox categoryBox, currencyBox;
    private JScrollPane scrollPane;

    public ManagerView() {
    }

    /**
     * Init all the class attributes
     */
    public void init() {

        //frame and panels initiations
        this.frame = new JFrame("Expenses Manager");
        this.allCostPanel = new JPanel();
        this.addPanel = new JPanel();
        this.getPanel = new JPanel();
        this.massagePanel = new JPanel();
        this.addCategoryPanel = new JPanel();
        this.addCostPanel = new JPanel();
        this.showAllPanel =new JPanel();
        this.southPanel = new JPanel();

        //combo boxes initiations
        String defaultCategories[] = {"Sport","Groceries","Home"};
        DefaultComboBoxModel catList = new DefaultComboBoxModel(defaultCategories);
        this.categoryBox = new JComboBox(catList);

        String defaultCurrency[] = {"ILS","USD","EUR"};
        DefaultComboBoxModel currencyList = new DefaultComboBoxModel(defaultCurrency);
        this.currencyBox = new JComboBox(currencyList);

        //JButtons  initiations
        this.getCostsBt = new JButton("GET EXPENSES");
        this.addCategoryBt = new JButton("ADD CATEGORY");
        this.addCostBt = new JButton("ADD EXPENSES");
        this.showAllItemsBt= new JButton("SHOW ALL EXPENSES");
        this.showAllCategoriesBt= new JButton("SHOW ALL CATEGORIES");

        //text area initiation
        this.listOfCostsTa = new JTextArea(30, 50);
        this.listOfCostsTa.setBackground(new Color(51,153,255));
        Font font = new Font("Ariel", Font.BOLD, 14);
        this.listOfCostsTa.setFont(font);
        this.listOfCostsTa.setForeground(Color.WHITE);
        this.scrollPane = new JScrollPane(listOfCostsTa);

        //text fields initiations
        this.dateBeginningTf = new JTextField(10);
        dateBeginningTf.setDisabledTextColor(Color.GRAY);
        PromptSupport.setPrompt("Start Date... (2021-10-31)", dateBeginningTf);

        this.dateEndTf = new JTextField(10);
        dateEndTf.setDisabledTextColor(Color.GRAY);
        PromptSupport.setPrompt("End Date... (2021-12-31)", dateEndTf);

        this.newCategoryTf = new JTextField(10);
        newCategoryTf.setDisabledTextColor(Color.GRAY);
        PromptSupport.setPrompt("New Category Name...", newCategoryTf);


        this.sumTf = new JTextField(10);
        sumTf.setDisabledTextColor(Color.GRAY);
        PromptSupport.setPrompt("Sum", sumTf);


        this.descriptionTf = new JTextField(10);
        descriptionTf.setDisabledTextColor(Color.GRAY);
        PromptSupport.setPrompt("Description", descriptionTf);

        this.dateTf = new JTextField(10);
        dateTf.setDisabledTextColor(Color.GRAY);
        PromptSupport.setPrompt("Date... (2021-12-31)", dateTf);

        this.errorMessageTf = new JTextField(25);
        this.infoMassageTf = new JTextField(25);
    }

    /**
     * Show the initial view
     */
    public void start() {
        // Window Settings
        frame.setLayout(new BorderLayout());
        allCostPanel.setLayout(new BoxLayout(allCostPanel, BoxLayout.Y_AXIS));
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        addPanel.setLayout(new GridLayout(2, 1));
        getPanel.setLayout(new GridLayout(1, 3));
        addCategoryPanel.setLayout(new GridLayout(2, 1));
        addCostPanel.setLayout(new GridLayout(6, 1));
        frame.add(allCostPanel, BorderLayout.CENTER);
        frame.add(addPanel, BorderLayout.EAST);
        frame.add(southPanel, BorderLayout.SOUTH);

        //set south panel
        showAllPanel.setLayout(new FlowLayout());
        showAllPanel.add(showAllItemsBt);
        showAllPanel.add(showAllCategoriesBt);
        southPanel.add(getPanel);
        southPanel.add(showAllPanel);

        //set message panel
        massagePanel.setLayout(new FlowLayout());
        massagePanel.add(infoMassageTf);
        massagePanel.add(errorMessageTf);
        massagePanel.setSize(0, 0);

        //set the center panel
        allCostPanel.add(scrollPane);
        allCostPanel.add(massagePanel);

        //set the add panel - right side panel with fields and items to add expenses and categories
        addPanel.add(addCostPanel);
        addPanel.add(addCategoryPanel);

        //set the panel that gets items by date start and date end
        getPanel.add(dateBeginningTf);
        getPanel.add(dateEndTf);
        getPanel.add(getCostsBt);

        //set  add category panel
        addCategoryPanel.add(newCategoryTf);
        addCategoryPanel.add(addCategoryBt);

        //set add cost panel
        addCostPanel.add(categoryBox);
        addCostPanel.add(sumTf);
        addCostPanel.add(currencyBox);
        addCostPanel.add(dateTf);
        addCostPanel.add(descriptionTf);
        addCostPanel.add(addCostBt);
        vm.printCategories();

        //set edit disable to the min text area and messages fields
        listOfCostsTa.setEditable(false);
        errorMessageTf.setEditable(false);
        infoMassageTf.setEditable(false);

        // set scroll panel size
        scrollPane.setSize(70, 50);

        //Add Action Listeners to all the buttons :

        addCostBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vm.addItem(new CostItem(Double.parseDouble(sumTf.getText()), descriptionTf.getText(),
                            Currency.getInstance(String.valueOf(currencyBox.getSelectedItem())), Date.valueOf(dateTf.getText()), String.valueOf(categoryBox.getSelectedItem())));
                } catch (Exception exception) {
                    showErrorMessage(exception.getMessage());
                }
            }
        });

        addCategoryBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vm.addCategory(new Category(newCategoryTf.getText()));
                } catch (Exception exception) {
                    showErrorMessage(exception.getMessage());
                }
            }
        });



        getCostsBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vm.getCostsReport(new ReportFilters(Date.valueOf(dateBeginningTf.getText()), Date.valueOf(dateEndTf.getText())));
                } catch (Exception exception) {
                    showErrorMessage(exception.getMessage());
                }
            }
        });

        showAllItemsBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vm.getItemsAndShow();
                } catch (Exception exception) {
                    showErrorMessage(exception.getMessage());
                }
            }
        });

        showAllCategoriesBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vm.getAndShowAllCategories();
                } catch (Exception exception) {
                    showErrorMessage(exception.getMessage());
                }
            }
        });
        frame.setSize(800, 550);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    /**
     * Display the error message to the user
     * @param text - string
     */
    @Override
    public void showErrorMessage(String text) {
        if (SwingUtilities.isEventDispatchThread()) {
            errorMessageTf.setText("ERROR: " + text);
            infoMassageTf.setText("");
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    errorMessageTf.setText("ERROR: " + text);
                    infoMassageTf.setText("");
                }
            });
        }
    }

    /**
     * Display the information message to the user
     * @param text - string
     */
    @Override
    public void showInfoMessage(String text) {
        if (SwingUtilities.isEventDispatchThread()) {
            infoMassageTf.setText("INFO: " + text);
            errorMessageTf.setText("");
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    infoMassageTf.setText("INFO: " + text);
                    errorMessageTf.setText("");
                }
            });
        }
    }

    /**
     * generic function to how to the user list of items
     * in this app - we used it to show list of cost items or categories
     * @param items - it could be any item that has "toString" method
     * @param <T> - type of items in the list
     */
    @Override
    public <T> void showItems(List<T> items) {
        if (SwingUtilities.isEventDispatchThread()) {

            StringBuffer sb = new StringBuffer();

            for (T item : items) {
                sb.append(item.toString());
                sb.append("\n");
            }

            listOfCostsTa.setText(sb.toString());

        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {

                    StringBuffer sb = new StringBuffer();
                    for (T item : items) {
                        sb.append(item.toString());
                        sb.append("\n");
                    }

                    listOfCostsTa.setText(sb.toString());

                }
            });
        }
    }

    /**
     * Set view model (composition)
     * @param _vm -  view model
     */
    public void setViewModel(IViewModel _vm) {
        this.vm = _vm;
    }

    /**
     * shows all of the categories exists in our DB in the combo box.
     * @param categories - list of all categories.
     */
    public void printCategoriesPre(List<Category> categories) {
        if (SwingUtilities.isEventDispatchThread()) {
            categoryBox.setModel(new DefaultComboBoxModel(categories.toArray()));
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    categoryBox.setModel(new DefaultComboBoxModel(categories.toArray()));
                }
            });
        }
    }




}
