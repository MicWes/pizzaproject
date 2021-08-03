/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.models.TipoTb;
import com.mycompany.pizzaproject.dao.TipoDao;
import com.mycompany.pizzaproject.views.TipoView;
import java.util.List;

/**
 *
 * @author Michael
 */
public class TipoTbJpaController extends Controller<TipoDao, TipoView> {

    public TipoTbJpaController() {
        this.view = new TipoView();
        this.dao = new TipoDao();
        super.initController();
    }
    
    public TipoTbJpaController(TipoView view) {
        this.view = view;
        this.dao = new TipoDao();
        super.initController();
    }

    @Override
    public void list() {
        try {
            List<TipoTb> tipos = this.dao.list(true, -1, -1);
            this.view.list(tipos);
        } catch(Exception ex) {
            this.view.showError("Erro ao listar os tipos");
        }
    }

    @Override
    public void find() {
        TipoTb tipo = this.dao.find(1);
    }    

    @Override
    public void create() {
        try {
            TipoTb tipo = this.view.getForm();
            this.dao.create(tipo);
            this.view.insertModel(tipo);
        } catch (Exception ex) {
            this.view.showError("Erro ao inserir um novo tipo");
        }
    }

    @Override
    public void edit() {
       try{
            TipoTb tipo = this.view.getForm(true);
            if(tipo==null){
                this.view.showInfo("Selecione um contato na tabela para atualizar.");
            } else {
                this.dao.update(tipo);
                this.view.updateModel(tipo);
            }
        }catch(Exception ex){
            view.showError("Erro ao atualizar o tipo.");
        }
    }

    @Override
    public void destroy() {
        try{
            List<TipoTb> tipos = view.getModels();
            for(TipoTb tipo:tipos){
               this.dao.delete(tipo.getTipoId());
            }
            this.view.deleteModel(tipos);
        } catch(Exception ex){
            view.showError("Erro ao excluir os tipos.");
        }
    }
}