/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author DELL
 */
public class Nodo<T> {
    public T dato;           
    public Nodo<T> sig, ant;    
    public int cantidad = 1;

    public Nodo(T info) {
        dato = info;
        sig =ant = null;
        
    }
}
