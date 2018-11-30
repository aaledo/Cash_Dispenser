package cajeroV4.vista;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


import cajeroV4.controlador.CierraRecursos;
import cajeroV4.controlador.RealizaConsultas;
import cajeroV4.controlador.ValidaTarjetaPIN;
import cajeroV4.modelo.AccederSaldo;
import cajeroV4.modelo.CreaMovto;


public class VentanaWB extends JFrame {

	/**	Instancia la clase Ventana sin parámetros y dibuja todos los marcos
	 * 
	 */
	public VentanaWB() {

		
		//	Ventana o Frame :

		setBounds(500,300,600,400);
		setTitle("App Cajero V4 (MVC)");

		//	Panel contenedor y CardLayout :
		
		panelCont = new JPanel();		
		layout = new CardLayout();
		panelCont.setLayout(layout);
		add(panelCont);

		//	Panel para gestion de Tarjeta :	
		
		JPanel panelTarjeta = new JPanel();	
		panelTarjeta.setLayout(new BorderLayout());
		JPanel panelTarjetasup = new JPanel();
		JLabel pedirTarjeta = new JLabel(); 
		pedirTarjeta.setText("Introduzca el número de la tarjeta :");
		panelTarjetasup.add(pedirTarjeta);	
		tarjetaPanelTarjeta = new JTextField(15);
		
		//	Limitamos el nro máximo de caracteres introducidos a 20 con este control :
		
		tarjetaPanelTarjeta.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	            if (tarjetaPanelTarjeta.getText().length() >= 20 ) // limit to 20 characters
	                e.consume();
	        }
	    });
		
		panelTarjetasup.add(tarjetaPanelTarjeta);
		panelTarjeta.add(panelTarjetasup,BorderLayout.NORTH);	

		mensajePanelTarjeta = new JLabel(); 	
		panelTarjeta.add(mensajePanelTarjeta,BorderLayout.CENTER);	
		mensajePanelTarjeta.setHorizontalAlignment( (int) CENTER_ALIGNMENT);	
		
		JPanel panelTarjetainf = new JPanel();
		aceptarPanelTarjeta=new JButton("Aceptar");
		panelTarjetainf.add(aceptarPanelTarjeta);	
		cancelarPanelTarjeta=new JButton("Cancelar");
		panelTarjetainf.add(cancelarPanelTarjeta);	
		panelTarjeta.add(panelTarjetainf,BorderLayout.SOUTH);
			
		aceptarPanelTarjeta.addActionListener((new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
//				try {
////					new ValidaTarjetaPIN(VentanaWB.this).validaTarjeta(tarjetaPanelTarjeta.getText());
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		}));	
		
		
		cancelarPanelTarjeta.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				CierraRecursos.cierraTodo();
			}
		});	
		

		//	Ponemos el campo de contraseña a la escucha de eventos de teclado para que pulsar la tecla <ENTER>
		//	ejecute el mismo código que pulsar el botón aceptar :
		
		tarjetaPanelTarjeta.addKeyListener(new KeyAdapter() {
			  public void keyPressed(KeyEvent e) {
				    if (e.getKeyCode()==KeyEvent.VK_ENTER){
				    	aceptarPanelTarjeta.doClick();
				    }
				  }		
		});		
		
		panelCont.add(panelTarjeta, "panelTarjeta");		
		
		
		
		//	Panel para gestion de PIN :	
		
		JPanel panelPIN = new JPanel();	
		panelPIN.setLayout(new BorderLayout());
		JPanel panelPINsup = new JPanel();
		JLabel pedirPIN = new JLabel(); 
		pedirPIN.setText("Introduzca el PIN de la tarjeta :");
		panelPINsup.add(pedirPIN);	
		passPanelPIN = new JPasswordField(4);
		panelPINsup.add(passPanelPIN);
		panelPIN.add(panelPINsup,BorderLayout.NORTH);	

		mensajePanelPIN = new JLabel(); 	
		panelPIN.add(mensajePanelPIN,BorderLayout.CENTER);	
		mensajePanelPIN.setHorizontalAlignment( (int) CENTER_ALIGNMENT);	
		
		JPanel panelPINinf = new JPanel();
		aceptarPanelPIN=new JButton("Aceptar");
		panelPINinf.add(aceptarPanelPIN);	
		cancelarPanelPIN=new JButton("Cancelar");
		panelPINinf.add(cancelarPanelPIN);	
		panelPIN.add(panelPINinf,BorderLayout.SOUTH);
	
		aceptarPanelPIN.addActionListener((new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
//				try {
//					new ValidaTarjetaPIN(VentanaWB.this).validaPIN(new String(getPassPanelPIN().getPassword()));
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		}));		
		

		
		cancelarPanelPIN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				tarjetaPanelTarjeta.setText("");
				layout.show(panelCont, "panelTarjeta");
				tarjetaPanelTarjeta.requestFocusInWindow();				
			}
		});	
		

		//	Ponemos el campo de contraseña a la escucha de eventos de teclado para que pulsar la tecla <ENTER>
		//	ejecute el mismo código que pulsar el botón aceptar :
		
		getPassPanelPIN().addKeyListener(new KeyAdapter() {
			  public void keyPressed(KeyEvent e) {
				    if (e.getKeyCode()==KeyEvent.VK_ENTER){
				       aceptarPanelPIN.doClick();
				    }
				  }		
		});		
		
		panelCont.add(panelPIN, "panelPIN");		
	
	
	//	Panel Ventana de Opciones :
	
//	lámina ppal ;
	JPanel panelVentanaOpciones = new JPanel();
	panelVentanaOpciones.setLayout(new BorderLayout());
	
//	lámina superior ;
	JPanel panelVentanaOpcionesSup = new JPanel();
	panelVentanaOpcionesSup.setLayout(new DisposicionUnaColumna());
	
//	lámina inferior ;
	JPanel panelVentanaOpcionesInf = new JPanel();
	JButton botonRetirar = new JButton("Retirar Dinero");
	JButton botonDepositar = new JButton("Depositar Dinero");	
	JButton botonConsultarSaldo = new JButton("Consultar Saldo...");
	
	botonRetirar.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e2){
//			importeRetirarDinero1.setText("");
//			importeRetirarDinero1.requestFocusInWindow();
			layout.show(panelCont, "panelRetirarDinero1");
			//	PONEMOS EL FOCO EN EL CAMPO IMPORTE DE LA PANTALLA RETIRAR DINERO :
			importeRetirarDinero1.requestFocusInWindow();
		}
	});		
	
	botonDepositar.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e2){
//			importeRetirarDinero1.setText("");
//			importeRetirarDinero1.requestFocusInWindow();
			
			
			importeIngresarDinero.requestFocusInWindow();
			importeIngresarDinero.setText("");
			importeIngresarDinero.setEditable(true);
			mensajeIngresarDinero.setText("");
			todoValidado=false;			
			layout.show(panelCont, "panelIngresarDinero");
			//	PONEMOS EL FOCO EN EL CAMPO IMPORTE DE LA PANTALLA RETIRAR DINERO :
			importeIngresarDinero.requestFocusInWindow();
		}
	});	
	
	
	botonConsultarSaldo.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e2){
//			new RealizaConsultas(Ventana.this).consultarSaldoActual();
			
//			El método getMonthValue() de la clase LocalDate siempre devuelve un mes más.
//			Por tanto recurrimos al truco de crear una fecha auxiliar con un mes menos y luego extraer 
//			y asignar loa valores por defecto ( fecha actual ) a la fecha de saldo para selección en pantalla :
			
			radioFechaActual.setSelected(true);
			LocalDate hoyMenosUnMes = LocalDate.now().minusMonths(1);
			datePickerSaldo.getModel().setDay(hoyMenosUnMes.getDayOfMonth());
			datePickerSaldo.getModel().setMonth(hoyMenosUnMes.getMonthValue());												
			datePickerSaldo.getModel().setYear(hoyMenosUnMes.getYear());
			datePickerSaldo.setVisible(false);
			layout.show(panelCont, "panelSeleccionarFechaSaldo");
			
			
			
		}
	});
	
	
	JButton botonConsultarMovtos = new JButton("Consultar Movtos...");
	botonConsultarMovtos.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			radioMovtosTodos.setSelected(true);
			LocalDate hoyMenosUnMes = LocalDate.now().minusMonths(1);
			
			datePicker1.getModel().setDay(hoyMenosUnMes.getDayOfMonth());
			datePicker1.getModel().setMonth(hoyMenosUnMes.getMonthValue());												
			datePicker1.getModel().setYear(hoyMenosUnMes.getYear());	
			
			datePicker2.getModel().setDay(hoyMenosUnMes.getDayOfMonth());
			datePicker2.getModel().setMonth(hoyMenosUnMes.getMonthValue());												
			datePicker2.getModel().setYear(hoyMenosUnMes.getYear());
			
			datePicker1.setVisible(false);
			datePicker2.setVisible(false);
			mensajeSeleccionarFechaMovtos.setText("");
			layout.show(panelCont, "panelSeleccionarFechaMovtos");
		}
	});	
	
	JButton cancelarVentanaOpciones = new JButton("Cancelar");		

	panelVentanaOpcionesSup.add(botonRetirar);
	panelVentanaOpcionesSup.add(botonDepositar);	
	panelVentanaOpcionesSup.add(botonConsultarSaldo);	
	panelVentanaOpcionesSup.add(botonConsultarMovtos);
	panelVentanaOpciones.add(panelVentanaOpcionesSup, BorderLayout.CENTER);
	
	panelVentanaOpcionesInf.add(cancelarVentanaOpciones);
	panelVentanaOpciones.add(panelVentanaOpcionesInf, BorderLayout.SOUTH);
	cancelarVentanaOpciones.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e4){
			CierraRecursos.cierraTodo();
		}

	});
	panelCont.add(panelVentanaOpciones, "panelVentanaOpciones");	
	
	//************************************
	//	Panel Seleccionar Fecha Saldo :	 *
	//************************************	
	
	JPanel panelSeleccionarFechaSaldo = new JPanel();		
	panelSeleccionarFechaSaldo.setLayout(new BorderLayout());	
	
	
	//	1.-Panel Seleccionar Fecha Saldo Superior :		
	
	JPanel panelSeleccionarFechaSaldoSup = new JPanel();		
	panelSeleccionarFechaSaldoSup.setLayout(null);	

	JLabel encabezadopanelSeleccionarFechaSaldo = new JLabel("SELECCIONE FECHA PARA SALDO");
	encabezadopanelSeleccionarFechaSaldo.setBounds(100, 10, 400, 30);	
	encabezadopanelSeleccionarFechaSaldo.setHorizontalAlignment( (int) CENTER_ALIGNMENT);
	panelSeleccionarFechaSaldoSup.add(encabezadopanelSeleccionarFechaSaldo);	
	
	radioFechaActual = new JRadioButton("Saldo actual");
	radioFechaActual.setBounds(150, 60, 150, 23);
	JRadioButton radioFechaElegida = new JRadioButton("Saldo a Fecha");	
	radioFechaElegida.setBounds(150, 100, 150, 23);
	ButtonGroup grupoRadioFecha = new ButtonGroup();
	
	Properties p1 = new Properties();
    p1.put("text.today", "Today");
    p1.put("text.month", "Month");
    p1.put("text.year", "Year");
//	AbstractFormatter p2 = null;
	
	UtilDateModel model = new UtilDateModel();

    model.setSelected(true);
	JDatePanelImpl datePanel = new JDatePanelImpl(model, p1);
	datePickerSaldo = new JDatePickerImpl(datePanel, new DateComponentFormatter());
	datePickerSaldo.setBounds(300, 100, 150, 23);
	
	panelSeleccionarFechaSaldoSup.add(radioFechaActual);
	panelSeleccionarFechaSaldoSup.add(radioFechaElegida);
	panelSeleccionarFechaSaldoSup.add(datePickerSaldo);
	
//	panelSeleccionarFechaSaldo.add((Container)radioFechaSaldo);	
	grupoRadioFecha.add(radioFechaActual);
	grupoRadioFecha.add(radioFechaElegida);		
	
	radioFechaActual.setSelected(true);
	
	datePickerSaldo.setVisible(radioFechaElegida.isSelected());

	radioFechaActual.addActionListener(t -> datePickerSaldo.setVisible(false));	
	
	radioFechaElegida.addActionListener(t->	{

		LocalDate hoyMenosUnMes = LocalDate.now().minusMonths(1);
		datePickerSaldo.getModel().setDay(hoyMenosUnMes.getDayOfMonth());
		datePickerSaldo.getModel().setMonth(hoyMenosUnMes.getMonthValue());												
		datePickerSaldo.getModel().setYear(hoyMenosUnMes.getYear());	
		
//												datePickerSaldo.getModel().setDay(LocalDate.now().getDayOfMonth());
//												datePickerSaldo.getModel().setMonth(LocalDate.now().getMonthValue());												
//												datePickerSaldo.getModel().setYear(LocalDate.now().getYear());												
												
		datePickerSaldo.setVisible(true);
		panelSeleccionarFechaSaldo.repaint();
		
							});
	
	panelSeleccionarFechaSaldo.add(panelSeleccionarFechaSaldoSup,BorderLayout.CENTER );

	
	//	2.- Panel Seleccionar Fecha Saldo Inferior :		
	
	JPanel panelSeleccionarFechaSaldoInf = new JPanel();	
	
	JButton aceptarSeleccionarFechaSaldo = new JButton("Aceptar");
	panelSeleccionarFechaSaldoInf.add(aceptarSeleccionarFechaSaldo);	
	
	aceptarSeleccionarFechaSaldo.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
		   //	Convertimos a LocalDateTime a través de clase intermedia Date :

			if (radioFechaActual.isSelected())	{
//				new RealizaConsultas(VentanaWB.this).consultarSaldo(java.sql.Date.valueOf(LocalDate.now()));
				}
			else	{
//		    Extraemos primero la fecha en tipo java.util.Date
//			y luego en tipo java.sql.Date, que es finalmente la que manejamos :

		    java.util.Date fechaSaldoUtil = (java.util.Date) (datePickerSaldo.getModel().getValue());
		    java.sql.Date	fechaSaldoSQL = new java.sql.Date(fechaSaldoUtil.getTime());
		    
//			Ahora llamamos al método con la fecha seleccionada para que realice las operaciones :

//			new RealizaConsultas(VentanaWB.this).consultarSaldo(fechaSaldoSQL);

			}
		}
	});	
	
	JButton cancelarSeleccionarFechaSaldo = new JButton("Cancelar");
	panelSeleccionarFechaSaldoInf.add(cancelarSeleccionarFechaSaldo);
	cancelarSeleccionarFechaSaldo.addActionListener(t -> layout.show(panelCont, "panelVentanaOpciones"));	
	
	panelSeleccionarFechaSaldo.add(panelSeleccionarFechaSaldoInf,BorderLayout.SOUTH );
	
	panelCont.add(panelSeleccionarFechaSaldo, "panelSeleccionarFechaSaldo");
	
	
	//	Panel Consultar Saldo :		
	
	JPanel panelConsultarSaldo = new JPanel();		
	panelConsultarSaldo.setLayout(new BorderLayout());

	textoSaldo = new JLabel("");
	panelConsultarSaldo.add(textoSaldo, BorderLayout.CENTER);
	textoSaldo.setHorizontalAlignment((int) CENTER_ALIGNMENT);		
	
	JPanel panelConsultarSaldoInf= new JPanel();			
	JButton aceptarConsultarSaldo=new JButton("Aceptar");
	panelConsultarSaldoInf.add(aceptarConsultarSaldo);
	panelConsultarSaldo.add(panelConsultarSaldoInf, BorderLayout.SOUTH);
	
	aceptarConsultarSaldo.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e4){
		layout.show(panelCont, "panelVentanaOpciones");
		}
	});
	
	panelCont.add(panelConsultarSaldo, "panelConsultarSaldo");
	
	
	//************************************************
	//	Panel Seleccionar Rango Fecha Movimientos	 *
	//************************************************	
	
	JPanel panelSeleccionarFechaMovtos = new JPanel();		
	panelSeleccionarFechaMovtos.setLayout(new BorderLayout());	
	
	
	//	1.-Panel Seleccionar Fecha Movimientos Superior :		
	
	JPanel panelSeleccionarFechaMovtosSup = new JPanel();		
	panelSeleccionarFechaMovtosSup.setLayout(null);	

	JLabel encabezadoSeleccionarFechaMovtos = new JLabel("SELECCIONE FECHA PARA MOVIMIENTOS");
	encabezadoSeleccionarFechaMovtos.setBounds(100, 10, 400, 30);	
	encabezadoSeleccionarFechaMovtos.setHorizontalAlignment( (int) CENTER_ALIGNMENT);
	panelSeleccionarFechaMovtosSup.add(encabezadoSeleccionarFechaMovtos);
	
	
	radioMovtosTodos = new JRadioButton("Todos");
	radioMovtosTodos.setBounds(50, 60, 150, 23);
	JRadioButton radioMovtosEntreFechas = new JRadioButton("Rango Fechas");	
	radioMovtosEntreFechas.setBounds(50, 100, 150, 23);
	ButtonGroup grupoRadioMovtos = new ButtonGroup();
	//-------------------------------------------------	
	
	JLabel fechaDesde = new JLabel("Fecha Desde :");
	
//	Properties p1 = new Properties();
//    p1.put("text.today", "Today");
//    p1.put("text.month", "Month");
//    p1.put("text.year", "Year");
//	AbstractFormatter p2 = null;
	
	UtilDateModel model1 = new UtilDateModel();

    model1.setSelected(true);
	JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p1);
	datePicker1 = new JDatePickerImpl(datePanel1, new DateComponentFormatter());	
	datePicker1.setBounds(200, 100, 150, 23);

	
	JLabel fechaHasta = new JLabel("Fecha Hasta :");

	UtilDateModel model2 = new UtilDateModel();
    model2.setSelected(true);
	JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p1);
	datePicker2 = new JDatePickerImpl(datePanel2, new DateComponentFormatter());
	datePicker2.setBounds(400, 100, 150, 23);

	//-------------------------------------------------	
	panelSeleccionarFechaMovtosSup.add(radioMovtosTodos);
	panelSeleccionarFechaMovtosSup.add(radioMovtosEntreFechas);
	panelSeleccionarFechaMovtosSup.add(datePicker1);
	panelSeleccionarFechaMovtosSup.add(datePicker2);
	
//	panelSeleccionarFechaSaldo.add((Container)radioFechaSaldo);	
	grupoRadioMovtos.add(radioMovtosTodos);
	grupoRadioMovtos.add(radioMovtosEntreFechas);		
	
	mensajeSeleccionarFechaMovtos = new JLabel(""); 	
//	panelSeleccionarFechaMovtos.add(mensajeSeleccionarFechaMovtos,BorderLayout.CENTER);	
	mensajeSeleccionarFechaMovtos.setBounds(100, 200, 400, 23);
	mensajeSeleccionarFechaMovtos.setFont(new Font("Courier", Font.BOLD,12));
	mensajeSeleccionarFechaMovtos.setHorizontalAlignment( (int) CENTER_ALIGNMENT);
	panelSeleccionarFechaMovtosSup.add(mensajeSeleccionarFechaMovtos);
	
	radioMovtosTodos.setSelected(true);
	
	datePicker1.setVisible(radioMovtosEntreFechas.isSelected());
	datePicker2.setVisible(radioMovtosEntreFechas.isSelected());

	radioMovtosTodos.addActionListener(t -> {datePicker1.setVisible(false);datePicker2.setVisible(false);});	
	
	radioMovtosEntreFechas.addActionListener(t->	{
		
		LocalDate hoyMenosUnMes = LocalDate.now().minusMonths(1);
					
												datePicker1.getModel().setDay(hoyMenosUnMes.getDayOfMonth());
												datePicker1.getModel().setMonth(hoyMenosUnMes.getMonthValue());												
												datePicker1.getModel().setYear(hoyMenosUnMes.getYear());	
												
												datePicker2.getModel().setDay(hoyMenosUnMes.getDayOfMonth());
												datePicker2.getModel().setMonth(hoyMenosUnMes.getMonthValue());												
												datePicker2.getModel().setYear(hoyMenosUnMes.getYear());													
												
												datePicker1.setVisible(true);
												datePicker2.setVisible(true);
												mensajeSeleccionarFechaMovtos.setText("");
												panelSeleccionarFechaMovtos.repaint();
							});


	
	panelSeleccionarFechaMovtos.add(panelSeleccionarFechaMovtosSup,BorderLayout.CENTER );
	
	
	//	2.- Panel Seleccionar Fecha Movimientos Inferior :		
	
	JPanel panelSeleccionarFechaMovtosInf = new JPanel();	
	
	JButton aceptarSeleccionarFechaMovtos = new JButton("Aceptar");
	panelSeleccionarFechaMovtosInf.add(aceptarSeleccionarFechaMovtos);	
	
	aceptarSeleccionarFechaMovtos.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			if (radioMovtosTodos.isSelected())	{
//	    		try {
//	    			
//
//					new RealizaConsultas(VentanaWB.this).consultarMovtos(java.sql.Date.valueOf("1900-01-01"), java.sql.Date.valueOf("2099-12-31"));
//					} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();				
//						}
			}
	    		else	{
			
			
		   //	Convertimos a LocalDateTime a través de clase intermedia Date :
			
//		    Extraemos primero la fecha en tipo java.util.Date
//			y luego en tipo java.sql.Date, que es finalmente la que manejamos :

		    java.util.Date fecha1Util = (java.util.Date) (datePicker1.getModel().getValue());
		    java.sql.Date	fecha1SQL = new java.sql.Date(fecha1Util.getTime());
		    LocalDateTime fecha1Local = new java.sql.Timestamp(fecha1Util.getTime()).toLocalDateTime();

		    java.util.Date fecha2Util = (java.util.Date) (datePicker2.getModel().getValue());
		    java.sql.Date	fecha2SQL = new java.sql.Date(fecha2Util.getTime());		    
		    LocalDateTime fecha2Local = new java.sql.Timestamp(fecha2Util.getTime()).toLocalDateTime();		    
		    
		    if (fecha1Local.isAfter(fecha2Local))	{
		    	
		    	mensajeSeleccionarFechaMovtos.setText("FECHA DESDE NO PUEDE SER POSTERIOR A FECHA HASTA");	
		    	panelSeleccionarFechaMovtosSup.revalidate();
		    	panelSeleccionarFechaMovtosSup.repaint();		    	
				layout.show(panelCont, "panelSeleccionarFechaMovtos");
		    }		    	
		    	else	{
		    		
		    		mensajeSeleccionarFechaMovtos.setText("");	
//		    		try {
//						new RealizaConsultas(VentanaWB.this).consultarMovtos(fecha1SQL, fecha2SQL);
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
		    	}	
		    
		}
			}
	});
	
	JButton cancelarSeleccionarFechaMovtos = new JButton("Cancelar");
	panelSeleccionarFechaMovtosInf.add(cancelarSeleccionarFechaMovtos);
	cancelarSeleccionarFechaMovtos.addActionListener(t -> layout.show(panelCont, "panelVentanaOpciones"));	
	
	panelSeleccionarFechaMovtos.add(panelSeleccionarFechaMovtosInf,BorderLayout.SOUTH );
	
	panelCont.add(panelSeleccionarFechaMovtos, "panelSeleccionarFechaMovtos");
	
	
	
	//	Panel para visualizar movimientos :		

//		Encabezado superior :

	JPanel panelMostrarMovtos = new JPanel();	
	panelMostrarMovtos.setLayout(new BorderLayout());
	JLabel textoMostrarMovtos = new JLabel(); 
	textoMostrarMovtos.setText("MOVIMIENTOS DE LA CUENTA :");
	textoMostrarMovtos.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	panelMostrarMovtos.add(textoMostrarMovtos,BorderLayout.NORTH);

//	Area central para visualizar los movtos :		
	
	modelo = new DefaultTableModel(); 

	modelo.addColumn("Fecha"); 
	modelo.addColumn("Importe");
	modelo.addColumn("Descripción");	
	modelo.addColumn("Saldo");	
	JTable tabla = new JTable(modelo);
	
//	tabla.setRowHeight(30);		ajustaría la altura de todas las filas a 30 pixels, el método tabién admite cambiar la altura de una fila específica
	
//	Para establecer el ancho de cada columna :
	
	TableColumnModel modeloColumna = tabla.getColumnModel();
//	modeloColumna.setColumnMargin(5);	
	modeloColumna.getColumn(0).setPreferredWidth(50);
	modeloColumna.getColumn(1).setPreferredWidth(50);
	modeloColumna.getColumn(2).setPreferredWidth(120);
	modeloColumna.getColumn(3).setPreferredWidth(50);

//	Ahora establecemos la alineación para que salgan centradas las columnas de importe ( 1 y 3 ) :
		
    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();  
    dtcr.setHorizontalAlignment(SwingConstants.CENTER);		
	modeloColumna.getColumn(1).setCellRenderer(dtcr);	
	modeloColumna.getColumn(3).setCellRenderer(dtcr);	

//	Definimos el scroll y lo añadimos en el area central :
	
	JScrollPane scroll = new JScrollPane(tabla);
	
//	Para desactivar edición (PERO TAMBIEN SELECCION ) de las líneas mostradas :
//	tabla.setEnabled(false);
//	Para desactivar edición PERO OFRECER SELECCION de las líneas mostradas :	
	tabla.setDefaultEditor(Object.class, null);
	panelMostrarMovtos.add(scroll,BorderLayout.CENTER);
	
//		Subpanel inferior :	
	
	JPanel panelMostrarMovtosInf= new JPanel();		
	JButton aceptarMostrarMovtos=new JButton("Aceptar");
	panelMostrarMovtosInf.add(aceptarMostrarMovtos);
	panelMostrarMovtos.add(panelMostrarMovtosInf, BorderLayout.SOUTH);
	
	aceptarMostrarMovtos.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e7){
		
			layout.show(panelCont, "panelVentanaOpciones");
		}
	});			

	
	panelCont.add(panelMostrarMovtos, "panelMostrarMovtos");	
	
	
//	--------------------------------------------------------------------
	
	//************************************************	
	//	Paneles RetirarDinero :
	//************************************************
	
	//	1.- Panel para solicitar y comprobar dinero a retirar :
	
	panelRetirarDinero1 = new JPanel();		
	panelRetirarDinero1.setLayout(new BorderLayout());
	
	JPanel panelRetirarDinero1Sup = new JPanel();			
	JLabel textoRetirarDinero1_1 = new JLabel("INTRODUZCA IMPORTE A RETIRAR : ");
	importeRetirarDinero1 = new JTextField(5);
	importeRetirarDinero1.setText("");
//	System.out.println(importeRetirarDinero1.requestFocusInWindow());
//	System.out.println(importeRetirarDinero1.isFocusable());
//	System.out.println(importeRetirarDinero1.isVisible());
	
	panelRetirarDinero1Sup.add(textoRetirarDinero1_1);
	panelRetirarDinero1Sup.add(importeRetirarDinero1);
	panelRetirarDinero1.add(panelRetirarDinero1Sup, BorderLayout.NORTH);

//	importeRetirarDinero1.requestFocusInWindow();		
	
	textoRetirarDinero1_2 = new JLabel("");
	textoRetirarDinero1_2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	panelRetirarDinero1.add(textoRetirarDinero1_2, BorderLayout.CENTER);		
	
	JPanel panelRetirarDinero1Inf= new JPanel();			
	JButton aceptarRetirarDinero1=new JButton("Aceptar");
	JButton cancelarRetirarDinero1=new JButton("Cancelar");		
	panelRetirarDinero1Inf.add(aceptarRetirarDinero1);
	panelRetirarDinero1Inf.add(cancelarRetirarDinero1);		
	panelRetirarDinero1.add(panelRetirarDinero1Inf, BorderLayout.SOUTH);		
	
	cancelarRetirarDinero1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e4){
			importeRetirarDinero1.setText("");
			textoRetirarDinero1_2.setText("");
			
			layout.show(panelCont, "panelVentanaOpciones");
		}

	});
	
	aceptarRetirarDinero1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e5){

			String valor = importeRetirarDinero1.getText();
			if (valor.equals("0") || !valor.matches("\\d+"))	textoRetirarDinero1_2.setText("IMPORTE INVALIDO. TECLEE UN NUMERO ENTERO POSITIVO");				
			else	{
		
//			new RealizaConsultas(VentanaWB.this).retirarDinero(Integer.parseInt(valor));

			}
		}
	});	
	
	
	importeRetirarDinero1.addKeyListener(new KeyAdapter() {
		  public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    	aceptarRetirarDinero1.doClick();
			    }
			  }		
	});	
	
	
	panelCont.add(panelRetirarDinero1, "panelRetirarDinero1");
	
	
	//	2.- Panel para mostrar los detalles de la operación de retirada :
	
	panelRetirarDinero2 = new JPanel();
	panelRetirarDinero2.setLayout(new BorderLayout());		

	JPanel panelRetirarDinero2Sup = new JPanel();
	panelRetirarDinero2Sup.setLayout(new DisposicionUnaColumna());		
	JLabel textoRetirarDinero2_1 = new JLabel(" -- RETIRE LOS BILLETES --");
//	System.out.println("Importe confirmado : " + retirada);
	textoRetirarDinero2_2 = new JLabel();
//	float nuevoSaldo = dameSaldo(cuentaBanco) - retirada;
	textoRetirarDinero2_3 = new JLabel();		
	panelRetirarDinero2Sup.add(textoRetirarDinero2_1);	
	panelRetirarDinero2Sup.add(textoRetirarDinero2_2);		
	panelRetirarDinero2Sup.add(textoRetirarDinero2_3);		
	panelRetirarDinero2.add(panelRetirarDinero2Sup, BorderLayout.CENTER);				

	JPanel panelRetirarDinero2Inf= new JPanel();			
	JButton aceptarRetirarDinero2=new JButton("Aceptar");
	panelRetirarDinero2Inf.add(aceptarRetirarDinero2);
	panelRetirarDinero2.add(panelRetirarDinero2Inf, BorderLayout.SOUTH);

	aceptarRetirarDinero2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e6){
			layout.show(panelCont, "panelVentanaOpciones");
		}
	});			
	
	panelCont.add(panelRetirarDinero2, "panelRetirarDinero2");		
	
	
	//************************************************	
	//	Paneles Ingresar Dinero :
	//************************************************	
	
	//	1.- Panel para solicitar y comprobar dinero a ingresar  :
	
	JPanel panelIngresarDinero = new JPanel();		
	panelIngresarDinero.setLayout(new BorderLayout());

	JPanel panelIngresarDineroSup = new JPanel();			
	JLabel textoIngresarDinero = new JLabel("INTRODUZCA IMPORTE A DEPOSITAR : ");
	importeIngresarDinero = new JTextField(5);
	importeIngresarDinero.requestFocusInWindow();
	importeIngresarDinero.setEditable(true);
	
	panelIngresarDineroSup.add(textoIngresarDinero);
	panelIngresarDineroSup.add(importeIngresarDinero);

	panelIngresarDinero.add(panelIngresarDineroSup, BorderLayout.NORTH);

	
	mensajeIngresarDinero = new JLabel("");
	mensajeIngresarDinero.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	panelIngresarDinero.add(mensajeIngresarDinero, BorderLayout.CENTER);		
	
	JPanel panelIngresarDineroInf= new JPanel();			
	JButton aceptarIngresarDinero=new JButton("Aceptar");
	JButton cancelarIngresarDinero=new JButton("Cancelar");		
	panelIngresarDineroInf.add(aceptarIngresarDinero);
	panelIngresarDineroInf.add(cancelarIngresarDinero);		
	panelIngresarDinero.add(panelIngresarDineroInf, BorderLayout.SOUTH);		
	importeIngresarDinero.setEditable(true);
	//	Booleano para controlar las acciones del botón <Aceptar>	:
	//		-- true --> es la primera pantalla y debemos validar importe
	//		-- false --> es la segunda pantalla ( el importe ya está validado ) y comprobamos los billetes introducidos en cajón
	
//	todoValidado = false;

	
	cancelarIngresarDinero.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e4){
		layout.show(panelCont, "panelVentanaOpciones");
		}

	});
	
	aceptarIngresarDinero.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e5){
			
			String valor = importeIngresarDinero.getText();			
			importeIngresarDinero.requestFocusInWindow();			
		
		if (!todoValidado)	{
			if (valor.equals("0") || !valor.matches("\\d+"))	mensajeIngresarDinero.setText("IMPORTE INVALIDO. TECLEE UN NUMERO ENTERO POSITIVO");				
			else	{
			int ingreso = Integer.parseInt(valor);					
			importeIngresarDinero.requestFocusInWindow();
			if ( ingreso % 20 != 0 ) mensajeIngresarDinero.setText("EL IMPORTE DEBE SER MULTIPLO DE 20. TECLEE NUEVO IMPORTE");
			else	{	mensajeIngresarDinero.setText("DEPOSITE LOS BILLETES EN EL CAJON Y PULSE ACEPTAR");
						importeIngresarDinero.setEditable(false);
						todoValidado = true;
					}
			}	
		}
		
		else	{

//		Todo validado OK :	
			
			float saldo = new AccederSaldo().dameSaldo(ValidaTarjetaPIN.dameCuenta(),Date.valueOf(LocalDate.now()));
			int ingreso2 = Integer.parseInt(valor);	
			CreaMovto.creaMovimiento("haber",ValidaTarjetaPIN.dameCuenta(),ingreso2, saldo+ingreso2);
						todoValidado = false;
						importeIngresarDinero.setText("");
						mensajeIngresarDinero.setText("");
						importeIngresarDinero.setEditable(true);
						layout.show(panelCont, "panelVentanaOpciones");
			
		}
		}
		
	});		

	
	importeIngresarDinero.addKeyListener(new KeyAdapter() {
		  public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    	aceptarIngresarDinero.doClick();
			    }
			  }		
	});	
	
	panelCont.add(panelIngresarDinero, "panelIngresarDinero");	
	
	}	
	
	
	//	A continuación todos los getters y setters :	
	
	public JLabel getmensajePanelTarjeta() {
		return mensajePanelTarjeta;
	}
	public void setmensajePanelTarjeta(String mensajeTarjeta) {
		this.mensajePanelTarjeta.setText(mensajeTarjeta);
	}

	
	
	public JTextField getTarjetaTecleada() {
		return tarjetaPanelTarjeta;
	}

	public void setTarjetaTecleada(String valor) {
		this.tarjetaPanelTarjeta.setText(valor);
	}
	
	public void aceptarTarjetaVisible(boolean visible)	{
		aceptarPanelTarjeta.setVisible(visible);
	}

	public void cancelarTarjetaVisible(boolean visible)	{
		cancelarPanelTarjeta.setVisible(visible);
	}
	
	public JPasswordField getPass() {
		return getPassPanelPIN();
	}

	public void setPass(JPasswordField pass) {
		this.getPassPanelPIN().setText(pass.getText());
	}	
	
	
	public JLabel getMensajePanelPIN() {
		return mensajePanelPIN;
	}

	public void setMensajePanelPIN(String msg) {
		mensajePanelPIN.setText(msg);
	}

	public void aceptarPanelPINVisible(boolean visible)	{
		aceptarPanelPIN.setVisible(visible);
	}

	public void cancelarPanelPINVisible(boolean visible)	{
		cancelarPanelPIN.setVisible(visible);
	}	
	
	
	public JPasswordField getPassPanelPIN() {
		return passPanelPIN;
	}

	public void setPassPanelPIN(String valor) {
		passPanelPIN.setText(valor);
	}	

	public String getSaldoPantalla() {
		return saldoPantalla;
	}

	public void setSaldoPantalla(String saldoPantalla) {
		this.saldoPantalla = saldoPantalla;
	}
	
	public JLabel getTextoSaldo() {
		return textoSaldo;
	}

	public void setTextoSaldo(String textoSaldo) {
		this.textoSaldo.setText(textoSaldo);
	}	
	

	public DefaultTableModel getModelo() {
		return modelo;
	}


	public void setModelo(DefaultTableModel modelo) {
		this.modelo = modelo;
	}
	
	public String getTextoRetirarDinero1_2() {
		return textoRetirarDinero1_2.getText();
	}
	
	public void setTextoRetirarDinero1_2(String texto) {
		this.textoRetirarDinero1_2.setText(texto);
	}

	public String getTextoRetirarDinero2_2() {
		return textoRetirarDinero2_2.getText();
	}
	
	public void setTextoRetirarDinero2_2(String texto) {
		this.textoRetirarDinero2_2.setText(texto);
	}
	
	public String getTextoRetirarDinero2_3() {
		return textoRetirarDinero2_2.getText();
	}
	
	public void setTextoRetirarDinero2_3(String texto) {
		this.textoRetirarDinero2_2.setText(texto);
	}	
	
	public String getImporteRetirarDinero1() {
		return importeRetirarDinero1.getText();
	}
	
	public void setImporteRetirarDinero1(String texto) {
		this.importeRetirarDinero1.setText(texto);
	}	
	

	
	//	Definición de variables generales :


	public CardLayout layout;	
	public static JPanel panelCont;	
	private int nroTarjeta;
	JLabel mensajePanelTarjeta;
	JLabel mensajePanelPIN;
	public JTextField tarjetaPanelTarjeta;
	JButton aceptarPanelTarjeta;
	JButton cancelarPanelTarjeta;
	private JPasswordField passPanelPIN;
	JButton aceptarPanelPIN;
	JButton cancelarPanelPIN;	
	String saldoPantalla="";
	public JLabel textoSaldo;
	static JDatePickerImpl datePicker1=null;
	static JDatePickerImpl datePicker2=null;
	static JDatePickerImpl datePickerSaldo2=null;
	DefaultTableModel modelo;
	JRadioButton radioFechaActual;
	JRadioButton radioMovtosTodos;
	JRadioButton radioMovtosEntreFechas;
	JDatePickerImpl datePickerSaldo;
	JLabel mensajeSeleccionarFechaMovtos;
	JLabel textoRetirarDinero1_2;
	JLabel textoRetirarDinero2_2;
	JLabel textoRetirarDinero2_3;	
	public JPanel panelRetirarDinero2;
	JTextField importeRetirarDinero1;
	public JPanel panelRetirarDinero1;
	JTextField importeIngresarDinero;
	boolean todoValidado=false;
	JLabel mensajeIngresarDinero;

	
}
