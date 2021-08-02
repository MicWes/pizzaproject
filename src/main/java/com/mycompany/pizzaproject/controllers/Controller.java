/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.controllers.exceptions.NonexistentEntityException;
import com.mycompany.pizzaproject.dao.Dao;
import com.mycompany.pizzaproject.models.Model;
import com.mycompany.pizzaproject.views.View;
import java.io.Serializable;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author mathe
 */
public abstract class Controller<T extends Model> implements Serializable {
    
    public View view;
    public Dao dao;
    
    public Controller(View view) {
        this.view = view;
        this.initController();
    }
    
    private void initController(){
        this.view.setController(this);
        this.view.initView();
    }
     
//    public abstract Model create(Model model);
//    public abstract Model edit(Model model) throws NonexistentEntityException, Exception;
//    public abstract void destroy(Integer id) throws NonexistentEntityException;
    public abstract List<T> list();
    public abstract List<T> list(int maxResults, int firstResult);
    public abstract T find(Integer id) throws NonexistentEntityException;
    public abstract int getCount();
    
}
