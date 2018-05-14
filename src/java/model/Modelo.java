/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kennedy
 */
@Entity
@Table(name = "modelo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modelo.findAll", query = "SELECT m FROM Modelo m"),
    @NamedQuery(name = "Modelo.findByIdModelo", query = "SELECT m FROM Modelo m WHERE m.idModelo = :idModelo"),
    @NamedQuery(name = "Modelo.findByNome", query = "SELECT m FROM Modelo m WHERE m.nome = :nome"),
    @NamedQuery(name = "Modelo.findByDescricaoTecnica", query = "SELECT m FROM Modelo m WHERE m.descricaoTecnica = :descricaoTecnica")})
public class Modelo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_modelo")
    private Integer idModelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "descricao_tecnica")
    private String descricaoTecnica;
    @JoinColumn(name = "tipo_equipamento", referencedColumnName = "id_equipamento")
    @ManyToOne(optional = false)
    private Equipamento tipoEquipamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModelo")
    private Collection<Ordem> ordemCollection;

    public Modelo() {
    }

    public Modelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public Modelo(Integer idModelo, String nome, String descricaoTecnica) {
        this.idModelo = idModelo;
        this.nome = nome;
        this.descricaoTecnica = descricaoTecnica;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricaoTecnica() {
        return descricaoTecnica;
    }

    public void setDescricaoTecnica(String descricaoTecnica) {
        this.descricaoTecnica = descricaoTecnica;
    }

    public Equipamento getTipoEquipamento() {
        return tipoEquipamento;
    }

    public void setTipoEquipamento(Equipamento tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    @XmlTransient
    public Collection<Ordem> getOrdemCollection() {
        return ordemCollection;
    }

    public void setOrdemCollection(Collection<Ordem> ordemCollection) {
        this.ordemCollection = ordemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModelo != null ? idModelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modelo)) {
            return false;
        }
        Modelo other = (Modelo) object;
        if ((this.idModelo == null && other.idModelo != null) || (this.idModelo != null && !this.idModelo.equals(other.idModelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Modelo[ idModelo=" + idModelo + " ]";
    }
    
}
