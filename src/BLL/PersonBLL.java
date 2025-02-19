/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.Person;
import DAL.PersonDAL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author caothanh
 */
public class PersonBLL {

    PersonDAL psDAL;

    public PersonBLL() {
        psDAL = new PersonDAL();
    }

    public int addStudent(Person s) throws SQLException {
        int result = psDAL.insertStudent(s);
        return result;
    }

    public int updateStudent(Person s) throws SQLException {
        int result = psDAL.updateStudent(s);
        return result;
    }

    public int deleteStudent(int personID) throws SQLException {
        int result = psDAL.deleteStudent(personID);
        return result;
    }

    public ArrayList<Person> getList() throws SQLException {
        ArrayList list = psDAL.readStudent();
        return list;
    }

    public Person findStudents(int id) throws SQLException {
        Person ps = psDAL.findStudents(id);
        return ps;
    }

    public Person findLecturer(int id) throws SQLException {
        Person ps = psDAL.findLecturer(id);
        return ps;
    }

    public int studentid() {
        int studentID = psDAL.studentID();
        return studentID;
    }

    public String getNamePerson(int id) throws SQLException {
        String name = psDAL.getNamePerson(id);
        return name;
    }
        public int getIDPerson(String name) throws SQLException {
        int id = psDAL.getIDPerson(name);
        return id;
    }

}
