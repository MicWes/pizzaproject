/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.controllers.exceptions.IllegalOrphanException;
import com.mycompany.pizzaproject.controllers.exceptions.NonexistentEntityException;
import com.mycompany.pizzaproject.dao.EntityManagerUtil;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.pizzaproject.models.ClienteTb;
import com.mycompany.pizzaproject.models.PedidoTb;
import com.mycompany.pizzaproject.models.PizzaTb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;


public class PedidoTbJpaController implements Serializable {

    public PedidoTbJpaController() {
    }

    public EntityManager getEntityManager() {
        return EntityManagerUtil.getEntityManager();
    }
    
    public void create(PedidoTb pedidoTb) {
        if (pedidoTb.getPizzaTbCollection() == null) {
            pedidoTb.setPizzaTbCollection(new ArrayList<PizzaTb>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteTb clienteId = pedidoTb.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getClienteId());
                pedidoTb.setClienteId(clienteId);
            }
            Collection<PizzaTb> attachedPizzaTbCollection = new ArrayList<PizzaTb>();
            for (PizzaTb pizzaTbCollectionPizzaTbToAttach : pedidoTb.getPizzaTbCollection()) {
                pizzaTbCollectionPizzaTbToAttach = em.getReference(pizzaTbCollectionPizzaTbToAttach.getClass(), pizzaTbCollectionPizzaTbToAttach.getPizzaId());
                attachedPizzaTbCollection.add(pizzaTbCollectionPizzaTbToAttach);
            }
            pedidoTb.setPizzaTbCollection(attachedPizzaTbCollection);
            em.persist(pedidoTb);
            if (clienteId != null) {
                clienteId.getPedidoTbCollection().add(pedidoTb);
                clienteId = em.merge(clienteId);
            }
            for (PizzaTb pizzaTbCollectionPizzaTb : pedidoTb.getPizzaTbCollection()) {
                PedidoTb oldPedidoIdOfPizzaTbCollectionPizzaTb = pizzaTbCollectionPizzaTb.getPedidoId();
                pizzaTbCollectionPizzaTb.setPedidoId(pedidoTb);
                pizzaTbCollectionPizzaTb = em.merge(pizzaTbCollectionPizzaTb);
                if (oldPedidoIdOfPizzaTbCollectionPizzaTb != null) {
                    oldPedidoIdOfPizzaTbCollectionPizzaTb.getPizzaTbCollection().remove(pizzaTbCollectionPizzaTb);
                    oldPedidoIdOfPizzaTbCollectionPizzaTb = em.merge(oldPedidoIdOfPizzaTbCollectionPizzaTb);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PedidoTb pedidoTb) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoTb persistentPedidoTb = em.find(PedidoTb.class, pedidoTb.getPedidoId());
            ClienteTb clienteIdOld = persistentPedidoTb.getClienteId();
            ClienteTb clienteIdNew = pedidoTb.getClienteId();
            Collection<PizzaTb> pizzaTbCollectionOld = persistentPedidoTb.getPizzaTbCollection();
            Collection<PizzaTb> pizzaTbCollectionNew = pedidoTb.getPizzaTbCollection();
            List<String> illegalOrphanMessages = null;
            for (PizzaTb pizzaTbCollectionOldPizzaTb : pizzaTbCollectionOld) {
                if (!pizzaTbCollectionNew.contains(pizzaTbCollectionOldPizzaTb)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PizzaTb " + pizzaTbCollectionOldPizzaTb + " since its pedidoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getClienteId());
                pedidoTb.setClienteId(clienteIdNew);
            }
            Collection<PizzaTb> attachedPizzaTbCollectionNew = new ArrayList<PizzaTb>();
            for (PizzaTb pizzaTbCollectionNewPizzaTbToAttach : pizzaTbCollectionNew) {
                pizzaTbCollectionNewPizzaTbToAttach = em.getReference(pizzaTbCollectionNewPizzaTbToAttach.getClass(), pizzaTbCollectionNewPizzaTbToAttach.getPizzaId());
                attachedPizzaTbCollectionNew.add(pizzaTbCollectionNewPizzaTbToAttach);
            }
            pizzaTbCollectionNew = attachedPizzaTbCollectionNew;
            pedidoTb.setPizzaTbCollection(pizzaTbCollectionNew);
            pedidoTb = em.merge(pedidoTb);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getPedidoTbCollection().remove(pedidoTb);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getPedidoTbCollection().add(pedidoTb);
                clienteIdNew = em.merge(clienteIdNew);
            }
            for (PizzaTb pizzaTbCollectionNewPizzaTb : pizzaTbCollectionNew) {
                if (!pizzaTbCollectionOld.contains(pizzaTbCollectionNewPizzaTb)) {
                    PedidoTb oldPedidoIdOfPizzaTbCollectionNewPizzaTb = pizzaTbCollectionNewPizzaTb.getPedidoId();
                    pizzaTbCollectionNewPizzaTb.setPedidoId(pedidoTb);
                    pizzaTbCollectionNewPizzaTb = em.merge(pizzaTbCollectionNewPizzaTb);
                    if (oldPedidoIdOfPizzaTbCollectionNewPizzaTb != null && !oldPedidoIdOfPizzaTbCollectionNewPizzaTb.equals(pedidoTb)) {
                        oldPedidoIdOfPizzaTbCollectionNewPizzaTb.getPizzaTbCollection().remove(pizzaTbCollectionNewPizzaTb);
                        oldPedidoIdOfPizzaTbCollectionNewPizzaTb = em.merge(oldPedidoIdOfPizzaTbCollectionNewPizzaTb);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedidoTb.getPedidoId();
                if (findPedidoTb(id) == null) {
                    throw new NonexistentEntityException("The pedidoTb with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoTb pedidoTb;
            try {
                pedidoTb = em.getReference(PedidoTb.class, id);
                pedidoTb.getPedidoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedidoTb with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PizzaTb> pizzaTbCollectionOrphanCheck = pedidoTb.getPizzaTbCollection();
            for (PizzaTb pizzaTbCollectionOrphanCheckPizzaTb : pizzaTbCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PedidoTb (" + pedidoTb + ") cannot be destroyed since the PizzaTb " + pizzaTbCollectionOrphanCheckPizzaTb + " in its pizzaTbCollection field has a non-nullable pedidoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ClienteTb clienteId = pedidoTb.getClienteId();
            if (clienteId != null) {
                clienteId.getPedidoTbCollection().remove(pedidoTb);
                clienteId = em.merge(clienteId);
            }
            em.remove(pedidoTb);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PedidoTb> findPedidoTbEntities() {
        return findPedidoTbEntities(true, -1, -1);
    }

    public List<PedidoTb> findPedidoTbEntities(int maxResults, int firstResult) {
        return findPedidoTbEntities(false, maxResults, firstResult);
    }

    private List<PedidoTb> findPedidoTbEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PedidoTb.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PedidoTb findPedidoTb(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PedidoTb.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoTbCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PedidoTb> rt = cq.from(PedidoTb.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
