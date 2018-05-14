/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kennedy
 */
@Entity
@Table(name = "servico_prestado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServicoPrestado.findAll", query = "SELECT s FROM ServicoPrestado s"),
    @NamedQuery(name = "ServicoPrestado.findByIdServicoPrestado", query = "SELECT s FROM ServicoPrestado s WHERE s.idServicoPrestado = :idServicoPrestado"),
    @NamedQuery(name = "ServicoPrestado.findByQuantidade", query = "SELECT s FROM ServicoPrestado s WHERE s.quantidade = :quantidade")})
public class ServicoPrestado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_servico_prestado")
    private Integer idServicoPrestado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade")
    private int quantidade;
    @JoinColumn(name = "id_ordem", referencedColumnName = "id_ordem")
    @ManyToOne(optional = false)
    private Ordem idOrdem;
    @JoinColumn(name = "id_servico", referencedColumnName = "id_servico")
    @ManyToOne(optional = false)
    private Servico idServico;

    public ServicoPrestado() {
    }

    public ServicoPrestado(Integer idServicoPrestado) {
        this.idServicoPrestado = idServicoPrestado;
    }

    public ServicoPrestado(Integer idServicoPrestado, int quantidade) {
        this.idServicoPrestado = idServicoPrestado;
        this.quantidade = quantidade;
    }

    public Integer getIdServicoPrestado() {
        return idServicoPrestado;
    }

    public void setIdServicoPrestado(Integer idServicoPrestado) {
        this.idServicoPrestado = idServicoPrestado;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Ordem getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(Ordem idOrdem) {
        this.idOrdem = idOrdem;
    }

    public Servico getIdServico() {
        return idServico;
    }

    public void setIdServico(Servico idServico) {
        this.idServico = idServico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicoPrestado != null ? idServicoPrestado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicoPrestado)) {
            return false;
        }
        ServicoPrestado other = (ServicoPrestado) object;
        if ((this.idServicoPrestado == null && other.idServicoPrestado != null) || (this.idServicoPrestado != null && !this.idServicoPrestado.equals(other.idServicoPrestado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ServicoPrestado[ idServicoPrestado=" + idServicoPrestado + " ]";
    }
    
}
