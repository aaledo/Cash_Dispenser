package datePicker;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatter;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

//	PAGINA DONDE APARECE TODA LA INFORMACION :
//		
//		https://stackoverflow.com/questions/26323390/date-picker-gui-component-for-java-swing

//	FAQ EN STACKOVERFLOW :
//		https://stackoverflow.com/search?q=jdatepicker


public class Pedir_Calendario {
	
	public static void main(String[]args){
	
	Ventana ventana = new Ventana();
	ventana.pack();
	ventana.setVisible(true);
	ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	
	}	

	
	
	static class Ventana extends JFrame	{
		
		public Ventana()	{
		
		setBounds(500,300,400,300);
		JPanel panel = new JPanel();

		Properties p1 = new Properties();
	    p1.put("text.today", "Today");
	    p1.put("text.month", "Month");
	    p1.put("text.year", "Year");
		AbstractFormatter p2 = null;		

//		DefaultFormatter defaultFormatter = new DefaultFormatter();
//		defaultFormatter.to
		
		UtilDateModel model = new UtilDateModel();
		
//		System.out.println(model.toString());
	
//      model.setDate(1990, 8, 24);
        model.setSelected(true);
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p1);
//		datePicker = new JDatePickerImpl(datePanel, new DefaultFormatter());
		datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());		
		
//		
//		{
//			
//			public String toString()	{
//				return "Mierda";
//		}				
//		});		

		JButton acepta = new JButton("Aceptar");
		acepta.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			   //	Convertimos a LocalDateTime a través de clase intermedia Date : 				
			    Date date = (Date) datePicker.getModel().getValue();
			    LocalDateTime fechaLocal = new java.sql.Timestamp(date.getTime()).toLocalDateTime();			    
			    System.out.println(fechaLocal);
			
			}
		});
		
		panel.add(datePicker);
		panel.add(acepta);
		add(panel);


	}	
	
	

	
	
	 static JDatePickerImpl datePicker=null;

	}

}






