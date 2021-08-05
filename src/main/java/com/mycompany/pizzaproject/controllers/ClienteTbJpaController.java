/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.dao.ClienteDao;
import static com.mycompany.pizzaproject.dao.EntityManagerUtil.getEntityManager;
import com.mycompany.pizzaproject.models.ClienteTb;
import com.mycompany.pizzaproject.views.ClienteView;
import java.util.List;
import javax.persistence.EntityManager;

public class ClienteTbJpaController extends Controller<ClienteDao, ClienteView> {

    public ClienteTbJpaController() {
        this.view = new ClienteView();
        this.dao = new ClienteDao();
        super.initController();
    }

    public ClienteTbJpaController(ClienteView view) {
        this.view = view;
        this.dao = new ClienteDao();
        super.initController();
    }
    
    public ClienteTbJpaController(Boolean withoutView) {
        this.dao = new ClienteDao();
    }

    @Override
    public void create() {
        try {
            ClienteTb cliente = this.view.getForm();
            this.dao.create(cliente);
            this.view.insertModel(cliente);
        } catch (Exception ex) {
            this.view.showError("Erro ao inserir um novo cliente.");
        }
    }

    @Override
    public void edit() {
        try{
            ClienteTb cliente = this.view.getForm(true);
            if(cliente==null){
                this.view.showInfo("Selecione um cliente na tabela para atualizar.");
            } else {
                this.dao.update(cliente);
                this.view.updateModel(cliente);
            }
        }catch(Exception ex){
            view.showError("Erro ao atualizar cliente.");
        }
    }

    @Override
    public void destroy() {
        try{
            List<ClienteTb> clientes = view.getModels();
            for(ClienteTb cliente:clientes){
               this.dao.delete(cliente.getClienteId());
            }
            this.view.deleteModel(clientes);
        } catch(Exception ex){
            view.showError("Erro ao excluir os clientes.");
        }
    }

    @Override
    public void list() {
        try {
            List<ClienteTb> clientes = this.dao.list(true, -1, -1);
            this.view.list(clientes);
        } catch(Exception ex) {
            this.view.showError("Erro ao listar os clientes.");
        }
    }

    @Override
    public void find() {
        ClienteTb cliente = this.dao.find(1);
    }  
    
    public List<ClienteTb> findBySobrenome(String sobrenome) {
        try {
            EntityManager em = getEntityManager();
            List<ClienteTb> clientes = (List<ClienteTb>) em.createQuery("SELECT c FROM ClienteTb c WHERE c.sobrenome LIKE ?1").setParameter(1, sobrenome + '%').getResultList();
            return clientes;
        } catch(Exception ex) {
            this.view.showError("Erro ao listar os clientes.");
            return null;
        }
    }
    
    public ClienteTb findByTelefone(String telefone) {
        EntityManager em = getEntityManager();
        try {
            ClienteTb cliente = (ClienteTb)em.createQuery("SELECT c FROM ClienteTb c WHERE c.telefone = ?1").setParameter(1, telefone).getSingleResult();
            return cliente;
        }
        finally {
            em.close();
        }
    }
}
