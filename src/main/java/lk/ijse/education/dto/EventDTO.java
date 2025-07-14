package lk.ijse.education.dto;
import java.util.Date;

public class EventDTO {
    private String eventID;
    private String eventName;
    private Date date;
    private String time;

    public EventDTO() {}

    public EventDTO(String eventID, String eventName, Date date, String time) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.date = date;
        this.time = time;
    }
public String getEventID() {return eventID;}
public void setEventID(String eventID) {this.eventID = eventID;}
public String getEventName() {return eventName;}
public void setEventName(String eventName) {this.eventName = eventName;}
public Date getDate() {return date;}
public void setDate(Date date) {this.date = date;}
public String getTime() {return time;}
public void setTime(String time) {this.time = time;}

    @Override
    public String toString() {
        return "EventModel{" +
                "eventID='" + eventID + '\'' +
                ", eventName='" + eventName + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                '}';
    }
}
