/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author LENOVO
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class OnsiteCourseDAL extends MyDatabaseManager {

    public OnsiteCourseDAL() {
        connectDB();
    }

    public ArrayList readOnsiteCourse() throws SQLException {
        String query = "SELECT DISTINCT *"
                + "FROM course, department,onsitecourse "
                + "WHERE course.CourseID=onsitecourse.CourseID AND course.DepartmentID=department.DepartmentID ";
        ResultSet rs = OnsiteCourseDAL.doReadQuery(query);
        ArrayList list = new ArrayList();
        if (rs != null) {
            while (rs.next()) {
                OnsiteCourse onsite = new OnsiteCourse();
                onsite.setCourseID(rs.getInt("course.CourseID"));
                onsite.setTitle(rs.getString("Title"));
                onsite.setCredits(rs.getInt("Credits"));
                onsite.setNameDepartment(rs.getString("Name"));
                onsite.setLocation(rs.getString("Location"));
                onsite.setDate(rs.getString("Days"));
                onsite.setTime(rs.getString("Time"));
                list.add(onsite);
            }
        }
        return list;
    }

    public int insertOnsiteCourse(OnsiteCourse onsc) throws SQLException {
        String query = "Insert onsitecourse VALUES (?, ?, ?, ?)";
        PreparedStatement p = OnsiteCourseDAL.getConnection().prepareStatement(query);
        p.setInt(1, onsc.getCourseID());
        p.setString(2, onsc.getLocation());
        p.setString(3, onsc.getDate());
        p.setString(4, onsc.getTime());
        Course c = (Course) onsc;
        CourseDAL courseDAL = new CourseDAL();
        courseDAL.insertCourse(c);
        int result = p.executeUpdate();
        return result;
    }

    public int updateOnsiteCourse(OnsiteCourse onsc) throws SQLException {
        String query = "Update onsitecourse SET Location=? , Days = ?, Time = ? "
                + " WHERE CourseID = ?";
        PreparedStatement p = OnsiteCourseDAL.getConnection().prepareStatement(query);
        p.setString(1, onsc.getLocation());
        p.setString(2, onsc.getDate());
        p.setString(3, onsc.getTime());
        p.setInt(4, onsc.getCourseID());
        Course c = (Course) onsc;
        CourseDAL courseDAL = new CourseDAL();
        courseDAL.updateCourse(c);
        int result = p.executeUpdate();
        return result;
    }

    public int deleteOnsiteCourse(int CourseID) throws SQLException {
        String query = "DELETE FROM onsitecourse WHERE CourseID = ?";
        PreparedStatement p = OnsiteCourseDAL.getConnection().prepareStatement(query);
        p.setInt(1, CourseID);
        int result = p.executeUpdate();
        CourseDAL courseDAL = new CourseDAL();
        courseDAL.deleteCourse(CourseID);
        return result;
    }
}
