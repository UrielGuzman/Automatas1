package Minimizacion;

import java.util.ArrayList;


public class Automata {
    
    String[] alfabeto;
    String k;
    ArrayList<Estado> arrayEstados;

    public Automata(String[] alfabeto, ArrayList<Estado> arrayEstados) {
        this.alfabeto = alfabeto;
        this.arrayEstados = arrayEstados;
    }

    public void setAlfabeto(String[] alfabeto) {
        this.alfabeto = alfabeto;
    }

    public void setK(String k) {
        this.k = k;
    }
    
    public void setArrayEstados(ArrayList<Estado> arrayEstados) {
        this.arrayEstados = arrayEstados;
    }
    
    
    public String[] getAlfabeto() {return alfabeto;}
     
    public String getK() {return k;}

    public ArrayList<Estado> getArrayEstados() {return arrayEstados;}

}
