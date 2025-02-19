package BLL;

import DAL.Department;
import DAL.DepartmentDAL;
import DAL.OnsiteCourse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentBLL {

    DepartmentDAL dDAL = new DepartmentDAL();

    public DepartmentBLL() {
        dDAL  = new DepartmentDAL();
    }

    public ArrayList<Department> readDepartment() throws SQLException {
        ArrayList<Department> departmentList = null;
        departmentList = dDAL.readDepartment();
        return departmentList;
    }

    public ArrayList<Department> getList() throws SQLException {
        ArrayList list = dDAL.readDepartment();
        return list;
    }
}
