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
    public Nodo<T> sig;      

    public Nodo(T info) {
        dato = info;
        sig = null;
    }
}
