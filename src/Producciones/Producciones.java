/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Producciones;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author Izrux
 */
public class Producciones {

    int aux = 0, aux2 = 0, aux3 = 0;
    String[] Izquierda = new String[100];
    String[] Derecha = new String[100];
    String[] Terminales = new String[100];
    String[] NoTerminales = new String[100];
    
    LinkedHashMap<String, String> First = new LinkedHashMap<>();

    public void lee() throws FileNotFoundException, IOException {

        String l;
        FileReader f = new FileReader("src/Producciones/produccion.txt");
        try (BufferedReader b = new BufferedReader(f)) {
            while ((l = b.readLine()) != null) {

                System.out.println(l);
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

        for (int i = 0; i < palabra.length(); i++) {// ejecuta el ciclo for del inicio hasta el final de la cadena 
            if (palabra.charAt(i) != ' ') {// va recolectabdo caratecter por caracter hasta el espacio                           
                palabraSepara += palabra.charAt(i);
            } else {
                terminal(palabraSepara);
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

        
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Producciones a = new Producciones();
        a.lee();
        a.print();
    }

}
