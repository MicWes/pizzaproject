/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.models;

public class Circulo extends Forma{
    
    private Double raio;
    
    public Circulo(){}

    public Double getRaio() {
        return raio;
    }

    public void setRaio(Double raio) {
        this.raio = raio;
    }
    
    public void calculaAreaCirculo(double raio){
        this.setRaio(raio);
        this.setArea((3.14 * (Math.pow(raio, 2))));
        this.setTipo("Círculo");
    }
    
    public void calculaRaioCirculo(double area){
         this.setArea(area);
         this.setRaio(Math.sqrt(area/3.14));
         this.setTipo("Círculo");
    }    
}
