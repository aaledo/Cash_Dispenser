package cajeroV4.modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


/**Accede a la tabla de Movimientos.
 * @author Alfonso Aledo
 *
 */
public class AccederMovtos {

	/**Devuelve los movimientos de una cuenta dada entre un rango de fechas
	 * @param cuenta Cuenta para buscar movimientos
	 * @param fecha1 Fecha Desde
	 * @param fecha2 Fecha Hasta	
	 * @return ResultSet con el resultado de todos los movimientos cargados
	 */
	public ResultSet dameMovtos(String cuenta, Date fecha1, Date fecha2)	{
		
		Connection con = Conexion.dameConexion();

			try {
			PreparedStatement st = con.prepareStatement
					("SELECT * FROM MOVIMIENTOS WHERE cuenta = ? AND FECHA >=? AND FECHA <=?");
			st.setString(1, cuenta);
			st.setDate(2, fecha1);
			st.setDate(3, fecha2);	
			System.out.println(st.toString());	
			return st.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR AL EJECUTAR SENTENCIA EN BBDD");
			e.printStackTrace();
			return null;
		}
	}
}
