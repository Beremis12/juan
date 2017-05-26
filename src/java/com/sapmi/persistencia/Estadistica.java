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
@Table(name = "estadistica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadistica.findAll", query = "SELECT e FROM Estadistica e"),
    @NamedQuery(name = "Estadistica.findById", query = "SELECT e FROM Estadistica e WHERE e.id = :id"),
    @NamedQuery(name = "Estadistica.findByMac", query = "SELECT e FROM Estadistica e WHERE e.mac = :mac"),
    @NamedQuery(name = "Estadistica.findByDiasemana", query = "SELECT e FROM Estadistica e WHERE e.diasemana = :diasemana"),
    @NamedQuery(name = "Estadistica.findByCedula", query = "SELECT e FROM Estadistica e WHERE e.cedula = :cedula"),
    @NamedQuery(name = "Estadistica.findByFecha", query = "SELECT e FROM Estadistica e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "Estadistica.findByHoraInicial", query = "SELECT e FROM Estadistica e WHERE e.horaInicial = :horaInicial"),
    @NamedQuery(name = "Estadistica.findByHoraFinal", query = "SELECT e FROM Estadistica e WHERE e.horaFinal = :horaFinal"),
    @NamedQuery(name = "Estadistica.findBySala", query = "SELECT e FROM Estadistica e WHERE e.sala = :sala"),
    @NamedQuery(name = "Estadistica.findByEquipo", query = "SELECT e FROM Estadistica e WHERE e.equipo = :equipo")})
public class Estadistica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "mac")
    private String mac;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "Diasemana")
    private String diasemana;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horaInicial")
    @Temporal(TemporalType.TIME)
    private Date horaInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horaFinal")
    @Temporal(TemporalType.TIME)
    private Date horaFinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Sala")
    private int sala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Equipo")
    private int equipo;

    public Estadistica() {
    }

    public Estadistica(Integer id) {
        this.id = id;
    }

    public Estadistica(Integer id, String mac, String diasemana, String cedula, Date fecha, Date horaInicial, Date horaFinal, int sala, int equipo) {
        this.id = id;
        this.mac = mac;
        this.diasemana = diasemana;
        this.cedula = cedula;
        this.fecha = fecha;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.sala = sala;
        this.equipo = equipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDiasemana() {
        return diasemana;
    }

    public void setDiasemana(String diasemana) {
        this.diasemana = diasemana;
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

    public Date getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Date horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
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
        if (!(object instanceof Estadistica)) {
            return false;
        }
        Estadistica other = (Estadistica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sapmi.persistencia.Estadistica[ id=" + id + " ]";
    }
    
}
