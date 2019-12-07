/* Minimizacion De Automatas
Tecnologico Nacional De Mexico
instituto tecnologico de leon
ingenieria en sistemas computacionales
Lenguajes y Automatas 1
Martes-Jueves: 10:30am - 12:10pm
Viernes: 11:20am - 12:10pm
alumnos(a): 
Edvin Uriel Guzman Ruiz
Andres Hernandez Rodríguez
Fecha: 15/10/2019
 */
package Minimizacion;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Izrux
 */
public class Minimizacion {

    String[] alfabeto;
    int NoEstados, row;
    ArrayList<Estado> EstadosA1 = new ArrayList<>();
    Automata automata1;
    HashMap<String, String> hash1 = new HashMap<>();
    ArrayList<String> arrayM1M2 = new ArrayList<>();
    String[][] tabla;

    boolean isNum(String cad) {
        try {
            Double.parseDouble(cad);
            return true;
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Solo se admiten numeros ");
        }
        return false;
    }

    public void datos() {
        String aux;

        //Pedimos una cadena la cual separamos con split tomando el simbolo ","
        aux = JOptionPane.showInputDialog("Introduzca el alfabeto del automata, separando cada simbolo con una ',' (a,b,c,...,n)");
        alfabeto = aux.split(",");

        //Pedimos los estados que tiene el Automata asegurandonos de no seguir a menos de que sean numeros y sea mayor a 0
        do {
            do {
                aux = JOptionPane.showInputDialog("¿Cuantos estados tiene su AFD?");
            } while (!isNum(aux));
        } while (Integer.parseInt(aux) <= 0);
        NoEstados = Integer.parseInt(aux);

        //Recorremos los estados que nos dijo el usuario que habia iniciando del estado 0 al estado n
        for (int i = 0; i < NoEstados; i++) {
            //Ingresamos si el estado es o no es final
            aux = JOptionPane.showInputDialog(null, "¿Es final " + "q" + i + "?\n\"1\" = si" + " \n\"[Enter]\" = no");

            if (i == 0) { //Definimos que el estado inicial sea siempre q0

                JOptionPane.showMessageDialog(null, "El estado q0 siempre es inicial");

                if (aux.equals("1")) {
                    EstadosA1.add(new Estado("q" + i, true, true));
                } else {
                    EstadosA1.add(new Estado("q" + i, true, false));
                }
                // Todos los estados que no sean q0 los hacemos no iniciales y de acuerdo a la respuesta del usuario los hacemos finales
            } else {
                if (aux.equals("1")) {
                    EstadosA1.add(new Estado("q" + i, false, true));
                } else {
                    EstadosA1.add(new Estado("q" + i, false, false));
                }
            }
        }
        //Al terminar de definir los estados le damos valores al automata 1 con el alfabeto y los estados del automata
        automata1 = new Automata(alfabeto, EstadosA1);

        //Para cada estado recorremos el alfabeto para saber su transicion con cada simbolo
        for (int i = 0; i < EstadosA1.size(); i++) {
            for (int j = 0; j < alfabeto.length; j++) {
                //El usuario ingresa a cual estado se hace la transicion ingresando unicamente el numero del estado siguiente
                do {
                    aux = JOptionPane.showInputDialog("q" + i + " Leyendo un " + alfabeto[j] + " se mueve hacia q");
                } while (!isNum(aux));
                //Guardamos en un Hashmap una cadena de strings como la siguiente: "q0,0,q1" indicando la transicion con el simbolo
                hash1.put("q" + i + "," + alfabeto[j], "q" + aux);
            }
        }
    }

    public void Calculos() {
        //Recorremos el numero de estados buscando en cada uno si se puede reducir
        
        for (int i = 0; i < NoEstados; i++) {
            for (int j = i + 1; j < NoEstados; j++) {
                if (EstadosA1.get(i).isFinal() && EstadosA1.get(j).isFinal()
                        || !(EstadosA1.get(i).isFinal() && !EstadosA1.get(j).isFinal())) {
                    //Si los estados son compatibles, entonces llamamos el metodo moore
                    moore(i, j);
                    //De lo contrario, simplemente pasamos a la siguiente iteracion
                } else {
                }

            }

        }
        //Al finalizar todas las iteraciones recorremos el hashmap e imprimimos los valores de este
        for (String i : hash1.keySet()) {
            System.out.println(i + " nos movemos a: " + hash1.get(i));
        }
    }

    public void moore(int x, int y) {
        //Creamos una tabla con 10000 filas para automatas grandes y de alfabeto+1 columnas
        tabla = new String[10000][alfabeto.length + 1];
        //Regresamos a 0 la arrayList auxiliar
        arrayM1M2.clear();
        //Ponemos como inicio de los nuevos automatas qx y qy los cuales son los estados que estamos verificando
        arrayM1M2.add("q" + x + ",q" + y);
        //Regresamos el valor de las filas a 0 para no sobresaturar la tabla
        row = 0;
        //Por cada una de las filas de la tabla de moore llamamos al metodo getTabla (Utilizamos una columna auxiliar para verificacion)
        for (int i = 0; i < arrayM1M2.size(); i++) {
            row++;
            getTabla(arrayM1M2.get(i), i);
        }
        compEstados(x, y);
    }

    public void getTabla(String m1m2, int indiceRenglon) {

        //Por cada letra del alfabeto separamos y tomamos la parte izquierda y la parte derecha de la primera columna de la tabla
        for (int i = 0; i < alfabeto.length; i++) {
            String valorCeldaIzq = ((m1m2).split(",")[0] + ",");
            String valorCeldaDer = ((m1m2).split(",")[1] + ",");
            String valCeldT = valorCeldaIzq + valorCeldaDer;

            //ingresamos los valores en la tabla que estamos creando, en este caso creamos valores m1 m2 en la tabla
            tabla[indiceRenglon][0] = valCeldT;

            //Por cada letra del alfabeto tomamos el siguiente estado al cual se mueve el automata
            for (int j = 0; j < alfabeto.length; j++) {
                String valCelda = hash1.get(valorCeldaIzq + alfabeto[j]);
                valCelda += "," + hash1.get(valorCeldaDer + alfabeto[j]);
                tabla[indiceRenglon][j + 1] = valCelda;

                //Por cada fila de la tabla verificamos si la columna 1 auxiliar tiene los valores de la columna 1 de la tabla
                for (int k = 0; k < row; k++) {
                    if (!arrayM1M2.contains(String.valueOf(tabla[k][j + 1])) && tabla[k][j + 1] != null) {
                        arrayM1M2.add(String.valueOf(tabla[k][j + 1]));
                    }
                }
            }
        }
    }

    public void compEstados(int x, int y) {
        ArrayList<Estado> auxComparar1 = new ArrayList<>();
        ArrayList<Estado> auxComparar2 = new ArrayList<>();

        //Por cada par de estados en la columna auxiliar los separaremos, juntaremos y compararemos
        for (int i = 0; i < arrayM1M2.size(); i++) {
            //Separamos los estados de la columna 1 auxiliar

            String valorIzq = arrayM1M2.get(i).split(",")[0];
            String valorDer = arrayM1M2.get(i).split(",")[1];

            //Verificamos que el estado se encuentre en los estados ingresados y lo añadimos a un auxiliar (De cada uno de los 2 automatas)
            for (int j = 0; j < EstadosA1.size(); j++) {

                if (EstadosA1.get(j).getNombre().equals(valorIzq)) {
                    auxComparar1.add(EstadosA1.get(j));

                } else if (EstadosA1.get(j).getNombre().equals(valorDer)) {
                    auxComparar2.add(EstadosA1.get(j));
                }
            }
            //Sacamos el menor de los auxiliares
            int menor = auxComparar1.size();
            if (auxComparar2.size() < menor) {
                menor = auxComparar2.size();
            }
            
            //Utilizamos el menor como tope para nuestro if y comenzamos a comparar si todos los estados de la tabla son compatibles
            for (int k = 0; k < menor; k++) {

                if (auxComparar1.get(k).isFinal() && auxComparar2.get(k).isFinal()
                        || !(auxComparar1.get(k).isFinal()) && !(auxComparar2.get(k).isFinal())) {

                } else {
                    //Si al menos uno no es compatible terminamos con esta iteracion
                    return;
                }
            }
        }
        //Si todos los estados de la tabla fueron compatibles procedemos a minimizar los estados Qx y Qy
        borrar("q" + x, "q" + y);
    }

    public void borrar(String q1, String q2) {
        //Buscamos en el hashmap todos las "Flechas" del estado que vamos a eliminar y las removemos junto con el estado
        for (int i = 0; i < alfabeto.length; i++) {
            if (hash1.containsKey(q2 + "," + alfabeto[i])) {
                hash1.remove(q2 + "," + alfabeto[i]);
            }
        }
        //Recorremos todo el hashmap en busca de los estados que apuntan al estado que acabamos de borrar
        for (String i : hash1.keySet()) {
            //Si encontramos un estado que apunte al estado que borramos lo redirigimos al estado equivalente 
            if ((i + hash1.get(i)).equals(i + q2)) {
                hash1.replace(i, q1);
            }
        }
    }

    public static void main(String[] args) {
        Minimizacion a = new Minimizacion();
        a.datos();
        a.Calculos();
    }
}