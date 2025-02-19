package DAL;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author LENOVO
 */
public class OnsiteCourse extends Course {

    String location, date, time;

    public OnsiteCourse() {
    }
    public OnsiteCourse(int courseID, String title, int credits, int departmentID, String location, String date, String time) {
        super.setCourseID(courseID);
         super.setTitle(title);
        super.setCredits(credits);
        super.setDepartmentID(departmentID);
        this.location = location;
        this.date = date;
        this.time = time;
        
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
