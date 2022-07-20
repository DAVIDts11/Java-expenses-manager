// Submitted by:
//        Tsibulsky David - 309444065
//        Haham Omri - 308428226

package il.ac.shenkar.finalProject.costmanager.model;


import java.util.Objects;

/**
 * Category class - Holds only the name of the category.
 * (for  DB)
 */
public class Category {
    private String category;

    /**
     * Category C'tor
     * The argument in this  function is the name of the new category
     */
    public Category(String category) {
        this.setCategory(category);
    }


    /**
     * GETTER -> category
     * @return name of category (String)
     */
    public String getCategory() {
        return category;
    }


    /**
     * SETTER -> category
     * @param category name (String)
     */
    public void setCategory(String category) {
        if (!category.isEmpty())
            this.category = category;
    }


    /**
     * @return category in a string
     */
    @Override
    public String toString() {
        return category;
    }


    /**
     * @param o (category for compere)-> checking for equality in categories
     * @return True/False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category1 = (Category) o;
        return getCategory().equals(category1.getCategory());
    }


    /**
     * @return catagory code
     */
    @Override
    public int hashCode() {
        return Objects.hash(getCategory());
    }
}
