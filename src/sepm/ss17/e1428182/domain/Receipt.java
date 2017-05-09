package sepm.ss17.e1428182.domain;

import java.sql.Timestamp;


public class Receipt {


    private Integer r_id;
    private Integer b_id;
    private Integer receiptid;
    private double totalprice;
    private String clientname;
    private Timestamp start;
    public Receipt(Integer receiptid, Integer r_id, Integer b_id, double totalprice, String clientname, Timestamp start) {
        this.r_id = r_id;
        this.b_id = b_id;
        this.receiptid = receiptid;
        this.totalprice = totalprice;
        this.clientname = clientname;
        this.start = start;
    }


    public String getClientname()
    {
        return this.clientname;
    }

    public void setStart(Timestamp start)
    {
        this.start = start;
    }

    public Timestamp getStart()
    {
        return this.start;
    }

    public void setClientname(String clientname)
    {
        this.clientname = clientname;
    }
    public Integer getR_id() {
        return r_id;
    }

    public void setR_id(Integer r_id) {
        this.r_id = r_id;
    }

    public Integer getB_id() {
        return b_id;
    }

    public void setB_id(Integer b_id) {
        this.b_id = b_id;
    }

    public Integer getReceiptid() {
        return receiptid;
    }

    public void setReceiptid(Integer receiptid) {
        this.receiptid = receiptid;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receipt receipt = (Receipt) o;

        if (Double.compare(receipt.totalprice, totalprice) != 0) return false;
        if (!r_id.equals(receipt.r_id)) return false;
        if (!b_id.equals(receipt.b_id)) return false;
        if (!receiptid.equals(receipt.receiptid)) return false;
        return clientname.equals(receipt.clientname);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = r_id.hashCode();
        result = 31 * result + b_id.hashCode();
        result = 31 * result + receiptid.hashCode();
        temp = Double.doubleToLongBits(totalprice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + clientname.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "r_id=" + r_id +
                ", b_id=" + b_id +
                ", receiptid=" + receiptid +
                ", totalprice=" + totalprice +
                ", clientname='" + clientname + '\'' +
                '}';
    }
}
