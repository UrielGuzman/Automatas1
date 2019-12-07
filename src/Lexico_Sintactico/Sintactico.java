package Lexico_Sintactico;

import java.io.FileNotFoundException;
import java.io.IOException;

/* Lexico-Sintactico
Tecnologico Nacional De Mexico
instituto tecnologico de leon
ingenieria en sistemas computacionales
Lenguajes y Automatas 1
Martes-Jueves: 10:30am - 12:10pm
Viernes: 11:20am - 12:10pm
alumnos(a): 
Edvin Uriel Guzman Ruiz
Andres Hernandez Rodríguez
Fecha: 26/11/2019
 */
public class Sintactico {

    int matriz[][]
          =                           //  e,    {,  },  id, =,  ;, if,  (,  ),   intliteral, +,  -,  >,  <,  >=, <=, ==, !=
/*vacio*/                               {{0,    0,  0,  0,  0,  0,  0,  0,  0,           0,  0,  0,  0,  0,  0,  0,  0,  0},
/*program*/                             {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
/*sentencias*/                          {0, 0, 3, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
/*sentencia*/                           {0, 0, 0, 4, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
/*expresion*/                           {0, 0, 0, 6, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0},
/*resto_expresion*/                     {0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 8, 9, 0, 0, 0, 0, 0, 0},
/*expresion_logica*/                    {0, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
/*operador_relacional*/                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17}};

    String x, a;
    Producciones p;
    Lexico le;
    Pila palabra;
    boolean bandera = false;
    int aux, aux2, cont, ax1 = 1;
    String lexico;

    public Sintactico() throws IOException {
        palabra = new Pila();
        p = new Producciones();
        le = new Lexico();
        le.lee();
        p.lee();
        p.inicial();
        p.print();
    }

    public void lldriver() throws IOException {
        palabra.push(p.ini);
        x = palabra.print();
        le.tokens();
        a = le.auxc;
        int ble = 1;
        while (!palabra.vacio()) {
            System.out.println("Iteracion: " + ble);

            for (int i = 1; i <= p.aux2; i++) {
                if (p.NoTerminales[i] == null ? x == null : p.NoTerminales[i].equals(x)) {
                    aux = i;
                    bandera = true;
                }
            }

            for (int i = 1; i <= p.aux3; i++) {
                if (p.Terminales[i] == null ? a == null : p.Terminales[i].equals(a)) {
                    aux2 = i;

                }
            }
            if (bandera == true) {
                bandera = false;
                int fila = aux2;
                int columna = aux;
                System.out.println("Columna: " + aux);
                System.out.println("Fila: " + aux2);
                int posicion = matriz[columna][fila];
                System.out.println("Posicion: " + posicion);

                if (posicion != 0) {

                    ciclo(p.Derecha[posicion]);

                    x = palabra.print();

                } else {

                    System.out.println("Error *1");
                    System.exit(0);
                }

            } else {
                if (x.equals(a)) {
                    System.out.println("X y A son iguales: " + a);
                    palabra.pop();
                    if (!palabra.vacio()) {
                        x = palabra.print();
                        le.tokens();
                        a = le.auxc;
                    }
                } else {
                    System.out.println("Error *2");
                    System.exit(0);
                }

            }
            ble++;
            System.out.println("");
        }
        System.out.println("Terminado");
    }

    public void ciclo(String n) {

        palabra.pop();
        String[] p = n.split(" ");
        if (!"".equals(p[0])) {
            Pila tempo = new Pila();
            for (String p1 : p) {
                tempo.push(p1);
            }
            for (String p1 : p) {
                palabra.push(tempo.pop());
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Sintactico a = new Sintactico();
        a.lldriver();

    }
}
