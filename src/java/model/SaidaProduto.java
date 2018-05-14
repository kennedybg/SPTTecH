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
@Table(name = "saida_produto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaidaProduto.findAll", query = "SELECT s FROM SaidaProduto s"),
    @NamedQuery(name = "SaidaProduto.findByIdSaidaProduto", query = "SELECT s FROM SaidaProduto s WHERE s.idSaidaProduto = :idSaidaProduto"),
    @NamedQuery(name = "SaidaProduto.findByQuantidade", query = "SELECT s FROM SaidaProduto s WHERE s.quantidade = :quantidade"),
    @NamedQuery(name = "SaidaProduto.findByDataSaida", query = "SELECT s FROM SaidaProduto s WHERE s.dataSaida = :dataSaida"),
    @NamedQuery(name = "SaidaProduto.findByValorUnitario", query = "SELECT s FROM SaidaProduto s WHERE s.valorUnitario = :valorUnitario")})
public class SaidaProduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_saida_produto")
    private Integer idSaidaProduto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade")
    private int quantidade;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_saida")
    @Temporal(TemporalType.DATE)
    private Date dataSaida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;
    @JoinColumn(name = "id_ordem", referencedColumnName = "id_ordem")
    @ManyToOne
    private Ordem idOrdem;
    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto")
    @ManyToOne(optional = false)
    private Produto idProduto;
    @JoinColumn(name = "id_venda", referencedColumnName = "id_venda")
    @ManyToOne
    private Venda idVenda;

    public SaidaProduto() {
    }

    public SaidaProduto(Integer idSaidaProduto) {
        this.idSaidaProduto = idSaidaProduto;
    }

    public SaidaProduto(Integer idSaidaProduto, int quantidade, Date dataSaida, BigDecimal valorUnitario) {
        this.idSaidaProduto = idSaidaProduto;
        this.quantidade = quantidade;
        this.dataSaida = dataSaida;
        this.valorUnitario = valorUnitario;
    }

    public Integer getIdSaidaProduto() {
        return idSaidaProduto;
    }

    public void setIdSaidaProduto(Integer idSaidaProduto) {
        this.idSaidaProduto = idSaidaProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Ordem getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(Ordem idOrdem) {
        this.idOrdem = idOrdem;
    }

    public Produto getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Produto idProduto) {
        this.idProduto = idProduto;
    }

    public Venda getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Venda idVenda) {
        this.idVenda = idVenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSaidaProduto != null ? idSaidaProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaidaProduto)) {
            return false;
        }
        SaidaProduto other = (SaidaProduto) object;
        if ((this.idSaidaProduto == null && other.idSaidaProduto != null) || (this.idSaidaProduto != null && !this.idSaidaProduto.equals(other.idSaidaProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.SaidaProduto[ idSaidaProduto=" + idSaidaProduto + " ]";
    }
    
}
