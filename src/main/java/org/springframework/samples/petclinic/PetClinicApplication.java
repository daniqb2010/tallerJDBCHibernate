package org.springframework.samples.petclinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.samples.petclinic.owner.Owner;

import ch.qos.logback.classic.spi.STEUtil;

/**
 * PetClinic Spring Boot Application.
 * 
 * @author Dave Syer
 *
 */
@SpringBootApplication
public class PetClinicApplication {

    public static void main(String[] args) throws Exception {

    	try {
    		
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		
    	}catch (ClassNotFoundException e) {
    		System.out.println("No encuentro el driver");
			e.printStackTrace();
			return;
		}
    	
    	
    	Connection conexion = null;
    	Statement sentencia = null;
    	
    	conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic","root","root");
    	
    	if (conexion != null) {
			System.out.println("Conexion establecida\n");
		} 	
    	
    	//Owner cliente = new Owner(null, "Daniel", "Barroso", "Avenida Canovas", "Don Benito", "627004466");
    	
    	insertarDatos(conexion, sentencia);

    	modificarCiudad(conexion, sentencia);
    	
    	busquedaCliente(conexion, sentencia);
    	
    	crearOwner(conexion, sentencia);
    	
    	nuevoOwner(conexion, sentencia);
    	
    	datosClientes(conexion, sentencia);

    }
    
    

	private static void nuevoOwner(Connection conexion, Statement sentencia) throws SQLException {
		
		sentencia = conexion.createStatement();
		
		String[] valores;
		valores = new String[] {"Daniel", "Quirós", "Don Benito", "Badajoz", "627004466"};

		String sql = "INSERT INTO owners (first_name, last_name, address, city, telephone) VALUES(?,?,?,?,?)";

		PreparedStatement ps = conexion.prepareStatement(sql);
		
		ps = conexion.prepareStatement(sql);

		for(int i=0; i < valores.length; i++)

			ps.setString(i+1, valores[i]);

		int crearOwner = ps.executeUpdate();
		
		String buscarId = "SELECT id FROM owners WHERE first_name = 'Daniel' AND last_name = 'Quiros'";
		
		
		
	}



	private static void crearOwner(Connection conexion, Statement sentencia) throws SQLException {

		sentencia = conexion.createStatement();
		
		String[] valores;
		valores = new String[] {"Paco", "Paredes", "Chavola", "Malaga", "685748596"};

		String sql = "INSERT INTO owners (first_name, last_name, address, city, telephone) VALUES(?,?,?,?,?)";

		PreparedStatement ps = conexion.prepareStatement(sql);
		
		ps = conexion.prepareStatement(sql);

		for(int i=0; i < valores.length; i++)

			ps.setString(i+1, valores[i]);

		int crearOwner = ps.executeUpdate();
		
	}

	private static void busquedaCliente(Connection conexion, Statement sentencia) throws SQLException {

		sentencia = conexion.createStatement();
		
		String sql = "SELECT * FROM owners WHERE first_name LIKE ? OR last_name LIKE ?";
		String busqueda = "%Da%";

		PreparedStatement ps = conexion.prepareStatement(busqueda);
		
		ps = conexion.prepareStatement(sql);

		ps.setString(1, busqueda);
		ps.setString(2, busqueda);

		ResultSet rs = ps.executeQuery();

		System.out.println("\n******** BUSQUEDA ********\n");
		
		while(rs.next()){

			int id = rs.getInt("id");
	
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			
			System.out.print("Id: " + id);
			System.out.print(", Nombre: " + firstName);
			System.out.println(", Apellidos: " + lastName);

		}

		rs.close();
		
	}

	private static void modificarCiudad(Connection conexion, Statement sentencia) throws SQLException {
		
		sentencia = conexion.createStatement();
		
		String modificacion = "UPDATE owners SET city = 'Sevilla' WHERE first_name = 'Daniel'";
		
		int modificacionCiudad = sentencia.executeUpdate(modificacion);
		
	}

	private static void insertarDatos(Connection conexion, Statement sentencia) throws SQLException {

		sentencia = conexion.createStatement();
		
		String nombre = "Daniel";
		String apellido = "Quirós";
		
		String busqueda = "SELECT count(*) FROM owners WHERE first_name = ? AND last_name = ?";
		
		PreparedStatement ps = conexion.prepareStatement(busqueda);
		
		ps.setString(1, nombre);
		ps.setString(2, apellido);
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next() != false) {

			String eliminarCliente = "DELETE FROM owners WHERE first_name = 'Daniel' AND last_name = 'Quirós'";
			int eliminacion = sentencia.executeUpdate(eliminarCliente);
			
		} else {
			
			String sqlInsertar = "INSERT INTO owners (first_name, last_name, address, city, telephone) " + 
	    			"VALUES ('Daniel', 'Quirós', 'Avd. Cánovas 78', 'Don Benito', '627004466')";
			
			int insertarCliente = sentencia.executeUpdate(sqlInsertar);
		}
		
	}

	private static void datosClientes(Connection conexion, Statement sentencia) throws SQLException {
		
		sentencia = conexion.createStatement();
		
		String sql = "SELECT * FROM owners";
    	
    	String sqlInsertar = "INSERT INTO owners (first_name, last_name, address, city, telephone) " + 
    			"VALUES ('Daniel', 'Quirós', 'Avd. Cánovas 78', 'Don Benito', '627004466')";
   
    	ResultSet rs = sentencia.executeQuery(sql);
    	
		System.out.println("DATOS DE LOS CLIENTES\n");
    	
    	while (rs.next()) {
    		
			int id = rs.getInt("id");
			String nombre = rs.getString("first_name");
			String apellido = rs.getString("last_name");
			String address = rs.getString("address");
			String city = rs.getString("city");
			String telefono = rs.getString("telephone");
			
			System.out.println("*************************\n-ID: " + id);
			System.out.println("-Nombre: " + nombre);
			System.out.println("-Apellido: " + apellido);
			System.out.println("-Direccion: " + address);
			System.out.println("-Ciudad: " + city);
			System.out.println("-Telefono: " + telefono + "\n*************************\n");
			
		}
    	
    	rs.close();
		
	}

}

