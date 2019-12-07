/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatas;

/**
 *
 * @author Izrux
 */
public class Estado {
    
    String nombre;
    boolean Inicial;
    boolean Final;

    public Estado(String nombre, boolean Inicial, boolean Final){
        this.nombre = nombre;
        this.Inicial = Inicial;
        this.Final = Final;
    }
    

    public void setInicial(boolean Inicial) {
        this.Inicial = Inicial;
    }
    
    public void setFinal(boolean Final) {
        this.Final = Final;
    }
    
        public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isInicial() {return Inicial;}
        
    public boolean isFinal() {return Final;}

    public String getNombre() {return nombre;}
    
}
