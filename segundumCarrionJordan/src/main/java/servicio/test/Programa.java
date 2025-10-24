package servicio.test;

import java.time.LocalDate;

import servicio.FactoriaServicios;
import servicio.IServicioUsuario;

/**
 * Programa principal para probar la funcionalidad del proyecto segundum.
 */
public class Programa {
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("Programa de prueba de servicios iniciado.");
		// Aquí se pueden agregar llamadas a los servicios para probar su funcionalidad.
		
		//primero creamos una instancia del servicio de usuario
		IServicioUsuario servicioUsuario = FactoriaServicios.getServicio(IServicioUsuario.class);
		
		
		//Creamos el usuario de prueba 
		String nombre = "Pepe";
		String apellidos = "Pérez";
		String email = "an@um.es"; 
		String clave = "password123";
		LocalDate fechaNacimiento = LocalDate.now();

		//Creamos el usuario
		
		String idUsuario = servicioUsuario.registrarUsuario(nombre, apellidos, email, clave, fechaNacimiento, null);
		
		System.out.println("Usuario creado con ID: " + idUsuario + " con nombre: " + nombre + " " + apellidos+ ", email: " 
		+ email + ", clave: " + clave + " y  fechaNacimiento: " + fechaNacimiento);
		
		//Ahora el usuario quiere cambiar su email!! 
		String nuevoEmail = "asd@gmail.com"; 
		
		servicioUsuario.actualizarDatosUsuario(idUsuario, nombre, apellidos, nuevoEmail, clave, fechaNacimiento, null);
		
		System.out.println("Usuario con ID: " + idUsuario + " ha cambiado su email a: " + nuevoEmail);
	
	}
}