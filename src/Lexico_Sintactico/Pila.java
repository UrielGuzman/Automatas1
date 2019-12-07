
package Lexico_Sintactico;
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
public class Pila {

    public Nodo pila;
    public void push(String palabra) {
        Nodo nuevo = new Nodo(palabra);

        if (pila==null) {
            pila = nuevo;
        } else {
            nuevo.siguiente = pila;
            pila = nuevo;
        }
    }

    public String pop() {
        if (pila==null) 
        {
            return null;
        } else {
            Nodo aux = pila;
            pila = pila.siguiente;
            return aux.palabra;
        }
    }
    public boolean vacio(){
        if(pila==null){
        return true;
        }else{
            return false;
        }
    }
    
public String print(){
        return pila.palabra;
    }

    public class Nodo {

        public String palabra;
        Nodo  siguiente;

        public Nodo(String palabra) {
            this.palabra = palabra;
        }
    }
}
