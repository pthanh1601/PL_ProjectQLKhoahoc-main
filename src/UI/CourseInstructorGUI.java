/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import BLL.CourseBLL;
import BLL.CourseinstructorBLL;
import BLL.PersonBLL;
import BLL.StudentGradeBLL;
import DAL.Courseinstructor;
import DAL.CourseinstructorDAL;
import DAL.StudentGrade;
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
import java.util.ArrayList;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Thanhchan
 */
public class CourseInstructorGUI extends JPanel {

    private CourseBLL courseBLL = new CourseBLL();
    private PersonBLL personBLL = new PersonBLL();
    private CourseinstructorBLL courseinsBLL = new CourseinstructorBLL();
    private int DEFALUT_WIDTH;
    private JPanel pnDisplay, pnOption, pnFind, pnTable;
    JLabel lbAdd, lbEdit, lbRemove, btnConfirm, btnBack;
    private JTable tbCourseinstructor;
    private DefaultTableModel model;
    private boolean EditOrAdd = true;
    JTextField[] txtCourseinstructor;
    JLabel[] lblCourseinstructor, lbSuggestMa;
    int CourseID = 0;
    private checkError checkError = new checkError();
    TableRowSorter<TableModel> rowSorter;

    public CourseInstructorGUI(int width) {
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

                tbCourseinstructor.clearSelection();
                tbCourseinstructor.setEnabled(false);
            }
        });

        // MouseClick btnDelete
        lbRemove.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Xác nhận xóa", "Alert", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {

                        courseinsBLL.deleteCourseinstructor(CourseID);
                        cleanView();
                        tbCourseinstructor.clearSelection();
                        JOptionPane.showMessageDialog(null, "Xóa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        outModel(model, courseinsBLL.getList());
                    } catch (SQLException ex) {
                        Logger.getLogger(CourseInstructorGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        // MouseClick btnEdit
        lbEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (txtCourseinstructor[0].getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn row cần sửa!");
                    return;
                }

                EditOrAdd = false;

                lbAdd.setVisible(false);
                lbEdit.setVisible(false);
                lbRemove.setVisible(false);

                btnConfirm.setVisible(true);
                btnBack.setVisible(true);
                tbCourseinstructor.setEnabled(false);
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
                tbCourseinstructor.setEnabled(true);
            }
        });

        //MouseClick btnConfirm
        btnConfirm.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i;
                if (EditOrAdd) //Thêm
                {
                    i = JOptionPane.showConfirmDialog(null, "Xác nhận thêm?", "", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        insertCourseInstructor();
                    }
                } else // Edit
                {
                    i = JOptionPane.showConfirmDialog(null, "Xác nhận sửa?", "", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        updateCourseInstructor();
                    }

                }

            }
        });

        pnTable();
        tbCourseinstructor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = tbCourseinstructor.getSelectedRow();

                txtCourseinstructor[0].setText(tbCourseinstructor.getModel().getValueAt(i, 0).toString());
                txtCourseinstructor[1].setText(tbCourseinstructor.getModel().getValueAt(i, 1).toString());
                try {
                    CourseID = courseBLL.getIDCourse(txtCourseinstructor[0].getText());
                } catch (SQLException ex) {
                    Logger.getLogger(CourseInstructorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        ////CLICK CHỌN MÃ COURSE
        lbSuggestMa[0].addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SuggestCourseID sgCourseID = new SuggestCourseID(CourseID);
                int idnew = sgCourseID.getTextFieldContent();
                if (idnew != 0) {
                    try {
                        txtCourseinstructor[0].setText(courseBLL.getNameCourse(idnew));
                    } catch (SQLException ex) {
                        Logger.getLogger(CourseInstructorGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ////CLICK CHỌN MÃ Student
        lbSuggestMa[1].addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    SuggestPersonID sgPersonID = new SuggestPersonID("giangvien", personBLL.getIDPerson(txtCourseinstructor[1].getText()));
                    int idnew = sgPersonID.getTextFieldContent();
                    if (idnew != 0) {
                        txtCourseinstructor[1].setText(personBLL.getNamePerson(idnew));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CourseInstructorGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        pnDisplay.setPreferredSize(new Dimension(((this.DEFALUT_WIDTH - 200) / 2) - 20, 240));
        pnDisplay.setBackground(Color.pink);
        String[] arr = {"Course", "Lecturer"};
        txtCourseinstructor = new JTextField[arr.length];
        lblCourseinstructor = new JLabel[arr.length];
        lbSuggestMa = new JLabel[arr.length];
        int xLb = 40, yLb = 50;
        int xTxt = 250, yTxt = 50;
        int xLbSg = 450, yLbSg = 50;
        for (int i = 0; i < arr.length; i++) {
            lblCourseinstructor[i] = new JLabel(arr[i]);
            lblCourseinstructor[i].setBounds(xLb, yLb, 180, 30);
            lblCourseinstructor[i].setName("lbl" + i);
            pnDisplay.add(lblCourseinstructor[i]);
            yLb = yLb + 60;
            txtCourseinstructor[i] = new JTextField();
            txtCourseinstructor[i].setName("txt" + i);

            if (i < 2) {
                txtCourseinstructor[i].setBounds(xTxt, yTxt, 200, 30);
                txtCourseinstructor[i].setEditable(false);
            } else {
                txtCourseinstructor[i].setBounds(xTxt, yTxt, 230, 30);
            }
            pnDisplay.add(txtCourseinstructor[i]);
            yTxt = yTxt + 60;
            if (i < 2) {
                lbSuggestMa[i] = new JLabel("...", JLabel.CENTER);
                lbSuggestMa[i].setBounds(xLbSg, yLbSg, 30, 30);
                lbSuggestMa[i].setHorizontalAlignment(JLabel.CENTER);
                lbSuggestMa[i].setOpaque(true);
                lbSuggestMa[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                pnDisplay.add(lbSuggestMa[i]);
                yLbSg = yLbSg + 60;
            }

        }
        return pnDisplay;
    }

    public JPanel pnOption() {
        pnOption = new JPanel();
        pnOption.setLayout(null);
        pnOption.setBackground(Color.PINK);
        pnOption.setPreferredSize(new Dimension(530, 220));
        lbAdd = new JLabel(new ImageIcon("./src/img/add.png"), JLabel.CENTER);
        lbEdit = new JLabel(new ImageIcon("./src/img/edit.png"), JLabel.CENTER);
        lbRemove = new JLabel(new ImageIcon("./src/img/remove.png"), JLabel.CENTER);

        lbAdd.setBounds(155, 10, 200, 50);
        lbEdit.setBounds(155, 65, 200, 50);
        lbRemove.setBounds(155, 120, 200, 50);
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
        pnFind.setBounds(0, 190, 530, 40);
        pnFind.setBorder(createLineBorder(Color.BLACK)); //Chỉnh viền 
        //PHẦN CHỌN SEARCH
        JComboBox cmbChoice = new JComboBox();
        cmbChoice.setEditable(true);
        cmbChoice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbChoice.addItem("Course");
        cmbChoice.addItem("Lecturer");
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
        pnTable.setPreferredSize(new Dimension(1090, 440));
        /**
         * ************ TẠO MODEL VÀ HEADER ********************
         */
        Vector header = new Vector();
        header.add("Course");
        header.add("Lecturer");

        model = new DefaultTableModel(header, 2);
        tbCourseinstructor = new JTable(model);
        rowSorter = new TableRowSorter<TableModel>(model);
        tbCourseinstructor.setRowSorter(rowSorter);
        listcourseinstructor(); //Đọc từ database lên table 

        /**
         * ******************************************************
         */
        /**
         * ************** TẠO TABLE
         * ***********************************************************
         */
        // Chỉnh width các cột 
        tbCourseinstructor.getColumnModel().getColumn(0).setPreferredWidth(40);

        // Custom table
        tbCourseinstructor.setFocusable(false);
        tbCourseinstructor.setIntercellSpacing(new Dimension(0, 0));
        tbCourseinstructor.setRowHeight(30);
        tbCourseinstructor.getTableHeader().setOpaque(false);
        tbCourseinstructor.setFillsViewportHeight(true);
        tbCourseinstructor.getTableHeader().setBackground(new Color(232, 57, 99));
        tbCourseinstructor.getTableHeader().setForeground(Color.WHITE);
        tbCourseinstructor.setSelectionBackground(new Color(52, 152, 219));

        // Add table vào ScrollPane
        JScrollPane scroll = new JScrollPane(tbCourseinstructor);
        scroll.setBounds(new Rectangle(0, 0, this.DEFALUT_WIDTH - 230, 440));
        scroll.setBackground(null);

        pnTable.add(scroll);
        return pnTable;
    }

    public void cleanView() //Xóa trắng các TextField
    {
        for (int i = 0; i < txtCourseinstructor.length; i++) {
            txtCourseinstructor[i].setText("");

        }

    }

    public void outModel(DefaultTableModel model, ArrayList<Courseinstructor> ciArr) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for (Courseinstructor ci : ciArr) {
            try {
                data = new Vector();
                data.add(courseBLL.getNameCourse(ci.getCourseID()));
                data.add(personBLL.getNamePerson(ci.getPersonID()));
                model.addRow(data);
            } catch (SQLException ex) {
                Logger.getLogger(CourseInstructorGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        tbCourseinstructor.setModel(model);
    }

    public void listcourseinstructor() // Chép ArrayList lên table
    {
        try {
            ArrayList<Courseinstructor> ciArr = courseinsBLL.getList();
            outModel(model, ciArr);
        } catch (SQLException ex) {
            Logger.getLogger(CourseInstructorGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validate(String instructorid, String personid) {

        if (instructorid.equals("") || personid.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        return true;
    }

    public void insertCourseInstructor() {
        try {
            //Lấy dữ liệu từ TextField
            if (validate(txtCourseinstructor[0].getText(), txtCourseinstructor[1].getText()) == false) {
                return;
            }
            int courseid = courseBLL.getIDCourse(txtCourseinstructor[0].getText());
            int personid = personBLL.getIDPerson(txtCourseinstructor[1].getText());
            
            Courseinstructor ci = new Courseinstructor(courseid, personid);
            courseinsBLL.addCoureinstructor(ci);

            outModel(model, courseinsBLL.getList());// Load lại table
            cleanView();
            JOptionPane.showMessageDialog(null, "Thêm thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            tbCourseinstructor.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(CourseInstructorGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateCourseInstructor() {
        try {
            if (validate(txtCourseinstructor[0].getText(), txtCourseinstructor[1].getText()) == false) {
                return;
            }
            //Lấy dữ liệu từ TextField
            int courseid = courseBLL.getIDCourse(txtCourseinstructor[0].getText());
            int personid = personBLL.getIDPerson(txtCourseinstructor[1].getText());
            //Upload lên DAO và BUS
            Courseinstructor ci = new Courseinstructor(courseid, personid);
            courseinsBLL.updateCourseintructor(ci);

            outModel(model, courseinsBLL.getList());
            JOptionPane.showMessageDialog(null, "Sửa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            tbCourseinstructor.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(CourseInstructorGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
