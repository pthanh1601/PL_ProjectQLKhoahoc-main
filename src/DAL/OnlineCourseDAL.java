/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class OnlineCourseDAL extends MyDatabaseManager {
    
    public OnlineCourseDAL() {
        OnlineCourseDAL.connectDB();
    }
    
    public ArrayList readOnlineCourse() throws SQLException {
        String query = "SELECT course.CourseID, course.Title, course.Credits, " +
                       "course.DepartmentID, onlinecourse.url " +
                       "FROM course, onlinecourse " +
                       "WHERE course.CourseID = onlinecourse.CourseID";
        ResultSet rs = OnlineCourseDAL.doReadQuery(query);
        ArrayList list = new ArrayList();
        
        if (rs != null) {
            while (rs.next()) {
                OnlineCourse onlc = new OnlineCourse();
                onlc.setCourseID(rs.getInt("CourseID"));
                onlc.setTitle(rs.getString("Title"));
                onlc.setCredits(rs.getInt("Credits"));
                onlc.setDepartmentID(rs.getInt("DepartmentID"));
                onlc.setCourseURL(rs.getString("url"));
                list.add(onlc);
            }
        }
        return list;
    }
    

    public int updateOnlineCourse(OnlineCourse onlc) throws SQLException {
        String query = "Update onlinecourse SET CourseID = ? ,  url = ? "
                + " WHERE CourseID = ?";
        PreparedStatement p = OnlineCourseDAL.getConnection().prepareStatement(query);
        p.setInt(1, onlc.getCourseID());
        p.setString(2, onlc.getCourseURL());
        p.setInt(3, onlc.getCourseID());
        int result = p.executeUpdate();
        Course c = (Course)onlc;
        CourseDAL courseDAL = new CourseDAL();
        courseDAL.updateCourse(c);
        return result;
    }

    public int insertOnlineCourse(OnlineCourse onl) throws SQLException {
        String query = "Insert onlinecourse (CourseID, url) VALUES (?, ? )";
        PreparedStatement p = OnlineCourseDAL.getConnection().prepareStatement(query);
        p.setInt(1, onl.getCourseID());
        p.setString(2, onl.getCourseURL());
        Course c = (Course)onl;
        CourseDAL courseDAL = new CourseDAL();
        courseDAL.insertCourse(c);
        int result = p.executeUpdate();
        return result;
    }


    public int deleteOnlineCourse(int CourseID) throws SQLException {
        String query = "DELETE FROM onlinecourse WHERE CourseID = ?";
        PreparedStatement p = OnlineCourseDAL.getConnection().prepareStatement(query);
        p.setInt(1, CourseID);
        int result = p.executeUpdate();
        CourseDAL courseDAL = new CourseDAL();
        courseDAL.deleteCourse(CourseID);
        return result;
    }

   
}
