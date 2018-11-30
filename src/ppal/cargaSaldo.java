package cajeroV4.ppal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import cajeroV4.modelo.Conexion;

public class cargaSaldo {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
//		Connection miConexion = Conexion.dameConexion();
		
//		Para actualizar BBDD cajeroV1 :
		Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/cajeroV1", "root", "");	
		
//		Para actualizar BBDD cajeroV4 :
//		Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/cajeroV4", "root", "");		
		
		try	{
			Statement miStatement = miConexion.createStatement();
			Statement miStatement2 = miConexion.createStatement();
			int contador=1;
			String sentencia = "SELECT * FROM movimientosConSaldo ORDER BY fecha" ;
			ResultSet miResultset = miStatement.executeQuery(sentencia);
			float saldoPrevio=0F;
			while (miResultset.next())	{
				if (miResultset.getString("tipoMovimiento").equals("haber")) saldoPrevio+=miResultset.getFloat("importe");
				else saldoPrevio-=miResultset.getFloat("importe");

				String sentencia2 = "INSERT INTO `movimientos`"
						+ " (`idMovimiento`, `fecha`, `tipoMovimiento`, `cuenta`, `importe`, `descripcion`, `saldo`)"
						+ " VALUES ( '" + miResultset.getDouble("idMovimiento") + "','" +
						miResultset.getDate("fecha") +"','" + 
						miResultset.getString("tipoMovimiento") +"','" + 
						miResultset.getString("cuenta") +"','" + 
						miResultset.getFloat("importe") + "','" +
						miResultset.getString("descripcion") + "','" +
						saldoPrevio +"')";						

					System.out.println(sentencia2 + "     Registro " + contador);
				contador++;
				miStatement2.executeUpdate(sentencia2);				
				
			}	
			} catch (SQLException e) {
			e.printStackTrace();
		}				
		
	}
		
	}

