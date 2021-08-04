/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.controllers.exceptions.NonexistentEntityException;
import com.mycompany.pizzaproject.dao.EntityManagerUtil;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.pizzaproject.models.PedidoTb;
import com.mycompany.pizzaproject.models.PizzaTb;
import com.mycompany.pizzaproject.models.SaborTb;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Michael
 */
public class PizzaTbJpaController implements Serializable {

    public PizzaTbJpaController() {
    }

    public EntityManager getEntityManager() {
        return EntityManagerUtil.getEntityManager();
    }

    public void create(PizzaTb pizzaTb) {
        if (pizzaTb.getSaborTbCollection() == null) {
            pizzaTb.setSaborTbCollection(new ArrayList<SaborTb>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidoTb pedidoId = pizzaTb.getPedidoId();
            if (pedidoId != null) {
                pedidoId = em.getReference(pedidoId.getClass(), pedidoId.getPedidoId());
                pizzaTb.setPedidoId(pedidoId);
            }
            Collection<SaborTb> attachedSaborTbCollection = new ArrayList<SaborTb>();
            for (SaborTb saborTbCollectionSaborTbToAttach : pizzaTb.getSaborTbCollection()) {
                saborTbCollectionSaborTbToAttach = em.getReference(saborTbCollectionSaborTbToAttach.getClass(), saborTbCollectionSaborTbToAttach.getSaborId());
                attachedSaborTbCollection.add(saborTbCollectionSaborTbToAttach);
            }
            pizzaTb.setSaborTbCollection(attachedSaborTbCollection);
            em.persist(pizzaTb);
            if (pedidoId != null) {
                pedidoId.getPizzaTbCollection().add(pizzaTb);
                pedidoId = em.merge(pedidoId);
            }
            for (SaborTb saborTbCollectionSaborTb : pizzaTb.getSaborTbCollection()) {
                saborTbCollectionSaborTb.getPizzaTbCollection().add(pizzaTb);
                saborTbCollectionSaborTb = em.merge(saborTbCollectionSaborTb);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PizzaTb pizzaTb) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PizzaTb persistentPizzaTb = em.find(PizzaTb.class, pizzaTb.getPizzaId());
            PedidoTb pedidoIdOld = persistentPizzaTb.getPedidoId();
            PedidoTb pedidoIdNew = pizzaTb.getPedidoId();
            Collection<SaborTb> saborTbCollectionOld = persistentPizzaTb.getSaborTbCollection();
            Collection<SaborTb> saborTbCollectionNew = pizzaTb.getSaborTbCollection();
            if (pedidoIdNew != null) {
                pedidoIdNew = em.getReference(pedidoIdNew.getClass(), pedidoIdNew.getPedidoId());
                pizzaTb.setPedidoId(pedidoIdNew);
            }
            Collection<SaborTb> attachedSaborTbCollectionNew = new ArrayList<SaborTb>();
            for (SaborTb saborTbCollectionNewSaborTbToAttach : saborTbCollectionNew) {
                saborTbCollectionNewSaborTbToAttach = em.getReference(saborTbCollectionNewSaborTbToAttach.getClass(), saborTbCollectionNewSaborTbToAttach.getSaborId());
                attachedSaborTbCollectionNew.add(saborTbCollectionNewSaborTbToAttach);
            }
            saborTbCollectionNew = attachedSaborTbCollectionNew;
            pizzaTb.setSaborTbCollection(saborTbCollectionNew);
            pizzaTb = em.merge(pizzaTb);
            if (pedidoIdOld != null && !pedidoIdOld.equals(pedidoIdNew)) {
                pedidoIdOld.getPizzaTbCollection().remove(pizzaTb);
                pedidoIdOld = em.merge(pedidoIdOld);
            }
            if (pedidoIdNew != null && !pedidoIdNew.equals(pedidoIdOld)) {
                pedidoIdNew.getPizzaTbCollection().add(pizzaTb);
                pedidoIdNew = em.merge(pedidoIdNew);
            }
            for (SaborTb saborTbCollectionOldSaborTb : saborTbCollectionOld) {
                if (!saborTbCollectionNew.contains(saborTbCollectionOldSaborTb)) {
                    saborTbCollectionOldSaborTb.getPizzaTbCollection().remove(pizzaTb);
                    saborTbCollectionOldSaborTb = em.merge(saborTbCollectionOldSaborTb);
                }
            }
            for (SaborTb saborTbCollectionNewSaborTb : saborTbCollectionNew) {
                if (!saborTbCollectionOld.contains(saborTbCollectionNewSaborTb)) {
                    saborTbCollectionNewSaborTb.getPizzaTbCollection().add(pizzaTb);
                    saborTbCollectionNewSaborTb = em.merge(saborTbCollectionNewSaborTb);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pizzaTb.getPizzaId();
                if (findPizzaTb(id) == null) {
                    throw new NonexistentEntityException("The pizzaTb with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PizzaTb pizzaTb;
            try {
                pizzaTb = em.getReference(PizzaTb.class, id);
                pizzaTb.getPizzaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pizzaTb with id " + id + " no longer exists.", enfe);
            }
            PedidoTb pedidoId = pizzaTb.getPedidoId();
            if (pedidoId != null) {
                pedidoId.getPizzaTbCollection().remove(pizzaTb);
                pedidoId = em.merge(pedidoId);
            }
            Collection<SaborTb> saborTbCollection = pizzaTb.getSaborTbCollection();
            for (SaborTb saborTbCollectionSaborTb : saborTbCollection) {
                saborTbCollectionSaborTb.getPizzaTbCollection().remove(pizzaTb);
                saborTbCollectionSaborTb = em.merge(saborTbCollectionSaborTb);
            }
            em.remove(pizzaTb);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PizzaTb> findPizzaTbEntities() {
        return findPizzaTbEntities(true, -1, -1);
    }

    public List<PizzaTb> findPizzaTbEntities(int maxResults, int firstResult) {
        return findPizzaTbEntities(false, maxResults, firstResult);
    }

    private List<PizzaTb> findPizzaTbEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PizzaTb.class));
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

    public PizzaTb findPizzaTb(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PizzaTb.class, id);
        } finally {
            em.close();
        }
    }

    public int getPizzaTbCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PizzaTb> rt = cq.from(PizzaTb.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
