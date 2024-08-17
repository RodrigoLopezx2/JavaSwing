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
                escritor.write(usuario.toString());
                escritor.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean inicarSeion(String correo, String contrasena) {
        if (existeUsuario(correo)) {
            try {
                // Creas un FileReader y lo envuelves en un BufferedReader
                BufferedReader lector = new BufferedReader(new FileReader(rutaArchivoUsuarios));

                String linea;
                // Lee línea por línea hasta llegar al final del archivo (null)
                while ((linea = lector.readLine()) != null) {
                    if (linea.contains(correo) && linea.contains(contrasena)) {
                        return true;
                    }
                }

                // Cierras el lector
                lector.close();

            } catch (IOException e) {
                System.out.println("Ocurrió un error al leer el archivo.");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean existeUsuario(String correo) {
        try {
            // Creas un FileReader y lo envuelves en un BufferedReader
            BufferedReader lector = new BufferedReader(new FileReader(rutaArchivoUsuarios));

            String linea;
            // Lee línea por línea hasta llegar al final del archivo (null)
            while ((linea = lector.readLine()) != null) {
                if (linea.contains(correo)) {
                    return true;
                }
            }

            // Cierras el lector
            lector.close();

        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo.");
            e.printStackTrace();
        }
        return false;
    }

}
