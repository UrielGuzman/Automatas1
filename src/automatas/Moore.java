/* Comparacion de Automatas con teorema de Moore
Tecnologico Nacional De Mexico
instituto tecnologico de leon
ingenieria en sistemas computacionales
Lenguajes y Automatas 1
Martes-Jueves: 10:30am - 12:10pm
Viernes: 11:20am - 12:10pm
alumnos(a): 
Edvin Uriel Guzman Ruiz
Andres Hernandez
Fecha: 10/10/2019
*/
package automatas;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class Moore {

    String[] alfabeto;
    int NoEstados,row;
    ArrayList<Estado> EstadosA1 = new ArrayList<>();
    ArrayList<Estado> EstadosA2 = new ArrayList<>();
    Automata automata1;
    Automata automata2;
    HashMap<String, String> hash1 = new HashMap<>();
    HashMap<String, String> hash2 = new HashMap<>();
    String[][] tabla;
    ArrayList<String> arrayM1M2 = new ArrayList<>();

    //Declaramos un try catch para solo permitir numeros
    boolean isNum(String cad) {
        try {
            Double.parseDouble(cad);
            return true;
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Solo se admiten numeros ");
        }
        return false;
    }

    //Iniciamos creando una clase para la captura de datos
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
        //Repetimos el proceso con el automata2
        do {
            do {
                aux = JOptionPane.showInputDialog("¿Cuantos estados tiene su segundo AFD?");
            } while (!isNum(aux));
        } while (Integer.parseInt(aux) <= 0);
        NoEstados = Integer.parseInt(aux);

        for (int i = 0; i < NoEstados; i++) {
            aux = JOptionPane.showInputDialog(null, "¿Es final " + "q" + i + "?\n\"1\" = si" + " \n\"[Enter]\" = no");
            if (i == 0) {
                JOptionPane.showMessageDialog(null, "El estado q0 siempre es inicial");
                if (aux.equals("1")) {
                    EstadosA2.add(new Estado("q" + i, true, true));
                } else {
                    EstadosA2.add(new Estado("q" + i, true, false));
                }
            } else {
                if (aux.equals("1")) {
                    EstadosA2.add(new Estado("q" + i, false, true));
                } else {
                    EstadosA2.add(new Estado("q" + i, false, false));
                }
            }
        }
        automata2 = new Automata(alfabeto, EstadosA2);

        for (int i = 0; i < EstadosA2.size(); i++) {
            for (int j = 0; j < alfabeto.length; j++) {
                do {
                    aux = JOptionPane.showInputDialog("q" + i + " Leyendo un " + alfabeto[j] + " se mueve hacia q");
                } while (!isNum(aux));
                hash2.put("q" + i + "," + alfabeto[j], "q" + aux);
            }
        }
    }

    public void calculos() {
        //Generamos la tabla que nos servira para comparar y ver visualmente el resultado del teorema, de 100 filas y n+1 columnas
        tabla = new String[100][alfabeto.length + 1]; 

        //Definimos como una constante el encabezado de la primera columna de la tabla
        tabla[0][0] = "M1  M2";
        //Por cada simbolo del alfabeto creamos una columna para observar la transicion el el teorema de moore
        for (int i = 0; i < alfabeto.length; i++) {
            tabla[0][i + 1] = alfabeto[i];
        }
        //Ya que definimos que los estados iniciales son q0 y q0 ingresamos los primeros valores de la columna 1 del teorema de moore
        arrayM1M2.add("q0,q0");
        //Por cada una de las filas de la tabla de moore llamamos al metodo getTabla (Utilizamos una columna auxiliar para verificacion)
        for (int i = 0; i < arrayM1M2.size(); i++) { 
         row++;
            getTabla(arrayM1M2.get(i), i);
        }
        compEstados();
        resultados();

    }

    public void getTabla(String m1m2, int indiceRenglon) {

        //Por cada letra del alfabeto separamos y tomamos la parte izquierda y la parte derecha de la primera columna de la tabla
        for (int i = 0; i < alfabeto.length; i++) {

            String valorCeldaIzq = ((m1m2).split(",")[0] + ","); 
            String valorCeldaDer = ((m1m2).split(",")[1] + ","); 
            String valCeldT = valorCeldaIzq + valorCeldaDer;
            //ingresamos los valores en la tabla que estamos creando, en este caso creamos valores m1 m2 en la tabla
            tabla[indiceRenglon + 1][0] = valCeldT;

            //Por cada letra del alfabeto tomamos el siguiente estado al cual se mueve el automata
            for (int j = 0; j < alfabeto.length; j++) {
                String valCelda = hash1.get(valorCeldaIzq + alfabeto[j]);
                valCelda += "," + hash2.get(valorCeldaDer + alfabeto[j]);
                tabla[indiceRenglon + 1][j + 1] = valCelda;

                //Por cada fila de la tabla verificamos si la columna 1 auxiliar tiene los valores de la columna 1 de la tabla
                for (int k = 0; k < row; k++) {
                    if (!arrayM1M2.contains(String.valueOf(tabla[k + 1][j + 1])) && tabla[k + 1][j + 1] != null) {
                        arrayM1M2.add(String.valueOf(tabla[k + 1][j + 1]));
                    }
                }
            }
        }
    }

    public void compEstados() {
        int indice1, indice2;
        ArrayList<Estado> auxComparar1 = new ArrayList<>();
        ArrayList<Estado> auxComparar2 = new ArrayList<>();

        //Por cada par de estados en la columna auxiliar los separaremos, juntaremos y compararemos
        for (int i = 0; i < arrayM1M2.size(); i++) {
            //Separamos los estados de la columna 1 auxiliar
            String valorIzq = arrayM1M2.get(i).split(",")[0];
            String[] valorIzqa = valorIzq.split("q");
            String valorDer = arrayM1M2.get(i).split(",")[1];
            String[] valorDerc = valorDer.split("q");
            int track = Integer.parseInt(valorIzqa[1]);
            int track2 = Integer.parseInt(valorDerc[1]);
             auxComparar1.add(EstadosA1.get(track));
             auxComparar2.add(EstadosA2.get(track2));
            
             //Se comparan
            for (int k = 0; k < auxComparar1.size(); k++) {

                if (auxComparar1.get(k).isFinal() && auxComparar2.get(k).isFinal()
                        || !(auxComparar1.get(k).isFinal()) && !(auxComparar2.get(k).isFinal())) {
                } else {
                    JOptionPane.showMessageDialog(null, "No Son Compatibles");
                    return;
                }
            }

        }
        JOptionPane.showMessageDialog(null, "Son Compatibles");
            
    }

    void resultados() {
        //Imprimimos la tabla
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j <= alfabeto.length; j++) {
                System.out.print("   "+tabla[i][j]+"    ");
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        Moore a = new Moore();
        a.datos();
        a.calculos();

    }
}
