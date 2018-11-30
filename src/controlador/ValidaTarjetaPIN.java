package cajeroV4.controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import cajeroV4.modelo.AccederCliente;
import cajeroV4.modelo.AccederTarjeta;
import cajeroV4.modelo.Tarjeta;
import cajeroV4.vista.Ventana;

/**Realiza las validaciones y consultas de la tarjeta introducida por el cliente, incluidos su estado y credenciales.
 * @author Alfonso
 *
 */
public class ValidaTarjetaPIN {
	
	/**Creación de la clase guardando una copia de la clase de la ventana para poder trabajar sobre la interfaz gráfica
	 * @param ventana Interfaz de ventanas de la aplicación
	 */
	public ValidaTarjetaPIN(Ventana ventana) {
		super();
		this.ventana = ventana;
	}
	
/**Crea el objeto de tipo Tarjeta con todos sus datos
 * @param rs : ResultSet obtenido del acceso a la tabla BBDD de tarjetas
 * @return Devuleve un objeto de tipo Tarjeta
 * @throws SQLException Error al operar en la BBDD
 */
public Tarjeta creaTarjeta(ResultSet rs) throws SQLException	{	
	
	return new Tarjeta(rs.getString("idTarjeta"), rs.getInt("bloqueo"), rs.getString("PIN"), rs.getInt("importeMaxAutorizado"),rs.getString("cuenta")); 

}
	

/**Comprueba la corrección del Número de tarjeta teccleado ( dígitos numéricos, longitud, existencia de la tarjeta y estado no bloqueado )
 * @param tarjetaTecleada Simulamos la lectura de una tarjeta mediante la introducción manual de su número por parte del usuario
 * @throws SQLException Error al operar en la BBDD
 */
public void validaTarjeta(String tarjetaTecleada) throws SQLException	{

	ventana.setmensajePanelTarjeta("");
	ventana.aceptarTarjetaVisible(true);
	if (tarjetaTecleada.equals("0") || !tarjetaTecleada.matches("\\d+") || tarjetaTecleada.length()!=20 )	{
		//	Tarjeta mal tecleada
		ventana.setTarjetaTecleada("");
		ventana.setmensajePanelTarjeta("IDENTIFICADOR INVALIDO. TECLEE UN NUMERO DE 20 DIGITOS");
		ventana.tarjetaPanelTarjeta.requestFocusInWindow();
	}		
		
	else	{
		//	Tarjeta bien tecleada	 
		rs1 = new AccederTarjeta().dameTarjeta(tarjetaTecleada);
		if (rs1 !=null && rs1.next())	{
		//	Tarjeta encontrada
			miTarjeta=creaTarjeta(rs1);				//	Creamos el objeto tarjeta y le asignamos valores
			if (miTarjeta.getBloqueo() != 0)	{
		//	Tarjeta bloqueada					
				ventana.setmensajePanelTarjeta("LA TARJETA ESTA BLOQUEADA");
				ventana.aceptarTarjetaVisible(false);
			}	else {
		//	Tarjeta OK --> vamos al panel de pedir el PIN	
					intentos=0;
					ventana.layout.show(ventana.panelCont, "panelPIN");
					ventana.getPassPanelPIN().requestFocusInWindow();
					}
			}
				else	{
		//	Tarjeta no encontrada		
				ventana.setTarjetaTecleada("");
				ventana.setmensajePanelTarjeta("LA TARJETA NO EXISTE. INTRODUZCA NRO TARJETA VALIDO");
				ventana.tarjetaPanelTarjeta.requestFocusInWindow();
			}

		}
		
	//	Cerramos ResultSet en todos los casos
	
	if (rs1 !=null) rs1.close();
	
}	
	

	/**Valida la introducción por parte del usuario de una contraseña numérica válida en un máximo de tres intentos
	 * @param pass PIN introducido por el usuaio
	 * @throws SQLException
	 */
	public void validaPIN(String pass) throws SQLException	{		
	
		if (intentos >3 ) CierraRecursos.cierraTodo();
		ventana.setMensajePanelPIN("");
		if (pass.equals("0") || !pass.matches("\\d+") || pass.length()!=4 )	{
//		if (pass.equals("") || !pass.matches("\\d+") )	{			
			//	PIN mal tecleado
			ventana.setTarjetaTecleada("");
			ventana.setPassPanelPIN("");
			ventana.setMensajePanelPIN("INTRODUZCA UN PIN VALIDO DE CUATRO NUMEROS");	
			ventana.getPassPanelPIN().requestFocusInWindow();
		}		
			
		else	{
			//	PIN bien tecleada. Comprobamos su correccion :	 
				intentos ++;
				
				if (!pass.equals(miTarjeta.getPIN()))
					if (intentos<3)	{
						ventana.setMensajePanelPIN("VALIDACION INCORRECTA DEL PIN");
						ventana.setPassPanelPIN("");
						ventana.getPassPanelPIN().requestFocusInWindow();
					}
					else	{
						ventana.setMensajePanelPIN("HA FALLADO TRES INTENTOS. SU TARJETA HA SIDO RETENIDA");
						ventana.aceptarPanelPINVisible(false);
						ventana.aceptarTarjetaVisible(false);						
					}
				else	{
		//	Todo correcto :	
					
		//	1.- Sometemos calcular saldo por otro hilo de ejecución :			
		//			(llamando a método  cargaInicialMovtosSaldo() de la clase RealizaConsultas )
					
//					RealizaConsultas.cargaInicialMovtosSaldo();

		
					
					
		//	2.- Mostramos la pantalla de las opciones  :						
					ventana.layout.show(ventana.panelCont, "panelVentanaOpciones");

				}
				
		}
	}
	
	/**Devuelve el nro de cuenta
	 * @return
	 */
	public static String dameCuenta()	{
		return miTarjeta.getCuenta();
	}

	/**Devuelve el improte máximo autorizado para retirada en la propia tarjeta
	 * @return
	 */
	public static int dameimporteMax()	{
		return miTarjeta.getImporteMaxAutorizado();
	}
	
	
	
	static int intentos;
	ResultSet rs1=null;	
	Ventana ventana;
	static Tarjeta miTarjeta;

}
