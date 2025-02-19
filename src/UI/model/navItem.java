/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Shadow
 */
public class navItem extends JPanel implements MouseListener {

    private JLabel lb, icon;
    private Color hover = new Color(100, 113, 140);
    private Color normal = new Color(67, 76, 94);
    private boolean active;
    private String name, img, imgActive, imgHover;
    int width, height;
    private Rectangle rec = new Rectangle();
    JPanel itemDetail;

    public navItem(String s, Rectangle r, String img, String imgActive) {
        name = s;
        lb = new JLabel(name);
        this.img = img;
        this.imgActive = imgActive;
        this.imgHover = this.img;
        this.icon = new JLabel();
        rec = r;
        init();
    }

    public navItem(String s, int width, int height, String img, String imgActive) {
        name = s;
        lb = new JLabel(name);
        this.img = img;
        this.imgActive = imgActive;
        this.imgHover = this.img;
        this.icon = new JLabel();
        this.width = width;
        this.height = height;
        initBounds();
    }

    public navItem(String s, Rectangle r, String img, String imgActive, String imgHover, Color hover) {
        name = s;
        lb = new JLabel(name);
        this.img = img;
        this.imgActive = imgActive;
        this.imgHover = imgHover;
        this.icon = new JLabel();
        rec = r;
        this.hover = hover;
        init();
    }

    public navItem(String s, Rectangle r) {
        lb = new JLabel(s);
        icon = new JLabel();
        rec = r;
        init();
    }

    public void setColorNormal(Color e) {
        this.normal = e;
        setBackground(e);
        repaint();
    }

    public JPanel isButton() {
        icon.setBounds(new Rectangle(rec.width / 4 + 2, rec.height / 4, 50, 30));
        normal = null;
        setBackground(normal);
//        repaint();
        return this;
    }

    public void init() {

        addMouseListener(this);
        Font font = new Font("Segoe UI", Font.BOLD, 13);
        setLayout(null);
        setBounds(rec);        

        icon.setIcon(new ImageIcon("./src/img/" + img));
        icon.setBackground(Color.white);
        icon.setBounds(new Rectangle(rec.width / 7, rec.height / 4, 50, 30));

        lb.setFont(font);
        lb.setForeground(Color.white);
        lb.setBounds(new Rectangle(rec.width / 4 + 10, rec.height / 4, 150, 30));

        if (active) {
            setBackground(Color.WHITE);
        } 
        if(!active) {
            setBackground(normal);
        }
        add(icon);
        add(lb);

    }

    public void initBounds() {

        addMouseListener(this);
        Font font = new Font("Segoe UI", Font.BOLD, 13);
        setLayout(new FlowLayout(0));
        setBounds(rec);
        itemDetail = new JPanel(null);

        icon.setIcon(new ImageIcon("./src/img/" + img));
        icon.setBackground(Color.white);
        icon.setBounds(new Rectangle(10, 0, 50, 40));

        lb.setFont(font);
        lb.setForeground(Color.white);
        lb.setBounds(new Rectangle(35, 0, 150, 40));

        if (active) {
            itemDetail.setBackground(Color.white);
        } else {
            itemDetail.setBackground(normal);
        }
        itemDetail.add(icon);
        itemDetail.add(lb);
        itemDetail.setPreferredSize(new Dimension(width, height));
        setBackground(normal);
        add(itemDetail);
    }

    public void setActive(boolean a) {
        active = a;

    }

    public void doActive() {      
        active = true;
        icon.setIcon(new ImageIcon("./src/img/" + imgActive));
        lb.setForeground(Color.BLACK);        
        itemDetail.setBackground(Color.WHITE);
    }

    public void noActive() {
        active = false;
        icon.setIcon(new ImageIcon("./src/img/" + img));
        lb.setForeground(Color.WHITE);
        itemDetail.setBackground(normal);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!active) {
            setBackground(hover);
            icon.setIcon(new ImageIcon("./src/img/" + imgHover));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!active) {
            setBackground(normal);
            icon.setIcon(new ImageIcon("./src/img/" + img));
        }
    }
}
