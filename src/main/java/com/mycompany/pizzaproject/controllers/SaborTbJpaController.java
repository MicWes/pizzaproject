/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.models.SaborTb;
import com.mycompany.pizzaproject.dao.SaborDao;
import com.mycompany.pizzaproject.views.SaborView;
import java.util.List;

/**
 *
 * @author Michael
 */
public class SaborTbJpaController extends Controller<SaborDao, SaborView> {

    public SaborTbJpaController() {
        this.view = new SaborView();
        this.dao = new SaborDao();
        super.initController();
    }
    
    public SaborTbJpaController(SaborView view) {
        this.view = view;
        this.dao = new SaborDao();
        super.initController();
    }

    @Override
    public void list() {
        try {
            List<SaborTb> sabores = this.dao.list(true, -1, -1);
            this.view.list(sabores);
        } catch(Exception ex) {
            this.view.showError("Erro ao listar os sabores");
        }
    }

    @Override
    public void find() {
        SaborTb sabor = this.dao.find(1);
    }    

    @Override
    public void create() {
        try {
            SaborTb sabor = this.view.getForm();
            this.dao.create(sabor);
            this.view.insertModel(sabor);
        } catch (Exception ex) {
            this.view.showError("Erro ao inserir um novo sabor");
        }
    }

    @Override
    public void edit() {
       try{
            SaborTb sabor = this.view.getModel();
            if(sabor==null){
                this.view.showInfo("Selecione um contato na tabela para atualizar.");
            } else {
                this.dao.update(sabor);
                this.view.updateModel(sabor);
            }
        }catch(Exception ex){
            view.showError("Erro ao atualizar contato.");
        }
    }

    @Override
    public void destroy() {
        try{
            List<SaborTb> sabores = view.getModels();
            for(SaborTb sabor:sabores){
               this.dao.delete(sabor.getSaborId());
            }
            this.view.deleteModel(sabores);
        } catch(Exception ex){
            view.showError("Erro ao excluir contatos.");
        }
    }
}
