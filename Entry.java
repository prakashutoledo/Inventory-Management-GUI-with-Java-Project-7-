package inventorygui;
// Creates new Entry class for storing into file
public class Entry  {
    public String name;
    public String quantity;
    public String notes;
    
    public Entry() {
        name  = new String();
        quantity =  new String();
        notes= new String();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}