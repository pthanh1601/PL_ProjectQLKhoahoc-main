package UI.model;

import java.time.Month;

public class ngayTrongThang {

    int month;
    int year;
    int day;
    public ngayTrongThang(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public int tinhNgayTrongThang(int month, int year) {
        switch (month) {
// các tháng 1, 3, 5, 7, 8, 10 và 12 có 31 ngày.
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day=31;
                break;

// các tháng 4, 6, 9 và 11 có 30 ngày
            case 4:
            case 6:
            case 9:
            case 11:
                day=30;
                break;

// Riêng tháng 2 nếu là năm nhuận thì có 29 ngày, còn không thì có 28 ngày.
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    day = 29;
                } else {
                    day = 28;
                }
                break;
        }
        return day;
    }

}
