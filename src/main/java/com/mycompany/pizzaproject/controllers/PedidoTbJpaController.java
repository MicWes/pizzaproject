/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.dao.PedidoDao;
import com.mycompany.pizzaproject.dao.SaborDao;
import com.mycompany.pizzaproject.models.PedidoTb;
import com.mycompany.pizzaproject.models.SaborTb;
import com.mycompany.pizzaproject.views.PedidoForm;
import com.mycompany.pizzaproject.views.PedidoView;
import java.util.List;


public class PedidoTbJpaController extends Controller<PedidoDao, PedidoView> {
    
    private PedidoForm form;

    public PedidoTbJpaController() {
        this.view = new PedidoView();
        this.dao = new PedidoDao();
        super.initController();
    }
    
    public PedidoTbJpaController(PedidoView view) {
        this.view = view;
        this.dao = new PedidoDao();
    }
    
    public PedidoTbJpaController(Boolean withouView) {
        this.dao = new PedidoDao();
    }

    public List<SaborTb> findSaborTbEntities(){
        SaborDao daoSabor = new SaborDao();
        List<SaborTb> sabores = daoSabor.list(true, -1, -1);
        return sabores;
    }

    @Override
    public void list() {
        try {
            List<PedidoTb> tipos = this.dao.list(true, -1, -1);
            this.view.list(tipos);
        } catch(Exception ex) {
            this.view.showError("Erro ao listar os pedidos.");
        }
    }
    
    @Override
    public void find() {
        PedidoTb tipo = this.dao.find(1);
    }    

    @Override
    public void create() {
        try {
            this.form = new PedidoForm();
        } catch (Exception ex) {
            this.view.showError("Erro ao abrir o formulário");
        }
    }
    
    public PedidoTb save(PedidoTb pedido) {
        try {
            this.dao.create(pedido);
            return pedido;
        } catch (Exception ex) {
            this.view.showError("Erro ao salvar um novo pedido");
            return null;
        }
    }

    @Override
    public void edit() {
       try{
            PedidoTb pedido = this.view.getModel();
            if (pedido == null) {
                this.view.showError("Selecione um pedido para editar");
            } else if (!pedido.isEditable()) {
                this.view.showError("Não é possível editar mais o pedido.");
            } else {
                this.form = new PedidoForm(pedido);
            }
        }catch(Exception ex){
            view.showError("Erro ao abrir o formulário.");
        }
    }
    
    public void updateStatus() {
        try {
             PedidoTb pedido = this.view.getModel();
            if (pedido == null) {
                this.view.showError("Selecione um pedido para atualizar");
            } else {
                pedido.nextStatus();
                this.dao.update(pedido);
                this.view.updateModel(pedido);
            }
        } catch (Exception ex) {
            view.showError("Não foi possível atualizar o status");
        }
    }

    @Override
    public void destroy() {
        try{
            List<PedidoTb> tipos = view.getModels();
            for(PedidoTb tipo:tipos){
               this.dao.delete(tipo.getPedidoId());
            }
            this.view.deleteModel(tipos);
        } catch(Exception ex){
            view.showError("Erro ao excluir os pedidos.");
        }
    }
    
}
