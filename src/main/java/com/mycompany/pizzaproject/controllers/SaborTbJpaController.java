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
import com.mycompany.pizzaproject.models.TipoTb;
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
public class SaborTbJpaController implements Serializable {

    public SaborTbJpaController() {
    }

    public EntityManager getEntityManager() {
        return EntityManagerUtil.getEntityManager();
    }

    public void create(SaborTb saborTb) {
        if (saborTb.getPizzaTbCollection() == null) {
            saborTb.setPizzaTbCollection(new ArrayList<PizzaTb>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTb tipoId = saborTb.getTipoId();
            if (tipoId != null) {
                tipoId = em.getReference(tipoId.getClass(), tipoId.getTipoId());
                saborTb.setTipoId(tipoId);
            }
            Collection<PizzaTb> attachedPizzaTbCollection = new ArrayList<PizzaTb>();
            for (PizzaTb pizzaTbCollectionPizzaTbToAttach : saborTb.getPizzaTbCollection()) {
                pizzaTbCollectionPizzaTbToAttach = em.getReference(pizzaTbCollectionPizzaTbToAttach.getClass(), pizzaTbCollectionPizzaTbToAttach.getPizzaId());
                attachedPizzaTbCollection.add(pizzaTbCollectionPizzaTbToAttach);
            }
            saborTb.setPizzaTbCollection(attachedPizzaTbCollection);
            em.persist(saborTb);
            if (tipoId != null) {
                tipoId.getSaborTbCollection().add(saborTb);
                tipoId = em.merge(tipoId);
            }
            for (PizzaTb pizzaTbCollectionPizzaTb : saborTb.getPizzaTbCollection()) {
                pizzaTbCollectionPizzaTb.getSaborTbCollection().add(saborTb);
                pizzaTbCollectionPizzaTb = em.merge(pizzaTbCollectionPizzaTb);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaborTb saborTb) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaborTb persistentSaborTb = em.find(SaborTb.class, saborTb.getSaborId());
            TipoTb tipoIdOld = persistentSaborTb.getTipoId();
            TipoTb tipoIdNew = saborTb.getTipoId();
            Collection<PizzaTb> pizzaTbCollectionOld = persistentSaborTb.getPizzaTbCollection();
            Collection<PizzaTb> pizzaTbCollectionNew = saborTb.getPizzaTbCollection();
            if (tipoIdNew != null) {
                tipoIdNew = em.getReference(tipoIdNew.getClass(), tipoIdNew.getTipoId());
                saborTb.setTipoId(tipoIdNew);
            }
            Collection<PizzaTb> attachedPizzaTbCollectionNew = new ArrayList<PizzaTb>();
            for (PizzaTb pizzaTbCollectionNewPizzaTbToAttach : pizzaTbCollectionNew) {
                pizzaTbCollectionNewPizzaTbToAttach = em.getReference(pizzaTbCollectionNewPizzaTbToAttach.getClass(), pizzaTbCollectionNewPizzaTbToAttach.getPizzaId());
                attachedPizzaTbCollectionNew.add(pizzaTbCollectionNewPizzaTbToAttach);
            }
            pizzaTbCollectionNew = attachedPizzaTbCollectionNew;
            saborTb.setPizzaTbCollection(pizzaTbCollectionNew);
            saborTb = em.merge(saborTb);
            if (tipoIdOld != null && !tipoIdOld.equals(tipoIdNew)) {
                tipoIdOld.getSaborTbCollection().remove(saborTb);
                tipoIdOld = em.merge(tipoIdOld);
            }
            if (tipoIdNew != null && !tipoIdNew.equals(tipoIdOld)) {
                tipoIdNew.getSaborTbCollection().add(saborTb);
                tipoIdNew = em.merge(tipoIdNew);
            }
            for (PizzaTb pizzaTbCollectionOldPizzaTb : pizzaTbCollectionOld) {
                if (!pizzaTbCollectionNew.contains(pizzaTbCollectionOldPizzaTb)) {
                    pizzaTbCollectionOldPizzaTb.getSaborTbCollection().remove(saborTb);
                    pizzaTbCollectionOldPizzaTb = em.merge(pizzaTbCollectionOldPizzaTb);
                }
            }
            for (PizzaTb pizzaTbCollectionNewPizzaTb : pizzaTbCollectionNew) {
                if (!pizzaTbCollectionOld.contains(pizzaTbCollectionNewPizzaTb)) {
                    pizzaTbCollectionNewPizzaTb.getSaborTbCollection().add(saborTb);
                    pizzaTbCollectionNewPizzaTb = em.merge(pizzaTbCollectionNewPizzaTb);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saborTb.getSaborId();
                if (findSaborTb(id) == null) {
                    throw new NonexistentEntityException("The saborTb with id " + id + " no longer exists.");
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
            SaborTb saborTb;
            try {
                saborTb = em.getReference(SaborTb.class, id);
                saborTb.getSaborId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saborTb with id " + id + " no longer exists.", enfe);
            }
            TipoTb tipoId = saborTb.getTipoId();
            if (tipoId != null) {
                tipoId.getSaborTbCollection().remove(saborTb);
                tipoId = em.merge(tipoId);
            }
            Collection<PizzaTb> pizzaTbCollection = saborTb.getPizzaTbCollection();
            for (PizzaTb pizzaTbCollectionPizzaTb : pizzaTbCollection) {
                pizzaTbCollectionPizzaTb.getSaborTbCollection().remove(saborTb);
                pizzaTbCollectionPizzaTb = em.merge(pizzaTbCollectionPizzaTb);
            }
            em.remove(saborTb);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaborTb> findSaborTbEntities() {
        return findSaborTbEntities(true, -1, -1);
    }

    public List<SaborTb> findSaborTbEntities(int maxResults, int firstResult) {
        return findSaborTbEntities(false, maxResults, firstResult);
    }

    private List<SaborTb> findSaborTbEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaborTb.class));
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

    public SaborTb findSaborTb(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaborTb.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaborTbCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaborTb> rt = cq.from(SaborTb.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
