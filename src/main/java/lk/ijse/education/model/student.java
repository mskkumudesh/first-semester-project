package lk.ijse.education.model;

public class student {
    private String studID;
    private String studName;
    private String DOB;
    private String studAdress;
    private String NIC;
    private String telephone;

    public student() {}

    public  student(String studID,String studName,String DOB,String studAdress,String NIC,String telephone){
        this.studID=studID;
        this.studName=studName;
        this.DOB=DOB;
        this.studAdress=studAdress;
        this.NIC=NIC;
        this.telephone=telephone;
    }
    public String getstudID(){return studID;}
    public void setstudID(String studID){this.studID=studID;}
    public String getstudName(){return studName;}
    public void setstudName(String studName){this.studName=studName;}
    public String getDOB(){return DOB;}
    public void setDOB(String DOB){this.DOB=DOB;}
    public String getStudAdress(){return studAdress;}
    public void setStudAdress(String studAdress){this.studAdress=studAdress;}
    public String getNIC(){return NIC;}
    public void setNIC(String NIC){this.NIC=NIC;}
    public String getTelephone(){return telephone;}
    public void setTelephone(String telephone){this.telephone=telephone;}

    @Override
    public String toString() {
        return "Student{ID=" + studID + ", Name=" + studName + "', DOB=" + DOB +", Address=" +studAdress + ", NIC=" + NIC + ", Telephone=" + telephone + "}";
    }
    }

