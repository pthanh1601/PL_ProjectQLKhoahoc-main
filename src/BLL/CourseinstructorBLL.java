/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.Courseinstructor;
import DAL.CourseinstructorDAL;
import DAL.Person;
import DAL.PersonDAL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thanhchan
 */
public class CourseinstructorBLL {
    CourseinstructorDAL ciDAL;

    public CourseinstructorBLL() {
        ciDAL = new CourseinstructorDAL();
    }


    public int addCoureinstructor(Courseinstructor c) throws SQLException {
        int result = ciDAL.insertCourseinstructor(c);
        return result;
    }

    public int updateCourseintructor(Courseinstructor c) throws SQLException {
        int result = ciDAL.updateCourseinstructor(c);
        return result;
    }

    public int deleteCourseinstructor(int CourseID) throws SQLException {
        int result = ciDAL.deleteCourseinstructor(CourseID);
        return result;
    }

    public ArrayList<Courseinstructor> getList() throws SQLException {
        ArrayList list = ciDAL.readCourseinstructor();
        return list;
    }

}
