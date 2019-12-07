/* Lector Lexico
Tecnologico Nacional De Mexico
instituto tecnologico de leon
ingenieria en sistemas computacionales
Lenguajes y Automatas 1
Martes-Jueves: 10:30am - 12:10pm
Viernes: 11:20am - 12:10pm
alumnos(a): 
Edvin Uriel Guzman Ruiz
Andres Hernandez Rodríguez
Fecha: 5/11/2019
 */
package Automata;

import java.io.*;

public class Lector {

    int estado = 0, aux, ascii, var = 0;
    String sCadena, caux = "";
    boolean termino, aux2 = true;

    public void lee() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader("src/Automata/test.txt");
        BufferedReader bf = new BufferedReader(fr);
        while ((sCadena = bf.readLine()) != null) { // le asigna el valor a cadena de lo que tiene el buffer 
            separa(sCadena);// envia al metodo split el valor de la cadena 
        }
    }

    public void separa(String entrada) {
        //Recorremos cada caracter de la cadena
        for (int i = 0; i < entrada.length(); i++) {
            //Si nuestra variable auxiliar es true entonces estamos leyendo una nueva palabra
            if (aux2 == true) {
                caux = caux + entrada.charAt(var);
                reconoceInicial(entrada.charAt(var));
                aux2 = false;
                //Si nuestra variable auxiliar es false entonces estamos leyendo los caracteres de la palabra
            } else {
                caux = caux + entrada.charAt(i);
                reconoce(entrada.charAt(i));
                var++;
            }
        }
    }

    public void reconoceInicial(char palabra) {// 

        if (Character.isDigit(palabra) == true) {//Si el primer caracter es numero pasaremos al siguiente estado
            estado = 1;
        } else if (Character.isLetter(palabra) == true) {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 2;
        } else if (isAscii(palabra) == true) {//Si el primer caracter es un caracter ascii pasaremos al siguiente estado 
            aux = 4;
            estado = 5;

        }
    }

    public void reconoce(char palabra) {// 
        //De acuerdo al estado en el que estamos seguiremos recorriendo el automata
        switch (estado) {
            case 1://Si siguen entrando numeros se queda en el mismo estado
                if (Character.isDigit(palabra) == true) {
                    estado = 1;
                    aux = 1;
                //Si recibe un . entonces cambia de estado pues detecta que es un punto flotante
                } else if (palabra == '.') {
                    estado = 3;
                //Con cualquier otra cosa se llama a un metodo de estado final
                } else {
                    estado4(palabra);
                }
                break;
            case 2:// si recibe una letra sigue siendo un token tipo identificador 
                if (Character.isLetter(palabra) == true) {
                    estado = 2;
                    aux = 2;
                } // si recibe un digito sigue siendo un token tipo identificador
                else if (Character.isDigit(palabra) == true) {
                    estado = 2;
                    aux = 2;
                //Con cualquier otra cosa se llama a un metodo de estado final
                } else {
                    estado4(palabra);
                }
                break;
            case 3://Siendo un punto flotante solamente con mas numeros sigue siendo un token de punto flotante
                if (Character.isDigit(palabra) == true) {
                    estado = 3;
                    aux = 3;
                } else {
                //Con cualquier otra cosa se llama a un metodo de estado final
                    estado4(palabra);
                }
                break;
            case 5://Puesto que solo puede tener 1 codigo ascii si es uno se va al estado final
                estado4(palabra);
                break;
        }

    }

    //Este metodo recibe un character y verifica que sea el character de cierre, en este caso ;
    public void estado4(char x) {
        termino = x == ';';
        //Regresa los valores a 0 para pasar a la siguiente palabra e imprimimos la palabra y su token
        print();
        estado = 0;
        aux = 0;
        caux = "";
        var++;
        aux2 = true;

    }

    //Checamos el numero ascii del numero
    public boolean isAscii(char c) {
        ascii = c;
        return ascii >= 0 && ascii <= 255;
    }

    //De acuerdo al caso imprimimos el token o el error en la palabra
    public void print() {
        System.out.println(caux);
        if (aux == 1 && termino == true) {
            System.out.println("Token 260 (int)");
        } else if (aux == 2 && termino == true) {
            System.out.println("Token 262 (letras)");
        } else if (aux == 3 && termino == true) {
            System.out.println("Token 261 (punto flotante)");
        } else if (aux == 4 && termino == true) {
            System.out.println("Token " + ascii + " (Ascii)");
        } else if (aux == 1 | aux == 2 | aux == 3 | aux == 4) {
            System.out.println("Falta termino de sentencia");
        } else {
            System.out.println("Error lexico");
        }

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Lector a = new Lector();
        a.lee();
    }
}
