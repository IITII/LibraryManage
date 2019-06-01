public class Student {
    private int Sid;
    private String Sna;
    private String Sde;
    private String Ssp;
    public Student(){}
    public Student(int Sid,String Sna,String Sde,String Ssp){
        this.Sid=Sid;
        this.Sna=Sna;
        this.Sde=Sde;
        this.Ssp=Ssp;
    }

    public int getSid() {
        return Sid;
    }

    public void setSid(int sid) {
        Sid = sid;
    }

    public String getSna() {
        return Sna;
    }

    public void setSna(String sna) {
        Sna = sna;
    }

    public String getSde() {
        return Sde;
    }

    public void setSde(String sde) {
        Sde = sde;
    }

    public String getSsp() {
        return Ssp;
    }

    public void setSsp(String ssp) {
        Ssp = ssp;
    }
}
