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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_tb")
@NamedQueries({
    @NamedQuery(name = "TipoTb.findAll", query = "SELECT t FROM TipoTb t"),
    @NamedQuery(name = "TipoTb.findByTipoId", query = "SELECT t FROM TipoTb t WHERE t.tipoId = :tipoId"),
    @NamedQuery(name = "TipoTb.findByTitulo", query = "SELECT t FROM TipoTb t WHERE t.titulo = :titulo"),
    @NamedQuery(name = "TipoTb.findByPreco", query = "SELECT t FROM TipoTb t WHERE t.preco = :preco")})
public class TipoTb extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tipo_id")
    private Integer tipoId;
    
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    
    @Basic(optional = false)
    @Column(name = "preco")
    private double preco;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoId")
    private Collection<SaborTb> saborTbCollection;

    public TipoTb() {
    }

    public TipoTb(Integer tipoId) {
        this.tipoId = tipoId;
    }

    public TipoTb(Integer tipoId, String titulo, double preco) {
        this.tipoId = tipoId;
        this.titulo = titulo;
        this.preco = preco;
    }

    public Integer getTipoId() {
        return tipoId;
    }

    public void setTipoId(Integer tipoId) {
        this.tipoId = tipoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Collection<SaborTb> getSaborTbCollection() {
        return saborTbCollection;
    }

    public void setSaborTbCollection(Collection<SaborTb> saborTbCollection) {
        this.saborTbCollection = saborTbCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoId != null ? tipoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTb)) {
            return false;
        }
        TipoTb other = (TipoTb) object;
        if ((this.tipoId == null && other.tipoId != null) || (this.tipoId != null && !this.tipoId.equals(other.tipoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pizzaproject.models.TipoTb[ tipoId=" + tipoId + " ]";
    }
    
}
