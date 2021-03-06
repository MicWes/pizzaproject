/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.dao.PedidoDao;
import com.mycompany.pizzaproject.dao.PizzaDao;
import com.mycompany.pizzaproject.models.Circulo;
import com.mycompany.pizzaproject.models.PedidoTb;
import com.mycompany.pizzaproject.models.PizzaTb;
import com.mycompany.pizzaproject.models.Quadrado;
import com.mycompany.pizzaproject.models.Triangulo;
import com.mycompany.pizzaproject.views.PizzaView;
import java.util.List;

public class PizzaTbJpaController extends Controller<PizzaDao, PizzaView> {
    
    private PedidoTb pedido;
    private PedidoDao pedidoDao;
    private double amount;
 
    public PizzaTbJpaController(PizzaView view) {
        this.view = view;
        this.dao = new PizzaDao();
        this.pedidoDao = new PedidoDao();
    }
    
    public PizzaTbJpaController(Boolean withouView) {
        this.dao = new PizzaDao();
        this.pedidoDao = new PedidoDao();
    }
    
    public void setPedido(PedidoTb pedido) {
        this.pedido = pedido;
    }
    
    private void updateAmount(List<PizzaTb> pizzas){
        this.amount = 0.0;
        pizzas.forEach(pizza -> {
            this.amount += pizza.getTotalPizza();
        });
        this.pedido.setTotalPedido(amount);
        this.pedidoDao.update(pedido);
    }
    
    @Override
    public void list() {
        try {
            this.pedido = this.pedidoDao.find(this.pedido.getPedidoId());
            List<PizzaTb> pizzas = (List<PizzaTb>) this.pedido.getPizzaTbCollection();
            updateAmount(pizzas);
            this.view.list(pizzas, this.pedido);
        } catch(Exception ex) {
            this.view.showError("Erro ao listar as pizzas do pedido.");
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
            list();
        } catch (Exception ex) {
            this.view.showError("Erro ao inserir uma nova pizza.");
        }
    }

    @Override
    public void edit() {
       try{
            PizzaTb pizza = this.view.getForm(true);
            pizza.setPedidoId(pedido);
            if(pizza==null){
                this.view.showInfo("Selecione um contato na tabela para atualizar.");
            } else {
                pizza.setPedidoId(this.pedido);
                this.dao.update(pizza);
                list();
            }
        }catch(Exception ex){
            System.out.print(ex);
            view.showError("Erro ao atualizar a pizza.");
        }
    }

    @Override
    public void destroy() {
        try{
            List<PizzaTb> tipos = view.getModels();
            for(PizzaTb tipo:tipos){
               this.dao.delete(tipo.getPizzaId());
            }
            list();
        } catch(Exception ex){
            view.showError("Erro ao excluir as pizza.");
        }
    }
    
    public Double calculaAreaPizza(String forma, Double tamanho){
        switch(forma){
        case("Quadrado"):
            Quadrado quadrado = new Quadrado();
            quadrado.calculaAreaQuadrado(tamanho);
            return quadrado.getArea();
        case("Tri??ngulo"):
            Triangulo triangulo = new Triangulo();
            triangulo.calculaAreaTriangulo(tamanho);
            return triangulo.getArea();
        case("C??rculo"):
            Circulo circulo = new Circulo();
            circulo.calculaAreaCirculo(tamanho);
            return circulo.getArea();
        default:
            return 0.0;
        }
    }
    
    public Double calculaDimensaoPizza(String forma, Double area){
        switch(forma){
        case("Quadrado"):
            Quadrado quadrado = new Quadrado();
            quadrado.calculaLadoQuadrado(area);
            return quadrado.getLado();
        case("Tri??ngulo"):
            Triangulo triangulo = new Triangulo();
            triangulo.calculaLadoTriangulo(area);
            return triangulo.getLado();
        case("C??rculo"):
            Circulo circulo = new Circulo();
            circulo.calculaRaioCirculo(area);
            return circulo.getRaio();
        default:
            return 0.0;
        }
    }
}
