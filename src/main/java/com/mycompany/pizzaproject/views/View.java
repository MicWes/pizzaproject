/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.views;

import com.mycompany.pizzaproject.controllers.Controller;
import com.mycompany.pizzaproject.models.Model;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mathe
 * @param <T>
 */
public abstract class View<T extends Model, V extends Controller, W extends TableModel> extends javax.swing.JFrame {
    
    public T element;
    public W model;
    public int linhaClicadaParaAtualizacao = -1;
        
    public abstract void setController(V controller);

    public abstract void initView();
    
    
    public void showInfo(String text) {
        JOptionPane.showMessageDialog(null,text + "\n", "Erro", JOptionPane.ERROR_MESSAGE);
    };
    
    public void showError(String text) {
        JOptionPane.showMessageDialog(null,text + "\n", "Informação", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public abstract void list(List<T> models);
    public abstract T getForm();
    public abstract T getForm(Boolean update);
    public abstract void setForm(T model);
    public abstract T getModel();
    public abstract List<T> getModels();
    public abstract void updateModel(T model);
    public abstract void insertModel(T model);
    public abstract void deleteModel(List<T> model);
    
}
