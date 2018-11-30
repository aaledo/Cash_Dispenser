package cajeroV4.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Acceso a tabla de clientes. NO UTILIZADO FINALMENTE POR NO NECESITAR LA TABLA, AL NO IMPLEMENTAR EL SALUDO DE BIENVENIDA
 * CON EL NOMBRE DEL CLIENTE. 
 * @author Alfonso
 *
 */
public class AccederCliente {

//	public AccederTarjeta()	{
//
////	Connection con;
//	}	
	
	/**Devuelve el nombre del cliente tras acceder a su tabla maestra.
	 * @param codigoCliente Código de Cliente.
	 * @return Nombre de pila del cliente
	 */
	public String dameNombreCliente(String codigoCliente) 	{
	
		Connection con = Conexion.dameConexion();
		String nombreCliente="";
		try {
			PreparedStatement st = con.prepareStatement("SELECT * FROM CLIENTES WHERE idCliente = ?");
			st.setString(1, codigoCliente);
			ResultSet rs = st.executeQuery();
			if (rs!=null && rs.next())	nombreCliente = rs.getString("nombre");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR AL EJECUTAR SENTENCIA EN BBDD");
			e.printStackTrace();
			return null;
		}
		finally	{
			return nombreCliente;
		}
		
	
	}
	
//	Prueba 15/11/18 para GitHub
	
}
