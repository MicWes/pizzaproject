/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.models.SaborTb;
import com.mycompany.pizzaproject.views.View;
import com.mycompany.pizzaproject.dao.Sabor;
import java.util.List;

/**
 *
 * @author Michael
 */
public class SaborTbJpaController extends Controller<SaborTb> {

    public SaborTbJpaController(View view) {
        super(view);
        this.dao = new Sabor();
    }

    @Override
    public List<SaborTb> list() {
        List<SaborTb> t = this.dao.list(true, -1, -1);
        return this.dao.list(true, -1, -1);
    }
    
    @Override
    public List<SaborTb> list(int maxResults, int firstResult) {
        return this.dao.list(false, maxResults, firstResult);
    }

    @Override
    public SaborTb find(Integer id) {
        return (SaborTb) this.dao.find(id);
    }

    @Override
    public int getCount() {
        return this.dao.getCount();
    }    
}
