
package UI.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class header extends JPanel{
    private int height,width;
    private JFrame frame;
    public header(JFrame frame, int w,int h)
    {
        width = w;
        height = h;        
        init(frame);
    }
    public void init(JFrame frame)
    {
        setLayout(null);
        setSize(width,height);
        setBackground(null);
        
        JLabel logo = new JLabel(new ImageIcon("./src/img/title.png"),JLabel.CENTER);
        logo.setBounds(new Rectangle(30,10, 25, 25));
        Font font = new Font("Segoe UI",Font.BOLD,15);
        JLabel name = new JLabel("QUẢN LÝ KHÓA HỌC",JLabel.CENTER);
        name.setFont(font);
        name.setForeground(Color.white);
        name.setBounds(new Rectangle(60, 0, 150, 40));
        
        add(logo);
        add(name);
        
        
        navItem exit = new navItem("", new Rectangle(width-40, -8, 40, 50), "close_window_26px.png", "close_window_26px.png");
        navItem minimize = new navItem("", new Rectangle(width-80, -8, 40, 50), "minimize_window_26px.png", "minimize_window_26px.png");
        add(minimize);
        add(exit);
        
        exit.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e)
           {
             frame.dispose();
           }
        });
        
        minimize.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e)
           {
              frame.setState(Frame.ICONIFIED);
           }
        });

        
    }
}
