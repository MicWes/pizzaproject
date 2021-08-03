/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.dao;

import com.mycompany.pizzaproject.models.Model;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author mathe
 */
public abstract class Dao<T extends Model> {
    
    public EntityManager getEntityManager() {
        return EntityManagerUtil.getEntityManager();
    }
    
    public abstract List<T> list(boolean all, int maxResults, int firstResult);
    public abstract T find(Integer id);
    public abstract int getCount();
    public abstract T create(T model);
    public abstract T update(T model);
    public abstract void delete(Integer id);
}
