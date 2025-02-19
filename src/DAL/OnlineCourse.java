/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author ACER
 */
public class OnlineCourse extends Course{
    String courseURL;

    public OnlineCourse(int courseID, String title, int credits, int departmentID, String courseURL) {
        super.setCourseID(courseID);
        super.setTitle(title);
        super.setCredits(credits);
        super.setDepartmentID(departmentID);
        this.courseURL = courseURL;
    }

    public OnlineCourse(String courseURL) {
        this.courseURL = courseURL;
    }

    public OnlineCourse() {
        
    }

//    public int getCourseID() {
//        return courseID;
//    }
//
//    public void setCourseID(int courseID) {
//        this.courseID = courseID;
//    }

    public String getCourseURL() {
        return courseURL;
    }

    public void setCourseURL(String courseURL) {
        this.courseURL = courseURL;
    }
}
