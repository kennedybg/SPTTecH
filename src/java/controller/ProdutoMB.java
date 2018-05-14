package controller;

import dao.ProdutoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.Produto;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.NivelAcesso;
import util.TimelineTipo;
import util.UtilMensagens;

@ManagedBean(name = "produtoMB")
@SessionScoped
public class ProdutoMB implements Serializable {

     
    private String nome;
    private String descricao;
    private String garantia; 
    private Produto produto;
    private List<Produto> produtos;
    
    public ProdutoMB() {
        ProdutoDao.inicializarSistema();
        listar();
    }
    
    public void listar(){
        ProdutoDao produtoDao = new ProdutoDao();
        produtos = new ArrayList();
        produtos = produtoDao.listar();
    }
    
    public Produto buscaPorId(int id_Produto) {
        
        Produto client = new Produto();
        ProdutoDao clientDao = new ProdutoDao();
        client = clientDao.consultar(id_Produto);        
        
        return client;
    }
    
    public String alteracao(int id_Produto){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=8&faces-redirect=true";
        
        produto = buscaPorId(id_Produto);
        
        nome = produto.getNome();  
        descricao = produto.getDescricao();
        garantia = produto.getGarantia();
        return "alter?i=8&faces-redirect=true";
    }
    
    
    public String excluir(){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=8&faces-redirect=true";
        
        ProdutoDao produtoDao = new ProdutoDao();
        if (produtoDao.excluir(produto)){
            listar();
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" removeu o produto ["+produto.getNome()+"].", TimelineTipo.AVISO, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Produto excluído com sucesso!");
            return "list?i=8&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao excluir o produto!");
            return null;
        }
    }
    

    public String novo(){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=8&faces-redirect=true";
        
        nome = null;
        descricao = null;
        garantia = null;
        
        return "new?i=8&faces-redirect=true";
    }
    
    public Produto criarProduto(){
        
        Produto produto = new Produto();
        
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setGarantia(garantia);
        
        return produto;
    }

    
    public String gravar(){
        ProdutoDao produtoDao = new ProdutoDao();
        
        if (produtoDao.gravar(criarProduto())){
            listar();
            produto = new Produto();
            produto.setNome(nome);
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" cadastrou o produto ["+produto.getNome()+"].", TimelineTipo.INFO, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Produto cadastrado com sucesso!");
            return "list?i=8&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao cadastrar o produto!");
            return null;
        }            
    }
    
    public Produto alterarProduto(){
        
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setGarantia(garantia);
        
        return produto;
    }
    
    
    public String alterar(){
        ProdutoDao produtoDao = new ProdutoDao();
        if (produtoDao.alterar(alterarProduto())){
            listar();
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" atualizou o produto ["+produto.getNome()+"].", TimelineTipo.ALERTA, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Os dados do produto foram atualizados!");
            return "list?i=8&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao alterar os dados do produto!");
            return null;
        }
    }
    
    public String voltar(){
        return "list?i=8&faces-redirect=true";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
