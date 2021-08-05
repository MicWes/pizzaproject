/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.dao;

import com.mycompany.pizzaproject.models.PizzaTb;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PizzaDao extends Dao<PizzaTb> {

    public PizzaDao() {
    }

    @Override
    public List<PizzaTb> list(boolean all, int maxResults, int firstResult) {
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
    
    @Override
    public PizzaTb find(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PizzaTb.class, id);
        } finally {
            em.close();
        }
    }
    
    @Override
    public int getCount(){
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

    @Override
    public PizzaTb create(PizzaTb model) {
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
    public PizzaTb update(PizzaTb model) {
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
            PizzaTb tipoTb = em.find(PizzaTb.class, id);
            em.remove(tipoTb);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
