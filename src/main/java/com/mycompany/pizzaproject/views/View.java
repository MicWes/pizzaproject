/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.views;

import com.mycompany.pizzaproject.controllers.Controller;
import java.awt.event.MouseAdapter;

/**
 *
 * @author mathe
 */
public abstract class View extends javax.swing.JFrame {
    
    public abstract void setController(Controller controller);

    public abstract void initView();
    
}
