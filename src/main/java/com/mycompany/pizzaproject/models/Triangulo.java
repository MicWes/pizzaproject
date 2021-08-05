/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.models;

import static java.lang.Math.sqrt;

public class Triangulo extends Forma{
    
    private double lado;
    
    public Triangulo() {
    }
    
    public double getLado() {
        return lado;
    }

    public void setLado(double lado) {
        this.lado = lado;
    }   
    
    public void calculaAreaTriangulo(double lado){
       this.setLado(lado);
       Double altura=sqrt(3)/2*lado;
       this.setArea((lado * altura)/2);
       this.setTipo("Triângulo");    
    }
    
    public void calculaLadoTriangulo(double area){
        this.setArea(area);
        this.setLado(Math.sqrt(2*area));
        this.setTipo("Triângulo");
    }    
}
