import java.util.Date;

public class Record {
    private int RecordID;
    private int Sno;
    private String Bno;
    private String Status;
    private Date StartTime;
    private Date endTime;
    private Record(){}
    public Record(int RecordID,int Sno,String Bno,String Status,Date startTime,Date endTime){
        this.Bno=Bno;
        this.endTime=endTime;
        this.RecordID=RecordID;
        this.Sno=Sno;
        this.Status=Status;
        this.StartTime=startTime;

    }

    public int getRecordID() {
        return RecordID;
    }

    public void setRecordID(int recordID) {
        RecordID = recordID;
    }

    public int getSno() {
        return Sno;
    }

    public void setSno(int sno) {
        Sno = sno;
    }

    public String getBno() {
        return Bno;
    }

    public void setBno(String bno) {
        Bno = bno;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
