/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import UI.model.header;
import UI.model.navItem;

/**
 *
 * @author Shadow
 */
public class QLKhoaHoc extends JFrame implements MouseListener {

    private String userID;
    private String userName;
    private String role;
    private ArrayList<String> dsChucNangKhongThuocQuyen;
    JScrollPane scroll;
    private boolean flag = true;
    private JPanel header, nav, main;
    private int DEFAULT_HEIGHT = 730, DEFALUT_WIDTH = 1300;
    private ArrayList<String> navItem = new ArrayList<>();  //Chứa thông tin có button cho menu gồm
    private ArrayList<navItem> navObj = new ArrayList<>();  //Chứa cái button trên thanh menu

    public QLKhoaHoc() {
        Toolkit screen = Toolkit.getDefaultToolkit();
        init();
    }

    public void init() {
        setTitle("Quản Lý nhân sự");
        ImageIcon logo = new ImageIcon("/img/title.png");
        setIconImage(logo.getImage());
        setLayout(new BorderLayout());
        setSize(DEFALUT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        /**
         * ********** PHẦN HEADER ************************************
         */
        header = new JPanel(null);
        header.setBackground(new Color(27, 27, 30));
        header.setPreferredSize(new Dimension(DEFALUT_WIDTH, 40));

        header headermain = new header(this, DEFALUT_WIDTH, 40);

        if (userName != null) {
            if (role.equals("Admin")) {
                userName = "Admin";
            }
            JLabel user = new JLabel("Chào, " + userName);
            user.setForeground(Color.WHITE);
            user.setBounds(new Rectangle(DEFALUT_WIDTH - 300, -7, 150, 50));
            headermain.add(user);
            //Tạo btn Logout
            navItem btnLogOut = new navItem("", new Rectangle(DEFALUT_WIDTH - 150, -8, 50, 50), "Logout_24px.png", "Logout_24px.png", "Logout_24px.png", new Color(80, 80, 80));
            headermain.add(btnLogOut.isButton());
        }

        header.add(headermain);

        /**
         * ********** PHẦN NAVIGATION ( MENU ) *************************
         */
        menu();
        /**
         * ********** PHẦN MAIN ( HIỂN THỊ ) *************************
         */

        main = new JPanel(null);
        main.removeAll();
        main.add(new MainGUI(1300));
        main.repaint();
        main.revalidate();
        /**
         * ********** PHẦN ADD *************************
         */

        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }
        QLKhoaHoc ql = new QLKhoaHoc();

    }

    public void menu() {

        nav = new JPanel(new FlowLayout(0));
        nav.setBackground(new Color(55, 63, 81));
        nav.setPreferredSize(new Dimension(220, DEFAULT_HEIGHT));

        scroll = new JScrollPane(nav);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(1, 600));
        scroll.setHorizontalScrollBarPolicy(scroll.HORIZONTAL_SCROLLBAR_NEVER);

        //Thêm item vào thanh menu (Tên item : icon : icon hover)
        navItem = new ArrayList<>();  //Chứa thông tin có button cho menu gồm ( Tên btn : icon : icon hover )   
        navItem.add("Quản lý khóa học:study_24px.png:study_24px_click.png");
        navItem.add("Quản lý phân công giảng dạy:task_24px.png:task_24px_click.png");
        navItem.add("Quản lý kết quả khóa học:leaderboard_24px.png:leaderboard_24px_click.png");
        outNav();
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e){
        for (int i = 0; i < navObj.size(); i++) {
            navItem item = navObj.get(i); // lấy vị trí item trong menu
            if (e.getSource() == item) {
                item.doActive(); // Active NavItem đc chọn 
                changeMainInfo(i); // Hiển thị ra phần main
            } else {
                item.noActive();
            }
        }
    }

    public void changeMainInfo(int i) //Đổi Phần hiển thị khi bấm btn trên menu
    {
        if (flag && i > 0) // Thay đổi nếu đang dropdown
        {
            i = i + 2;
        }

        switch (i) {
            case 0:
                System.out.println("KHÓA HỌC");
                if (flag) {
                    System.out.println(flag);
                    // Thêm 2 btn vào dưới thống kê
                    navItem.add(1, "Online:online_group_studying_24px.png:online_group_studying_24px_click.png");
                    navItem.add(2, "Onsite:classroom_24px.png:classroom_24px_click.png");

                    flag = false;
                } else {
                    navItem.remove(1);
                    navItem.remove(1);

                    flag = true;
                }
                outNav();
                break;
            case 1:
                System.out.println("ONLINE");
                main.removeAll();
                main.add(new OnlineCourseGUI(DEFALUT_WIDTH));
                main.repaint();
                main.revalidate();
                break;
            case 2:
                System.out.println("ONSITE");
                main.removeAll();
                main.add(new OnsiteCourseGUI(DEFALUT_WIDTH));
                main.repaint();
                main.revalidate();
                break;
            case 3:
                System.out.println("PHÂN CÔNG GIẢNG DẠY");
                main.removeAll();
                main.add(new CourseInstructorGUI(DEFALUT_WIDTH));
                main.repaint();
                main.revalidate();
                break;
            case 4:
                System.out.println("KẾT QUẢ");
                main.removeAll();
                main.add(new StudentGradeGUI(DEFALUT_WIDTH));
                main.repaint();
                main.revalidate();
                break;
            default:
                break;
        }
    }

    public void outNav() {
        //Gắn các NavItem vào NavOBJ
        navObj.clear();
        for (int i = 0; i < navItem.size(); i++) {
            String s = navItem.get(i).split(":")[0];
            String icon = navItem.get(i).split(":")[1];
            String iconActive = navItem.get(i).split(":")[2];
            navObj.add(new navItem(s, 220, 40, icon, iconActive));
            navObj.get(i).setToolTipText(s);
            navObj.get(i).addMouseListener(this);
            if (iconActive.equals("circle_16px.png")) {
                navObj.get(i).setColorNormal(new Color(86, 94, 127));
            }

        }
        if (!flag && navObj.size() > 5) //Đổi màu phần DropDown của thống kê
        {
            navObj.get(3).setColorNormal(new Color(86, 94, 127));
            navObj.get(4).setColorNormal(new Color(86, 94, 127));
        }

        //Xuất ra Naigation
        nav.removeAll();
        JLabel profile = new JLabel(new ImageIcon("./src/img/course_170px.png"));
        profile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                main.removeAll();
                main.add(new MainGUI(1300));
                main.repaint();
                main.revalidate();
            }
        });

        nav.add(profile);
        for (navItem n : navObj) {
            nav.add(n);
        }
        repaint();
        revalidate();
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {

    }

}
