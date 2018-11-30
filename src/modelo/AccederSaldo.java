package cajeroV4.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/** Accede a la tabla de movimientos para obtener el saldo a la fecha solicitada.
 * @author Alfonso
 *
 */
public class AccederSaldo {

	/**Devuelve el saldo de una cuenta a una fecha determinada
	 * @param cuenta	Cuenta bancaria
	 * @param fechaSaldo	Fecha a la que calcular el valor
	 * @return Saldo resultante
	 */
	public Float dameSaldo(String cuenta, java.sql.Date fechaSaldo) 	{
		
		Connection con = Conexion.dameConexion();
		PreparedStatement st=null;
		ResultSet rs=null;
		Float saldoAFecha=0F;
		try {

//			Si fecha elegida es igual o posterior a la actual, recuperamos el saldo del último registro :
			
		if(fechaSaldo.toLocalDate().compareTo(LocalDate.now()) >=0 )	{
				st = con.prepareStatement("SELECT * FROM MOVIMIENTOS WHERE cuenta = ? ORDER BY fecha DESC LIMIT 1");
				st.setString(1, cuenta);
				rs=st.executeQuery();
				if(rs!=null && rs.next())	saldoAFecha=rs.getFloat("saldo");	
		}
		
//		Si fecha elegida es anterior a la actual cargamos rs hasta llegar al último registro con fecha igual o anterior a la seleccionada :
			

		else	{
			st = con.prepareStatement("SELECT * FROM MOVIMIENTOS WHERE cuenta = ? AND FECHA <= ? ORDER BY fecha");
			st.setString(1, cuenta);
			st.setDate(2, fechaSaldo);
			rs=st.executeQuery();			
			if(rs!=null && rs.next())	{
				rs.last();
				saldoAFecha=rs.getFloat("saldo");
			}
		} 
		
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR AL EJECUTAR SENTENCIA EN BBDD");
			e.printStackTrace();

		}
		
		return saldoAFecha;
		}
}
