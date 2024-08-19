/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login.negocio;

import com.mycompany.login.modelo.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rodri
 */
public class UsuarioDAO {

    final private String rutaArchivoUsuarios = "C:\\Users\\rodri\\OneDrive\\Documentos\\NetBeansProjects\\Login\\src\\main\\Archivos\\archivoUsuarios.txt";

    private static UsuarioDAO instancia;

    private UsuarioDAO() {
        // Constructor privado para evitar instanciación directa
    }

    public static UsuarioDAO getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioDAO();
        }
        return instancia;
    }

    public boolean crearUsuario(Usuario usuario) {

        File archivoUsuarios = new File(rutaArchivoUsuarios);
        try {
            if (archivoUsuarios.createNewFile()) {
                System.out.println("Archivo de Usuario Creado");
            }

            if (!existeUsuario(usuario.getCorreo())) {
                FileWriter escritor = new FileWriter(rutaArchivoUsuarios, true); // true para escribir al final del archivo
                escritor.write(usuario.toString() + "\n");
                escritor.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        Usuario usuarioDatoOld = buscarUsuario(usuario.getCorreo());
        try {
            // Leer todas las líneas del archivo en una lista
            List<String> lineas = Files.readAllLines(Paths.get(rutaArchivoUsuarios));

            // Reemplazar la línea antigua con la nueva
            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).equals(usuarioDatoOld.toString())) {
                    lineas.set(i, usuario.toString());  // Actualizamos la línea
                    break;
                }
            }

            // Sobrescribir el archivo con las líneas actualizadas
            Files.write(Paths.get(rutaArchivoUsuarios), lineas);

            System.out.println("Línea actualizada correctamente.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean eliminarUsuario(String correo){
        Usuario usuario = buscarUsuario(correo);
        try {
            // Leer todas las líneas del archivo en una lista
            List<String> lineas = Files.readAllLines(Paths.get(rutaArchivoUsuarios));

            // Reemplazar la línea antigua con la nueva
            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).equals(usuario.toString())) {
                    lineas.set(i, "");  // Actualizamos la línea
                    break;
                }
            }

            // Sobrescribir el archivo con las líneas actualizadas
            Files.write(Paths.get(rutaArchivoUsuarios), lineas);

            System.out.println("Línea actualizada correctamente.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean inicarSesion(String correo, String contrasena) {
        Usuario usuario = buscarUsuario(correo);
        if (usuario != null) {
            if (correo.equals(usuario.getCorreo()) && contrasena.equals(usuario.getContra())) {
                return true;
            }
        }

        return false;
    }

    public boolean existeUsuario(String correo) {
        Usuario usuario = buscarUsuario(correo);
        if (usuario != null) {
            return true;
        }
        return false;
    }

    public Usuario buscarUsuario(String correo) {

        String linea = buscarLineaUsuario(correo);
        if (linea != null) {
            return cadenaAUsuario(linea);
        }

        return null;
    }

    public String buscarLineaUsuario(String correo) {
        try {
            // Creas un FileReader y lo envuelves en un BufferedReader
            BufferedReader lector = new BufferedReader(new FileReader(rutaArchivoUsuarios));

            String linea;
            // Lee línea por línea hasta llegar al final del archivo (null)
            while ((linea = lector.readLine()) != null) {

                if (linea.indexOf(correo) != -1) {
                    return linea;
                }
            }
            // Cierras el lector
            lector.close();

        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo.");
            e.printStackTrace();
        }
        return null;
    }

    public Usuario cadenaAUsuario(String cadena) {

        String[] datosUsuario = cadena.split(",");
        Usuario usuario = new Usuario();
        for (String datoUser : datosUsuario) {
            String[] llaveDato = datoUser.split("=");

            String llave = llaveDato[0].trim();
            String dato = llaveDato[1].trim();

            switch (llave) {
                case "nombre":
                    usuario.setNombre(dato);
                    break;
                case "apellidoPaterno":
                    usuario.setApellidoPaterno(dato);
                    break;
                case "apellidoMaterno":
                    usuario.setApellidoMaterno(dato);
                    break;
                case "correo":
                    usuario.setCorreo(dato);
                    break;
                case "contra":
                    usuario.setContra(dato);
                    break;
                case "edad":
                    usuario.setEdad(Integer.parseInt(dato));
                    break;
            }

        }

        return usuario;
    }

}
