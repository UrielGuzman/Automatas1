package Lexico_Sintactico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class Producciones {

    int aux = 0, aux2 = 0, aux3 = 0, aux4 = 0;
    String[] Izquierda = new String[100];
    String[] Derecha = new String[100];
    String[] Terminales = new String[100];
    String[] NoTerminales = new String[100];
    String[] Palabras = new String[100];
    String ini;

    public void lee() throws FileNotFoundException, IOException {

        String l;
        FileReader f = new FileReader("src/Lexico_Sintactico/gramatica.txt");
        try (BufferedReader b = new BufferedReader(f)) {
            while ((l = b.readLine()) != null) {

                aux++;
                separar(l);

            }
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e);
        }

        for (int i = 1; i <= aux; i++) {
            Palabras(Derecha[i]);
        }
    }

    public void separar(String x) {
        int inicio = 0, fin = 0;
        String palabra = "";
        for (int i = 0; i < x.length(); i++) {

            if (x.charAt(i) == '-' && x.charAt(i + 1) == '>') {
                fin = i - 1;
                while (inicio != fin) {
                    palabra += x.charAt(inicio);
                    inicio++;
                }
                Izquierda[aux] = palabra;
                izquierda(palabra);
                inicio = i + 1;
                palabra = "";

            } else if (x.charAt(i) == '>' && x.charAt(i - 1) == '-') {
                i++;
                if (x.length() != i) {

                    fin = x.length();
                    inicio = i;
                    if (x.charAt(inicio) == ' ') {
                        inicio++;
                    }
                    while (inicio != fin) {
                        palabra += x.charAt(inicio);

                        inicio++;
                    }
                } else {
                    palabra = " ";
                }
                Derecha[aux] = palabra;

            }

        }

    }

    public void Palabras(String palabra) {
        String palabraSepara = "";

        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) != ' ') {                           
                palabraSepara += palabra.charAt(i);
            } else {
                terminal(palabraSepara);
                aux4++;
                Palabras[aux4] = palabraSepara;
                palabraSepara = "";
            }

        }
    }

    public void izquierda(String palabra) {
        for (int i = 1; i < aux; i++) {
            if (palabra == null ? NoTerminales[i] != null : !palabra.equals(NoTerminales[i])) {
            } else {
                return;
            }
        }
        aux2++;
        NoTerminales[aux2] = palabra;
    }

    public void terminal(String palabra) {
        for (int i = 1; i <= aux; i++) {
            if (palabra == null ? NoTerminales[i] != null : !palabra.equals(NoTerminales[i])) {
                if (palabra == null ? Terminales[i] != null : !palabra.equals(Terminales[i])) {
                    if (palabra != "") {
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        aux3++;
        Terminales[aux3] = palabra;
    }

    public void inicial() {
        String temporal = "";
        for (int i = 1; i <= aux2; i++) {

            temporal = NoTerminales[i];
            for (int j = 1; j <= aux4; j++) {
                if (NoTerminales[i] == null ? Palabras[j] != null : !NoTerminales[i].equals(Palabras[j])) {
                } else {
                    return;
                }

            }
             ini = temporal;
        }
       
    }

    public void print() {

        System.out.println("\n");
        for (int i = 1; i <= aux2; i++) {
            System.out.println("   " + NoTerminales[i] + "    ");
        }
        System.out.println("\n");
        for (int i = 1; i <= aux; i++) {
            System.out.println("   " + Derecha[i] + "    ");
        }
        System.out.println("\n");
        for (int i = 1; i <= aux3; i++) {
            System.out.println("   " + Terminales[i] + "    ");
        }
System.out.println("\n");
        for (int i = 1; i <= aux4; i++) {
            System.out.println("   " + Palabras[i] + "    ");
        }
        
        System.out.println(ini);
    }

}
