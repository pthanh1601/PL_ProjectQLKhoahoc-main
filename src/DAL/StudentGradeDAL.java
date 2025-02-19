/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author MaiThy
 */
public class StudentGradeDAL extends MyDatabaseManager {

    public StudentGradeDAL() {
        StudentGradeDAL.connectDB();
    }

    public ArrayList readStudentGrade() throws SQLException {
        String query = "SELECT * FROM studentgrade";
        ResultSet rs = PersonDAL.doReadQuery(query);
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                StudentGrade sg = new StudentGrade();
                sg.setEnrollmentID(rs.getInt("EnrollmentID"));
                sg.setCourseID(rs.getInt("CourseID"));
                sg.setStudentID(rs.getInt("StudentID"));
                sg.setGrade(rs.getFloat("Grade"));
                list.add(sg);
            }
        }
        return list;
    }

    public int updateStudentGrade(StudentGrade sg) throws SQLException {
        String query = "Update studentgrade SET CourseID = ? , StudentID = ?, Grade = ? "
                + " WHERE EnrollmentID = ?";
        PreparedStatement p = StudentGradeDAL.getConnection().prepareStatement(query);
        p.setInt(1, sg.getCourseID());
        p.setInt(2, sg.getStudentID());
        p.setFloat(3, sg.getGrade());
        p.setInt(4, sg.getEnrollmentID());
        int result = p.executeUpdate();
        return result;
    }

    public int insertStudentGrade(StudentGrade sg) throws SQLException {
        String query = "Insert studentgrade VALUES (?, ?, ?, ?)";
        PreparedStatement p = StudentGradeDAL.getConnection().prepareStatement(query);
        p.setInt(1, sg.getEnrollmentID());
        p.setInt(2, sg.getCourseID());
        p.setInt(3, sg.getStudentID());
        p.setFloat(4, sg.getGrade());
        int result = p.executeUpdate();
        return result;
    }

    public int deleteStudentGrade(int id) throws SQLException {
        String query = "DELETE FROM studentgrade WHERE EnrollmentID = ?";
        PreparedStatement p = PersonDAL.getConnection().prepareStatement(query);
        p.setInt(1, id);
        int result = p.executeUpdate();
        return result;
    }

    public int enrollmentID() {
        int id = 0;
        try {
            String query = "SELECT EnrollmentID "
                    + "FROM studentgrade "
                    + "ORDER BY `EnrollmentID` DESC "
                    + "LIMIT 1";
            PreparedStatement p = StudentGradeDAL.getConnection().prepareStatement(query);
            ResultSet rs = p.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) {
                id = 0;
            } else {
                while (rs.next()) {
                    id = Integer.parseInt(rs.getString("EnrollmentID"));
                }

            }
        } catch (SQLException ex) {
        }
        id = id + 1;
        return id;
    }
}
