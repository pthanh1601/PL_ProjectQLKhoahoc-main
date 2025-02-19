package DAL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MaiThy
 */
public class StudentGrade {

    int enrollmentID, courseID, studentID;
    float grade;

    public StudentGrade() {
    }

    public StudentGrade(int enrollmentID, int courseID, int studentID, float grade) {
        this.enrollmentID = enrollmentID;
        this.courseID = courseID;
        this.studentID = studentID;
        this.grade = grade;
    }

    public int getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(int enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

}
