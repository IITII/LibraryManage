public class Card {
    private int Sid;
    private int Sno;
    private int MAX_ORDER;
    private int Ordered;
    private int Bill;
    public Card (){}
public Card(int Sno,int Sid,int MAX_ORDER,int Ordered,int Bill){
    this.Sid=Sid;
    this.Sno=Sno;
    this.MAX_ORDER=MAX_ORDER;
    this.Ordered=Ordered;
    this.Bill=Bill;
}

    public int getSid() {
        return Sid;
    }

    public void setSid(int sid) {
        Sid = sid;
    }

    public int getSno() {
        return Sno;
    }

    public void setSno(int sno) {
        Sno = sno;
    }

    public int getMAX_ORDER() {
        return MAX_ORDER;
    }

    public void setMAX_ORDER(int MAX_ORDER) {
        this.MAX_ORDER = MAX_ORDER;
    }

    public int getOrdered() {
        return Ordered;
    }

    public void setOrdered(int ordered) {
        Ordered = ordered;
    }

    public int getBill() {
        return Bill;
    }

    public void setBill(int bill) {
        Bill = bill;
    }
}
