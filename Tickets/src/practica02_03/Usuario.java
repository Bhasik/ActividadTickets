package practica02_03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Usuario {
	
	public Usuario(){
		
	}


	public Usuario(int id,String nombre,String mail,String pass,String departament,boolean admin){
		
		this.id=id;
		this.nombre=nombre;
		this.mail=mail;
		this.pass=pass;
		this.departament=departament;
		this.admin=admin;
	}
	
	private String nombre,mail,pass;
	private String departament;
	private int id;
	
	boolean admin;
	private PreparedStatement preparedStatament = null;
	
	
	public void insertar(Connection conexion){
		
		int id_usuario = 0;
		
		try{
		
		String sql = "INSERT INTO usuaris (nom,mail,pass,departament,admin) VALUES (?,?,SHA1(?),?,?);";
		
	
		preparedStatament = conexion.prepareStatement(sql);
		
		
		preparedStatament.setString(1,"Manolo");
		preparedStatament.setString(2,"pepe");
		preparedStatament.setString(3,"1235");
		preparedStatament.setString(4,"Disseny");
		preparedStatament.setBoolean(5, false);
		
		preparedStatament.executeUpdate();
		
		
		
	/*try (ResultSet cogerId = preparedStatament.getGeneratedKeys()) {

		
			if (cogerId.next()) {

				this.id=cogerId.getInt(1);

			} else {

				System.out.println("No se puede recoger el ultimo Id");

			}

		}*/
		
		preparedStatament.close();
		
	
		
		
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError insertando usuario");
			
		}
		
		//return id_usuario;
	
	}
	
	public void leer(Connection conexion){
		
		try{
			
			
			
			String sql = "SELECT * FROM usuaris WHERE nom LIKE ? or id=? ;";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			//preparedStatament.setInt(1,id);
			preparedStatament.setString(1,this.nombre+"%");
			preparedStatament.setInt(2,this.id);
			
			ResultSet rs = preparedStatament.executeQuery();
			
			
			
			while(rs.next()){
				
			this.nombre = rs.getString("nom");
			this.mail = rs.getString("mail");
			this.pass = rs.getString("pass");
			this.departament = rs.getString("departament");
			this.admin =	rs.getBoolean("admin");
				
			
		
			}
	

			}catch(SQLException ex){
				
				System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError leyendo el usuario");
				
			}
		
		
		
		}
	
	
	
	public void actualizar(Connection conexion,int id){
		
		try{
			
			String sql = "UPDATE usuaris SET nom=?,mail=?,pass=SHA1(?),departament=?,admin=? WHERE id = ?";
			preparedStatament = conexion.prepareStatement(sql);
			
			preparedStatament.setString(1,"Pepe");
			preparedStatament.setString(2,"alberto_a@hotmail.es");
			preparedStatament.setString(3,"125800");
			preparedStatament.setString(4,"Disseny");
			preparedStatament.setBoolean(5, true);
			preparedStatament.setInt(6, id);
			
			
			preparedStatament.executeUpdate();
			
			preparedStatament.close();
			
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError leyendo el usuario");
		}
		
	}
	
	
	public void eliminar(Connection conexion,int id){
		
		try{
			
		String sql = "DELETE FROM usuaris WHERE id = ?";
		
		preparedStatament = conexion.prepareStatement(sql);
		
		preparedStatament.setInt(1, id);
	
		preparedStatament.executeUpdate();
		
		preparedStatament.close();
		
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError eliminando el usuario");
			
			
		}
	}
	
	
	
	public boolean validarUsuario(Connection conexion){
		
		boolean valido=false;
		
		
		try{
			
			String sql = "SELECT * FROM usuaris WHERE nom LIKE ? AND pass LIKE SHA1(?)";
			
			preparedStatament = conexion.prepareStatement(sql);
			
			
			preparedStatament.setString(1, this.nombre);
			preparedStatament.setString(1, this.pass);
			
			ResultSet rs = preparedStatament.executeQuery();
			
			
			while(rs.next()){
				
				this.nombre=rs.getString("nom");
				this.pass=rs.getString("pass");
				this.mail=rs.getString("mail");
				this.departament=rs.getString("departament");
				this.admin=rs.getBoolean("admin");
				
				valido=true;
					
			}
			
			
		}catch(SQLException ex){
			
			System.err.println(ex.getErrorCode() + " ," + ex.getMessage() + " ," + ex.getSQLState() + "\nError validando el usuario");
			
		}
		
		return valido;
		
		
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getDepartament() {
		return departament;
	}

	public void setDepartament(String departament) {
		this.departament = departament;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "Nombre: " + this.nombre + "\nEmail: "+ this.mail+  "\nPassword: " + this.pass+ "\nDeparamento: "+ this.departament+ "\nAdmin: "+ this.admin	
		+ "\n===================================================================================";
	}

	
	
}
