/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import DAL.StudentGrade;
import DAL.StudentGradeDAL;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author MaiThy
 */
public class StudentGradeBLL {
    StudentGradeDAL stgDAL;

    public StudentGradeBLL() {
        stgDAL = new StudentGradeDAL();
    }

    public int addStudentGrade(StudentGrade s) throws SQLException {
        int result = stgDAL.insertStudentGrade(s);
        return result;
    }

    public int updateStudentGrade(StudentGrade s) throws SQLException {
        int result = stgDAL.updateStudentGrade(s);
        return result;
    }

    public int deleteStudentGrade(int id) throws SQLException {
        int result = stgDAL.deleteStudentGrade(id);
        return result;
    }

    public ArrayList<StudentGrade> getList() throws SQLException {
        ArrayList list = stgDAL.readStudentGrade();
        return list;
    }

    public int Enrollmentid() {
        int id = stgDAL.enrollmentID();
        return id;
    }

    
}
