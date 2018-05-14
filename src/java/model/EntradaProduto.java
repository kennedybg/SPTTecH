/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kennedy
 */
@Entity
@Table(name = "entrada_produto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntradaProduto.findAll", query = "SELECT e FROM EntradaProduto e"),
    @NamedQuery(name = "EntradaProduto.findByIdEntradaProduto", query = "SELECT e FROM EntradaProduto e WHERE e.idEntradaProduto = :idEntradaProduto"),
    @NamedQuery(name = "EntradaProduto.findByQuantidade", query = "SELECT e FROM EntradaProduto e WHERE e.quantidade = :quantidade"),
    @NamedQuery(name = "EntradaProduto.findByValorUnitario", query = "SELECT e FROM EntradaProduto e WHERE e.valorUnitario = :valorUnitario"),
    @NamedQuery(name = "EntradaProduto.findByDataEntrada", query = "SELECT e FROM EntradaProduto e WHERE e.dataEntrada = :dataEntrada")})
public class EntradaProduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_entrada_produto")
    private Integer idEntradaProduto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade")
    private int quantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_entrada")
    @Temporal(TemporalType.DATE)
    private Date dataEntrada;
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id_fornecedor")
    @ManyToOne(optional = false)
    private Fornecedor idFornecedor;
    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto")
    @ManyToOne(optional = false)
    private Produto idProduto;

    public EntradaProduto() {
    }

    public EntradaProduto(Integer idEntradaProduto) {
        this.idEntradaProduto = idEntradaProduto;
    }

    public EntradaProduto(Integer idEntradaProduto, int quantidade, BigDecimal valorUnitario, Date dataEntrada) {
        this.idEntradaProduto = idEntradaProduto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.dataEntrada = dataEntrada;
    }

    public Integer getIdEntradaProduto() {
        return idEntradaProduto;
    }

    public void setIdEntradaProduto(Integer idEntradaProduto) {
        this.idEntradaProduto = idEntradaProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public Produto getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Produto idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntradaProduto != null ? idEntradaProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntradaProduto)) {
            return false;
        }
        EntradaProduto other = (EntradaProduto) object;
        if ((this.idEntradaProduto == null && other.idEntradaProduto != null) || (this.idEntradaProduto != null && !this.idEntradaProduto.equals(other.idEntradaProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.EntradaProduto[ idEntradaProduto=" + idEntradaProduto + " ]";
    }
    
}
