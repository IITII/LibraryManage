import java.util.Date;

public class Book {
    private String bno;
    private String bna;
    //private Date bda;
    private String bda;
    private String bpu;
    private String bpl;
    private float bpr;
    private int MAX_NUMBER;
    private int currentNumber;
    public Book(){}
    /*
    *书籍类
    * @pram bno 图书编号
    * @*/
    //public Book(String bno,String bna,Date bda,String bpu,String bpl,float bpr,int MAX_NUMBER,int currentNumber){
    public Book(String bno,String bna,String bda,String bpu,String bpl,float bpr,int MAX_NUMBER,int currentNumber){
        this.bno=bno;
        this.bna=bna;
        this.bda=bda;
        this.bpu=bpu;
        this.bpl=bpl;
        this.bpr=bpr;
        this.MAX_NUMBER=MAX_NUMBER;
        this.currentNumber=currentNumber;
    }

    public String getBno() {
        return bno;
    }

    public void setBno(String bno) {
        this.bno = bno;
    }

    public String getBna() {
        return bna;
    }

    public void setBna(String bna) {
        this.bna = bna;
    }

    public String getBda() {
        return bda;
    }

    public void setBda(String bda) {
        this.bda = bda;
    }

    public String getBpu() {
        return bpu;
    }

    public void setBpu(String bpu) {
        this.bpu = bpu;
    }

    public String getBpl() {
        return bpl;
    }

    public void setBpl(String bpl) {
        this.bpl = bpl;
    }

    public float getBpr() {
        return bpr;
    }

    public void setBpr(float bpr) {
        this.bpr = bpr;
    }

    public int getMAX_NUMBER() {
        return MAX_NUMBER;
    }

    public void setMAX_NUMBER(int MAX_NUMBER) {
        this.MAX_NUMBER = MAX_NUMBER;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }
}
