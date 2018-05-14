package controller;

import dao.EntradaProdutoDao;
import dao.FornecedorDao;
import dao.ProdutoDao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.EntradaProduto;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Fornecedor;
import model.Produto;
import util.NivelAcesso;
import util.TimelineTipo;
import util.UtilMensagens;

@ManagedBean(name = "entradaProdutoMB")
@SessionScoped
public class EntradaProdutoMB implements Serializable {

     
    private Integer idProduto;
    private Integer idFornecedor;
    private int quantidade;
    private BigDecimal valorUnitario;
    private Date dataEntrada;
    private EntradaProduto entradaProduto;    
    private List<EntradaProduto> entradasProduto;
    
    public EntradaProdutoMB() {
        EntradaProdutoDao.inicializarSistema();
        listar();
    }
    
    public void listar(){
        EntradaProdutoDao entradaProdutoDao = new EntradaProdutoDao();
        entradasProduto = new ArrayList();
        entradasProduto = entradaProdutoDao.listar();
    }
    
    private EntradaProduto buscaPorId(int id_entradaProduto) {
        
        EntradaProduto entr = new EntradaProduto();
        EntradaProdutoDao entradaPDao = new EntradaProdutoDao();
        entr = entradaPDao.consultar(id_entradaProduto);        
        
        return entr;
    }
    
    public String alteracao(int id_entradaProduto){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=8&faces-redirect=true";
        
        entradaProduto = buscaPorId(id_entradaProduto);
        
        idProduto = entradaProduto.getIdProduto().getIdProduto();
        idFornecedor = entradaProduto.getIdFornecedor().getIdFornecedor();
        quantidade = entradaProduto.getQuantidade();
        valorUnitario = entradaProduto.getValorUnitario();
        dataEntrada = entradaProduto.getDataEntrada();
        
        return "inalter?i=8&faces-redirect=true";
    }
    
    public String excluir(){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=8&faces-redirect=true";
        
        EntradaProdutoDao entradaProdutoDao = new EntradaProdutoDao();
        if (entradaProdutoDao.excluir(entradaProduto)){
            listarEntradas(idProduto);
            TimelineMB tMB = new TimelineMB();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataView = sdf.format(entradaProduto.getDataEntrada());
            
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" removeu a entrada para o produto ["+entradaProduto.getIdProduto().getNome()+"] com Data de ["+dataView+"].", TimelineTipo.AVISO, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Entrada excluída com sucesso!");
            
            if(entradasProduto.isEmpty()) {
                
                UtilMensagens.mensagemInfo("Produto não possui mais entradas!");
                return "list?i=8&faces-redirect=true";
            }
            else return "inlist?i=8&faces-redirect=true";
            
        }
        else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao excluir a entrada!");
            return null;
        }
    }
    
    
    public String listarEntradas(int id_Produto){
        idProduto = id_Produto;
        
        String query = "from EntradaProduto where id_produto";
        query += "=" + id_Produto;        
        query += " order by idEntradaProduto";
        
        EntradaProdutoDao entrDao = new EntradaProdutoDao();
        
        entradasProduto = entrDao.consultarQuery(query);
        
        return "inlist?i=8&faces-redirect=true";
    }
    
    public String nomeProduto() {
        
        ProdutoMB p = new ProdutoMB();
        return p.buscaPorId(idProduto).getNome();
    }

    public String novo(){
    
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=8&faces-redirect=true";
        
        FornecedorMB fMB = new FornecedorMB();
        
        if(fMB.getFornecedores().isEmpty()) {
            
            UtilMensagens.mensagemErro("Nenhum fornecedor cadastrado!");
            return "list?i=8&faces-redirect=true";
        }
        else {
            
            idProduto = null;
            idFornecedor = null;
            quantidade = 0;
            valorUnitario = null;
            dataEntrada = null;

            return "in?i=8&faces-redirect=true";
        }
    }
    
    public EntradaProduto criarEntradaProduto(){
        
        EntradaProduto entradaProduto = new EntradaProduto();
        
        
        ProdutoDao produtoDao = new ProdutoDao();
        Produto produto = produtoDao.consultar(idProduto);
        entradaProduto.setIdProduto(produto);
        
        FornecedorDao fornecedorDao = new FornecedorDao();
        Fornecedor fornecedor = fornecedorDao.consultar(idFornecedor);
        entradaProduto.setIdFornecedor(fornecedor);        
        
        entradaProduto.setQuantidade(quantidade);
        entradaProduto.setValorUnitario(valorUnitario);
        entradaProduto.setDataEntrada(dataEntrada);
         
        
        return entradaProduto;
    }

    
    public String gravar(){
        EntradaProdutoDao entradaProdutoDao = new EntradaProdutoDao();
        
        if (entradaProdutoDao.gravar(criarEntradaProduto())){
            listar();
            entradaProduto = new EntradaProduto();
            entradaProduto.setIdProduto(new ProdutoMB().buscaPorId(idProduto));
            entradaProduto.setQuantidade(quantidade);
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" realizou a entrada do produto ["+entradaProduto.getIdProduto().getNome()+"] - Quantidade: ["+entradaProduto.getQuantidade()+"].", TimelineTipo.INFO, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Entrada inserida com sucesso!");
            return "list?i=8&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao inserir a entrada!");
            return null;
        }            
    }
    
    public EntradaProduto alterarEntradaProduto(){
        
        ProdutoDao produtoDao = new ProdutoDao();
        Produto produto = produtoDao.consultar(idProduto);
        entradaProduto.setIdProduto(produto);
        
        FornecedorDao fornecedorDao = new FornecedorDao();
        Fornecedor fornecedor = fornecedorDao.consultar(idFornecedor);
        entradaProduto.setIdFornecedor(fornecedor);

        entradaProduto.setQuantidade(quantidade);
        entradaProduto.setValorUnitario(valorUnitario);
        entradaProduto.setDataEntrada(dataEntrada);
        
        return entradaProduto;
    }
    
    
    public String alterar(){
        EntradaProdutoDao entradaProdutoDao = new EntradaProdutoDao();
        if (entradaProdutoDao.alterar(alterarEntradaProduto())){
            listar();
            TimelineMB tMB = new TimelineMB();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataView = sdf.format(entradaProduto.getDataEntrada());            
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" atualizou a entrada para o produto ["+entradaProduto.getIdProduto().getNome()+"] com Data de ["+dataView+"].", TimelineTipo.ALERTA, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Os dados da entrada foram atualizados!");
            return "inlist?i=8&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao alterar os dados da entrada!");
            return null;
        }
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

    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
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

    public EntradaProduto getEntradaProduto() {
        return entradaProduto;
    }

    public void setEntradaProduto(EntradaProduto entradaProduto) {
        this.entradaProduto = entradaProduto;
    }

    public List<EntradaProduto> getEntradasProduto() {
        return entradasProduto;
    }

    public void setEntradasProduto(List<EntradaProduto> entradasProduto) {
        this.entradasProduto = entradasProduto;
    }
    
}
