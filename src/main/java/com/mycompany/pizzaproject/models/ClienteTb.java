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
@Table(name = "cliente_tb")
@NamedQueries({
    @NamedQuery(name = "ClienteTb.findAll", query = "SELECT c FROM ClienteTb c"),
    @NamedQuery(name = "ClienteTb.findByClienteId", query = "SELECT c FROM ClienteTb c WHERE c.clienteId = :clienteId"),
    @NamedQuery(name = "ClienteTb.findByNome", query = "SELECT c FROM ClienteTb c WHERE c.nome = :nome"),
    @NamedQuery(name = "ClienteTb.findBySobrenome", query = "SELECT c FROM ClienteTb c WHERE c.sobrenome = :sobrenome"),
    @NamedQuery(name = "ClienteTb.findByTelefone", query = "SELECT c FROM ClienteTb c WHERE c.telefone = :telefone")})
public class ClienteTb extends Model {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cliente_id")
    private Integer clienteId;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Column(name = "sobrenome")
    private String sobrenome;
    @Basic(optional = false)
    @Column(name = "telefone")
    private String telefone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteId")
    private Collection<PedidoTb> pedidoTbCollection;

    public ClienteTb() {
    }

    public ClienteTb(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public ClienteTb(Integer clienteId, String nome, String telefone) {
        this.clienteId = clienteId;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Collection<PedidoTb> getPedidoTbCollection() {
        return pedidoTbCollection;
    }

    public void setPedidoTbCollection(Collection<PedidoTb> pedidoTbCollection) {
        this.pedidoTbCollection = pedidoTbCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteId != null ? clienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteTb)) {
            return false;
        }
        ClienteTb other = (ClienteTb) object;
        if ((this.clienteId == null && other.clienteId != null) || (this.clienteId != null && !this.clienteId.equals(other.clienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pizzaproject.models.ClienteTb[ clienteId=" + clienteId + " ]";
    }
    
}
