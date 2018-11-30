package cajeroV4.modelo;

/**Cuenta bancaria con sus propiedades
 * @author Alfonso
 *
 */
public class Cuenta {

	private String idCuenta;
	private String tipoCuenta;
	private int oficina;	
	private String cliente;
	
	/**  Crea instancia de la clase Cuenta y almacena sus propiedades
	 * @param idCuenta	: cuenta bancaria
	 * @param tipoCuenta : Crédito o Débito
	 * @param oficina : Sucursal bancaria
	 * @param cliente : Código de cliente propietatio de la cuenta 
	 */
	public Cuenta(String idCuenta, String tipoCuenta, int oficina, String cliente) {
		super();
		this.idCuenta = idCuenta;
		this.tipoCuenta = tipoCuenta;
		this.oficina = oficina;
		this.cliente = cliente;
	}

	/**Devuelve el código de una cuenta
	 * @return Identificador Cuenta
	 */
	public String getIdCuenta() {
		return idCuenta;
	}

	/**Asigna el código a una cuenta
	 * @param idCuenta : Identificador de Cuenta
	 */
	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}
	/**Devuelve el tipo de una cuenta
	 * @return Tipo de Cuenta
	 */
	public String getTipoCuenta() {
		return tipoCuenta;
	}
	/**Asigna el tipo a una cuenta
	 * @param tipoCuenta : Tipo de Cuenta
	 */
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	/**Devuelve la oficina de una cuenta
	 * @return Oficina de la Cuenta
	 */
	public int getOficina() {
		return oficina;
	}
	/**Asigna la oficina a una cuenta
	 * @param oficina :Oficina de la Cuenta
	 */
	public void setOficina(int oficina) {
		this.oficina = oficina;
	}
	/**Devuelve el Código de Cliente de una cuenta
	 * @return Cliente de la Cuenta
	 */
	public String getCliente() {
		return cliente;
	}
	/**Asigna el Código de Cliente de una cuenta
	 * @param oficina Código de Cliente
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}	
	
	
	
}
