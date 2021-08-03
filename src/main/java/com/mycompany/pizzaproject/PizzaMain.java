/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject;

import com.mycompany.pizzaproject.controllers.TipoTbJpaController;
import com.mycompany.pizzaproject.views.MenuView;

/**
 *
 * @author Michael
 */
public class PizzaMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new MenuView().setVisible(true);
        //new TipoTbJpaController();
    }
    
}
