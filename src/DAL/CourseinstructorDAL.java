/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DAL.Courseinstructor;
import DAL.MyDatabaseManager;
import DAL.PersonDAL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Thanhchan
 */
public class CourseinstructorDAL extends MyDatabaseManager {
     public CourseinstructorDAL () {

        PersonDAL.connectDB();
}

    public ArrayList readCourseinstructor() throws SQLException {
        String query = "SELECT * FROM courseinstructor";
        ResultSet rs = CourseinstructorDAL.doReadQuery(query);
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                Courseinstructor ci = new Courseinstructor();
                ci.setCourseID(rs.getInt("CourseID"));
                ci.setPersonID(rs.getInt("PersonID"));
                list.add(ci);
            }
        }
        return list;
    }

    public int updateCourseinstructor(Courseinstructor ci) throws SQLException {
        String query = "Update courseinstructor SET PersonID = ?  "
                + " WHERE CourseID= ?";
        PreparedStatement p = CourseinstructorDAL.getConnection().prepareStatement(query);
        p.setInt(1, ci.getPersonID());
        p.setInt(2, ci.getCourseID());
       
        int result = p.executeUpdate();
        return result;
    }

    public int insertCourseinstructor(Courseinstructor ci) throws SQLException {
        String query = "Insert courseinstructor (CourseID, PersonID) VALUES (?, ?)";
        PreparedStatement p = CourseinstructorDAL.getConnection().prepareStatement(query);
        p.setInt(1, ci.getCourseID());
        p.setInt(2, ci.getPersonID());
        
        int result = p.executeUpdate();
        return result;
    }


    public int deleteCourseinstructor(int CourseID) throws SQLException {
        String query = "DELETE FROM courseinstructor WHERE CourseID = ?";
        PreparedStatement p = CourseinstructorDAL.getConnection().prepareStatement(query);
        p.setInt(1, CourseID);
        int result = p.executeUpdate();
        return result;
    }
}
    
