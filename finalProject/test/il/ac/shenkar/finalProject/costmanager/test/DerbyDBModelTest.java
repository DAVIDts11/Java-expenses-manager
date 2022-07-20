// Submitted by:
//        Tsibulsky David - 309444065
//        Haham Omri - 308428226

package il.ac.shenkar.finalProject.costmanager.test;

import il.ac.shenkar.finalProject.costmanager.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Currency;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test  DerbyDBModel
 */
public class DerbyDBModelTest {

    private DerbyDBModel dataBase;
    private ResultSet rs;



    @Before
    public void setUp() throws CostManagerException {
        dataBase = new DerbyDBModel();
        rs = null;
    }

    @After
    public void tearDown() throws CostManagerException{
        dataBase.dropTables();
        dataBase = null;
        rs = null;
    }

    /**
     * Test AddCostItem function
     */
    @org.junit.Test
    public void testAddCostItem() {
        Currency c = Currency.getInstance("USD");
        Date d = Date.valueOf("2040-05-12");
        int countBeforeAdding =0;
        int countAfterAdding =0;
        CostItem testItem = new CostItem(12.4, "sport",c , d, "hobbies");
        try {
            List<CostItem> listBeforeAdding =  dataBase.getCostItemTable();
            for (CostItem item :listBeforeAdding){
                if(item.equals(testItem))
                    countBeforeAdding++;
            }
            this.dataBase.addCostItem(testItem);
            List<CostItem> listAfterAdding = dataBase.getCostItemTable();
            for (CostItem item :listAfterAdding){
                if(item.equals(testItem))
                    countAfterAdding++;
            }
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        assertEquals(countAfterAdding,countBeforeAdding+1);

    }

    /**
     * Test GetCostItems function
     */
    @Test
    public void testGetCostItems() {
        List<CostItem> actual =null;
        try {
            this.dataBase.addCostItem(new CostItem(12.4, "sport", Currency.getInstance("USD") , Date.valueOf("1998-12-01"), "hobbies"));
            this.dataBase.addCostItem(new CostItem(12.4, "sport", Currency.getInstance("USD") , Date.valueOf("2000-12-01"), "hobbies"));
            this.dataBase.addCostItem(new CostItem(19, "pool", Currency.getInstance("USD") ,Date.valueOf("2003-12-01"), "hobbies"));
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        Date fromDate = Date.valueOf("1999-12-01");
        Date toDate = Date.valueOf("2020-12-01");
        ReportFilters rf = new ReportFilters(fromDate,toDate);
        try {
             actual = this.dataBase.getCostItems(rf);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        List<CostItem> expected = new Vector<CostItem>();
        expected.add(new CostItem(12.4, "sport", Currency.getInstance("USD") , Date.valueOf("2000-12-01"), "hobbies"));
        expected.add(new CostItem(19, "pool", Currency.getInstance("USD") ,Date.valueOf("2003-12-01"), "hobbies"));
        assertEquals(expected,actual);
    }

    /**
     * Test AddCategory function
     */
    @org.junit.Test
    public void testAddCategory() {

        Category c = new Category("sport");
        try {
            this.dataBase.addCategory(c);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        try {
            this.dataBase.addCategory(c);
        } catch (CostManagerException e) {
            assertEquals("This category is already exist .",e.getMessage());
        }
    }
}