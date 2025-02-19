/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author ACER
 */


public class DepartmentDAL extends MyDatabaseManager {

    public DepartmentDAL() {
        DepartmentDAL.connectDB();
    }

    public ArrayList readDepartment() throws SQLException {
        String query = "SELECT * FROM department";
        ResultSet rs = DepartmentDAL.doReadQuery(query);
        ArrayList list = new ArrayList();

        if (rs != null) {
            while (rs.next()) {
                Department d = new Department();
                d.setDepartmentID(rs.getInt("DepartmentID"));
                d.setName(rs.getString("Name"));
                d.setBudget(rs.getInt("Budget"));
                d.setStartDate(rs.getDate("StartDate"));
                d.setAdmin(rs.getInt("Administrator"));
                list.add(d);
            }
        }
        return list;
    }
}
