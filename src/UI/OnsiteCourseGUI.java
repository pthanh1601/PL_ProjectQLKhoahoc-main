/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import BLL.CourseBLL;
import BLL.DepartmentBLL;
import BLL.OnsiteCourseBLL;
import DAL.Course;
import DAL.Department;
import DAL.OnsiteCourse;
import DAL.Person;
import UI.model.checkError;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.calendar.DaySelectionModel;

/**
 *
 * @author LENOVO
 */
public class OnsiteCourseGUI extends JPanel {

    private int DEFALUT_WIDTH;
    private JPanel pnDisplay, pnOption, pnFind, pnTable;
    JLabel lbAdd, lbEdit, lbRemove, btnConfirm, btnBack;
    private JTable tbCourse;
    private DefaultTableModel model;
    private boolean EditOrAdd = true;
    JTextField[] txtOnsiteCourse;
    JLabel[] lbChucVu;
    JXDatePicker picker;
    Date date;
    SpinnerDateModel sm;
    JSpinner spinnertime;
    JComboBox<String> cmbDepartmentID;
    int onsitecourseID;
    private checkError checkError = new checkError();
    private OnsiteCourseBLL onsiteCourseBLL = new OnsiteCourseBLL();
    private CourseBLL courseBLL = new CourseBLL();
    private DepartmentBLL departmentBLL = new DepartmentBLL();
    TableRowSorter<TableModel> rowSorter;
    int courseID;
    Object obTime;

    public OnsiteCourseGUI(int width) {
        DEFALUT_WIDTH = width;
        init();
    }

    public void init() {
        setLayout(new BorderLayout(10, 10));
        setBackground(null);
        setBounds(new Rectangle(10, 10, this.DEFALUT_WIDTH - 200, 680));
        pnDisplay();
        pnOption();

        // MouseClick btnADD
        lbAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditOrAdd = true;
                cleanView();
                lbAdd.setVisible(false);
                lbEdit.setVisible(false);
                lbRemove.setVisible(false);
                btnConfirm.setVisible(true);
                btnBack.setVisible(true);

//                tbStudent.clearSelection();
//                tbStudent.setEnabled(false);
            }
        });
        // MouseClick btnEdit
        lbEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (txtOnsiteCourse[0].getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn khóa học cần sửa!");
                    return;
                }

                EditOrAdd = false;

                lbAdd.setVisible(false);
                lbEdit.setVisible(false);
                lbRemove.setVisible(false);

                btnConfirm.setVisible(true);
                btnBack.setVisible(true);
                tbCourse.setEnabled(false);
            }
        });
        // MouseClick btnDelete
        lbRemove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Xác nhận xóa", "Alert", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        onsiteCourseBLL.deletedOnsiteCourse(courseID);
                        cleanView();
                        tbCourse.clearSelection();
                        JOptionPane.showMessageDialog(null, "Xóa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        outModel(model, onsiteCourseBLL.getList());
                    } catch (SQLException ex) {
                        Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        //MouseClick btnBack
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cleanView();
                lbAdd.setVisible(true);
                lbEdit.setVisible(true);
                lbRemove.setVisible(true);
                btnConfirm.setVisible(false);
                btnBack.setVisible(false);
                tbCourse.setEnabled(true);
            }
        });
        spinnertime.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                obTime = spinnertime.getValue();
            }

        });
        //MouseClick btnConfirm
        btnConfirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i;
                if (EditOrAdd) //Thêm
                {
                    i = JOptionPane.showConfirmDialog(null, "Xác nhận thêm", "", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        insertOnsiteCourse();
                    }
                } else // Edit
                {
                    i = JOptionPane.showConfirmDialog(null, "Xác nhận sửa ", "", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        updateOnsiteCourse();
                    }
                }
            }
        });

        pnTable();
        tbCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tbCourse.getSelectedRow();
                courseID = (int) tbCourse.getModel().getValueAt(i, 0);
                txtOnsiteCourse[0].setText(tbCourse.getModel().getValueAt(i, 1).toString());
                txtOnsiteCourse[1].setText(tbCourse.getModel().getValueAt(i, 2).toString());
                String depart = tbCourse.getModel().getValueAt(i, 3) + "";
                String time = tbCourse.getModel().getValueAt(i, 6) + "";
                int flag = 0;
                for (int tmp = 0; i <= cmbDepartmentID.getItemCount(); tmp++) {
                    if (cmbDepartmentID.getItemAt(tmp).contains(depart)) {
                        flag = tmp;
                        break;
                    }
                }
                cmbDepartmentID.setSelectedIndex(flag);
                txtOnsiteCourse[3].setText(tbCourse.getModel().getValueAt(i, 4).toString());
                txtOnsiteCourse[4].setText(tbCourse.getModel().getValueAt(i, 5).toString());
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
                Date date = null;
                try {
                    date = formatter.parse(time);
                } catch (ParseException ex) {

                }
                spinnertime.setValue(date);
            }
        });
        this.add(pnDisplay, BorderLayout.WEST);
        this.add(pnOption, BorderLayout.CENTER);
        this.add(pnTable, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public JPanel pnDisplay() {
        pnDisplay = new JPanel();
        pnDisplay.setLayout(null);
        pnDisplay.setPreferredSize(new Dimension(((this.DEFALUT_WIDTH - 200) / 2) - 20, 200));
        pnDisplay.setBackground(Color.pink);
        String[] arrOnsiteCourse = {"Title", "Credit", "DepartmentID", "Location", "Days", "Time"};
        txtOnsiteCourse = new JTextField[arrOnsiteCourse.length];
        lbChucVu = new JLabel[arrOnsiteCourse.length];
        int xLb = 30, yLb = 15;
        int xTxt = 240, yTxt = 15;
        for (int i = 0; i < arrOnsiteCourse.length; i++) {
            lbChucVu[i] = new JLabel(arrOnsiteCourse[i]);
            lbChucVu[i].setBounds(xLb, yLb, 180, 30);
            lbChucVu[i].setHorizontalAlignment(JLabel.CENTER);
            lbChucVu[i].setName("lb" + i);
            pnDisplay.add(lbChucVu[i]);
            yLb = yLb + 45;
            if (i != 5 && i != 2) {
                txtOnsiteCourse[i] = new JTextField();
                txtOnsiteCourse[i].setName("txt" + i);
                txtOnsiteCourse[i].setBounds(xTxt, yTxt, 220, 30);
                pnDisplay.add(txtOnsiteCourse[i]);
                yTxt = yTxt + 45;
            } else if (i == 5) {
                date = new Date();
                sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
                spinnertime = new JSpinner(sm);
                JSpinner.DateEditor de = new JSpinner.DateEditor(spinnertime, "HH:mm:ss");
                spinnertime.setEditor(de);
                spinnertime.setBounds(xTxt, yTxt, 220, 30);
                pnDisplay.add(spinnertime);
                yTxt = yTxt + 45;
            }
            if (i == 2) {
                cmbDepartmentID = new JComboBox<>();
                loadDataComboboxDepartment();
                cmbDepartmentID.setBounds(xTxt, yTxt, 220, 30);
                pnDisplay.add(cmbDepartmentID);
                yTxt = yTxt + 45;
            }
        }
        return pnDisplay;
    }

    private void loadDataComboboxDepartment() {
        cmbDepartmentID.removeAllItems();
        cmbDepartmentID.addItem("...");
        ArrayList<Department> dArr;
        try {
            dArr = departmentBLL.getList();
            for (Department department : dArr) {
                cmbDepartmentID.addItem(department.getDepartmentID() + " - " + department.getName());
            }
        } catch (SQLException ex) {
            Logger.getLogger(OnsiteCourseGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JPanel pnOption() {
        pnOption = new JPanel();
        pnOption.setLayout(null);
        pnOption.setBackground(Color.PINK);
        pnOption.setPreferredSize(new Dimension(300, 200));
        lbAdd = new JLabel(new ImageIcon("./src/img/add.png"), JLabel.CENTER);
        lbEdit = new JLabel(new ImageIcon("./src/img/edit.png"), JLabel.CENTER);
        lbRemove = new JLabel(new ImageIcon("./src/img/remove.png"), JLabel.CENTER);
        lbAdd.setBounds(155, 30, 200, 50);
        lbEdit.setBounds(155, 95, 200, 50);
        lbRemove.setBounds(155, 160, 200, 50);
        pnOption.add(lbAdd);
        pnOption.add(lbEdit);
        pnOption.add(lbRemove);
        lbAdd.setCursor(new Cursor((Cursor.HAND_CURSOR)));
        lbEdit.setCursor(new Cursor((Cursor.HAND_CURSOR)));
        lbRemove.setCursor(new Cursor((Cursor.HAND_CURSOR)));

        btnConfirm = new JLabel(new ImageIcon("./src/img/done.png"));
        btnConfirm.setVisible(false);
        btnConfirm.setBounds(80, 50, 155, 100);
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnBack = new JLabel(new ImageIcon("./src/img/back.png"));
        btnBack.setVisible(false);
        btnBack.setBounds(280, 50, 155, 100);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnOption.add(btnConfirm);
        pnOption.add(btnBack);
        pnFind();
        pnOption.add(pnFind);
        return pnOption;
    }

    public JPanel pnFind() {
        /**
         * ********************* SORT TABLE ****************************
         */
        pnFind = new JPanel();
        pnFind.setLayout(null);
        pnFind.setBounds(0, 240, 530, 40);
        pnFind.setBorder(createLineBorder(Color.BLACK)); //Chỉnh viền 
        //PHẦN CHỌN SEARCH
        JComboBox cmbChoice = new JComboBox();
        cmbChoice.setEditable(true);
        cmbChoice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbChoice.addItem("Course ID");
        cmbChoice.addItem("Location");
        cmbChoice.addItem("Days");
        cmbChoice.addItem("Time");
        cmbChoice.setBounds(0, 0, 150, 40);

        //Phần TextField
        JTextField txtSearch = new JTextField();
        txtSearch.setBounds(120, 0, 330, 40);
        txtSearch.setBorder(null);
        txtSearch.setOpaque(false);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        // Custem Icon search
        JLabel searchIcon = new JLabel(new ImageIcon("./src/img/search_24px.png"));
        searchIcon.setBounds(new Rectangle(480, 0, 50, 40));
        searchIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add tất cả vào search box
        pnFind.add(cmbChoice);
        pnFind.add(txtSearch);
        pnFind.add(searchIcon);
        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                searchIcon.setIcon(new ImageIcon("./src/img/search_24px.png")); //Đổi màu icon
                pnFind.setBorder(createLineBorder(new Color(52, 152, 219))); // Đổi màu viền 
            }

            public void focusLost(FocusEvent e) //Trờ về như cũ
            {
                searchIcon.setIcon(new ImageIcon("./src/img/search_24px.png"));
                pnFind.setBorder(createLineBorder(Color.BLACK));
            }
        });
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txtSearch.getText();
                int choice = cmbChoice.getSelectedIndex();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text + "", choice));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtSearch.getText();
                int choice = cmbChoice.getSelectedIndex();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text + "", choice));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
        return pnFind;

    }

    public JPanel pnTable() {
        pnTable = new JPanel();
        pnTable.setLayout(null);
        pnTable.setPreferredSize(new Dimension(1090, 390));
        /**
         * ************ TẠO MODEL VÀ HEADER ********************
         */
        Vector header = new Vector();
        header.add("CourseID");
        header.add("Title");
        header.add("Credits");
        header.add("Department name");
        header.add("Location");
        header.add("Days");
        header.add("Time");
        model = new DefaultTableModel(header, 4);
        tbCourse = new JTable(model);
        rowSorter = new TableRowSorter<TableModel>(model);
        tbCourse.setRowSorter(rowSorter);
        listOnSiteCourse(); //Đọc từ database lên table 

        /**
         * ******************************************************
         */
        /**
         * ************** TẠO TABLE
         * ***********************************************************
         */
        // Chỉnh width các cột 
        tbCourse.getColumnModel().getColumn(0).setPreferredWidth(40);

        // Custom table
        tbCourse.setFocusable(false);
        tbCourse.setIntercellSpacing(new Dimension(0, 0));
        tbCourse.setRowHeight(30);
        tbCourse.getTableHeader().setOpaque(false);
        tbCourse.setFillsViewportHeight(true);
        tbCourse.getTableHeader().setBackground(new Color(232, 57, 99));
        tbCourse.getTableHeader().setForeground(Color.WHITE);
        tbCourse.setSelectionBackground(new Color(52, 152, 219));

        // Add table vào ScrollPane
        JScrollPane scroll = new JScrollPane(tbCourse);
        scroll.setBounds(new Rectangle(0, 0, this.DEFALUT_WIDTH - 230, 440));
        scroll.setBackground(null);

        pnTable.add(scroll);
        return pnTable;

    }

    public void cleanView() //Xóa trắng các TextField
    {
        txtOnsiteCourse[0].setEditable(true);
        for (int i = 0; i < txtOnsiteCourse.length; i++) {
            if (i == 2) {
                cmbDepartmentID.setSelectedIndex(0);
            } else if (i == 5) {
                date = new Date();
                sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
                spinnertime = new JSpinner(sm);
                JSpinner.DateEditor de = new JSpinner.DateEditor(spinnertime, "HH:mm:ss");
                spinnertime.setEditor(de);
            } else {
                txtOnsiteCourse[i].setText("");
            }

        }

    }

    public void outModel(DefaultTableModel model, ArrayList<OnsiteCourse> onscArr) { // Xuất ra Table từ ArrayList    
        Vector data;
        model.setRowCount(0);
        for (OnsiteCourse onsiteCourse : onscArr) {
            data = new Vector();
            data.add(onsiteCourse.getCourseID());
            data.add(onsiteCourse.getTitle());
            data.add(onsiteCourse.getCredits());
            data.add(onsiteCourse.getNameDepartment());
            data.add(onsiteCourse.getLocation());
            data.add(onsiteCourse.getDate());
            data.add(onsiteCourse.getTime());
            model.addRow(data);
        }
        tbCourse.setModel(model);
    }

    public void listOnSiteCourse() { // Chép ArrayList lên table    
        try {
            ArrayList<OnsiteCourse> onscArr = onsiteCourseBLL.getList();
            outModel(model, onscArr);
        } catch (SQLException ex) {
            Logger.getLogger(OnsiteCourseGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validate(String Title, String Credits, String Department, String Location, String days) {

        boolean validateFull = true;
        if (Title.equals("") || Credits.equals("") || Location.equals("") || days.equals("")
                || Department.equals("...")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        if (!checkError.check_Number(Credits)) { //Nếu không phải số
            JOptionPane.showMessageDialog(null, "Vui lòng nhập trường credits là số");
            validateFull = false;

        }
        return validateFull;
    }

    public void insertOnsiteCourse() {
        try {
//            LẤY DATA TỪ TEXTFIELD
            int courseidAdd = courseBLL.courseID();
            String Title = txtOnsiteCourse[0].getText();
            int Credits = Integer.parseInt(txtOnsiteCourse[1].getText());
            String Department = "" + cmbDepartmentID.getItemAt(cmbDepartmentID.getSelectedIndex());
            String[] Departmenttmp = Department.split(" - ");
            int departmentID = Integer.parseInt(Departmenttmp[0]);
            String Location = txtOnsiteCourse[3].getText();
            String Days = txtOnsiteCourse[4].getText();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String timeFormat = formatter.format(obTime);
            if (validate(Title, txtOnsiteCourse[1].getText(), Department, Location, Days) == false) { return; }
            OnsiteCourse onsiteCourse = new OnsiteCourse(courseidAdd, Title, Credits, 
                    departmentID, Location, Days, timeFormat);
            onsiteCourseBLL.addOnsiteCourse(onsiteCourse);
            outModel(model, onsiteCourseBLL.getList());// Load lại table
            cleanView();
            JOptionPane.showMessageDialog(null, "Thêm thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            tbCourse.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateOnsiteCourse() {
        try {
            //Lấy dữ liệu từ TextField
            String Title = txtOnsiteCourse[0].getText();
            int Credits = Integer.parseInt(txtOnsiteCourse[1].getText());
            String Department = "" + cmbDepartmentID.getItemAt(cmbDepartmentID.getSelectedIndex());
            String[] Departmenttmp = Department.split(" - ");
            int departmentID = Integer.parseInt(Departmenttmp[0]);
            String Location = txtOnsiteCourse[3].getText();
            String Days = txtOnsiteCourse[4].getText();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String timeFormat = formatter.format(obTime);
            if (validate(Title, txtOnsiteCourse[1].getText(), Department, Location, Days) == false) { return; }                       
            OnsiteCourse onsiteCourse = new OnsiteCourse(courseID, Title, Credits, 
                    departmentID, Location, Days, timeFormat);
            onsiteCourseBLL.updateOnsiteCourse(onsiteCourse);
            outModel(model, onsiteCourseBLL.getList());
            JOptionPane.showMessageDialog(null, "Sửa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            tbCourse.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
