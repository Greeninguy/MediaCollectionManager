package project2_phelps;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class Library implements Serializable {
    
    private ArrayList<Item> collection = new ArrayList<Item>();
    private boolean dateTrue = false;

    public ArrayList<Item> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<Item> collection) {
        this.collection = collection;
    }
    
    public void setDateTrue(boolean dateTrue) {
        this.dateTrue = true;
    }
    
    public Item getItem(int i) {
        Item t = collection.get(i);
        return t;
    }
    
    public int getSize() {
        int size = collection.size();
        return size;
    }
    
    public void sortByDate() {
        setDateTrue(true);
        
    }
    
    /**
     * This method adds an item to the collection.  
     * Error message is outputted if the item has already been added.
     */
    public void addItem(String title, String format) {
        
        boolean same = checkCollection(title);
        if (same == false) {
            collection.add(new Item(title, format));
            System.out.println(title + " has been added.");
            Collections.sort(collection);
        }
        else if (title.equals(""))
            System.out.println("Enter a media item title");
        else if (format.equals(""))
            System.out.println("Enter a media format");
        else
            System.out.println("Error: This item has already been added.");
        System.out.println();
    }
     /**
      * This method removes an item from the collection.
      * Error message is outputted if the item is not in the collection.
      * This method was edited to suit the GUI for project 2.
      */
    public void removeItem(Item item) {
        collection.remove(item);
        String title = item.getTitle();
        System.out.println(title + " has been removed.");
    }
    
    /**
     * This method checks if a title is in the collection.
     * Used by addItem and removeItem methods.
     * @param title The item's title is searched for
     * @return Returns true if the item is found, false if not.
     */
    public boolean checkCollection(String title) {
        String titleTest = title;
        boolean same = false;
        String test = null;
        for (Item t : collection) {
            test = t.getTitle();
            if (test.equals(titleTest)) 
                same = true;
        }
        return same;
    }
    
    /**
     * This method loans an item.  Changes the items status to loaned.
     * Method was edited to suit the GUI for Project 2
     */
    public void loanItem(Item item, String who, LocalDate when) {
        if (item.isLoanedOut() == true)
            System.out.println("This item has already been loaned out.");
        else if (who.equals(""))
            System.out.println("Enter the person who has loaned the item.");
        else 
        item.setLoanedOut(true);
        item.setWhoHasIt(who);
        item.setDateLoaned(when);
        
        boolean found = false;
    }
    
    /**
     * This method returns a loaned item.
     * This method was edited to suit the GUI for Project 2
     */
    public void returnItem(Item item) {
        if (item.isLoanedOut() == false) 
            System.out.println("This item has not been loaned.");
        else {
            item.setLoanedOut(false);
            item.setWhoHasIt(null);
            item.setDateLoaned(null);
        }
    }
    
    /**
     * This method sorts and outputs the collection list.
     */
    public void listItems() {
        System.out.println("YOUR ITEMS:");
        Collections.sort(collection);
        for (Object item : collection) {
            System.out.println(item);
            System.out.println();
        }
        System.out.println();
    }
}
