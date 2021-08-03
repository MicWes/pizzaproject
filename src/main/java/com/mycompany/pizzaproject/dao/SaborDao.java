/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.dao;

import com.mycompany.pizzaproject.models.SaborTb;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author mathe
 */
public class SaborDao extends Dao<SaborTb> {

    public SaborDao() {
    }

    @Override
    public List<SaborTb> list(boolean all, int maxResults, int firstResult) {
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
    
    @Override
    public SaborTb find(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaborTb.class, id);
        } finally {
            em.close();
        }
    }
    
    @Override
    public int getCount(){
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

    @Override
    public SaborTb create(SaborTb model) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(model);
            em.getTransaction().commit();
        }  finally {
            if (em != null) {
                em.close();
            }
        }
        return model;
    }

    @Override
    public SaborTb update(SaborTb model) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(model);
            em.getTransaction().commit();
            return model;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaborTb saborTb = em.find(SaborTb.class, id);
            em.remove(saborTb);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
