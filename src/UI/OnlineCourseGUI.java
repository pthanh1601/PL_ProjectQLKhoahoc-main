package UI;

import BLL.CourseBLL;
import BLL.DepartmentBLL;
import BLL.OnlineCourseBLL;
import DAL.Course;
import DAL.Department;
import DAL.DepartmentDAL;
import DAL.OnlineCourse;
import UI.model.checkError;
import UI.model.navItem;
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

/**
 *
 * @author ACER
 */
public class OnlineCourseGUI extends JPanel {

    private OnlineCourseBLL onlCourseBLL = new OnlineCourseBLL();
    private CourseBLL CourseBLL = new CourseBLL();
    private ArrayList<Department> departmentList;
    private int DEFAULT_WIDTH;
    private JPanel pnDisplay, pnOption, pnFind, pnTable;
    JLabel lbAdd, lbEdit, lbRemove, btnConfirm, btnBack;
    private JTable tbOnlineCourse;
    private DefaultTableModel model;
    private boolean EditOrAdd = true;
    JTextField[] txtOnlineCourse;
    JLabel[] lbChucVu;
    JComboBox departmentChoice = new JComboBox();
    int courseID;
    checkError checkError = new checkError();
    TableRowSorter<TableModel> rowSorter;

    public OnlineCourseGUI(int width) {
        DEFAULT_WIDTH = width;
        init();
    }

    public void init() {
        try {
            departmentList = new DepartmentBLL().readDepartment();
        } catch (SQLException ex) {
            Logger.getLogger(OnlineCourseGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        setLayout(new BorderLayout(10, 10));
        setBackground(null);
        setBounds(new Rectangle(10, 10, this.DEFAULT_WIDTH - 200, 680));

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

                tbOnlineCourse.clearSelection();
                tbOnlineCourse.setEnabled(false);
            }
        });

        // MouseClick btnDelete
        lbRemove.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Xác nhận xóa", "Alert", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        onlCourseBLL.deleteOnlineCourse(courseID);
                        cleanView();
                        tbOnlineCourse.clearSelection();
                        JOptionPane.showMessageDialog(null, "Xóa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        outModel(model, onlCourseBLL.getList());
                    } catch (SQLException ex) {
                        Logger.getLogger(OnlineCourseGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        // MouseClick btnEdit
        lbEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (txtOnlineCourse[0].getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn khóa học online cần sửa!");
                    return;
                }
                EditOrAdd = false;
                lbAdd.setVisible(false);
                lbEdit.setVisible(false);
                lbRemove.setVisible(false);
                btnConfirm.setVisible(true);
                btnBack.setVisible(true);
                tbOnlineCourse.setEnabled(false);
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
                tbOnlineCourse.setEnabled(true);
            }
        });

        //MouseClick btnConfirm
        btnConfirm.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i;
                if (EditOrAdd) {//Thêm                
                    i = JOptionPane.showConfirmDialog(null, "Xác nhận thêm", "", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        insertOnlineCourse();
                    }
                } else // Edit
                {
                    i = JOptionPane.showConfirmDialog(null, "Xác nhận sửa", "", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        updateOnlineCourse();
                    }
                }

            }
        });

        pnTable();
        tbOnlineCourse.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = tbOnlineCourse.getSelectedRow();
                courseID = (int) tbOnlineCourse.getModel().getValueAt(i, 0);
                txtOnlineCourse[0].setText(tbOnlineCourse.getModel().getValueAt(i, 1).toString());
                txtOnlineCourse[1].setText(tbOnlineCourse.getModel().getValueAt(i, 2).toString());
                departmentChoice.setSelectedItem(getDepartmentName(Integer.parseInt(tbOnlineCourse.getModel().getValueAt(i, 3).toString())));
                txtOnlineCourse[3].setText(tbOnlineCourse.getModel().getValueAt(i, 4).toString());
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
        pnDisplay.setPreferredSize(new Dimension(((this.DEFAULT_WIDTH - 200) / 2) - 20, 240));
        pnDisplay.setBackground(Color.pink);
        String[] arrChucVu = {"Title", "Credits", "DepartmentID", "URL"};
        txtOnlineCourse = new JTextField[arrChucVu.length];
        lbChucVu = new JLabel[arrChucVu.length];

        int xLb = 40, yLb = 30; //sua
        int xTxt = 250, yTxt = 30; //sua
        for (int i = 0; i < arrChucVu.length; i++) {
            lbChucVu[i] = new JLabel(arrChucVu[i]);
            lbChucVu[i].setBounds(xLb, yLb, 180, 30);
            lbChucVu[i].setHorizontalAlignment(JLabel.CENTER);
            lbChucVu[i].setName("lb" + i);
            pnDisplay.add(lbChucVu[i]);
            yLb = yLb + 50;
            if (i != 2) {
                txtOnlineCourse[i] = new JTextField();
                txtOnlineCourse[i].setName("txt" + i);
                txtOnlineCourse[i].setBounds(xTxt, yTxt, 220, 30);
                pnDisplay.add(txtOnlineCourse[i]);
                yTxt = yTxt + 50;
            } else {
                departmentChoice.addItem("...");
                for (Department de : departmentList) {
                    departmentChoice.addItem(de.getName());
                    departmentChoice.setName(Integer.toString(de.getDepartmentID()));
                }

                departmentChoice.setBounds(xTxt, yTxt, 220, 30);
                pnDisplay.add(departmentChoice);
                yTxt = yTxt + 50;
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
        cmbChoice.setFont(new Font("Segoe UI", Font.PLAIN, 15));//sua
        cmbChoice.addItem("OnlineCourse ID");
        cmbChoice.addItem("Title");
        cmbChoice.addItem("Credits");
        cmbChoice.addItem("DepartmentID");
//        cmbChoice.addItem("Enrollment Date");
        cmbChoice.setBounds(0, 0, 150, 40);

        //Phần TextField
        JTextField txtSearch = new JTextField();
        txtSearch.setBounds(120, 0, 330, 40);
        txtSearch.setBorder(null);
        txtSearch.setOpaque(false);
//        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 20));

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
        header.add("OnlineCourse ID");
        header.add("Title");
        header.add("Credits");
        header.add("DepartmentID");
        header.add("URL");
        model = new DefaultTableModel(header, 5);
        tbOnlineCourse = new JTable(model);
        rowSorter = new TableRowSorter<TableModel>(model);
        tbOnlineCourse.setRowSorter(rowSorter);
        listOnlineCourse(); //Đọc từ database lên table 

        /**
         * ******************************************************
         */
        /**
         * ************** TẠO TABLE
         * ***********************************************************
         */
        // Chỉnh width các cột 
        tbOnlineCourse.getColumnModel().getColumn(0).setPreferredWidth(40);

        // Custom table
        tbOnlineCourse.setFocusable(false);
        tbOnlineCourse.setIntercellSpacing(new Dimension(0, 0));
        tbOnlineCourse.setRowHeight(30);
        tbOnlineCourse.getTableHeader().setOpaque(false);
        tbOnlineCourse.setFillsViewportHeight(true);
        tbOnlineCourse.getTableHeader().setBackground(new Color(232, 57, 99));
        tbOnlineCourse.getTableHeader().setForeground(Color.WHITE);
        tbOnlineCourse.setSelectionBackground(new Color(52, 152, 219));

        // Add table vào ScrollPane
        JScrollPane scroll = new JScrollPane(tbOnlineCourse);
        scroll.setBounds(new Rectangle(0, 0, this.DEFAULT_WIDTH - 230, 440));
        scroll.setBackground(null);

        pnTable.add(scroll);
        return pnTable;
    }

    public void cleanView() //Xóa trắng các TextField
    {
        txtOnlineCourse[0].setEditable(true);
        for (int i = 0; i < txtOnlineCourse.length; i++) {
            if (i == 2) {
                departmentChoice.setSelectedIndex(0);
            } else {
                txtOnlineCourse[i].setText("");
            }
        }
    }

    public void outModel(DefaultTableModel model, ArrayList<OnlineCourse> onlcArr) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for (OnlineCourse onlc : onlcArr) {
            data = new Vector();
            data.add(onlc.getCourseID());
            data.add(onlc.getTitle());
            data.add(onlc.getCredits());
            data.add(onlc.getDepartmentID());
            data.add(onlc.getCourseURL());
            model.addRow(data);
        }
        tbOnlineCourse.setModel(model);
    }

    public void listOnlineCourse() // Chép ArrayList lên table
    {
        try {
            ArrayList<OnlineCourse> onlcArr = onlCourseBLL.getList();
            outModel(model, onlcArr);
        } catch (SQLException ex) {
            Logger.getLogger(OnlineCourseGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validate(String title, String credits, String department, String url) {
        boolean validateFull = true;
        if (title.equals("") || credits.equals("") || department.equals("...") || url.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            validateFull = false;
        }

        if (!checkError.check_Number(credits)) { //Nếu không phải số
            JOptionPane.showMessageDialog(null, "Vui lòng nhập trường credits là số");
            validateFull = false;

        }
        return validateFull;
    }

    public String getDepartmentName(int departmentId) {
        for (Department de : departmentList) {
            if (de.getDepartmentID() == departmentId) {
                return de.getName();
            }
        }
        return "";
    }

    public int getDepartmentId(String name) {
        for (Department de : departmentList) {
            if (de.getName().equals(name)) {
                return de.getDepartmentID();
            }
        }
        return -1;
    }

    public void insertOnlineCourse() {
        try {
            //Lấy dữ liệu từ TextField
            int courseIDAdd = CourseBLL.courseID();
            String title = txtOnlineCourse[0].getText();
            String credits = txtOnlineCourse[1].getText();
            String departmentID = departmentChoice.getName();
            String url = txtOnlineCourse[3].getText();
            if (validate(title, credits, departmentID, url) == false) {
                return;
            }
            OnlineCourse onlc = new OnlineCourse(courseIDAdd, title, Integer.parseInt(credits),
                    Integer.parseInt(departmentID), url);
            onlCourseBLL.addOnlineCourse(onlc);
            outModel(model, onlCourseBLL.getList());// Load lại table
            cleanView();
            JOptionPane.showMessageDialog(null, "Thêm thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            tbOnlineCourse.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(OnlineCourseGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateOnlineCourse() {
        try {
            //Lấy dữ liệu từ TextField
            String title = txtOnlineCourse[0].getText();
            String credits = txtOnlineCourse[1].getText();
            String departmentName = departmentChoice.getSelectedItem().toString();
            int departmentID = getDepartmentId(departmentName);
            String url = txtOnlineCourse[3].getText();
            if (validate(title, credits, departmentName, url) == false) {
                return;
            }
            OnlineCourse onlc = new OnlineCourse(courseID, title, Integer.parseInt(credits),
                    departmentID, url);
            onlCourseBLL.updateOnlineCourse(onlc);
            outModel(model, onlCourseBLL.getList());
            JOptionPane.showMessageDialog(null, "Sửa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            tbOnlineCourse.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(OnlineCourseGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
