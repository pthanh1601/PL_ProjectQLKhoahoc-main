package UI.model;

import java.util.Scanner;

public class checkError {

    Scanner sc = new Scanner(System.in);

    public boolean Check_hoten(String input) {
        if (input.matches("[\\pL\\pMn*\\s*]+")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean check_Phone(String input) {
        input = input.toUpperCase();
        if (input.matches("^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean check_Email(String input) {
        if (input.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            return false;
        } else {
            return true;
        }

    }
    public boolean check_CMND(String input) {
        if (input.matches("[0-9]{9}")) {
            return false;
        } else {
            return true;
        }

    }

    public boolean check_ThoiGianBCC(String s) {
        boolean flag = false;
        if (s.indexOf("/") == -1) {
            flag = false;
        } else {
            String s1[] = s.split("/");
            String s2[] = s1[0].split(" ");
            System.out.println("s2[1]:" + s2[1]);
            if (s2[0].equalsIgnoreCase("Tháng") && s2[1].matches("^[1-9]|1[0-2]|0[1-9]$") && s1[1].matches("\\s[12]\\d{3}")) {
                flag = true;

            }
        }
        return flag;
    }
   public boolean check_Number(String input) {
        if (input.matches("^\\d+$")) {
            return true;
        } else {
            return false;
        }

    }
}
