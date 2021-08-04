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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "pizza_tb")
@NamedQueries({
    @NamedQuery(name = "PizzaTb.findAll", query = "SELECT p FROM PizzaTb p"),
    @NamedQuery(name = "PizzaTb.findByPizzaId", query = "SELECT p FROM PizzaTb p WHERE p.pizzaId = :pizzaId"),
    @NamedQuery(name = "PizzaTb.findByForma", query = "SELECT p FROM PizzaTb p WHERE p.forma = :forma"),
    @NamedQuery(name = "PizzaTb.findByTamanho", query = "SELECT p FROM PizzaTb p WHERE p.tamanho = :tamanho"),
    @NamedQuery(name = "PizzaTb.findByTotalPizza", query = "SELECT p FROM PizzaTb p WHERE p.totalPizza = :totalPizza")})
public class PizzaTb extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pizza_id")
    private Integer pizzaId;
    @Basic(optional = false)
    @Column(name = "forma")
    private String forma;
    @Basic(optional = false)
    @Column(name = "tamanho")
    private double tamanho;
    @Basic(optional = false)
    @Column(name = "total_pizza")
    private double totalPizza;
    @JoinTable(name = "pizza_sabor", joinColumns = {
        @JoinColumn(name = "pizza_id", referencedColumnName = "pizza_id")}, inverseJoinColumns = {
        @JoinColumn(name = "sabor_id", referencedColumnName = "sabor_id")})
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Collection<SaborTb> saborTbCollection;
    @JoinColumn(name = "pedido_id", referencedColumnName = "pedido_id")
    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private PedidoTb pedidoId;

    public PizzaTb() {
    }

    public PizzaTb(Integer pizzaId) {
        this.pizzaId = pizzaId;
    }

    public PizzaTb(Integer pizzaId, String forma, double tamanho, double totalPizza) {
        this.pizzaId = pizzaId;
        this.forma = forma;
        this.tamanho = tamanho;
        this.totalPizza = totalPizza;
    }

    public Integer getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(Integer pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public double getTamanho() {
        return tamanho;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }

    public double getTotalPizza() {
        return totalPizza;
    }

    public void setTotalPizza(double totalPizza) {
        this.totalPizza = totalPizza;
    }

    public Collection<SaborTb> getSaborTbCollection() {
        return saborTbCollection;
    }

    public void setSaborTbCollection(Collection<SaborTb> saborTbCollection) {
        this.saborTbCollection = saborTbCollection;
    }

    public PedidoTb getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(PedidoTb pedidoId) {
        this.pedidoId = pedidoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pizzaId != null ? pizzaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PizzaTb)) {
            return false;
        }
        PizzaTb other = (PizzaTb) object;
        if ((this.pizzaId == null && other.pizzaId != null) || (this.pizzaId != null && !this.pizzaId.equals(other.pizzaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pizzaproject.models.PizzaTb[ pizzaId=" + pizzaId + " ]";
    }
    
}
