package com.example.demo.CalculadoraMathView;


import com.example.demo.Controlador.Controlador;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class CalculadoraMathView extends VerticalLayout  {

    // Debo crear una instancia del Controlador para poder usar la API
    private Controlador controlador;



    public CalculadoraMathView() {

        controlador = new Controlador();

        //Añadimos los elementos de la calculadora
        controlador.getElementosCalculadora(this);

    }


}

