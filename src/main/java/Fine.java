public class Fine {
    private int recordID;
    private String fineType;
    private int fine;
    public Fine(){}
    public Fine(int recordID,String fineType,int fine){
        this.fine=fine;
        this.fineType=fineType;
        this.recordID=recordID;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getFineType() {
        return fineType;
    }

    public void setFineType(String fineType) {
        this.fineType = fineType;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }
}
