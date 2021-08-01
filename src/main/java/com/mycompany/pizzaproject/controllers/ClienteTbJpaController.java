/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.controllers;

import com.mycompany.pizzaproject.controllers.exceptions.IllegalOrphanException;
import com.mycompany.pizzaproject.controllers.exceptions.NonexistentEntityException;
import com.mycompany.pizzaproject.dao.EntityManagerUtil;
import com.mycompany.pizzaproject.models.ClienteTb;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.pizzaproject.models.PedidoTb;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Michael
 */
public class ClienteTbJpaController implements Serializable {

    public ClienteTbJpaController() {
    }

    public EntityManager getEntityManager() {
        return EntityManagerUtil.getEntityManager();
    }

    public void create(ClienteTb clienteTb) {
        if (clienteTb.getPedidoTbCollection() == null) {
            clienteTb.setPedidoTbCollection(new ArrayList<PedidoTb>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PedidoTb> attachedPedidoTbCollection = new ArrayList<PedidoTb>();
            for (PedidoTb pedidoTbCollectionPedidoTbToAttach : clienteTb.getPedidoTbCollection()) {
                pedidoTbCollectionPedidoTbToAttach = em.getReference(pedidoTbCollectionPedidoTbToAttach.getClass(), pedidoTbCollectionPedidoTbToAttach.getPedidoId());
                attachedPedidoTbCollection.add(pedidoTbCollectionPedidoTbToAttach);
            }
            clienteTb.setPedidoTbCollection(attachedPedidoTbCollection);
            em.persist(clienteTb);
            for (PedidoTb pedidoTbCollectionPedidoTb : clienteTb.getPedidoTbCollection()) {
                ClienteTb oldClienteIdOfPedidoTbCollectionPedidoTb = pedidoTbCollectionPedidoTb.getClienteId();
                pedidoTbCollectionPedidoTb.setClienteId(clienteTb);
                pedidoTbCollectionPedidoTb = em.merge(pedidoTbCollectionPedidoTb);
                if (oldClienteIdOfPedidoTbCollectionPedidoTb != null) {
                    oldClienteIdOfPedidoTbCollectionPedidoTb.getPedidoTbCollection().remove(pedidoTbCollectionPedidoTb);
                    oldClienteIdOfPedidoTbCollectionPedidoTb = em.merge(oldClienteIdOfPedidoTbCollectionPedidoTb);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteTb clienteTb) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteTb persistentClienteTb = em.find(ClienteTb.class, clienteTb.getClienteId());
            Collection<PedidoTb> pedidoTbCollectionOld = persistentClienteTb.getPedidoTbCollection();
            Collection<PedidoTb> pedidoTbCollectionNew = clienteTb.getPedidoTbCollection();
            List<String> illegalOrphanMessages = null;
            for (PedidoTb pedidoTbCollectionOldPedidoTb : pedidoTbCollectionOld) {
                if (!pedidoTbCollectionNew.contains(pedidoTbCollectionOldPedidoTb)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoTb " + pedidoTbCollectionOldPedidoTb + " since its clienteId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PedidoTb> attachedPedidoTbCollectionNew = new ArrayList<PedidoTb>();
            for (PedidoTb pedidoTbCollectionNewPedidoTbToAttach : pedidoTbCollectionNew) {
                pedidoTbCollectionNewPedidoTbToAttach = em.getReference(pedidoTbCollectionNewPedidoTbToAttach.getClass(), pedidoTbCollectionNewPedidoTbToAttach.getPedidoId());
                attachedPedidoTbCollectionNew.add(pedidoTbCollectionNewPedidoTbToAttach);
            }
            pedidoTbCollectionNew = attachedPedidoTbCollectionNew;
            clienteTb.setPedidoTbCollection(pedidoTbCollectionNew);
            clienteTb = em.merge(clienteTb);
            for (PedidoTb pedidoTbCollectionNewPedidoTb : pedidoTbCollectionNew) {
                if (!pedidoTbCollectionOld.contains(pedidoTbCollectionNewPedidoTb)) {
                    ClienteTb oldClienteIdOfPedidoTbCollectionNewPedidoTb = pedidoTbCollectionNewPedidoTb.getClienteId();
                    pedidoTbCollectionNewPedidoTb.setClienteId(clienteTb);
                    pedidoTbCollectionNewPedidoTb = em.merge(pedidoTbCollectionNewPedidoTb);
                    if (oldClienteIdOfPedidoTbCollectionNewPedidoTb != null && !oldClienteIdOfPedidoTbCollectionNewPedidoTb.equals(clienteTb)) {
                        oldClienteIdOfPedidoTbCollectionNewPedidoTb.getPedidoTbCollection().remove(pedidoTbCollectionNewPedidoTb);
                        oldClienteIdOfPedidoTbCollectionNewPedidoTb = em.merge(oldClienteIdOfPedidoTbCollectionNewPedidoTb);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clienteTb.getClienteId();
                if (findClienteTb(id) == null) {
                    throw new NonexistentEntityException("The clienteTb with id " + id + " no longer exists.");
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
            ClienteTb clienteTb;
            try {
                clienteTb = em.getReference(ClienteTb.class, id);
                clienteTb.getClienteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clienteTb with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PedidoTb> pedidoTbCollectionOrphanCheck = clienteTb.getPedidoTbCollection();
            for (PedidoTb pedidoTbCollectionOrphanCheckPedidoTb : pedidoTbCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClienteTb (" + clienteTb + ") cannot be destroyed since the PedidoTb " + pedidoTbCollectionOrphanCheckPedidoTb + " in its pedidoTbCollection field has a non-nullable clienteId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clienteTb);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClienteTb> findClienteTbEntities() {
        return findClienteTbEntities(true, -1, -1);
    }

    public List<ClienteTb> findClienteTbEntities(int maxResults, int firstResult) {
        return findClienteTbEntities(false, maxResults, firstResult);
    }

    private List<ClienteTb> findClienteTbEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClienteTb.class));
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

    public ClienteTb findClienteTb(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteTb.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteTbCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClienteTb> rt = cq.from(ClienteTb.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
