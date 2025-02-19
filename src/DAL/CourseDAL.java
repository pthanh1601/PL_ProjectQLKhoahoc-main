package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDAL extends MyDatabaseManager {

    public CourseDAL() {
        CourseDAL.connectDB();
    }

    public ArrayList readCourse() throws SQLException {
        String query = "SELECT * FROM course";
        ResultSet rs = CourseDAL.doReadQuery(query);
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                Course oc = new Course();
                oc.setCourseID(rs.getInt("CourseID"));
                oc.setTitle(rs.getString("Title"));
                oc.setCredits(rs.getInt("Credits"));
                oc.setDepartmentID(rs.getInt("DepartmentID"));
                list.add(oc);
            }
        }
        return list;
    }

    public ArrayList readCourseDisplayNameDepartment() throws SQLException {
        String query = "SELECT CourseID, Title, Credits, Name "
                + "FROM course, department "
                + "WHERE course.DepartmentID = department.DepartmentID";
        ResultSet rs = CourseDAL.doReadQuery(query);
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                Course c = new Course();
                c.setCourseID(rs.getInt("CourseID"));
                c.setTitle(rs.getString("Title"));
                c.setCredits(rs.getInt("Credits"));
                c.setNameDepartment(rs.getString("Name"));
                list.add(c);
            }
        }
        return list;
    }

    public Course findCourse(int id) throws SQLException {

        String query = "SELECT DISTINCT CourseID, Title, Credits, department.Name "
                + "FROM course, department "
                + "WHERE course.DepartmentID = department.DepartmentID "
                + "AND CourseID = " + id + "";
        ResultSet rs = CourseDAL.doReadQuery(query);
        Course s = new Course();
        if (rs != null) {
            while (rs.next()) {

                s.setCourseID(rs.getInt("CourseID"));
                s.setTitle(rs.getString("Title"));
                s.setCredits(rs.getInt("Credits"));
                s.setNameDepartment(rs.getString("Name"));
            }
        }
        return s;
    }

    public String getNameDepartment(int id) throws SQLException {
        String query = "SELECT Name "
                + "FROM course, department "
                + "WHERE course.DepartmentID = department.DepartmentID "
                + "AND course.DepartmentID = " + id + "";      
        ResultSet rs = CourseDAL.doReadQuery(query);
        String name = "";
        if (rs != null) {
            while (rs.next()) {
                name = rs.getString("Name");
            }
        }
        return name;
    }

    public String getNameCourse(int id) throws SQLException {
        String query = "SELECT Title "
                + "FROM course "
                + "WHERE course.CourseID = " + id + "";
        
        ResultSet rs = CourseDAL.doReadQuery(query);
        String name = "";
        if (rs != null) {
            while (rs.next()) {
                name = rs.getString("Title");
            }
        }
        return name;
    }

    public int getIDCourse(String name) throws SQLException {
        String query = "SELECT CourseID "
                + "FROM course "
                + "WHERE course.Title = '" + name + "'";        
        ResultSet rs = CourseDAL.doReadQuery(query);
        int id = 0;
        if (rs != null) {
            while (rs.next()) {
                id = rs.getInt("CourseID");
            }
        }
        return id;
    }

    public int updateCourse(Course s) throws SQLException {
        String query = "Update course SET CourseID = ? ,  Title = ? , Credits = ? , DepartmentID = ?  "
                + " WHERE CourseID = ?";
        PreparedStatement p = CourseDAL.getConnection().prepareStatement(query);
        p.setInt(1, s.getCourseID());
        p.setString(2, s.getTitle());
        p.setInt(3, s.getCredits());
        p.setInt(4, s.getDepartmentID());
        p.setInt(5, s.getCourseID());
        int result = p.executeUpdate();
        return result;
    }

    public int insertCourse(Course s) throws SQLException {
        String query = "Insert course (CourseID, Title, Credits, DepartmentID) VALUES (?, ? , ?, ?)";
        PreparedStatement p = CourseDAL.getConnection().prepareStatement(query);
        p.setInt(1, s.getCourseID());
        p.setString(2, s.getTitle());
        p.setInt(3, s.getCredits());
        p.setInt(4, s.getDepartmentID());
        int result = p.executeUpdate();
        return result;
    }

    public void deleteCourse(int CourseID) throws SQLException {
        String query = "DELETE FROM course WHERE CourseID = ?";
        PreparedStatement p = CourseDAL.getConnection().prepareStatement(query);
        p.setInt(1, CourseID);
        p.executeUpdate();
    }

    public int courseID() {
        int courseid = 0;
        try {
            String query = "SELECT CourseID "
                    + "FROM course "
                    + "ORDER BY `CourseID` DESC "
                    + "LIMIT 1";
            PreparedStatement p = OnlineCourseDAL.getConnection().prepareStatement(query);
            ResultSet rs = p.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) {
                courseid = 0;
            } else {
                while (rs.next()) {
                    courseid = Integer.parseInt(rs.getString("CourseID"));
                }

            }
        } catch (SQLException ex) {
        }
        courseid = courseid + 1;
        return courseid;
    }

}
