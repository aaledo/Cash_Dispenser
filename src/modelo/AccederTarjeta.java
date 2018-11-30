package cajeroV4.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Accede a la tabal de tarjetas de la BBDD
 * @author Alfonso
 *
 */
public class AccederTarjeta {

	/**Obtiene la tarjeta desde su identificador
	 * @param tarjetaTecleada : Identificador de tarjeta
	 * @return Resultado de la consulta (ResultSet)
	 */
	public ResultSet dameTarjeta(String tarjetaTecleada) 	{
	
		Connection con = Conexion.dameConexion();
		try {
			PreparedStatement st = con.prepareStatement("SELECT * FROM TARJETAS WHERE idTarjeta = ?");
			st.setString(1, tarjetaTecleada);
			return st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR AL EJECUTAR SENTENCIA EN BBDD");
			e.printStackTrace();
			return null;
		}
		
	
	}
}
