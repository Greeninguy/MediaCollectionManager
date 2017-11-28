package project2_phelps;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;

public class Item implements Comparable, Serializable{
    
    private String title;
    private String format;
    private boolean loanedOut;
    private String whoHasIt;
    private LocalDate dateLoaned;
    
    /**
     * Item that is not loaned will only have these parameters
     * @param title The name of the media item.
     * @param format The format of the media item.
     */
    public Item(String title, String format) {
        this.title = title;
        this.format = format;
        this.loanedOut = false;
    }
    
    /**
     * Loaned Items will have additional parameters for the loaner and date loaned
     * @param title The name of the media item.
     * @param format the format of the media item.
     * @param loanedOut This boolean is true if the item is loaned, false otherwise.
     * @param whoHasit The person that the item has been loaned to.
     * @param dateLoaned The date when the item was loaned.
     */
    public Item(String title, String format, boolean loanedOut, String whoHasit, LocalDate dateLoaned){
        this.title = title;
        this.format = format;
        this.loanedOut = true;
        this.whoHasIt = whoHasIt;
        this.dateLoaned = dateLoaned;
    }

    public String getTitle() {
        return title;
    }

    public String getFormat() {
        return format;
    }

    public boolean isLoanedOut() {
        return loanedOut;
    }

    public String getWhoHasIt() {
        return whoHasIt;
    }

    public LocalDate getDateLoaned() {
        return dateLoaned;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setLoanedOut(boolean loanedOut) {
        this.loanedOut = loanedOut;
    }

    public void setWhoHasIt(String whoHasIt) {
        this.whoHasIt = whoHasIt;
    }

    public void setDateLoaned(LocalDate dateLoaned) {
        this.dateLoaned = dateLoaned;
    }
    
    
    @Override
    public String toString(){
        if (loanedOut == false)
            return "Title: " + title + "\nFormat: " + format;
        else
            return "Title: " + title + "\nFormat: " + format + "\n(Loaned to "
                    + whoHasIt + " during " + dateLoaned + ")";
    }

    /**
     * compareTo method overridden in order to use collections.sort
     * @param t item to be compared with during the search
     * @return 
     */
    @Override
    public int compareTo(Object t) {
        String string = title;
        String string2 = ((Item)t).getTitle();
        int result = string.compareTo(string2);
        if (result > 0)
            return 1;
        else if (result < 0)
            return -1;
        else
            return 0;
    }
    
    /**
     * New Comparator that is used to sort the collection based on when the item was loaned.
     * Will list loaned items last after items that have not been loaned.
     */
    public static Comparator<Item> dateComparer = new Comparator<Item>() {
        @Override
        public int compare(Item t, Item t1) {
            if (t.getDateLoaned() == null && t1.getDateLoaned() == null)
                return 0;
            else if (t.getDateLoaned() != null && t1.getDateLoaned() == null)
                return 1;
            else if (t.getDateLoaned() == null && t1.getDateLoaned() != null)
                return -1;
            else {
                return t.getDateLoaned().compareTo(t1.getDateLoaned());
            }
        }
    };
    
}
