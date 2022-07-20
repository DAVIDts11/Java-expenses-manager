// Submitted by:
//        Tsibulsky David - 309444065
//        Haham Omri - 308428226

package il.ac.shenkar.finalProject.costmanager.model;

import java.sql.Date;
import java.util.Objects;

/**
 * Filters for costs reports
 * Getting values by setting parameters for reports.
 * (date start (from) , date end (to))
 */
public class ReportFilters {
    private Date start;
    private Date end;

    /**
     * CONSTRUCTOR
     */
    public ReportFilters(Date start, Date end) {
        this.setStart(start);
        this.setEnd(end);
    }

    /**
     * @return True/False
     * @o - getting cost object and checking equality
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportFilters)) return false;
        ReportFilters that = (ReportFilters) o;
        return getStart().equals(that.getStart()) && getEnd().equals(that.getEnd());
    }

    /**
     * @return cost object hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd());
    }


    /**
     * GETTER -> end date
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Setter -> end date
     */
    public void setEnd(Date end) {
        this.end = end;
    }


    /**
     * GETTER -> start date
     */
    public Date getStart() {
        return start;
    }

    /**
     * SETTER -> start date
     */
    public void setStart(Date start) {
        this.start = start;
    }

}
