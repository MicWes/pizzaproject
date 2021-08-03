/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.models;

/**
 *
 * @author Gustavo-Kamila
 */
public class Quadrado extends Forma{
    
    private double lado;

    public Quadrado() {
    }
    
    public double getLado() {
        return lado;
    }

    public void setLado(double lado) {
        this.lado = lado;
    }

    public void calculaAreaQuadrado(double lado){
        this.setLado(lado);
        this.setArea(lado * lado);
        this.setTipo("Quadrado");
    }
    
    public void calculaLadoQuadrado(double area){
        this.setArea(area);
        this.setLado(Math.sqrt(area));
        this.setTipo("Quadrado");
    }
}
