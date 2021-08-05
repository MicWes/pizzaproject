/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.models;

import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "pedido_tb")
@NamedQueries({
    @NamedQuery(name = "PedidoTb.findAll", query = "SELECT p FROM PedidoTb p"),
    @NamedQuery(name = "PedidoTb.findByPedidoId", query = "SELECT p FROM PedidoTb p WHERE p.pedidoId = :pedidoId"),
    @NamedQuery(name = "PedidoTb.findByTotalPedido", query = "SELECT p FROM PedidoTb p WHERE p.totalPedido = :totalPedido"),
    @NamedQuery(name = "PedidoTb.findByStatus", query = "SELECT p FROM PedidoTb p WHERE p.status = :status")})
public class PedidoTb extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pedido_id")
    private Integer pedidoId;
    @Basic(optional = false)
    @Column(name = "total_pedido")
    private double totalPedido;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private ClienteTb clienteId;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "pedidoId", fetch = FetchType.EAGER)
    private Collection<PizzaTb> pizzaTbCollection;

    public PedidoTb() {
        this.status = "Aberto";
    }

    public PedidoTb(Integer pedidoId) {
        this.pedidoId = pedidoId;
        this.status = "Aberto";
    }

    public PedidoTb(Integer pedidoId, double totalPedido, String status) {
        this.pedidoId = pedidoId;
        this.totalPedido = totalPedido;
        this.status = status;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ClienteTb getClienteId() {
        return clienteId;
    }

    public void setClienteId(ClienteTb clienteId) {
        this.clienteId = clienteId;
    }

    public Collection<PizzaTb> getPizzaTbCollection() {
        return pizzaTbCollection;
    }

    public void setPizzaTbCollection(Collection<PizzaTb> pizzaTbCollection) {
        this.pizzaTbCollection = pizzaTbCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pedidoId != null ? pedidoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedidoTb)) {
            return false;
        }
        PedidoTb other = (PedidoTb) object;
        if ((this.pedidoId == null && other.pedidoId != null) || (this.pedidoId != null && !this.pedidoId.equals(other.pedidoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pizzaproject.models.PedidoTb[ pedidoId=" + pedidoId + " ]";
    }

    public void nextStatus() {
         switch (this.status) {
            case "Aberto":
                this.status = "Caminho";
                break;
            case "Caminho": 
                this.status = "Entregue";
                break;
        }
    }

    public boolean isEditable() {
        return "Aberto".equals(this.status);
    }
    
}
