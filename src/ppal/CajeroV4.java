package cajeroV4.ppal;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.JFrame;

import cajeroV4.vista.Ventana;

/** Realiza la funcionalidad básica de un cajero automático, ofreciendo operaciones de consulta de saldo y movimientos, así como 
 * de depósito y reintegro.
 * Esta clase directora se limita a instanciar la clase principal de la ventana. Son los eventos quienes dirigen luego el flujo del programa.
 * @author Alfonso Aledo
 *
 */
public class CajeroV4 { 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Ventana miVentana = new Ventana();
		miVentana.setVisible(true);
		miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

		
	}

}
