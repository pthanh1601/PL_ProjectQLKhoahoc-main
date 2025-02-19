/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class formatDate extends AbstractFormatter {

    private String datePattern;
    private SimpleDateFormat dateFormatter;
    public formatDate(String datePattern) {
        this.datePattern = datePattern;
        dateFormatter = new SimpleDateFormat(datePattern);
    }
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            Calendar today = Calendar.getInstance();

            if (cal.getTime().before(today.getTime())) {
                return dateFormatter.format(today.getTime());
            }

            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

    public Callback<DatePicker, DateCell> getDayCellFactory() {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Vô hiệu hóa thứ 2, 3, 4
                        LocalDate localDate = LocalDate.now();
                        if (item.isBefore(localDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

}
