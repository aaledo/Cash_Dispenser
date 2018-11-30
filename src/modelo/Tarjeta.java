package cajeroV4.modelo;

/**Clase Tarjeta Bancaria con sus propiedades
 * @author Alfonso
 *
 */
public class Tarjeta {

	private String idTarjeta;
	private int bloqueo;
	private String PIN;
	private int importeMaxAutorizado;	
	private String cuenta;
	
	/**Constructor con todas sus propiedades
	 * @param idTarjeta : Código de Tarjeta
	 * @param bloqueo : Tarjeta bloqueada
	 * @param PIN : Contraseña de acceso
	 * @param importeMaxAutorizado : Umbral máximo permitido para retiradas de efectivo
	 * @param cuenta : Cuenta bancaria
	 */
	public Tarjeta(String idTarjeta, int bloqueo, String PIN, int importeMaxAutorizado, String cuenta) {
		super();
		this.idTarjeta = idTarjeta;
		this.bloqueo = bloqueo;
		this.PIN = PIN;
		this.importeMaxAutorizado = importeMaxAutorizado;
		this.cuenta = cuenta;
	}

	public String getIdTarjeta() {
		return idTarjeta;
	}

	public void setIdTarjeta(String idTarjeta) {
		this.idTarjeta = idTarjeta;
	}

	public int getBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(int bloqueo) {
		this.bloqueo = bloqueo;
	}

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = PIN;
	}

	public int getImporteMaxAutorizado() {
		return importeMaxAutorizado;
	}

	public void setImporteMaxAutorizado(int importeMaxAutorizado) {
		this.importeMaxAutorizado = importeMaxAutorizado;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}	

	
	
	
	
	
}
