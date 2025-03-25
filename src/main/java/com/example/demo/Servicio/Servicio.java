package com.example.demo.Servicio;

import com.example.demo.CalculadoraMathView.CalculadoraMathView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;

public class Servicio extends VerticalLayout {

    // Patrón Singleton
    private static Servicio servicio;

    // Propiedades del Servicio
    Button[] arrayBotonesNumber = new Button[10];
    char[] numbers = {'0','1','2','3','4','5','6','7','8','9'};
    Button[] arrayBotonesOperadores = new Button[5];
    char[] operadores = {'+','-','*','/','='};
    Input input1;  // Para ingresar los números y operadores
    Input inputResultado; // Para mostrar el resultado
    // Lista para guardar todos los valores seleccionados y luego poder discriminar cada valor, para poder operar siguiendo el orden matemático
    ArrayList<String> valoresElegidos = new ArrayList<String>();

    private Servicio (){}

    public static Servicio getInstance() {
        if (servicio == null) {
            servicio = new Servicio();
        }
        return servicio;
    }

    // Crear los elementos visuales y añadirlos a la vista
    public void elementosCalculadora(CalculadoraMathView view) {

        // 1º Creamos los elementos
        view.add(new H1("Calculadora"));
        view.add(input1 = new Input());  // Input para ingresar números y operadores
        view.add(inputResultado = new Input());  // Input para mostrar el resultado

        // 2º Creamos los objetos botón, le añadimos el texto y los añadimos a la ventana
        rellenarArraysBotones();
        addBotones(view);
    }

    private void rellenarArraysBotones() {
        // Rellenamos los botones de los números
        for (int i = 0; i < arrayBotonesNumber.length; i++) {
            Button button = new Button(String.valueOf(numbers[i]));
            button.addClickListener(event -> {
                actualizarInput(event.getSource().getText());

                // Guardamos el texto de los botones que han sido seleccionados
                valoresElegidos.add(event.getSource().getText());
            });
            arrayBotonesNumber[i] = button;
        }

        // Rellenamos los botones de los operadores
        for (int i = 0; i < arrayBotonesOperadores.length; i++) {
            Button button = new Button(String.valueOf(operadores[i]));
            button.addClickListener(event -> {
                if (!event.getSource().getText().equals("=")) {
                    actualizarInput(event.getSource().getText());
                    // Guardamos el texto de los botones que han sido seleccionados
                    valoresElegidos.add(event.getSource().getText());
                } else if (event.getSource().getText().equals("=")) {

                    // Aquí llamamos al método que va a recorrer el array de valores elegidos y en base a ellos va a operar y generar un resultado
                    int resultado = hacerOperacion();
                    String resultadoString = "Resultado: " + resultado;
                    actualizarInputResultado(resultadoString);  // Actualizamos el nuevo input de resultado

                    // Limpiar los valores después de la operación
                    valoresElegidos.clear();
                }
            });
            arrayBotonesOperadores[i] = button;
        }
    }

    private void addBotones(CalculadoraMathView view) {
        // Usamos un VerticalLayout para los números y operadores
        VerticalLayout layoutBotones = new VerticalLayout();

        // Creamos filas para los números con un HorizontalLayout
        HorizontalLayout fila1 = new HorizontalLayout(arrayBotonesNumber[1], arrayBotonesNumber[2], arrayBotonesNumber[3]);
        HorizontalLayout fila2 = new HorizontalLayout(arrayBotonesNumber[4], arrayBotonesNumber[5], arrayBotonesNumber[6]);
        HorizontalLayout fila3 = new HorizontalLayout(arrayBotonesNumber[7], arrayBotonesNumber[8], arrayBotonesNumber[9]);
        HorizontalLayout fila4 = new HorizontalLayout(arrayBotonesNumber[0]);

        // Añadimos las filas de números al layout
        layoutBotones.add(fila1, fila2, fila3, fila4);

        // Creamos el HorizontalLayout para los operadores
        HorizontalLayout filaOperadores = new HorizontalLayout(arrayBotonesOperadores[0], arrayBotonesOperadores[1], arrayBotonesOperadores[2], arrayBotonesOperadores[3], arrayBotonesOperadores[4]);

        // Añadimos los botones a la vista
        view.add(layoutBotones);
        view.add(filaOperadores);
    }

    private void actualizarInput(String textoBoton) {
        StringBuilder calculo = new StringBuilder();
        calculo.append(input1.getValue()).append(textoBoton);
        input1.setValue(calculo.toString());
    }

    private void actualizarInputResultado(String resultado) {
        inputResultado.setValue(resultado);  // Actualizamos el input del resultado
    }

    // Calcular el resultado de la operación ingresada en el elemento input
    private int hacerOperacion() {
        int resultado = 0;
        String number1 = "", number2 = "";
        char operador = ' ';

        // Separar valores por operadores
        for (String valor : valoresElegidos) {
            if (valor.charAt(0)>= '0' && valor.charAt(0)<= '9') { // Empleamos la tabla ASCII para comprobar
                if (operador == ' ') {
                    number1 += valor; // construir el primer número
                } else {
                    number2 += valor; // construir el segundo número
                }
            } else { // Si es un operador
                if (operador == ' ') {
                    operador = valor.charAt(0); // definir el operador
                } else {
                    // Realizar la operación con los números ya acumulados
                    resultado = calcularResultado(Integer.parseInt(number1), Integer.parseInt(number2), String.valueOf(operador));
                    number1 = String.valueOf(resultado); // poner el resultado como el primer número
                    number2 = ""; // reiniciar el segundo número
                    operador = valor.charAt(0); // actualizar operador
                }
            }
        }

        // Realizar la operación final
        if (!number2.isEmpty()) {
            resultado = calcularResultado(Integer.parseInt(number1), Integer.parseInt(number2), String.valueOf(operador));
        }

        return resultado;
    }

    private int calcularResultado(int number1, int number2, String operador) {
        int resultado = 0;
        switch (operador) {
            case "*":
                resultado = number1 * number2;
                break;
            case "/":
                if (number2 != 0) resultado = number1 / number2; else System.err.println("Error: No se puede dividir entre 0");
                break;
            case "+":
                resultado = number1 + number2;
                break;
            case "-":
                resultado = number1 - number2;
                break;
            default:
                System.err.println("Error: Operador incorrecto");
                break;
        }
        return resultado;
    }
}
