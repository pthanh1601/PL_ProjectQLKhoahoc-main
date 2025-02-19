/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import DAL.Course;
import DAL.CourseDAL;
import DAL.CourseDAL;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author MaiThy
 */
public class CourseBLL {

    CourseDAL cDAL;

    public CourseBLL() {
        cDAL = new CourseDAL();
    }

    public ArrayList getList() throws SQLException {
        ArrayList list = cDAL.readCourse();
        return list;
    }

    public ArrayList getListDisplayNameDepartment() throws SQLException {
        ArrayList list = cDAL.readCourseDisplayNameDepartment();
        return list;
    }

    public Course findCourse(int id) throws SQLException {
        Course c = cDAL.findCourse(id);
        return c;
    }

    public String getNameDepartment(int id) throws SQLException {
        String name = cDAL.getNameDepartment(id);
        return name;
    }

    public String getNameCourse(int id) throws SQLException {
        String name = cDAL.getNameCourse(id);
        return name;
    }

    public int getIDCourse(String name) throws SQLException {
        int id = cDAL.getIDCourse(name);
        return id;
    }

    public int courseID() {
        int courseID = cDAL.courseID();
        return courseID;
    }
}
