/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.OnsiteCourse;
import DAL.OnsiteCourseDAL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class OnsiteCourseBLL {

    OnsiteCourseDAL onsiteCourseDAL;

    public OnsiteCourseBLL() {
        onsiteCourseDAL = new OnsiteCourseDAL();
    }

    public List LoadOnsiteCourse(int page) throws SQLException {
        int numofrecords = 30;
        ArrayList list = onsiteCourseDAL.readOnsiteCourse();
        int size = list.size();
        int from, to;
        from = (page - 1) * numofrecords;
        to = page * numofrecords;

        return list.subList(from, Math.min(to, size));
    }

    public ArrayList<OnsiteCourse> getList() throws SQLException {
        ArrayList list = onsiteCourseDAL.readOnsiteCourse();
        return list;
    }

    public int addOnsiteCourse(OnsiteCourse oc) throws SQLException {
        int result = onsiteCourseDAL.insertOnsiteCourse(oc);
        return result;
    }

    public boolean checkCourseID(int courseid) throws SQLException {
        ArrayList<OnsiteCourse> listst = getList();
        for (OnsiteCourse st : listst) {
            if (st.getCourseID() == courseid) {
                return true;
            }
        }
        return false;
    }

    public int updateOnsiteCourse(OnsiteCourse s) throws SQLException {
        int result = onsiteCourseDAL.updateOnsiteCourse(s);
        return result;
    }

    public int deletedOnsiteCourse(int CourseID) throws SQLException {
        int ResultDel = onsiteCourseDAL.deleteOnsiteCourse(CourseID);
        return ResultDel;
    }

}
