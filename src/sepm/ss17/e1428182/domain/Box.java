package sepm.ss17.e1428182.domain;

import javafx.beans.property.SimpleStringProperty;


public class Box {


    private Integer id;
    private SimpleStringProperty boxname;
    private SimpleStringProperty horsename;
    private double dayprice;
    private double size;
    private String location;
    private boolean window;
    private String litter;
    private String picture;
    private boolean isDeleted;


    public Box()
    {

    }
    public Box(boolean isDeleted, Integer id, SimpleStringProperty boxname, SimpleStringProperty horsename, double dayprice, double size, String location, boolean window, String litter, String picture) {
        this.id = id;
        this.boxname = (boxname);
        this.horsename = (horsename);
        this.dayprice = dayprice;
        this.size = size;
        this.location = location;
        this.window = window;
        this.litter = litter;
        this.picture = picture;
        this.isDeleted = isDeleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SimpleStringProperty getBoxname() {
        return boxname;
    }

    public void setBoxname(SimpleStringProperty boxname) {
        this.boxname = (boxname);
    }

    public SimpleStringProperty getHorsename() {
        return horsename;
    }

    public void setHorsename(SimpleStringProperty horsename) {
        this.horsename = (horsename);
    }

    public double getDayprice() {
        return dayprice;
    }

    public void setDayprice(double dayprice) {
        this.dayprice = dayprice;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isWindow() {
        return window;
    }

    public void setWindow(boolean window) {
        this.window = window;
    }

    public String getLitter() {
        return litter;
    }

    public void setLitter(String litter) {
        this.litter = litter;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Box box = (Box) o;

        if (Double.compare(box.dayprice, dayprice) != 0) return false;
        if (Double.compare(box.size, size) != 0) return false;
        if (window != box.window) return false;
        if (isDeleted != box.isDeleted) return false;
        if (!id.equals(box.id)) return false;
        if (!boxname.equals(box.boxname)) return false;
        if (!horsename.equals(box.horsename)) return false;
        if (!location.equals(box.location)) return false;
        if (!litter.equals(box.litter)) return false;
        return picture.equals(box.picture);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + boxname.hashCode();
        result = 31 * result + horsename.hashCode();
        temp = Double.doubleToLongBits(dayprice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(size);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + location.hashCode();
        result = 31 * result + (window ? 1 : 0);
        result = 31 * result + litter.hashCode();
        result = 31 * result + picture.hashCode();
        result = 31 * result + (isDeleted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", boxname='" + boxname + '\'' +
                ", horsename='" + horsename + '\'' +
                ", dayprice=" + dayprice +
                ", size=" + size +
                ", location='" + location + '\'' +
                ", window=" + window +
                ", litter='" + litter + '\'' +
                ", picture='" + picture + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}