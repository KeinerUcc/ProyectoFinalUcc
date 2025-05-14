/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;

/**
 *
 * @author DELL
 */
public class PilaUsuarios {

    Nodo<Usuario> tope;

    public PilaUsuarios() {
        tope = null;
    }

    public boolean getEsVacia() {
        return tope == null ? true : false;
        /*
            if(tope==null)
                return true;
            else
                return false;
         */
    }

    public Nodo<Usuario> getBase() {
        if (getEsVacia()) {
            return null;
        } else {
            Nodo<Usuario> p = tope;
            while (p.sig != tope) {
                p = p.sig;
            }
            return p;
        }
    }

    public void aggUsuario(String usuario, String contra) {
        Usuario user = new Usuario(usuario, contra);
        Nodo<Usuario> nuevoNodo = new Nodo(user);

        if (getEsVacia()) {
            tope = nuevoNodo;
            tope.sig = tope;
        } else {
            nuevoNodo.sig = tope;
            Nodo<Usuario> ultimo = getBase();
            ultimo.sig = nuevoNodo;
            tope = nuevoNodo;
        }
    }

    public boolean validacion(String usuario, String contra) {
        if (getEsVacia()) {
            return false;
        }
        Nodo<Usuario> p = tope;
        usuario = usuario.trim();
        contra = contra.trim();
        do {
            if (p.dato.user.equals(usuario) && p.dato.contra.equals(contra)) {
                return true;
            }
            p = p.sig;
        } while (p != tope);
        return false;
    }
}
