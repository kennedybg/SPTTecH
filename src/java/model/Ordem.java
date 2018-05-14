/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kennedy
 */
@Entity
@Table(name = "ordem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordem.findAll", query = "SELECT o FROM Ordem o"),
    @NamedQuery(name = "Ordem.findByIdOrdem", query = "SELECT o FROM Ordem o WHERE o.idOrdem = :idOrdem"),
    @NamedQuery(name = "Ordem.findByProblema", query = "SELECT o FROM Ordem o WHERE o.problema = :problema"),
    @NamedQuery(name = "Ordem.findByStatusOrdem", query = "SELECT o FROM Ordem o WHERE o.statusOrdem = :statusOrdem"),
    @NamedQuery(name = "Ordem.findByDataOrdem", query = "SELECT o FROM Ordem o WHERE o.dataOrdem = :dataOrdem")})
public class Ordem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ordem")
    private Integer idOrdem;
    @Size(max = 1024)
    @Column(name = "problema")
    private String problema;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "statusOrdem")
    private String statusOrdem;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_ordem")
    @Temporal(TemporalType.DATE)
    private Date dataOrdem;
    @OneToMany(mappedBy = "idOrdem")
    private Collection<SaidaProduto> saidaProdutoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOrdem")
    private Collection<ServicoPrestado> servicoPrestadoCollection;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false)
    private Cliente idCliente;
    @JoinColumn(name = "id_modelo", referencedColumnName = "id_modelo")
    @ManyToOne(optional = false)
    private Modelo idModelo;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public Ordem() {
    }

    public Ordem(Integer idOrdem) {
        this.idOrdem = idOrdem;
    }

    public Ordem(Integer idOrdem, String statusOrdem, Date dataOrdem) {
        this.idOrdem = idOrdem;
        this.statusOrdem = statusOrdem;
        this.dataOrdem = dataOrdem;
    }

    public Integer getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(Integer idOrdem) {
        this.idOrdem = idOrdem;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getStatusOrdem() {
        return statusOrdem;
    }

    public void setStatusOrdem(String statusOrdem) {
        this.statusOrdem = statusOrdem;
    }

    public Date getDataOrdem() {
        return dataOrdem;
    }

    public void setDataOrdem(Date dataOrdem) {
        this.dataOrdem = dataOrdem;
    }

    @XmlTransient
    public Collection<SaidaProduto> getSaidaProdutoCollection() {
        return saidaProdutoCollection;
    }

    public void setSaidaProdutoCollection(Collection<SaidaProduto> saidaProdutoCollection) {
        this.saidaProdutoCollection = saidaProdutoCollection;
    }

    @XmlTransient
    public Collection<ServicoPrestado> getServicoPrestadoCollection() {
        return servicoPrestadoCollection;
    }

    public void setServicoPrestadoCollection(Collection<ServicoPrestado> servicoPrestadoCollection) {
        this.servicoPrestadoCollection = servicoPrestadoCollection;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Modelo getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Modelo idModelo) {
        this.idModelo = idModelo;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrdem != null ? idOrdem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordem)) {
            return false;
        }
        Ordem other = (Ordem) object;
        if ((this.idOrdem == null && other.idOrdem != null) || (this.idOrdem != null && !this.idOrdem.equals(other.idOrdem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Ordem[ idOrdem=" + idOrdem + " ]";
    }
    
}
