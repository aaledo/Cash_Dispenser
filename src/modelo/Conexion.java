package cajeroV4.modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**Crea una conexion única para todos los accesos a la BBDD.
 * Usamos el patrón Singleton para utilizar una sola instancia de la conexion a la BBDD para toda la aplicación 
 * Guardamos los parámetros de acceso a la BBDD en un fichero "PROPERTIES" en la ruta por defecto.
 * @author Alfonso
 *
 */
public class Conexion {

		private static Connection con=null; 

		/**Devuelve una conexión a la BBDD. Si no existe la crea mediante el uso del patrón Singleton.
		 * Guardamos los parametros de conexion de el fichero PROPERTIES  "cajerov4.properties". 
		 * @return
		 * Un objeto de tipo Connection
		 */
		public static Connection dameConexion()	{
			
			try	{
				if (con==null) { 
					
				String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
				String appConfigPath = rootPath + "cajerov4.properties";								
				Properties appProps = new Properties();
				appProps.load(new FileInputStream(appConfigPath));	

System.out.println(appProps.getProperty("conexionBBDD"));				
System.out.println(appProps.getProperty("usuario"));				
System.out.println(appProps.getProperty("contra"));				
				
				
				con = DriverManager.getConnection(
						appProps.getProperty("conexionBBDD"),   
						appProps.getProperty("usuario"),
						appProps.getProperty("contra"));
						
//				miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/cajeroV4", "root", "");
//					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cajeroV4", "root", "");	
				}
			}
			
			catch(SQLException ex){
					System.out.println("ERROR SQL");
			         ex.printStackTrace();
			} 
			
			catch (FileNotFoundException e) {
				System.out.println("ERROR. FICHERO NO ENCONTRADO");
				e.printStackTrace();
			} 
			
			catch (IOException e) {
				System.out.println("ERROR DE ENTRADA/SALIDA");
				e.printStackTrace();
			}

			return con;	

		}	
}	

