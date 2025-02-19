package UI;

import BLL.CourseBLL;
import DAL.Course;
import UI.model.headerSuggest;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SuggestCourseID extends JDialog {

    private CourseBLL cBLL = new CourseBLL();
    private JPanel pnDisplay, pnTable, pnOption, header;
    private JTable tbCourse;
    private DefaultTableModel model;
    JTextField[] txtCourse;
    JLabel[] lbCourse;
    Font font;
    int id=0;
//  true: add || false: edit
    TableRowSorter<TableModel> rowSorter;
    boolean back = false;

    public SuggestCourseID(int id) {
        setModal(true);
        this.id = id;
        init(id);
    }

    public void init(int id) {
        font = new Font("Segoe UI", Font.BOLD, 14);

        setLayout(new BorderLayout(10, 10));
        setBackground(null);
        setBounds(new Rectangle(0, 0, 1300, 730));
        /**
         * ********** PHẦN HEADER ************************************
         */
        header = new JPanel(null);
        header.setBackground(new Color(27, 27, 30));
        header.setPreferredSize(new Dimension(1300, 40));

        headerSuggest headerSuggest = new headerSuggest("DANH SÁCH KHÓA HỌC", 1300, 40);
        header.add(headerSuggest);
//        DISPLAY
        pnDisplay();
        pnTable();
        tbCourse.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = tbCourse.getSelectedRow();
                txtCourse[0].setText(tbCourse.getModel().getValueAt(i, 0).toString());
                txtCourse[1].setText(tbCourse.getModel().getValueAt(i, 1).toString());
                txtCourse[2].setText(tbCourse.getModel().getValueAt(i, 2).toString());
                txtCourse[3].setText(tbCourse.getModel().getValueAt(i, 3).toString());
            }
        });
        if (id!=0) {
            setValueCourse(id);
        }
        this.add(header, BorderLayout.NORTH);
        this.add(pnDisplay, BorderLayout.CENTER);
        this.add(pnTable, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setUndecorated(true);
        this.setVisible(true);

    }

    public JPanel pnDisplay() {
        pnDisplay = new JPanel();

        pnDisplay.setLayout(new BorderLayout());
        pnDisplay.setPreferredSize(new Dimension(1300, 300));
        JPanel pnDisplayTop = new JPanel();
        pnDisplayTop.setLayout(null);
        pnDisplayTop.setBackground(Color.pink);
        pnDisplayTop.setPreferredSize(new Dimension(1300, 300 - 70));
        String[] arrCourse = {"Course ID", "Title", "Credits", "Department"};
        txtCourse = new JTextField[arrCourse.length];
        lbCourse = new JLabel[arrCourse.length];
        int xLb = 390, yLb = 20;
        int xTxt = 600, yTxt = 20;
        for (int i = 0; i < arrCourse.length; i++) {
            lbCourse[i] = new JLabel(arrCourse[i]);
            lbCourse[i].setBounds(xLb, yLb, 200, 30);
            lbCourse[i].setHorizontalAlignment(JLabel.CENTER);
            lbCourse[i].setFont(new Font("Segoe UI", Font.CENTER_BASELINE, 17));
            lbCourse[i].setName("lb" + i);
            pnDisplayTop.add(lbCourse[i]);
            yLb = yLb + 50;
            txtCourse[i] = new JTextField();
            txtCourse[i].setName("txt" + i);
            txtCourse[i].setBounds(xTxt, yTxt, 240, 30);
            txtCourse[i].setEditable(false);
            pnDisplayTop.add(txtCourse[i]);
            yTxt = yTxt + 50;
        }
        pnOption();
        pnDisplay.add(pnDisplayTop, BorderLayout.NORTH);
        pnDisplay.add(pnOption, BorderLayout.CENTER);
        return pnDisplay;
    }

    public JPanel pnOption() {
        pnOption = new JPanel();
        pnOption.setLayout(new FlowLayout());
        pnOption.setPreferredSize(new Dimension(1300, 70));
        JLabel btnConfirm = new JLabel(new ImageIcon("./src/img/done.png"));
        btnConfirm.setPreferredSize(new Dimension(155, 70));
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirm.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

        JLabel btnBack = new JLabel(new ImageIcon("./src/img/back.png"));
        btnBack.setPreferredSize(new Dimension(155, 70));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                back = true;
                dispose();
            }
        });
        pnOption.add(btnConfirm);
        pnOption.add(btnBack);
        pnOption.setBackground(Color.pink);
        return pnOption;
    }

    public JPanel pnTable() {
        pnTable = new JPanel();
        pnTable.setLayout(null);
        pnTable.setPreferredSize(new Dimension(1300, 360));
        /**
         * ************ TẠO MODEL VÀ HEADER ********************
         */
        Vector header = new Vector();
        header.add("Course ID");
        header.add("Title");
        header.add("Credits");
        header.add("Department");
        model = new DefaultTableModel(header, 4);
        tbCourse = new JTable(model);
        rowSorter = new TableRowSorter<TableModel>(model);
        tbCourse.setRowSorter(rowSorter);
        listCourse(); //Đọc từ database lên table 

        /**
         * ************** TẠO TABLE
         * ***********************************************************
         */
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
        scroll.setBounds(new Rectangle(0, 0, 1300, 730 - (730 / 3)));
        scroll.setBackground(null);

        pnTable.add(scroll);
        return pnTable;
    }

    public void outModel(DefaultTableModel model, ArrayList<Course> cArr) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for (Course c : cArr) {
            data = new Vector();
            data.add(c.getCourseID());
            data.add(c.getTitle());
            data.add(c.getCredits());
            data.add(c.getNameDepartment());
            model.addRow(data);
        }
        tbCourse.setModel(model);
    }

    public void listCourse() {
        try {
            ArrayList<Course> c = cBLL.getListDisplayNameDepartment();
            outModel(model, c);
        } catch (SQLException ex) {
            Logger.getLogger(SuggestCourseID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTextFieldContent() {
        if (!back) {
            return Integer.parseInt(txtCourse[0].getText());
        }
        return id;
    }

    public void setValueCourse(int id) {
        try {

            Course c = cBLL.findCourse(id);
            txtCourse[0].setText(Integer.toString(c.getCourseID()));
            txtCourse[1].setText(c.getTitle());
            txtCourse[2].setText(Integer.toString(c.getCredits()));
            txtCourse[3].setText(c.getNameDepartment());

        } catch (SQLException ex) {
            Logger.getLogger(SuggestCourseID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
