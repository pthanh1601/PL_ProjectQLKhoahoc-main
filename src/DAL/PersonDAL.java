package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAL extends MyDatabaseManager {

    public PersonDAL() {

        PersonDAL.connectDB();
    }

    public ArrayList readStudent() throws SQLException {
        String query = "SELECT * FROM Person WHERE EnrollmentDate >0";
        ResultSet rs = PersonDAL.doReadQuery(query);
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                Person s = new Person();
                s.setPersonId(rs.getInt("PersonID"));
                s.setFirstName(rs.getString("FirstName"));
                s.setLastName(rs.getString("LastName"));
                s.setEnrollmentDate(rs.getString("EnrollmentDate"));
                list.add(s);
            }
        }
        return list;
    }

    public int updateStudent(Person ps) throws SQLException {
        String query = "Update Person SET FirstName = ? , LastName = ?, EnrollmentDate = ? "
                + " WHERE PersonID = ?";
        PreparedStatement p = PersonDAL.getConnection().prepareStatement(query);
        p.setString(1, ps.getFirstName());
        p.setString(2, ps.getLastName());
        p.setString(3, ps.getEnrollmentDate());
        p.setInt(4, ps.getPersonId());
        int result = p.executeUpdate();
        return result;
    }

    public int insertStudent(Person ps) throws SQLException {
        String query = "Insert Person (FirstName, LastName, EnrollmentDate) VALUES (?, ?, ?)";
        PreparedStatement p = PersonDAL.getConnection().prepareStatement(query);
        p.setString(1, ps.getFirstName());
        p.setString(2, ps.getLastName());
        p.setString(3, ps.getEnrollmentDate().toString());
        int result = p.executeUpdate();
        return result;
    }

    public Person findStudents(int id) throws SQLException {
        String query = "SELECT * FROM Person WHERE PersonID = " + id + " "
                + "AND EnrollmentDate IS NOT NULL";
        PreparedStatement p = PersonDAL.getConnection().prepareStatement(query);
        ResultSet rs = p.executeQuery();
        Person ps = new Person();
        if (rs != null) {
            while (rs.next()) {
                ps.setPersonId(rs.getInt("PersonID"));
                ps.setFirstName(rs.getString("FirstName"));
                ps.setLastName(rs.getString("LastName"));
                ps.setEnrollmentDate(rs.getString("EnrollmentDate"));
            }
        }
        return ps;
    }

    public Person findLecturer(int id) throws SQLException {
        String query = "SELECT * FROM Person WHERE PersonID = " + id + " "
                + "AND EnrollmentDate IS NULL";
        PreparedStatement p = PersonDAL.getConnection().prepareStatement(query);
        ResultSet rs = p.executeQuery();
        Person ps = new Person();
        if (rs != null) {
            while (rs.next()) {
                ps.setPersonId(rs.getInt("PersonID"));
                ps.setFirstName(rs.getString("FirstName"));
                ps.setLastName(rs.getString("LastName"));
                ps.setEnrollmentDate(rs.getString("HireDate"));
            }
        }
        return ps;
    }

    public int deleteStudent(int personID) throws SQLException {
        String query = "DELETE FROM Person WHERE PersonID = ?";
        PreparedStatement p = PersonDAL.getConnection().prepareStatement(query);
        p.setInt(1, personID);
        int result = p.executeUpdate();
        return result;
    }

    public int studentID() {
        int studentid = 0;
        try {
            String query = "SELECT PersonID "
                    + "FROM person "
                    + "ORDER BY `PersonID` DESC "
                    + "LIMIT 1";
            PreparedStatement p = PersonDAL.getConnection().prepareStatement(query);
            ResultSet rs = p.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) {
                studentid = 0;
            } else {
                while (rs.next()) {
                    studentid = Integer.parseInt(rs.getString("PersonID"));
                }

            }
        } catch (SQLException ex) {
        }
        studentid = studentid + 1;
        return studentid;
    }

    public String getNamePerson(int id) throws SQLException {
        String query = "SELECT Lastname, Firstname "
                + "FROM person "
                + "WHERE person.PersonID = " + id + "";
        ResultSet rs = CourseDAL.doReadQuery(query);
        String lastname = "";
        String firstname = "";
        if (rs != null) {
            while (rs.next()) {
                lastname = rs.getString("Lastname");
                firstname = rs.getString("Firstname");
            }
        }
        return lastname + " " + firstname;
    }

    public int getIDPerson(String name) throws SQLException {
        String[] nameArr = name.split(" ");
        int id = 0;
        if (nameArr.length > 1) {
            String query = "SELECT PersonID "
                    + "FROM person "
                    + "WHERE person.Lastname = '" + nameArr[0] + "' "
                    + "AND person.Firstname = '" + nameArr[1] + "'";
            ResultSet rs = CourseDAL.doReadQuery(query);

            if (rs != null) {
                while (rs.next()) {
                    id = rs.getInt("PersonID");
                }
            }
        }

        return id;
    }

}
