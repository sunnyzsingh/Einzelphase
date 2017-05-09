package sepm.ss17.e1428182.domain;

import java.sql.Timestamp;


public class Reservation {


    private Integer r_id;
    private String clientname;
    private Timestamp start;
    private Timestamp end;
    private String horsename;
    private boolean deleted;


    public Reservation(Integer r_id, String clientname, Timestamp start, Timestamp end, String horsename, boolean deleted) {
        this.r_id = r_id;
        this.clientname = clientname;
        this.start = start;
        this.end = end;
        this.horsename = horsename;
        this.deleted = deleted;
    }

    public Integer getR_id() {
        return r_id;
    }

    public void setR_id(Integer r_id) {
        this.r_id = r_id;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public String getHorsename() {
        return horsename;
    }

    public void setHorsename(String horsename) {
        this.horsename = horsename;
    }

    public boolean isDeleted()
    {
        return this.deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (!r_id.equals(that.r_id)) return false;
        if (!clientname.equals(that.clientname)) return false;
        if (!start.equals(that.start)) return false;
        if (!end.equals(that.end)) return false;
        return horsename.equals(that.horsename);
    }

    @Override
    public int hashCode() {
        int result = r_id.hashCode();
        result = 31 * result + clientname.hashCode();
        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + horsename.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "r_id=" + r_id +
                ", clientname='" + clientname + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", horsename='" + horsename + '\'' +
                '}';
    }
}
