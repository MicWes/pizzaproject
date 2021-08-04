/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.dao.PizzaDao;
import com.mycompany.pizzaproject.models.PedidoTb;
import com.mycompany.pizzaproject.models.PizzaTb;
import com.mycompany.pizzaproject.views.PizzaView;
import java.util.List;

/**
 *
 * @author Michael
 */
public class PizzaTbJpaController extends Controller<PizzaDao, PizzaView> {
    
    private PedidoTb pedido;
 
    public PizzaTbJpaController(PizzaView view) {
        this.view = view;
        this.dao = new PizzaDao();
    }
    
    public PizzaTbJpaController(Boolean withouView) {
        this.dao = new PizzaDao();
    }
    
    public void setPedido(PedidoTb pedido) {
        this.pedido = pedido;
    }

    @Override
    public void list() {
        try {
            List<PizzaTb> pizzas = (List<PizzaTb>) this.pedido.getPizzaTbCollection();
            this.view.list(pizzas);
        } catch(Exception ex) {
            this.view.showError("Erro ao listar os tipos.");
        }
    }

    @Override
    public void find() {
//        PizzaTb tipo = this.dao.find(1);
    }    

    @Override
    public void create() {
        try {
            PizzaTb pizza = this.view.getForm();
            pizza.setPedidoId(this.pedido);
            this.dao.create(pizza);
            this.view.insertModel(pizza);
        } catch (Exception ex) {
            this.view.showError("Erro ao inserir um novo tipo.");
        }
    }

    @Override
    public void edit() {
       try{
            PizzaTb pizza = this.view.getForm(true);
            if(pizza==null){
                this.view.showInfo("Selecione um contato na tabela para atualizar.");
            } else {
                pizza.setPedidoId(this.pedido);
                this.dao.update(pizza);
                this.view.updateModel(pizza);
            }
        }catch(Exception ex){
            view.showError("Erro ao atualizar o tipo.");
        }
    }

    @Override
    public void destroy() {
        try{
            List<PizzaTb> tipos = view.getModels();
            for(PizzaTb tipo:tipos){
               this.dao.delete(tipo.getPizzaId());
            }
            this.view.deleteModel(tipos);
        } catch(Exception ex){
            view.showError("Erro ao excluir os tipos.");
        }
    }
    
}
