package controller;

import dao.ClienteDao;
import dao.VendaDao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Venda;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Cliente;
import model.Estoque;
import model.ItemVenda;
import util.NivelAcesso;
import util.TimelineTipo;
import util.UtilMensagens;

@ManagedBean(name = "vendaMB")
@SessionScoped
public class VendaMB implements Serializable {

    private Date dataVenda;
    private Integer idCliente;
    private Integer idProdutoVenda;
    private Integer quantidade;           
    private Integer tempEstoque;
    private Venda venda;
    private List<Venda> vendas;
    private List<ItemVenda> itensVenda;
    
    private EstoqueMB e = new EstoqueMB();
    private Estoque es;
        
    public VendaMB() {
        VendaDao.inicializarSistema();
        listar();
    }
    
    public void listar(){
        VendaDao vendaDao = new VendaDao();
        vendas = new ArrayList();
        vendas = vendaDao.listar();
    }
    
    public Venda buscaPorId(int id_Venda) {
        
        Venda client = new Venda();
        VendaDao clientDao = new VendaDao();
        client = clientDao.consultar(id_Venda);        
        
        return client;
    }
    
    public String alteracao(int id_Venda){
        
        venda = buscaPorId(id_Venda);
        dataVenda = venda.getDataVenda();
        idCliente = venda.getIdVenda();
        
        return "alter?i=10&faces-redirect=true";
    }
    
    public String excluir(){
        VendaDao vendaDao = new VendaDao();
        if (vendaDao.excluir(venda)){
            listar();
            UtilMensagens.mensagemInfo("Venda excluída com sucesso!");
            return "list?i=10&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao excluir a venda!");
            return null;
        }
    }
    

    public String novo(){
        
        dataVenda = null;
        idCliente = null;
        idProdutoVenda = null;
        itensVenda = new ArrayList();        
        tempEstoque = 0;
        quantidade = 1;
        
        return "new?i=10&faces-redirect=true";
    }
    
    private boolean jaAdicionado(int id) {
        
        return itensVenda.stream().anyMatch((i) -> (i.getIdItem() == id));
    }
    
    private void atualizarAdicionado(int id, int qtde) {
        
        itensVenda.stream().filter((i) -> (i.getIdItem() == id)).map((i) -> {
            i.setQuantidade(qtde+i.getQuantidade());
            return i;
        }).forEach((i) -> {
            i.setValorTotal(i.getValorUnitario().multiply(new BigDecimal(i.getQuantidade())));
        });        
  
    }
    
    private void defineTempEstoque(int id_produto) {
        
        es = e.buscaPorIdProduto(idProdutoVenda);
        
        if(es == null) tempEstoque = 0;
        
        else tempEstoque = es.getQuantidade();
        
        if(tempEstoque > 0) itensVenda.stream().filter((i) -> (i.getIdItem() == id_produto)).forEach((i) -> {
            tempEstoque = tempEstoque - i.getQuantidade();
        });
                
    }
    
    public String adicionarProdutoVenda() {
            
        if(quantidade >0) {
            
        
            ItemVenda i = new ItemVenda();                       
            
            defineTempEstoque(idProdutoVenda);

            if(tempEstoque == 0) UtilMensagens.mensagemErro("Produto indisponível! Estoque: "+tempEstoque);

            else if(tempEstoque < quantidade) UtilMensagens.mensagemErro("Quantidade excedeu o estoque! Estoque: "+tempEstoque);

            else {

                if(!jaAdicionado(idProdutoVenda)) {

                    i.setIdItem(idProdutoVenda);
                    i.setQuantidade(quantidade);
                    i.setValorUnitario(es.getValorUnitario());
                    BigDecimal b = new BigDecimal(quantidade);
                    i.setValorTotal(es.getValorUnitario().multiply(b));
                    i.setTipo('p');
                    itensVenda.add(i);                    
                }

                else atualizarAdicionado(idProdutoVenda, quantidade);                                                                            
            }
            
            
        }
        else UtilMensagens.mensagemErro("A quantidade deve ser maior que 0 (zero)!");
            quantidade = 1;               
                
        return "dav?i=10&faces-redirect=true";
    }
    
    public String removerProdutoVenda(ItemVenda item) {
        
        itensVenda.remove(item);
        
        return "dav?i=10&faces-redirect=true";
    }
    
    public Venda criarVenda(){
        
        ArrayList<Venda> vendas = new ArrayList();
        
        ClienteDao clienteDao = new ClienteDao();
        Cliente cliente = clienteDao.consultar(idCliente);        
        
        Date data = new Date(System.currentTimeMillis());  
        dataVenda = data;
        
        Venda venda = new Venda();
        venda.setDataVenda(dataVenda);
        venda.setIdCliente(cliente);
        
        return venda;
    }

    public String gravar(){
        VendaDao vendaDao = new VendaDao();
        
        if(!itensVenda.isEmpty()) {
            
            if (vendaDao.gravar(criarVenda())){
            
                SaidaProdutoMB sMB = new SaidaProdutoMB();
                int ultimo = vendaDao.ultimoId();

                itensVenda.stream().map((i) -> {
                    sMB.setIdProduto(i.getIdItem());
                        return i;
                    }).map((i) -> {
                        sMB.setDataSaida(dataVenda);
                        sMB.setQuantidade(i.getQuantidade());
                        return i;
                    }).map((i) -> {            
                        sMB.setValorUnitario(i.getValorUnitario());
                        return i;
                    }).map((_item) -> {
                        sMB.setIdVenda(ultimo);
                        return _item;
                    }).forEach((_item) -> {
                        sMB.gravar();
                    });

                listar();
                TimelineMB tMB = new TimelineMB();
                ClienteMB cMB = new ClienteMB();
                tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" efetuou uma venda para ["+cMB.buscaPorId(idCliente).getNome()+"].", TimelineTipo.NOTE, NivelAcesso.ADMINISTRADOR);
                
                UtilMensagens.mensagemInfo("Venda realizada com sucesso!");
            
                return "dav?i=10&faces-redirect=true";
            }
            else UtilMensagens.mensagemErro("Ocorreu um erro ao realizar a venda!");
            
        }
        else UtilMensagens.mensagemErro("Adicione pelo menos um produto na lista!");
        
        return null;
    }
    
    public Venda alterarVenda(){
        
        venda.setDataVenda(dataVenda);
        ClienteDao clienteDao = new ClienteDao();
        Cliente cliente = clienteDao.consultar(idCliente);        
        venda.setIdCliente(cliente);
   
        
        return venda;
    }
    
    
    public String alterar(){
        VendaDao vendaDao = new VendaDao();
        if (vendaDao.alterar(alterarVenda())){
            listar();
            UtilMensagens.mensagemInfo("Os dados da venda foram atualizados!");
            return "list?i=10&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao alterar os dados da venda!");
            return null;
        }
    }
    
    public String voltar(){
        return "list?i=10&faces-redirect=true";
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    

    public Integer getIdProdutoVenda() {
        return idProdutoVenda;
    }

    public void setIdProdutoVenda(Integer idProdutoVenda) {
        this.idProdutoVenda = idProdutoVenda;
    }
}
