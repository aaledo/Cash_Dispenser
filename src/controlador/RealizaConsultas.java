package cajeroV4.controlador;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Formatter;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JOptionPane;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import cajeroV4.modelo.AccederMovtos;
import cajeroV4.modelo.AccederSaldo;
import cajeroV4.modelo.AccederTarjeta;
import cajeroV4.modelo.CreaMovto;
import cajeroV4.vista.Ventana;



/**Clase principal que engloba todas las validaciones y consultas que se requieren a partir de los datos introducidos por pantalla.
 * @author Alfonso Aledo
 *
 */
public class RealizaConsultas {


	/**
	 * Devuelve el saldo de la cuenta
	 * @return Float
	 */
	public static float getsaldoActual() {
	return saldoActual;
}
	
	/**
	 * Establece el saldo de una cuenta
	 * @param saldoActual : Float
	 */
	public static void setsaldoActual(float saldoActual) {
	RealizaConsultas.saldoActual = saldoActual;
}

	/**
	 * Constructor vacío
	 */
	public RealizaConsultas() {

	}	
	
	/**
	 * Constructor con parámetro objeto procedente de la vista para gestionar la interfaz gráfica
	 * @param miVentana : objeto de la clase Ventana, que define las pantallas.
	 */
	public RealizaConsultas(Ventana miVentana) {
		super();
		this.miVentana = miVentana;
	}	


	/**
	 * Devuelve el valor del saldo a la fecha elegida en la ventana ( actual o a una fecha determinada ).
	 * Simplemente instancia la clase AccederSaldo y llama al método DameSaldo(), encargado de todos los accesos a la BBDD y cálculos
	 * @param fechaSaldo : fecha elegida por pantalla ( actual o determinada )
	 */
	public void consultarSaldo(java.sql.Date fechaSaldo) {	
		
			Float elSaldo=new AccederSaldo().dameSaldo(ValidaTarjetaPIN.dameCuenta(),fechaSaldo );
	
			
			String patron = "dd/MM/yyyy"; 
			SimpleDateFormat formato = new SimpleDateFormat(patron); 
			DecimalFormat numformat = new DecimalFormat("##,###,###.00");
			
			miVentana.setTextoSaldo("EL SALDO DE LA CUENTA  A FECHA " + formato.format(fechaSaldo) + " ES   :  " + 
				numformat.format(elSaldo) + "  EUR");
			miVentana.layout.show(Ventana.panelCont, "panelConsultarSaldo");		
		
	}
		
	
	/**	Recorre los movtos cargados al comienzo del programa y añade los que cumplen los criterios
	 * a las lineas de consulta de pantalla ( JTable )
	 * @param fecha1 : Fecha Inicial
	 * @param fecha2 : Fecha Final
	 * @throws SQLException : Si se produce un error al recuperar datos del ResultSet
	 */
	public void consultarMovtos( java.sql.Date fecha1, java.sql.Date fecha2) throws SQLException 	{	
	
		String patron = "dd/MM/yyyy"; 
		SimpleDateFormat formato = new SimpleDateFormat(patron); 	
		
		ResultSet rsMovtos = new AccederMovtos().dameMovtos(ValidaTarjetaPIN.dameCuenta(), fecha1, fecha2);
		
		miVentana.getModelo().setRowCount(0);
		float saldo=0F;
		
		if (rsMovtos!=null)	{
		
//			Posicionamos el ResultSet al comienzo para recorrerlo de nuevo :
//			rsCompleto.beforeFirst();
		
		while (rsMovtos.next())	{

//				float importePantalla = rsMovtos.getFloat("importe");
//				if (rsMovtos.getString("tipoMovimiento").equals("debe")) importePantalla*=(-1);
//				saldo+=importePantalla;				
				
				miVentana.getModelo().addRow(new Object[]{formato.format(rsMovtos.getDate("fecha").getTime()), rsMovtos.getFloat("importe"), rsMovtos.getString("descripcion"), rsMovtos.getFloat("saldo")});
			}
		}
	
		miVentana.layout.show(Ventana.panelCont, "panelMostrarMovtos");
	}
	
	/**	Valida el importe a retirar y crea movto al debe
	 * @param retirada : Importe seleccinadol

	 */
	
	
	
	/**Dado un importe a retirar tecleado pro el usuario, valida su corrección y graba en la BBDD la correspondeinte 
	 * transacción al debe.
	 * También manda un email si el importe supera el umbral establecido para la aplicación el el fichero de propiedades cajeroV4.properties
	 * @param retirada El importe tecleado por el usuario para retirar
	 */
	public void retirarDinero( int retirada) 	{	
	
		float saldo = new AccederSaldo().dameSaldo(ValidaTarjetaPIN.dameCuenta(),Date.valueOf(LocalDate.now()));		
		System.out.println("El saldo actual es :  " + saldo);
		int maximoRetirada=ValidaTarjetaPIN.dameimporteMax();
		if ( retirada > maximoRetirada ) 
			muestraMensajeRetirarDinero("IMPORTE SUPERA MAXIMO AUTORIZADO. TECLEE NUEVO IMPORTE");
		else if ( retirada > saldo) 
			muestraMensajeRetirarDinero("SALDO INSUFICIENTE. TECLEE NUEVO IMPORTE");			
		else if ( retirada % 20 != 0 ) 
			muestraMensajeRetirarDinero("IMPORTE DEBE SER MULTIPLO DE 20. TECLEE NUEVO IMPORTE");
		else	{	

//			Todo OK
			

			CreaMovto.creaMovimiento("debe",ValidaTarjetaPIN.dameCuenta(),retirada, saldo-retirada);
			miVentana.setImporteRetirarDinero1("");	
			miVentana.setTextoRetirarDinero1_2("");			
			miVentana.setTextoRetirarDinero2_2("IMPORTE RETIRADO : " + retirada + " EUR");
			miVentana.setTextoRetirarDinero2_3("NUEVO SALDO   : " + new AccederSaldo().dameSaldo(ValidaTarjetaPIN.dameCuenta(),Date.valueOf(LocalDate.now())) + " EUR");

//		Comprobamos si el importe retirado es superior al establecido para notificacion, 
//		en cuyo caso avisamos por email :
			
			
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String appConfigPath = rootPath + "cajerov4.properties";								
			Properties appProps = new Properties();
			try {
				appProps.load(new FileInputStream(appConfigPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			int importeNotificar = Integer.parseInt(appProps.getProperty("importeNotificar"));
			
			if (retirada > importeNotificar){
				
				Email email = new SimpleEmail();
				email.setHostName("smtp.googlemail.com");
				email.setSmtpPort(465);
				email.setAuthenticator(new DefaultAuthenticator("Direccion Correo", "Contrasena"));
				email.setSSLOnConnect(true);
				try {
					email.setFrom("aaledo@gmail.com");
					email.setSubject("Notificacion App CajeroV4(MVC)");
					email.setMsg("Se han retirado " + retirada + "EUR  de su cuenta");
					email.addTo("aaledo@hotmail.com");
					email.send();			
				} catch (EmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
			}
								
			
			miVentana.panelRetirarDinero2.revalidate();
			miVentana.panelRetirarDinero2.repaint();			
			miVentana.layout.show(Ventana.panelCont, "panelRetirarDinero2");
		}	
	
	
	}
	
		/**Visualiza en pantalla el importe de error producido por el importe a retirar
		 * @param mensaje Importe a retirar
		 */
		public void muestraMensajeRetirarDinero( String mensaje) 	{	
			
			miVentana.setTextoRetirarDinero1_2(mensaje);
			miVentana.panelRetirarDinero1.revalidate();
			miVentana.panelRetirarDinero1.repaint();
			miVentana.layout.show(Ventana.panelCont, "panelRetirarDinero1");
	
		}	
	
	Ventana miVentana;
	static ResultSet rsCompleto;
	ResultSet rsParcial;
	private static float saldoActual;
	static ExecutorService es;
    static Future<Float> future;
    boolean primeraVez = true;
    
	
}
