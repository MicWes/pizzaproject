/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.dao.Dao;
import com.mycompany.pizzaproject.views.View;
import java.io.Serializable;


public abstract class Controller<T extends Dao, V> implements Serializable {
    
    public V view;
    public T dao;
    
    void initController(){
        View check = (View) this.view;
        if(View.class.isAssignableFrom(check.getClass())) {
            check.setController(this);
            check.initView();
        }
    }
     
    public abstract void create();
    public abstract void edit();
    public abstract void destroy();
    public abstract void list();
    public abstract void find();
    
}
