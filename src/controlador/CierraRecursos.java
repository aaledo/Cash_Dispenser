package cajeroV4.controlador;



import java.sql.Connection;
import java.sql.SQLException;

import cajeroV4.modelo.Conexion;

/** Cierra los recursos de BBDD y su acceso antes de finalizar la aplicación
 * @author Alfonso
 *
 */

public class CierraRecursos {

	/**Cierra todos recursos antes de finalizar la aplicación
	 * 
	 */
	public static void cierraTodo()	{
		
		Connection con = Conexion.dameConexion();
		if (con!=null)
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		finally	{	System.exit(0);     }
		
	}
	
	
	
}
