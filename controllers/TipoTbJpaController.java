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
import com.mycompany.pizzaproject.models.SaborTb;
import com.mycompany.pizzaproject.models.TipoTb;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Michael
 */
public class TipoTbJpaController implements Serializable {

    public TipoTbJpaController() {
    }

    public EntityManager getEntityManager() {
        return EntityManagerUtil.getEntityManager();
    }

    public void create(TipoTb tipoTb) {
        if (tipoTb.getSaborTbCollection() == null) {
            tipoTb.setSaborTbCollection(new ArrayList<SaborTb>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<SaborTb> attachedSaborTbCollection = new ArrayList<SaborTb>();
            for (SaborTb saborTbCollectionSaborTbToAttach : tipoTb.getSaborTbCollection()) {
                saborTbCollectionSaborTbToAttach = em.getReference(saborTbCollectionSaborTbToAttach.getClass(), saborTbCollectionSaborTbToAttach.getSaborId());
                attachedSaborTbCollection.add(saborTbCollectionSaborTbToAttach);
            }
            tipoTb.setSaborTbCollection(attachedSaborTbCollection);
            em.persist(tipoTb);
            for (SaborTb saborTbCollectionSaborTb : tipoTb.getSaborTbCollection()) {
                TipoTb oldTipoIdOfSaborTbCollectionSaborTb = saborTbCollectionSaborTb.getTipoId();
                saborTbCollectionSaborTb.setTipoId(tipoTb);
                saborTbCollectionSaborTb = em.merge(saborTbCollectionSaborTb);
                if (oldTipoIdOfSaborTbCollectionSaborTb != null) {
                    oldTipoIdOfSaborTbCollectionSaborTb.getSaborTbCollection().remove(saborTbCollectionSaborTb);
                    oldTipoIdOfSaborTbCollectionSaborTb = em.merge(oldTipoIdOfSaborTbCollectionSaborTb);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTb tipoTb) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTb persistentTipoTb = em.find(TipoTb.class, tipoTb.getTipoId());
            Collection<SaborTb> saborTbCollectionOld = persistentTipoTb.getSaborTbCollection();
            Collection<SaborTb> saborTbCollectionNew = tipoTb.getSaborTbCollection();
            List<String> illegalOrphanMessages = null;
            for (SaborTb saborTbCollectionOldSaborTb : saborTbCollectionOld) {
                if (!saborTbCollectionNew.contains(saborTbCollectionOldSaborTb)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SaborTb " + saborTbCollectionOldSaborTb + " since its tipoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<SaborTb> attachedSaborTbCollectionNew = new ArrayList<SaborTb>();
            for (SaborTb saborTbCollectionNewSaborTbToAttach : saborTbCollectionNew) {
                saborTbCollectionNewSaborTbToAttach = em.getReference(saborTbCollectionNewSaborTbToAttach.getClass(), saborTbCollectionNewSaborTbToAttach.getSaborId());
                attachedSaborTbCollectionNew.add(saborTbCollectionNewSaborTbToAttach);
            }
            saborTbCollectionNew = attachedSaborTbCollectionNew;
            tipoTb.setSaborTbCollection(saborTbCollectionNew);
            tipoTb = em.merge(tipoTb);
            for (SaborTb saborTbCollectionNewSaborTb : saborTbCollectionNew) {
                if (!saborTbCollectionOld.contains(saborTbCollectionNewSaborTb)) {
                    TipoTb oldTipoIdOfSaborTbCollectionNewSaborTb = saborTbCollectionNewSaborTb.getTipoId();
                    saborTbCollectionNewSaborTb.setTipoId(tipoTb);
                    saborTbCollectionNewSaborTb = em.merge(saborTbCollectionNewSaborTb);
                    if (oldTipoIdOfSaborTbCollectionNewSaborTb != null && !oldTipoIdOfSaborTbCollectionNewSaborTb.equals(tipoTb)) {
                        oldTipoIdOfSaborTbCollectionNewSaborTb.getSaborTbCollection().remove(saborTbCollectionNewSaborTb);
                        oldTipoIdOfSaborTbCollectionNewSaborTb = em.merge(oldTipoIdOfSaborTbCollectionNewSaborTb);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoTb.getTipoId();
                if (findTipoTb(id) == null) {
                    throw new NonexistentEntityException("The tipoTb with id " + id + " no longer exists.");
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
            TipoTb tipoTb;
            try {
                tipoTb = em.getReference(TipoTb.class, id);
                tipoTb.getTipoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTb with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SaborTb> saborTbCollectionOrphanCheck = tipoTb.getSaborTbCollection();
            for (SaborTb saborTbCollectionOrphanCheckSaborTb : saborTbCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoTb (" + tipoTb + ") cannot be destroyed since the SaborTb " + saborTbCollectionOrphanCheckSaborTb + " in its saborTbCollection field has a non-nullable tipoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoTb);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTb> findTipoTbEntities() {
        return findTipoTbEntities(true, -1, -1);
    }

    public List<TipoTb> findTipoTbEntities(int maxResults, int firstResult) {
        return findTipoTbEntities(false, maxResults, firstResult);
    }

    private List<TipoTb> findTipoTbEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTb.class));
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

    public TipoTb findTipoTb(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTb.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTbCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTb> rt = cq.from(TipoTb.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
