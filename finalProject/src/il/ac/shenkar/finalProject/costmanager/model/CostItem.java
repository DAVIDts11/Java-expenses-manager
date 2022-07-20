// Submitted by:
//        Tsibulsky David - 309444065
//        Haham Omri - 308428226

package il.ac.shenkar.finalProject.costmanager.model;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.sql.Date;
import java.util.Objects;

/**
 * Cost item has an item with the sum, description , currency ,date and id params .
 * (for DB)
 */

public class CostItem {
 //   private int id;
    private String category;
    private double sum;
    private String description;
    private Currency currency;
    private Date date;

    /**
     * @param o -> checks if the sums of 2 data objects are equal
     * @return True/False
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CostItem)) return false;
        CostItem costItem = (CostItem) o;

        return  Double.compare(costItem.getSum(),
                getSum()) == 0 && getCategory().equals(costItem.getCategory()) &&
                getDescription().equals(costItem.getDescription()) &&
                getCurrency().equals(costItem.getCurrency()) &&
                getDate().equals(costItem.getDate());
    }

    /**
     * @return cost object hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash( getCategory(), getSum(),
                getDescription(), getCurrency(), getDate());
    }

    /**
     * CostItem Constructor, id assigned automatically with a unique value
     *
     * @param date        - this value is the date of a creation of a costItem.
     * @param category    - a way to divide each cost into a category.
     * @param description - string value with holds a few words to describe the costItem
     * @param sum         - the total sum of this costItem
     * @param currency    - one of the currencies available.
     */
    public CostItem(double sum, String description, Currency currency, Date date, String category) {
     //   this.setId(id);
        this.setCategory(category);
        this.setCurrency(currency);
        this.setDate(date);
        this.setDescription(description);
        this.setSum(sum);
    }


    /**
     * @return string representation
     */
    @Override
    public String toString() {
        return  "{ category=" + getCategory() +
                ", date=" + new SimpleDateFormat("yyyy-MM-dd").format(getDate()) +
                ", sum=" + getSum() +
                ", currency=" + getCurrency() +
                ", description='" + getDescription() +
                " }";
    }


    /**
     * GETTER -> date
     */
    public Date getDate() {
        return date;
    }

    /**
     * SETTER -> date
     */
    public void setDate(Date date) {
        this.date = date;
    }


    /**
     * GETTER -> currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * SETTER -> currency
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    /**
     * GETTER -> description
     */
    public String getDescription() {
        return description;
    }

    /**
     * SETTER -> description
     */
    public void setDescription(String description) {
        if (!description.isEmpty())
            this.description = description;
    }


    /**
     * GETTER -> sum
     */
    public double getSum() {
        return sum;
    }

    /**
     * SETTER -> sum
     */
    public void setSum(double sum) {
        if (sum > 0)
            this.sum = sum;
    }


    /**
     * GETTER -> category
     */
    public String getCategory() {
        return category;
    }

    /**
     * SETTER -> category
     */
    public void setCategory(String category) {
        this.category = category;
    }

//
//    /**
//     * GETTER -> ID
//     */
//    public int getId() {
//        return id;
//    }
//
//    /**
//     * SETTER -> ID
//     */
//    public void setId(int id) {
//        if (id > 0)
//            this.id = id;
//    }
}
