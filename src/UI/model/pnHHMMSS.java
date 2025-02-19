package UI.model;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class pnHHMMSS extends JPanel {

    public pnHHMMSS() {
        init();
    }

    public void init() {
        JLabel lbTmp = new JLabel(":", JLabel.CENTER);
        lbTmp.setPreferredSize(new Dimension(20, 30));
        JPanel pn = new JPanel();
        pn.setLayout(new FlowLayout(1, 0, 0));

        String hour[] = new String[25];
        for (int i = 0; i <= 24; i++) {
            hour[i] = String.valueOf(i);
        }
        JComboBox cb = new JComboBox(hour);
        cb.setSize(50, 30);
    
        this.add(cb);
        String minutes[] = new String[61];
        for (int i = 0; i <= 60; i++) {
            minutes[i] = String.valueOf(i);
        }
        JComboBox cbminutes = new JComboBox(minutes);
        cbminutes.setSize(50, 30);
   
        this.add(cbminutes);
        String seconds[] = new String[61];
        for (int i = 0; i <= 60; i++) {
            seconds[i] = String.valueOf(i);
        }
        JComboBox cbseconds = new JComboBox(seconds);
        cbseconds.setSize(50, 30);

        this.add(cbseconds);

        this.setVisible(true);
    }

//    public static void main(String[] args) {
//        new pnHHMMSS();
//    }
}
