/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.OnlineCourse;
import DAL.OnlineCourseDAL;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author ACER
 */
public class OnlineCourseBLL {
    
    OnlineCourseDAL onlCourseDAL;
    
    public OnlineCourseBLL() {
        onlCourseDAL = new OnlineCourseDAL();
    }

    public int addOnlineCourse(OnlineCourse s) throws SQLException {
        int result = onlCourseDAL.insertOnlineCourse(s);
        return result;
    }

    public int updateOnlineCourse(OnlineCourse s) throws SQLException {
        int result = onlCourseDAL.updateOnlineCourse(s);
        return result;
    }

    public int deleteOnlineCourse(int courseID) throws SQLException {
        int result = onlCourseDAL.deleteOnlineCourse(courseID);
        return result;
    }

    public ArrayList<OnlineCourse> getList() throws SQLException {
        ArrayList list = onlCourseDAL.readOnlineCourse();
        return list;
    }

    public boolean checkOnlineCourseID(int courseid) throws SQLException {
        ArrayList<OnlineCourse> list = getList();
        for (OnlineCourse st : list) {
            if (st.getCourseID() == courseid) {
                return true;
            }
        }
        return false;
    }


}
