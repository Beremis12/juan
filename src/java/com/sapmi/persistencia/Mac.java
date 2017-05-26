/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapmi.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Auxiliares
 */
@Entity
@Table(name = "mac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mac.findAll", query = "SELECT m FROM Mac m"),
    @NamedQuery(name = "Mac.findById", query = "SELECT m FROM Mac m WHERE m.id = :id"),
    @NamedQuery(name = "Mac.findByMacId", query = "SELECT m FROM Mac m WHERE m.macId = :macId"),
    @NamedQuery(name = "Mac.findBySala", query = "SELECT m FROM Mac m WHERE m.sala = :sala"),
    @NamedQuery(name = "Mac.findByEquipo", query = "SELECT m FROM Mac m WHERE m.equipo = :equipo")})
public class Mac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "mac_id")
    private String macId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sala")
    private int sala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "equipo")
    private int equipo;

    public Mac() {
    }

    public Mac(Integer id) {
        this.id = id;
    }

    public Mac(Integer id, String macId, int sala, int equipo) {
        this.id = id;
        this.macId = macId;
        this.sala = sala;
        this.equipo = equipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public int getEquipo() {
        return equipo;
    }

    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mac)) {
            return false;
        }
        Mac other = (Mac) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sapmi.persistencia.Mac[ id=" + id + " ]";
    }
    
}
