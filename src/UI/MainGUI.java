/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.FlowLayout;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author MaiThy
 */
public class MainGUI extends JPanel {
    
    private int DEFALUT_WIDTH;
    private JLabel lbmain;
    private JPanel pnmain;
    
    public MainGUI(int width) {
        DEFALUT_WIDTH = width;
        init();
    }
    
    public void init() {
        setLayout(new FlowLayout());
        setBackground(null);
        setBounds(new Rectangle(-10, -10, this.DEFALUT_WIDTH - 200, 700));
//        LBMAIN
        lbmain = new JLabel(new ImageIcon("./src/img/QuanLyKhoaHoc.png"));
        lbmain.setBounds(new Rectangle(0, 0, this.DEFALUT_WIDTH - 200, 680));
//        PNMAIN
        pnmain = new JPanel();
        pnmain.setLayout(new FlowLayout());
        pnmain.setBounds(new Rectangle(0, 0, this.DEFALUT_WIDTH - 200, 680));
        pnmain.add(lbmain);
        
        this.add(pnmain);
//        this.setUndecorated(true);
        this.setVisible(true);
        
    }
    
}
