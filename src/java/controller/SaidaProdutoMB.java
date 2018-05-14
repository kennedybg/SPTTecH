package controller;

import dao.SaidaProdutoDao;
import dao.OrdemDao;
import dao.ProdutoDao;
import dao.VendaDao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.SaidaProduto;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Ordem;
import model.Produto;
import model.Venda;
import util.UtilMensagens;

@ManagedBean(name = "saidaProdutoMB")
@SessionScoped
public class SaidaProdutoMB implements Serializable {

     
    private Integer idProduto;    
    private int quantidade;
    private BigDecimal valorUnitario;
    private Date dataSaida;
    private Integer idVenda;
    private Integer idOrdem;
    private SaidaProduto saidaProduto;    
    private List<SaidaProduto> saidasProduto;
    
    public SaidaProdutoMB() {
        SaidaProdutoDao.inicializarSistema();
        listar();
    }
    
    public void listar(){
        SaidaProdutoDao saidaProdutoDao = new SaidaProdutoDao();
        saidasProduto = new ArrayList();
        saidasProduto = saidaProdutoDao.listar();
    }
    
    private SaidaProduto buscaPorId(int id_saidaProduto) {
        
        SaidaProduto entr = new SaidaProduto();
        SaidaProdutoDao saidaPDao = new SaidaProdutoDao();
        entr = saidaPDao.consultar(id_saidaProduto);        
        
        return entr;
    }
    
    public String alteracao(int id_saidaProduto){
        
        saidaProduto = buscaPorId(id_saidaProduto);
        
        idProduto = saidaProduto.getIdProduto().getIdProduto();        
        idOrdem = saidaProduto.getIdOrdem().getIdOrdem();
        idVenda = saidaProduto.getIdVenda().getIdVenda();
        quantidade = saidaProduto.getQuantidade();
        valorUnitario = saidaProduto.getValorUnitario();
        dataSaida = saidaProduto.getDataSaida();
        
        return "inalter?i=8&faces-redirect=true";
    }
    
    public String excluir(){
        SaidaProdutoDao saidaProdutoDao = new SaidaProdutoDao();
        if (saidaProdutoDao.excluir(saidaProduto)){
            listarSaidas(idProduto);
            if(saidasProduto.isEmpty()) {
                
                UtilMensagens.mensagemInfo("Saida excluída com sucesso!");
                UtilMensagens.mensagemInfo("Produto não possui mais saidas!");
                return "list?i=8&faces-redirect=true";
            }
            else {
                
                return "inlist?i=8&faces-redirect=true";
            }            
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao excluir a saida!");
            return null;
        }
    }
    
    
    public String listarSaidas(int id_Produto){
        idProduto = id_Produto;
        
        String query = "from SaidaProduto where id_produto";
        query += "=" + id_Produto;        
        query += " order by idSaidaProduto";
        
        SaidaProdutoDao entrDao = new SaidaProdutoDao();
        
        saidasProduto = entrDao.consultarQuery(query);
        
        return "inlist?i=8&faces-redirect=true";
    }
    
    public String nomeProduto() {
        
        ProdutoMB p = new ProdutoMB();
        return p.buscaPorId(idProduto).getNome();
    }

    public String novo(){
                
        FornecedorMB fMB = new FornecedorMB();
        
        if(fMB.getFornecedores().isEmpty()) {
            
            UtilMensagens.mensagemErro("Nenhum fornecedor cadastrado!");
            return "list?i=8&faces-redirect=true";
        }
        else {
            
            idProduto = null;
            idOrdem = null;
            idVenda = null;
            quantidade = 0;
            valorUnitario = null;
            dataSaida = null;

            return "in?i=8&faces-redirect=true";
        }
    }
    
    public SaidaProduto criarSaidaProduto(){
        
        SaidaProduto saidaProduto = new SaidaProduto();
                
        ProdutoDao produtoDao = new ProdutoDao();
        Produto produto = produtoDao.consultar(idProduto);
        saidaProduto.setIdProduto(produto);
        
        VendaDao vendaDao = new VendaDao();
        Venda venda = vendaDao.consultar(idVenda);
        saidaProduto.setIdVenda(venda);
                
        OrdemDao ordemDao = new OrdemDao();
        Ordem ordem = ordemDao.consultar(idOrdem);
        saidaProduto.setIdOrdem(ordem);
        
        saidaProduto.setQuantidade(quantidade);
        saidaProduto.setValorUnitario(valorUnitario);
        saidaProduto.setDataSaida(dataSaida);
                
        return saidaProduto;
    }

    
    public void gravar(){
        SaidaProdutoDao saidaProdutoDao = new SaidaProdutoDao();
        
        saidaProdutoDao.gravar(criarSaidaProduto());            
    }
    public void alterar(SaidaProduto sp){
            
        SaidaProdutoDao saidaProdutoDao = new SaidaProdutoDao();
        saidaProdutoDao.alterar(sp);            
    }
    
    public String voltar(){
        return "inlist?i=8&faces-redirect=true";
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

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto = saidaProduto;
    }

    public List<SaidaProduto> getSaidasProduto() {
        return saidasProduto;
    }

    public void setSaidasProduto(List<SaidaProduto> saidasProduto) {
        this.saidasProduto = saidasProduto;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(int idOrdem) {
        this.idOrdem = idOrdem;
    }
    
    
    
}
