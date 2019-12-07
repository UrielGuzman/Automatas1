
package Lexico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/* Lector Lexico Modificado
Tecnologico Nacional De Mexico
instituto tecnologico de leon
ingenieria en sistemas computacionales
Lenguajes y Automatas 1
Martes-Jueves: 10:30am - 12:10pm
Viernes: 11:20am - 12:10pm
alumnos(a): 
Edvin Uriel Guzman Ruiz
Andres Hernandez Rodríguez
Fecha: 21/11/2019
 */
public class Lexico {
    int estado = 0, aux, ascii, var = 0,inicio,fin;
    String sCadena,palabra;
    boolean termino, aux2 = true;

    public void lee() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader("src/Lexico/test.txt");
        BufferedReader bf = new BufferedReader(fr);
        while ((sCadena = bf.readLine()) != null) { // le asigna el valor a cadena de lo que tiene el buffer 
            separa(sCadena); 
        }
        
    }

    public void separa(String entrada) {
        //Recorremos cada caracter de la cadena
        
        for ( var = 0; var < entrada.length(); var++) {
        if (entrada.charAt(var)==' '){}
        else{
            if (aux2 == true) {
                reconoceInicial(entrada.charAt(var),entrada.charAt(var+1));
                aux2 = false;
                
                //Si nuestra variable auxiliar es false entonces estamos leyendo los caracteres de la palabra
            } else {
                reconoce(entrada.charAt(var));
              
            }
        }
        }
            
    }

    public void reconoceInicial(char palabra,char palabra1 ) {// 
        if (palabra == 'i' && palabra1 == 'f') {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 5;
            aux=5;
             var++;
        }else if (palabra == '>' && palabra1 == '=') {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 5;
            aux=6;
            var++;
        }else if (palabra == '<' && palabra1 == '=') {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 5;
            aux=7;
            var++;
        }   else if (palabra == '=' && palabra1 == '=') {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 5;
            aux=8;
            var++;
        }   else if (palabra == '!' && palabra1 == '=') {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 5;
            aux=9;
            var++;
        }    else if (Character.isDigit(palabra) == true) {//Si el primer caracter es numero pasaremos al siguiente estado
            estado = 1;
            aux=1;
        } else if (Character.isLetter(palabra) == true) {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 3;
            aux=3;
        } else if (isAscii(palabra) == true) {//Si el primer caracter es un caracter ascii pasaremos al siguiente estado 
            aux = 4;
            estado = 5;
            

        }
        else{}
    }

    public void reconoce(char palabra) {// 
        //De acuerdo al estado en el que estamos seguiremos recorriendo el automata
        switch (estado) {
            case 1://Si siguen entrando numeros se queda en el mismo estado
                if (Character.isDigit(palabra) == true) {
                    estado = 1;
                    aux = 1;
                //Si recibe un . entonces cambia de estado pues detecta que es un punto flotante
                } else {
                    estado4(palabra);
                }
                break;
                case 3:// si recibe una letra sigue siendo un token tipo identificador 
                if (Character.isLetter(palabra) == true) {
                    estado = 3;
                    aux = 3;
                } // si recibe un digito sigue siendo un token tipo identificador
                else if (Character.isDigit(palabra) == true) {
                    estado = 3;
                    aux = 3;
                //Con cualquier otra cosa se llama a un metodo de estado final
                } else {
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
        termino=true;
        var--;
        print();
        estado = 0;
        aux = 0;
        aux2=true;
        
    }

    //Checamos el numero ascii del caracter
    public boolean isAscii(char c) {
        ascii = c;
        return ascii >= 0 && ascii <= 255;
    }

    //De acuerdo al caso imprimimos el token o el error en la palabra
    public void print() {
        if (aux == 1 && termino == true) {
            System.out.println("Token 260 (int)");
        } else if (aux == 2 && termino == true) {
            System.out.println("Token 261 (punto flotante)");
        } else if (aux == 3 && termino == true) {
            System.out.println("Token 262 (Identificador)");
        }else if (aux == 4 && termino == true) {
            System.out.println("Token " + ascii + " (Ascii)");
        } else if (aux == 5 && termino == true) {
            System.out.println("Token 263 (Palabra reservada If)");
        }else if (aux == 6 && termino == true) {
            System.out.println("Token 264 (>=)");
        }else if (aux == 7 && termino == true) {
            System.out.println("Token 265 (<=)");
        }else if (aux == 8 && termino == true) {
            System.out.println("Token 266 (==)");
        }else if (aux == 9 && termino == true) {
            System.out.println("Token 267 (!=)");
        } else {
            System.out.println("Error lexico");
        }

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Lexico a = new Lexico();
        a.lee();
    }
}
