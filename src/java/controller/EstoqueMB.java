package controller;

import dao.EstoqueDao;
import dao.ProdutoDao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.Estoque;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Produto;
import util.UtilMensagens;

@ManagedBean(name = "estoqueMB")
@SessionScoped
public class EstoqueMB implements Serializable {

     
    private Integer idProduto;
    private int quantidade;
    private BigDecimal valorUnitario;    
    private Estoque estoque;
    private List<Estoque> entradasProduto;
    
    public EstoqueMB() {
        EstoqueDao.inicializarSistema();
        listar();
    }
    
    public void listar(){
        EstoqueDao estoqueDao = new EstoqueDao();
        entradasProduto = new ArrayList();
        entradasProduto = estoqueDao.listar();
    }
    
    public Estoque buscaPorId(int id_estoque) {
        
        Estoque e = new Estoque();
        EstoqueDao entradaPDao = new EstoqueDao();
        e = entradaPDao.consultar(id_estoque);        
                
        return e;
    }
    
    public Estoque buscaPorIdProduto(int id_produto) {
        
        Estoque e = new Estoque();
        EstoqueDao entradaPDao = new EstoqueDao();
        e = entradaPDao.consultarPorIdProduto(id_produto);        
        
        return e;
    }
    
    public String buscaQuantidadeEstoque(int idProduto){
        
        Estoque e = new Estoque();
        
        e = buscaPorIdProduto(idProduto);
        
        return ((e == null || e.getQuantidade() == 0) ? "Indisponível" : "x "+e.getQuantidade());
    }
    
    public String buscaValorUnitario(int idProduto){
        
        Estoque e = new Estoque();
        e = buscaPorIdProduto(idProduto);
        
        return ((e == null || e.getQuantidade() == 0) ? "Indisponível" : "R$ "+e.getValorUnitario());        
    }
    
    public String alteracao(int id_estoque){
        
        estoque = buscaPorId(id_estoque);
        
        idProduto = estoque.getIdProduto().getIdProduto();
        quantidade = estoque.getQuantidade();
        valorUnitario = estoque.getValorUnitario();
        
        return "alter?i=8&faces-redirect=true";
    }
    
    public String excluir(){
        EstoqueDao estoqueDao = new EstoqueDao();
        if (estoqueDao.excluir(estoque)){
            listar();
            UtilMensagens.mensagemInfo("Entrada excluída com sucesso!");
            return "list?i=8&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao excluir a entrada!");
            return null;
        }
    }
    

    public String novo(){
        
        idProduto = null;
        quantidade = 0;
        valorUnitario = null;
        
        return "in?i=8&faces-redirect=true";
    }
    
    public Estoque criarEstoque(){
        
        Estoque estoque = new Estoque();
        
        
        ProdutoDao produtoDao = new ProdutoDao();
        Produto produto = produtoDao.consultar(idProduto);
        estoque.setIdProduto(produto);
        estoque.setQuantidade(quantidade);
        estoque.setValorUnitario(valorUnitario);
        
        return estoque;
    }

    
    public String gravar(){
        EstoqueDao estoqueDao = new EstoqueDao();
        
        if (estoqueDao.gravar(criarEstoque())){
            listar();
            UtilMensagens.mensagemInfo("Entrada inserida com sucesso!");
            return "list?i=8&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao inserir a entrada!");
            return null;
        }            
    }
    
    public Estoque alterarEstoque(){
        
        ProdutoDao produtoDao = new ProdutoDao();
        Produto produto = produtoDao.consultar(idProduto);
        estoque.setIdProduto(produto);
        
        estoque.setQuantidade(quantidade);
        estoque.setValorUnitario(valorUnitario);

        return estoque;
    }
    
    
    public String alterar(){
        EstoqueDao estoqueDao = new EstoqueDao();
        if (estoqueDao.alterar(alterarEstoque())){
            listar();
            UtilMensagens.mensagemInfo("Os dados da entrada foram atualizados!");
            return "list?i=8&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao alterar os dados da entrada!");
            return null;
        }
    }
    
    public String voltar(){
        return "list?i=8&faces-redirect=true";
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
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

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public List<Estoque> getEntradasProduto() {
        return entradasProduto;
    }

    public void setEntradasProduto(List<Estoque> entradasProduto) {
        this.entradasProduto = entradasProduto;
    }
}
