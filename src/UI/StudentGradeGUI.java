package UI;

import BLL.StudentGradeBLL;
import DAL.StudentGrade;
import UI.model.checkError;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class StudentGradeGUI extends JPanel {

    private StudentGradeBLL sgBLL = new StudentGradeBLL();
    private int DEFALUT_WIDTH;
    private JPanel pnDisplay, pnOption, pnFind, pnTable;
    JLabel lbAdd, lbEdit, lbRemove, btnConfirm, btnBack;
    private JTable tbStudentGrade;
    private DefaultTableModel model;
    private boolean EditOrAdd = true;
    JTextField[] txtStudentGrade;
    JLabel[] lblStudentGrade, lbSuggestMa;
    int enrollmentID;
    private checkError checkError = new checkError();
    TableRowSorter<TableModel> rowSorter;

    public StudentGradeGUI(int width) {
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

                tbStudentGrade.clearSelection();
                tbStudentGrade.setEnabled(false);
            }
        });

        // MouseClick btnDelete
        lbRemove.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Xác nhận xóa", "Alert", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        sgBLL.deleteStudentGrade(enrollmentID);
                        cleanView();
                        tbStudentGrade.clearSelection();
                        outModel(model, sgBLL.getList());
                    } catch (SQLException ex) {
                        Logger.getLogger(StudentGradeGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        // MouseClick btnEdit
        lbEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (txtStudentGrade[0].getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn row cần sửa!");
                    return;
                }

                EditOrAdd = false;

                lbAdd.setVisible(false);
                lbEdit.setVisible(false);
                lbRemove.setVisible(false);

                btnConfirm.setVisible(true);
                btnBack.setVisible(true);
                tbStudentGrade.setEnabled(false);
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
                tbStudentGrade.setEnabled(true);
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
                        insertStudentGrade();

                    }
                } else // Edit
                {
                    i = JOptionPane.showConfirmDialog(null, "Xác nhận sửa?", "", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        updateStudentGrade();
                    }

                }

            }
        });

        pnTable();
        tbStudentGrade.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = tbStudentGrade.getSelectedRow();
                enrollmentID = (int) tbStudentGrade.getModel().getValueAt(i, 0);
                txtStudentGrade[0].setText(tbStudentGrade.getModel().getValueAt(i, 1).toString());
                txtStudentGrade[1].setText(tbStudentGrade.getModel().getValueAt(i, 2).toString());
                txtStudentGrade[2].setText(tbStudentGrade.getModel().getValueAt(i, 3).toString());
            }
        });
        ////CLICK CHỌN MÃ COURSE
        lbSuggestMa[0].addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SuggestCourseID sgCourseID = new SuggestCourseID(Integer.parseInt(txtStudentGrade[0].getText()));
                int idnew = sgCourseID.getTextFieldContent();
                if (idnew != 0) {
                    txtStudentGrade[0].setText(Integer.toString(idnew));
                }
            }
        });
        ////CLICK CHỌN MÃ Student
        lbSuggestMa[1].addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SuggestPersonID sgStudentID = new SuggestPersonID("student", Integer.parseInt(txtStudentGrade[1].getText()));
                int idnew = sgStudentID.getTextFieldContent();
                if (idnew != 0) {
                    txtStudentGrade[1].setText(Integer.toString(idnew));
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
        String[] arr = {"Course ID", "Student ID", "Grade"};
        txtStudentGrade = new JTextField[arr.length];
        lblStudentGrade = new JLabel[arr.length];
        lbSuggestMa = new JLabel[arr.length];
        int xLb = 40, yLb = 50;
        int xTxt = 250, yTxt = 50;
        int xLbSg = 450, yLbSg = 50;
        for (int i = 0; i < arr.length; i++) {
            lblStudentGrade[i] = new JLabel(arr[i]);
            lblStudentGrade[i].setBounds(xLb, yLb, 180, 30);
            lblStudentGrade[i].setName("lbl" + i);
            pnDisplay.add(lblStudentGrade[i]);
            yLb = yLb + 60;
            txtStudentGrade[i] = new JTextField();
            txtStudentGrade[i].setName("txt" + i);

            if (i < 2) {
                txtStudentGrade[i].setBounds(xTxt, yTxt, 200, 30);
                txtStudentGrade[i].setEditable(false);
            } else {
                txtStudentGrade[i].setBounds(xTxt, yTxt, 230, 30);
            }
            pnDisplay.add(txtStudentGrade[i]);
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
        cmbChoice.addItem("Enrollment ID");
        cmbChoice.addItem("Course ID");
        cmbChoice.addItem("Student ID");
        cmbChoice.addItem("Grade");
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
        header.add("Enrollment ID");
        header.add("Course ID");
        header.add("Student ID");
        header.add("Grade");
        model = new DefaultTableModel(header, 4);
        tbStudentGrade = new JTable(model);
        rowSorter = new TableRowSorter<TableModel>(model);
        tbStudentGrade.setRowSorter(rowSorter);
        listStudentGrade(); //Đọc từ database lên table 

        /**
         * ******************************************************
         */
        /**
         * ************** TẠO TABLE
         * ***********************************************************
         */
        // Chỉnh width các cột 
        tbStudentGrade.getColumnModel().getColumn(0).setPreferredWidth(40);

        // Custom table
        tbStudentGrade.setFocusable(false);
        tbStudentGrade.setIntercellSpacing(new Dimension(0, 0));
        tbStudentGrade.setRowHeight(30);
        tbStudentGrade.getTableHeader().setOpaque(false);
        tbStudentGrade.setFillsViewportHeight(true);
        tbStudentGrade.getTableHeader().setBackground(new Color(232, 57, 99));
        tbStudentGrade.getTableHeader().setForeground(Color.WHITE);
        tbStudentGrade.setSelectionBackground(new Color(52, 152, 219));

        // Add table vào ScrollPane
        JScrollPane scroll = new JScrollPane(tbStudentGrade);
        scroll.setBounds(new Rectangle(0, 0, this.DEFALUT_WIDTH - 230, 440));
        scroll.setBackground(null);

        pnTable.add(scroll);
        return pnTable;
    }

    public void cleanView() //Xóa trắng các TextField
    {
        for (int i = 0; i < txtStudentGrade.length; i++) {
            txtStudentGrade[i].setText("");

        }

    }

    public void outModel(DefaultTableModel model, ArrayList<StudentGrade> sgArr) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for (StudentGrade sg : sgArr) {
            data = new Vector();
            data.add(sg.getEnrollmentID());
            data.add(sg.getCourseID());
            data.add(sg.getStudentID());
            data.add(String.format("%.02f", sg.getGrade()));
            model.addRow(data);
        }
        tbStudentGrade.setModel(model);
    }

    public void listStudentGrade() // Chép ArrayList lên table
    {
        try // Chép ArrayList lên table
        {
            ArrayList<StudentGrade> sgArr = sgBLL.getList();
            outModel(model, sgArr);
        } catch (SQLException ex) {
            Logger.getLogger(StudentGradeGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validate(String courseID, String studentID, String grade) {
        boolean validateFull = true;
        if (courseID.equals("") || studentID.equals("") || grade.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            validateFull = false;
        }

        if (!checkError.check_Number(grade)) { //Nếu không phải số
            JOptionPane.showMessageDialog(null, "Vui lòng nhập trường điểm là số");
            validateFull = false;

        } else {
            float gradeNumber = Float.parseFloat(grade);
            if (gradeNumber < 0 || gradeNumber > 4) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập điểm từ 0 đến 4!");
                validateFull = false;
            }
        }
        return validateFull;
    }

    public void insertStudentGrade() {
        try {
            //Lấy dữ liệu từ TextField
            if (validate(txtStudentGrade[0].getText(), txtStudentGrade[1].getText(), txtStudentGrade[2].getText()) == false) {
                return;
            }
            int enrollmentidAdd = sgBLL.Enrollmentid();
            int courseid = Integer.parseInt(txtStudentGrade[0].getText());
            int studentid = Integer.parseInt(txtStudentGrade[1].getText());
            float grade = Float.parseFloat(txtStudentGrade[2].getText());

            StudentGrade st = new StudentGrade(enrollmentidAdd, courseid, studentid, grade);
            sgBLL.addStudentGrade(st);

            outModel(model, sgBLL.getList());// Load lại table
            cleanView();
            JOptionPane.showMessageDialog(null, "Thêm thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            tbStudentGrade.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(StudentGradeGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateStudentGrade() {
        try {
            if (validate(txtStudentGrade[0].getText(), txtStudentGrade[1].getText(), txtStudentGrade[2].getText()) == false) {
                return;
            }
            //Lấy dữ liệu từ TextField
            int courseid = Integer.parseInt(txtStudentGrade[0].getText());
            int studentid = Integer.parseInt(txtStudentGrade[1].getText());
            float grade = Float.parseFloat(txtStudentGrade[2].getText());
            //Upload lên DAO và BUS
            StudentGrade sg = new StudentGrade(enrollmentID, courseid, studentid, grade);
            sgBLL.updateStudentGrade(sg);

            outModel(model, sgBLL.getList());
            JOptionPane.showMessageDialog(null, "Sửa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            tbStudentGrade.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(StudentGradeGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
