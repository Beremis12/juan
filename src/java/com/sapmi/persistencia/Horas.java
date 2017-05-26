/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapmi.persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Auxiliares
 */
@Entity
@Table(name = "horas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horas.findAll", query = "SELECT h FROM Horas h"),
    @NamedQuery(name = "Horas.findById", query = "SELECT h FROM Horas h WHERE h.id = :id"),
    @NamedQuery(name = "Horas.findByCedula", query = "SELECT h FROM Horas h WHERE h.cedula = :cedula"),
    @NamedQuery(name = "Horas.findByFecha", query = "SELECT h FROM Horas h WHERE h.fecha = :fecha"),
    @NamedQuery(name = "Horas.findByTotalmin", query = "SELECT h FROM Horas h WHERE h.totalmin = :totalmin"),
    @NamedQuery(name = "Horas.findByTiempoExtra", query = "SELECT h FROM Horas h WHERE h.tiempoExtra = :tiempoExtra")})
public class Horas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "totalmin")
    private int totalmin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "TiempoExtra")
    private String tiempoExtra;

    public Horas() {
    }

    public Horas(Integer id) {
        this.id = id;
    }

    public Horas(Integer id, String cedula, Date fecha, int totalmin, String tiempoExtra) {
        this.id = id;
        this.cedula = cedula;
        this.fecha = fecha;
        this.totalmin = totalmin;
        this.tiempoExtra = tiempoExtra;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTotalmin() {
        return totalmin;
    }

    public void setTotalmin(int totalmin) {
        this.totalmin = totalmin;
    }

    public String getTiempoExtra() {
        return tiempoExtra;
    }

    public void setTiempoExtra(String tiempoExtra) {
        this.tiempoExtra = tiempoExtra;
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
        if (!(object instanceof Horas)) {
            return false;
        }
        Horas other = (Horas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sapmi.persistencia.Horas[ id=" + id + " ]";
    }
    
}
