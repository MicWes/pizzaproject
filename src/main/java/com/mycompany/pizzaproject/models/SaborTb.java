/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pizzaproject.models;

import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "sabor_tb")
@NamedQueries({
    @NamedQuery(name = "SaborTb.findAll", query = "SELECT s FROM SaborTb s"),
    @NamedQuery(name = "SaborTb.findBySaborId", query = "SELECT s FROM SaborTb s WHERE s.saborId = :saborId"),
    @NamedQuery(name = "SaborTb.findByDescricao", query = "SELECT s FROM SaborTb s WHERE s.descricao = :descricao")})
public class SaborTb extends Model {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sabor_id")
    private Integer saborId;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    @ManyToMany(mappedBy = "saborTbCollection")
    private Collection<PizzaTb> pizzaTbCollection;
    @JoinColumn(name = "tipo_id", referencedColumnName = "tipo_id")
    @ManyToOne(optional = false)
    private TipoTb tipoId;

    public SaborTb() {
    }

    public SaborTb(Integer saborId) {
        this.saborId = saborId;
    }

    public SaborTb(Integer saborId, String descricao) {
        this.saborId = saborId;
        this.descricao = descricao;
    }

    public Integer getSaborId() {
        return saborId;
    }

    public void setSaborId(Integer saborId) {
        this.saborId = saborId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Collection<PizzaTb> getPizzaTbCollection() {
        return pizzaTbCollection;
    }

    public void setPizzaTbCollection(Collection<PizzaTb> pizzaTbCollection) {
        this.pizzaTbCollection = pizzaTbCollection;
    }

    public TipoTb getTipoId() {
        return tipoId;
    }

    public void setTipoId(TipoTb tipoId) {
        this.tipoId = tipoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saborId != null ? saborId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaborTb)) {
            return false;
        }
        SaborTb other = (SaborTb) object;
        if ((this.saborId == null && other.saborId != null) || (this.saborId != null && !this.saborId.equals(other.saborId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pizzaproject.models.SaborTb[ saborId=" + saborId + " ]";
    }
    
}
