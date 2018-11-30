package datePicker;

import java.util.Properties;

import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class DatePicker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public JDatePicker dameDatePicker()	{
	
		Properties p1 = new Properties();
	    p1.put("text.today", "Today");
	    p1.put("text.month", "Month");
	    p1.put("text.year", "Year");
		AbstractFormatter p2 = null;		
		UtilDateModel model = new UtilDateModel();
        model.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p1);
		JDatePicker datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());			
		return datePicker;
	}
}
