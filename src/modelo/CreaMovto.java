package cajeroV4.modelo;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import cajeroV4.vista.Ventana;

/**Crea una transacción en la tabla de Movimientos de la BBDD conel depósito o retirada seleccionados por el usuario.
 * @author Alfonso
 *
 */
public class CreaMovto {

	/**Crea un movimiento en la BBDD
	 * @param parTipo : Debe o Haber
	 * @param parCuenta : Cuenta bancaria
	 * @param parImporte : Importe
	 * @param parSaldo : Saldo resultante trase el movimiento
	 */
	public static void creaMovimiento(String parTipo, String parCuenta, float parImporte, float parSaldo) {
		
		String auxDescripcion="";
		switch(parTipo)	{
		case "haber" : auxDescripcion = "Deposito " + parImporte; break;
		case "debe" : auxDescripcion = "Reintegro " + parImporte; break;
		default: auxDescripcion = "Descripcion desconocida";
		}
		
		String sentencia = "INSERT INTO `movimientos`"
				+ " (`idMovimiento`, `fecha`, `tipoMovimiento`, `cuenta`, `importe`, `descripcion`, `saldo`)"
				+ " VALUES (NULL, '" + LocalDate.now() +"','" + parTipo +"','" + parCuenta +"','" + parImporte +"','" + auxDescripcion  +"','" + parSaldo	+"')";
		System.out.println(sentencia);

		Connection con = Conexion.dameConexion();
		PreparedStatement st;
		try {
			st = con.prepareStatement(sentencia);
			st.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "SE HA PRODUCIDO UN ERROR CON LA BBDD");			
			e1.printStackTrace();
		}
		
	
	}
}
