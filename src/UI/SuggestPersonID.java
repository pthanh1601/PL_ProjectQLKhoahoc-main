package UI;

import BLL.PersonBLL;
import DAL.Person;
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

public class SuggestPersonID extends JDialog {

    private PersonBLL stBLL = new PersonBLL();
    private JPanel pnDisplay, pnTable, pnOption, header;
    private JTable tbStudent;
    private DefaultTableModel model;
    JTextField[] txtStudent;
    JLabel[] lbStudent;
    Font font;
    int id =0;
            String loaiPerson;
//  true: add || false: edit
    TableRowSorter<TableModel> rowSorter;
    boolean back = false;

    public SuggestPersonID(String loaiPerson, int id) {
        setModal(true);
        this.id = id;
        this.loaiPerson = loaiPerson;
        init(loaiPerson, id);
    }

    public void init(String loaiPerson,int id) {
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
        tbStudent.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = tbStudent.getSelectedRow();
                txtStudent[0].setText(tbStudent.getModel().getValueAt(i, 0).toString());
                txtStudent[1].setText(tbStudent.getModel().getValueAt(i, 1).toString());
                txtStudent[2].setText(tbStudent.getModel().getValueAt(i, 2).toString());
                txtStudent[3].setText(tbStudent.getModel().getValueAt(i, 3).toString());
            }
        });
        if (id!=0) {
            setValueStudent(loaiPerson,id);
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
        String[] arrStudent = {"Student ID", "First Name", "Last Name", "Enrollment Date"};
        txtStudent = new JTextField[arrStudent.length];
        lbStudent = new JLabel[arrStudent.length];
        int xLb = 390, yLb = 20;
        int xTxt = 600, yTxt = 20;
        for (int i = 0; i < arrStudent.length; i++) {
            lbStudent[i] = new JLabel(arrStudent[i]);
            lbStudent[i].setBounds(xLb, yLb, 200, 30);
            lbStudent[i].setHorizontalAlignment(JLabel.CENTER);
            lbStudent[i].setFont(new Font("Segoe UI", Font.CENTER_BASELINE, 17));
            lbStudent[i].setName("lb" + i);
            pnDisplayTop.add(lbStudent[i]);
            yLb = yLb + 50;
            txtStudent[i] = new JTextField();
            txtStudent[i].setName("txt" + i);
            txtStudent[i].setBounds(xTxt, yTxt, 240, 30);
            txtStudent[i].setEditable(false);
            pnDisplayTop.add(txtStudent[i]);
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
        header.add("Student ID");
        header.add("First Name");
        header.add("Last Name");
        header.add("Enrollment Date");
        model = new DefaultTableModel(header, 4);
        tbStudent = new JTable(model);
        rowSorter = new TableRowSorter<TableModel>(model);
        tbStudent.setRowSorter(rowSorter);
        listStudent(); //Đọc từ database lên table 

        /**
         * ************** TẠO TABLE
         * ***********************************************************
         */
        tbStudent.setFocusable(false);
        tbStudent.setIntercellSpacing(new Dimension(0, 0));
        tbStudent.setRowHeight(30);
        tbStudent.getTableHeader().setOpaque(false);
        tbStudent.setFillsViewportHeight(true);
        tbStudent.getTableHeader().setBackground(new Color(232, 57, 99));
        tbStudent.getTableHeader().setForeground(Color.WHITE);
        tbStudent.setSelectionBackground(new Color(52, 152, 219));

        // Add table vào ScrollPane
        JScrollPane scroll = new JScrollPane(tbStudent);
        scroll.setBounds(new Rectangle(0, 0, 1300, 730 - (730 / 3)));
        scroll.setBackground(null);

        pnTable.add(scroll);
        return pnTable;
    }

    public void outModel(DefaultTableModel model, ArrayList<Person> psArr) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for (Person p : psArr) {
            data = new Vector();
            data.add(p.getPersonId());
            data.add(p.getFirstName());
            data.add(p.getLastName());
            data.add(p.getEnrollmentDate());
            model.addRow(data);
        }
        tbStudent.setModel(model);
    }

    public void listStudent() {
        try {
            ArrayList<Person> ps = stBLL.getList();
            outModel(model, ps);
        } catch (SQLException ex) {
            Logger.getLogger(SuggestPersonID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTextFieldContent() {
        if (!back) {
            return Integer.parseInt(txtStudent[0].getText());
        }
        return id;
    }

    public void setValueStudent(String loaiPerson, int id) {
        try {
            Person ps;
            if(loaiPerson=="giangvien")
            {
                ps = stBLL.findLecturer(id);
            }
            else
            {
                ps = stBLL.findStudents(id);
            }
            txtStudent[0].setText(Integer.toString(ps.getPersonId()));
            txtStudent[1].setText(ps.getFirstName());
            txtStudent[2].setText(ps.getLastName());
            txtStudent[3].setText(ps.getEnrollmentDate());

        } catch (SQLException ex) {
            Logger.getLogger(SuggestPersonID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
