package lk.ijse.education.dto;

public class EventDetailsDTO {
    private String eventID;
    private String departID;

    public EventDetailsDTO(){}
    public EventDetailsDTO(String eventID, String departID){
        this.eventID = eventID;
        this.departID = departID;
    }

    public String getEventID() {return eventID;}
    public void setEventID(String eventID) {this.eventID = eventID;}
    public String getDepartID() {return departID;}
    public void setDepartID(String departID) {this.departID = departID;}

    @Override
    public String toString() {
        return "EventDetailsModel{" +
                "eventID='" + eventID + '\'' +
                ", departID='" + departID + '\'' +
                '}';
    }
}
