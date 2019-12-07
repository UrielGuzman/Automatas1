/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FirstFollow;

import Producciones.Producciones;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 *
 * @author Izrux
 */
public class FirstFollow {
 
    int aux = 0, aux2 = 0, aux3 = 0,aux4=0,aux5=0;
    String[] Izquierda = new String[100];
    String[] Derecha = new String[100];
    String[] Terminales = new String[100];
    String[] NoTerminales = new String[100];
    
    LinkedHashMap<String, String> First = new LinkedHashMap<>();
      LinkedHashMap<String, String> Follow = new LinkedHashMap<>();
    String temp="", temp2,temp3;
    boolean bandera = true,bandera2=true;

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
        
        for (String i : First.keySet()) {
            System.out.println("First (" + i + ") = {" + First.get(i)+"}");
        }
        
        System.out.println("\n");
        
        for (String i : Follow.keySet()) {
            System.out.println("Follow (" + i + ") = {" + Follow.get(i)+"}");
        }
    }

    public void fst() {
        String palabraSepara = "";
        for (int k=1;k<=aux;k++){
            int i=0;
        while (i<Derecha[k].length()){
            if (Derecha[k].charAt(i) != ' ') {                           
                palabraSepara += Derecha[k].charAt(i);
            } else {
                first1(palabraSepara,k);
                palabraSepara = "";
                break;
            }
            i++;
        }

    }
        
        for (int k=1;k<=aux;k++){
            int i=0;
           
            if (Derecha[k].length()==0){
            
                palabraSepara = "";        
                first2(palabraSepara,k);
                
            
            i++;
        }

    }
        for (int k=1;k<=aux;k++){
            int i=0;
        while (i<Derecha[k].length()){
            if (Derecha[k].charAt(i) != ' ') {                           
                palabraSepara += Derecha[k].charAt(i);
            } else {
                
                first3(palabraSepara,k);
                palabraSepara = "";
                break;
            }
            i++;
        }

        
    }
        
   }
    public void first1(String palabraSepara,int k){
        bandera=true;
                for (int j = 1; j <= aux3; j++) {
                    
                    if (palabraSepara.equals(Terminales[j]) && bandera==true) {
                       first(palabraSepara,k);
                       bandera=false;
                    } 
                    }
                                    
                }
    
        public void first2(String palabraSepara,int k){
            bandera=true;
                for (int j = 0; j <= aux3; j++) {
                    if(palabraSepara.equals("") && bandera==true){
                       first("ε",k);
                       bandera=false;
                    }
                    }
                                 
                }
        
            public void first3(String palabraSepara,int k){
              for (int j = 1; j <= aux3; j++) {
                    if(palabraSepara.equals(NoTerminales[j])){
                        
                     first(First.get(NoTerminales[j]),k);
                    }
                }
              
                }
                                   
        
    
    
    public void first(String palabra, int linea) {
            System.out.println(palabra+" TEST");
             for (int i = 1; i <= aux; i++) {
            if (palabra == null ? First.get(i) != null : !palabra.equals(First.get(i))) {
              
            } else {
                return;
             }
        }
            if (First.get(Izquierda[linea]) == null) {
                First.put(Izquierda[linea], palabra);
            } else {
                temp = First.get(Izquierda[linea]);
                First.put(Izquierda[linea], temp+" / "+ palabra);
            }
            
        }
    
    public void flw() {
        String palabraSepara = "";
        for (int k=1;k<=aux;k++){
            int i=0;
            while (i<Derecha[k].length()){
            if (Derecha[k].charAt(i) != ' ') {                           
                palabraSepara += Derecha[k].charAt(i);
            } else {
                follow1(palabraSepara,k);
                palabraSepara = "";
                
            }
            i++;
        }
follow(k);
    }
        bandera=true;
        for (int k=1;k<=aux;k++){
         int i=0;
            while (i<Derecha[k].length()){
            if (Derecha[k].charAt(i) != ' ') {                           
                palabraSepara += Derecha[k].charAt(i);
            } else {
                follow2(palabraSepara,k);
                palabraSepara = "";
                bandera=true;
            }
            i++;
        }
            
            followex(k);
    }
        
        
   }
    public void follow1(String palabraSepara,int k){
        bandera=true;
                for (int j = 1; j <= aux3; j++) {
                    
                    if (palabraSepara.equals(Terminales[j]) && bandera==true) {
                       first(palabraSepara,k);
                       bandera=false;
                    } 
                    }
                                    
                }
    
        public void follow2(String palabraSepara,int k){
            
                for (int j = 0; j <= aux3; j++) {
                    if(palabraSepara.equals(NoTerminales[j]) && bandera==true){
                       bandera=false;
                       temp2=palabraSepara;
                    }
                    else if(palabraSepara.equals(NoTerminales[j]) && bandera==false){
                       bandera2=false;
                       temp3=palabraSepara;
                    }
                    }
                                 
                }
        
        public void followex(int linea){
       for (int j = 1; j <= aux3; j++) {
            if (bandera==false&&bandera2==true){
            follow(linea);
        }
       
       }
      }
        
    public void follow(int linea) {
             
            if (Follow.get(temp2) == null) {
                Follow.put(temp2,First.get(temp3));
            } else {
                temp = Follow.get(temp2);
                Follow.put(temp2, temp+" / "+ First.get(temp3));
            }
            
        }
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FirstFollow a = new FirstFollow();
        a.lee();
        a.fst();
        a.flw();
        a.print();
    }
   
}
