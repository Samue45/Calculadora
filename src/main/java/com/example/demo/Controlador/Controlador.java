package com.example.demo.Controlador;


import com.example.demo.CalculadoraMathView.CalculadoraMathView;
import com.example.demo.Servicio.Servicio;

public class Controlador {

    private Servicio servicio;

    public Controlador() {
        this.servicio = Servicio.getInstance();
    }

    //¿Qué debe ofrecer mi API?
    // Método para crear los elementos visuales de la calculadora
    public void getElementosCalculadora(CalculadoraMathView view) {
        servicio.elementosCalculadora(view);
    }

    // Método para manejar la operación que el usuario quiere hacer (debemos pasar el texto del botón que ha sido pulsado)
    // Si el texto es '=', se realiza la operación, si no. No se devuelve nada
    public void realizarOperacion(CalculadoraMathView view) {

    }

}
