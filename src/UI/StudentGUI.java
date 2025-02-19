package UI;

import BLL.PersonBLL;
import DAL.Person;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.jdesktop.swingx.JXDatePicker;

public class StudentGUI extends JPanel {

    private PersonBLL stBLL = new PersonBLL();
    private int DEFALUT_WIDTH;
    private JPanel pnDisplay, pnOption, pnFind, pnTable;
    JLabel lbAdd, lbEdit, lbRemove, btnConfirm, btnBack;
    private JTable tbStudent;
    private DefaultTableModel model;
    private boolean EditOrAdd = true;
    JTextField[] txtStudent;
    JLabel[] lbChucVu;
    JXDatePicker picker;
    int studentID;

    TableRowSorter<TableModel> rowSorter;

    public StudentGUI(int width) {
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

                tbStudent.clearSelection();
                tbStudent.setEnabled(false);
            }
        });

        // MouseClick btnDelete
        lbRemove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Xác nhận xóa", "Alert", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        stBLL.deleteStudent(studentID);
                        cleanView();
                        tbStudent.clearSelection();
                        JOptionPane.showMessageDialog(null, "Xóa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        outModel(model, stBLL.getList());
                    } catch (SQLException ex) {
                        Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        // MouseClick btnEdit
        lbEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (txtStudent[0].getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sinh viên cần sửa!");
                    return;
                }

                EditOrAdd = false;

                lbAdd.setVisible(false);
                lbEdit.setVisible(false);
                lbRemove.setVisible(false);

                btnConfirm.setVisible(true);
                btnBack.setVisible(true);
                tbStudent.setEnabled(false);
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
                tbStudent.setEnabled(true);
            }
        });

        //MouseClick btnConfirm
        btnConfirm.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i;
                if (EditOrAdd) //Thêm
                {
                    i = JOptionPane.showConfirmDialog(null, "Xác nhận thêm", "", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        try {
                            //Lấy dữ liệu từ TextField
                            int studentidAdd = stBLL.studentid();
                            String firstname = txtStudent[0].getText();
                            String lastname = txtStudent[1].getText();
                            String enrollmentdate = picker.getEditor().getText();
                            if (validate(firstname, lastname, enrollmentdate) == false) {
                                return;
                            }
                            //Upload lên DAO và BUS
                            Person st = new Person(studentidAdd, firstname, lastname, enrollmentdate);
                            stBLL.addStudent(st);

                            outModel(model, stBLL.getList());// Load lại table
                            cleanView();
                            JOptionPane.showMessageDialog(null, "Thêm thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                            tbStudent.setEnabled(true);
                        } catch (SQLException ex) {
                            Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else // Edit
                {
                    i = JOptionPane.showConfirmDialog(null, "Xác nhận sửa chức vụ", "", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        try {
                            //Lấy dữ liệu từ TextField
                            String firstname = txtStudent[0].getText();
                            String lastname = txtStudent[1].getText();
                            String enrollmentdate = picker.getEditor().getText();
                            if (validate(firstname, lastname, enrollmentdate) == false) {
                                return;
                            }
                            //Upload lên DAO và BUS
                            Person st = new Person(studentID, firstname, lastname, enrollmentdate);
                            stBLL.updateStudent(st);
                            outModel(model, stBLL.getList());
                            JOptionPane.showMessageDialog(null, "Sửa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                            tbStudent.setEnabled(true);
                        } catch (SQLException ex) {
                            Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }

            }
        });

        pnTable();
        tbStudent.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = tbStudent.getSelectedRow();
                studentID = (int) tbStudent.getModel().getValueAt(i, 0);
                txtStudent[0].setText(tbStudent.getModel().getValueAt(i, 1).toString());
                txtStudent[1].setText(tbStudent.getModel().getValueAt(i, 2).toString());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String dateInString = tbStudent.getModel().getValueAt(i, 3).toString();
                Date date = null;
                try {
                    date = formatter.parse(dateInString);
                } catch (ParseException ex) {

                }
                picker.setDate(date);
            }
        });

//TEST
//
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
        String[] arrChucVu = {"First Name", "Last Name", "Enrollment Date"};
        txtStudent = new JTextField[arrChucVu.length];
        lbChucVu = new JLabel[arrChucVu.length];
        int xLb = 40, yLb = 60;
        int xTxt = 250, yTxt = 60;
        for (int i = 0; i < arrChucVu.length; i++) {
            lbChucVu[i] = new JLabel(arrChucVu[i]);
            lbChucVu[i].setBounds(xLb, yLb, 180, 30);
            lbChucVu[i].setHorizontalAlignment(JLabel.CENTER);
            lbChucVu[i].setName("lb" + i);
            pnDisplay.add(lbChucVu[i]);
            yLb = yLb + 40;
            if (i != 2) {
                txtStudent[i] = new JTextField();
                txtStudent[i].setName("txt" + i);
                txtStudent[i].setBounds(xTxt, yTxt, 220, 30);
                pnDisplay.add(txtStudent[i]);
                yTxt = yTxt + 40;
            } else {
                picker = new JXDatePicker();
                picker.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
                picker.setBounds(xTxt, yTxt, 220, 30);
                pnDisplay.add(picker);
                yTxt = yTxt + 40;
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
        cmbChoice.addItem("Student ID");
        cmbChoice.addItem("First Name");
        cmbChoice.addItem("Last Name");
        cmbChoice.addItem("Enrollment Date");
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
         * ******************************************************
         */
        /**
         * ************** TẠO TABLE
         * ***********************************************************
         */
        // Chỉnh width các cột 
        tbStudent.getColumnModel().getColumn(0).setPreferredWidth(40);

        // Custom table
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
        scroll.setBounds(new Rectangle(0, 0, this.DEFALUT_WIDTH - 230, 440));
        scroll.setBackground(null);

        pnTable.add(scroll);
        return pnTable;
    }

    public void cleanView() //Xóa trắng các TextField
    {
        txtStudent[0].setEditable(true);
        for (int i = 0; i < txtStudent.length; i++) {
            if (i == 2) {
                picker.setDate(null);
            } else {
                txtStudent[i].setText("");
            }

        }

    }

    public void outModel(DefaultTableModel model, ArrayList<Person> st) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for (Person s : st) {
            data = new Vector();
            data.add(s.getPersonId());
            data.add(s.getFirstName());
            data.add(s.getLastName());
            data.add(s.getEnrollmentDate());
            model.addRow(data);
        }
        tbStudent.setModel(model);
    }

    public void listStudent() // Chép ArrayList lên table
    {
        try // Chép ArrayList lên table
        {
            ArrayList<Person> cv = stBLL.getList();
            outModel(model, cv);
        } catch (SQLException ex) {
            Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validate(String firstname, String latsname, String enrollmentdate) {

        if (firstname.equals("") || latsname.equals("") || enrollmentdate.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        return true;
    }
}
