/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
 * @author Kennedy
 */
@Entity
@Table(name = "timeline")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Timeline.findAll", query = "SELECT t FROM Timeline t"),
    @NamedQuery(name = "Timeline.findByIdTimeline", query = "SELECT t FROM Timeline t WHERE t.idTimeline = :idTimeline"),
    @NamedQuery(name = "Timeline.findByTipo", query = "SELECT t FROM Timeline t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "Timeline.findByRegistro", query = "SELECT t FROM Timeline t WHERE t.registro = :registro"),
    @NamedQuery(name = "Timeline.findByData", query = "SELECT t FROM Timeline t WHERE t.data = :data"),
    @NamedQuery(name = "Timeline.findByNivel", query = "SELECT t FROM Timeline t WHERE t.nivel = :nivel")})
public class Timeline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_timeline")
    private Integer idTimeline;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 21)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "registro")
    private String registro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "nivel")
    private String nivel;

    public Timeline() {
    }

    public Timeline(Integer idTimeline) {
        this.idTimeline = idTimeline;
    }

    public Timeline(Integer idTimeline, String tipo, String registro, Date data, String nivel) {
        this.idTimeline = idTimeline;
        this.tipo = tipo;
        this.registro = registro;
        this.data = data;
        this.nivel = nivel;
    }

    public Integer getIdTimeline() {
        return idTimeline;
    }

    public void setIdTimeline(Integer idTimeline) {
        this.idTimeline = idTimeline;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTimeline != null ? idTimeline.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Timeline)) {
            return false;
        }
        Timeline other = (Timeline) object;
        if ((this.idTimeline == null && other.idTimeline != null) || (this.idTimeline != null && !this.idTimeline.equals(other.idTimeline))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Timeline[ idTimeline=" + idTimeline + " ]";
    }
    
}
