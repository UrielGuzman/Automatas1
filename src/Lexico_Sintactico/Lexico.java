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
public class Lexico {
    int estado = 0, aux, ascii, var = 0,inicio,fin;
    String sCadena,auxc,all="";
    boolean termino, aux2 = true;

    public void lee() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader("src/Lexico_Sintactico/text.txt");
        BufferedReader bf = new BufferedReader(fr);
        while ((sCadena = bf.readLine()) != null) { // le asigna el valor a cadena de lo que tiene el buffer 
            all=all+sCadena; 
        }
         all=all+" ";
    }
    
    public void tokens() throws IOException{
        auxc="";
        termino=true;
        separa(all);
        
    }
    
    public void separa(String entrada) throws IOException {
        //Recorremos cada caracter de la cadena
        
        while(termino==true) {
        
            if (aux2 == true) {
                
                reconoceInicial(entrada.charAt(var),entrada.charAt(var+1));
                aux2 = false;
                
                //Si nuestra variable auxiliar es false entonces estamos leyendo los caracteres de la palabra
            } else {
                
                reconoce(entrada.charAt(var));
              
            }
        
        var++;
        }
                 
    }

    public void reconoceInicial(char palabra,char palabra1 ) {// 
        if (palabra == 'i' && palabra1 == 'f') {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 5;
            aux=5;
            auxc=auxc+palabra+palabra1;
             var++;
        }else if (palabra == '>' && palabra1 == '=') {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 5;
            aux=6;
            auxc=auxc+palabra+palabra1;
            var++;
        }else if (palabra == '<' && palabra1 == '=') {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 5;
            aux=7;
            auxc=auxc+palabra+palabra1;
            var++;
        }   else if (palabra == '=' && palabra1 == '=') {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 5;
            aux=8;
            auxc=auxc+palabra+palabra1;
            var++;
        }   else if (palabra == '!' && palabra1 == '=') {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 5;
            aux=9;
            auxc=auxc+palabra+palabra1;
            var++;
        }    else if (Character.isDigit(palabra) == true) {//Si el primer caracter es numero pasaremos al siguiente estado
            estado = 1;
            aux=1;
            auxc=auxc+palabra;
        } else if (Character.isLetter(palabra) == true) {//Si el primer caracter es una letra del alfabeto pasaremos al siguiente estado
            estado = 3;
            aux=3;
            auxc=auxc+palabra;
        } else if (isAscii(palabra) == true) {//Si el primer caracter es un caracter ascii pasaremos al siguiente estado 
            aux = 4;
            estado = 5;
            auxc=auxc+palabra;
            

        }
        else{}
    }

    public void reconoce(char palabra) throws IOException {// 
        //De acuerdo al estado en el que estamos seguiremos recorriendo el automata
        switch (estado) {
            case 1://Si siguen entrando numeros se queda en el mismo estado
                if (Character.isDigit(palabra) == true) {
                    estado = 1;
                    aux = 1;
                    auxc=auxc+palabra;
                //Si recibe un . entonces cambia de estado pues detecta que es un punto flotante
                } else {
                    estado4(palabra);
                }
                break;
                case 3:// si recibe una letra sigue siendo un token tipo identificador 
                if (Character.isLetter(palabra) == true) {
                    estado = 3;
                    aux = 3;
                    auxc=auxc+palabra;
                } // si recibe un digito sigue siendo un token tipo identificador
                else if (Character.isDigit(palabra) == true) {
                    estado = 3;
                    aux = 3;
                    auxc=auxc+palabra;
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
    public void estado4(char x) throws IOException {
        
        var--;
        print();
        
        estado = 0;
        aux = 0;
        aux2=true;
        termino=false;
        
    }

    //Checamos el numero ascii del caracter
    public boolean isAscii(char c) {
        ascii = c;
        return ascii >= 0 && ascii <= 255;
    }

    //De acuerdo al caso imprimimos el token o el error en la palabra
    public void print() throws IOException {
        switch (aux) {
            case 1:
                auxc="intliteral";
                break;
            case 3:
                auxc="id";
                 break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            default:
                System.out.println("Error lexico");
                break;
        }
        
    }

}