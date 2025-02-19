/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.model;

import javax.swing.JOptionPane;

/**
 *
 * @author MaiThy
 */
public class validation {

    checkError check = new checkError();
    DateValidator validator = new DateValidatorUsingDateFormat("yyyy-MM-dd");

    public void validateNhanVien(String manhanvien, String hoten, String email, String sodienthoai, String ngaysinh, String diachi, String gioitinh, String cmnd, String mataikhoan, String mabangcap, String mahdld) {
        if (manhanvien.equals("") || hoten.equals("") || email.equals("") || sodienthoai.equals("") || ngaysinh.equals("") || diachi.equals("") || gioitinh.equals("") || cmnd.equals("") || mataikhoan.equals("") || mabangcap.equals("") || mahdld.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
           return;
        }
        if (check.check_Email(email)) {
            JOptionPane.showMessageDialog(null, "Email không đúng định dạng!");
           return;
        } 
        if (validator.isValid(ngaysinh)) {
            JOptionPane.showMessageDialog(null, "Ngày sinh không đúng định dạng!");
           return;
        }      
    }
}
