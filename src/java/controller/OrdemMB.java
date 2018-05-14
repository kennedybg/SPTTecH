package controller;

import dao.ClienteDao;
import dao.ModeloDao;
import dao.OrdemDao;
import dao.SaidaProdutoDao;
import dao.ServicoPrestadoDao;
import jasper.Relatorio;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Cliente;
import model.Estoque;
import model.ItemVenda;
import model.Modelo;
import model.Ordem;
import model.SaidaProduto;
import model.Servico;
import model.ServicoPrestado;
import model.Usuario;
import org.primefaces.event.SelectEvent;
import util.NivelAcesso;
import util.TimelineTipo;
import util.UtilMensagens;

@ManagedBean(name = "ordemMB")
@SessionScoped
public class OrdemMB implements Serializable {

    private Date dataInicial;
    private Date dataFinal;
    private int idCliente;
    private int idModelo;
    private int idUsuario;
    private int idProdutoOrdem;
    private int idServicoOrdem;
    private int quantidadeProduto;
    private int quantidadeServico;
    private String descricaoProblema;
    private String descricaoTemp;
    private String statusOrdem;
    private Date dataOs;
    private BigDecimal totalOs;
    private List<Ordem> ordens;
    private Ordem ordem;
    private Integer idOrdemConsulta;
    private List<ItemVenda> itensUtilizados;
    private List<ItemVenda> itensAtualizar;
    private List<ItemVenda> itensExibir;
    private EstoqueMB e = new EstoqueMB();
    private Estoque es;
    private Integer tempEstoque;
    private boolean atualizacao;
    private int usuarioRel;
    private int clienteRel;
    
    public OrdemMB() {
      
      dataOs = new Date();
    }  
    
    public String novo(){
        
        ordem = new Ordem();
        dataInicial = null;
        dataFinal = null;
        idCliente = 0;
        idModelo = 0;
        idUsuario = 0;
        idOrdemConsulta = 0;
        idProdutoOrdem = 0;
        idServicoOrdem = 0;
        quantidadeProduto = 1;
        quantidadeServico = 1;
        descricaoProblema = null;
        descricaoTemp = null;
        itensUtilizados = new ArrayList<>();
        itensAtualizar = new ArrayList<>();
        itensExibir = new ArrayList<>();
        tempEstoque = 0;
        atualizacao = false;
        clienteRel = 0;
        usuarioRel = 0;
                        
        return "new?i=6&faces-redirect=true";
    }
    
    public void listarRelUsuario(){
        OrdemDao ordemDao = new OrdemDao();
        ordens = new ArrayList();
        ordens = ordemDao.listarRelUsuario(usuarioRel);
    }
    public void listarRelCliente(){
        OrdemDao ordemDao = new OrdemDao();
        ordens = new ArrayList();
        ordens = ordemDao.listarRelCliente(clienteRel);
    }
    
    public void gerarRelatorio() {
                      
        listarTodas();        
        Relatorio relatorio = new Relatorio();
        relatorio.getRelatorioOrdem(ordens);        
    }
    
    public void gerarRelatorioCliente() {
                      
        listarRelCliente();
        Relatorio relatorio = new Relatorio();
        relatorio.getOrdensPorCliente(ordens);
    }
    
    public void gerarRelatorioUsuario() {
                      
        listarRelUsuario();
        Relatorio relatorio = new Relatorio();
        relatorio.getOrdensPorUsuario(ordens);
    }

    public String listarTodas() {
        
        OrdemDao ordemDao = new OrdemDao();
        ordens = new ArrayList();
        ordens = ordemDao.listar();
        
        
        return "list?i=6&faces-redirect=true";
    }
    
    public Ordem criarOrdem(){
        
            
        ordem = new Ordem();

        ordem.setDataOrdem(dataOs);

        ClienteDao clienteDao = new ClienteDao();
        Cliente cliente = clienteDao.consultar(idCliente);
        ordem.setIdCliente(cliente);

        ModeloDao modeloDao = new ModeloDao();
        Modelo modelo = modeloDao.consultar(idModelo);
        ordem.setIdModelo(modelo);

        Usuario u = new Usuario();
        u.setIdUsuario(1);

        ordem.setIdUsuario(u);
        ordem.setProblema(descricaoProblema);
        ordem.setStatusOrdem("execucao"); 
        
        
        return ordem;
    }
    
    private boolean jaAdicionado(int id, char tipo) {
        
        return (atualizacao ? itensAtualizar : itensUtilizados).stream().anyMatch((i) -> (i.getIdItem() == id && i.getTipo() == tipo));
    }
    
    private void atualizarAdicionado(int id, int qtde, char tipo) {
        
        (atualizacao ? itensAtualizar : itensUtilizados).stream().filter((i) -> (i.getIdItem() == id && i.getTipo() == tipo)).map((i) -> {
            i.setQuantidade(qtde+i.getQuantidade());
            return i;
        }).forEach((i) -> {
            i.setValorTotal(i.getValorUnitario().multiply(new BigDecimal(i.getQuantidade())));
        });        
  
    }
    
    private void defineTempEstoque(int id_produto) {
        
        es = e.buscaPorIdProduto(id_produto);
        
        if(es == null) tempEstoque = 0;
        
        else tempEstoque = es.getQuantidade();
        
        if(tempEstoque > 0) (atualizacao ? itensAtualizar : itensUtilizados).stream().filter((i) -> (i.getIdItem() == id_produto)).forEach((i) -> {
                
            tempEstoque = tempEstoque - i.getQuantidade();            
        });               
    }
    
    public String adicionarProdutoOrdem() {
            
        if(quantidadeProduto > 0 && idProdutoOrdem >0) {
            
        
            ItemVenda i = new ItemVenda();                       
            
            defineTempEstoque(idProdutoOrdem);

            if(tempEstoque == 0) UtilMensagens.mensagemErro("Produto indisponível! Estoque: "+tempEstoque);

            else if(tempEstoque < quantidadeProduto) UtilMensagens.mensagemErro("Quantidade excedeu o estoque! Estoque: "+tempEstoque);

            else {

                if(!jaAdicionado(idProdutoOrdem,'p')) {

                    i.setIdItem(idProdutoOrdem);
                    i.setQuantidade(quantidadeProduto);
                    i.setValorUnitario(es.getValorUnitario());
                    BigDecimal b = new BigDecimal(quantidadeProduto);
                    i.setValorTotal(es.getValorUnitario().multiply(b));
                    i.setTipo('p');
                    i.setAtualizar(i.isAtualizar());
                    (atualizacao ? itensAtualizar : itensUtilizados).add(i);                    
                }

                else atualizarAdicionado(idProdutoOrdem, quantidadeProduto,'p');                                                                            
                
                //Atualiza a view de produtos e servicos (Lista)
                itensExibir.clear();
                itensExibir.addAll(itensUtilizados);
                itensExibir.addAll(itensAtualizar);
            }
            
        }
        else UtilMensagens.mensagemErro("Nenhum produto selecionado!");
            
        quantidadeProduto = 1;              
                
        return "list?i=10&faces-redirect=true";
    }
    
    public String adicionarServicoOrdem() {
            
        if(quantidadeServico > 0 && idServicoOrdem >0) {
            
        
            ItemVenda i = new ItemVenda(); 
            ServicoMB svMB = new ServicoMB();
            Servico s = new Servico();
            s = svMB.buscaPorId(idServicoOrdem);

            if(!jaAdicionado(idServicoOrdem,'s')) {

                i.setIdItem(idServicoOrdem);
                i.setQuantidade(quantidadeServico);
                BigDecimal b1 = new BigDecimal(s.getValor());
                i.setValorUnitario(b1);                
                BigDecimal b2 = new BigDecimal(quantidadeServico);
                i.setValorTotal(i.getValorUnitario().multiply(b2));
                i.setTipo('s');
                i.setAtualizar(i.isAtualizar());
                (atualizacao ? itensAtualizar : itensUtilizados).add(i);                                       
            }

            else atualizarAdicionado(idServicoOrdem, quantidadeServico,'s');                                                                            
            
            
                //Atualiza a view de produtos e servicos (Lista)
                itensExibir.clear();
                itensExibir.addAll(itensUtilizados);
                itensExibir.addAll(itensAtualizar);
            
        }
        else UtilMensagens.mensagemErro("Nenhum serviço selecionado!");
            
        quantidadeServico = 1;               
                
        return "list?i=10&faces-redirect=true";
    }
    
    public String removerItemOrdem(ItemVenda item) {
        
        if(atualizacao && !RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "view?i=6&faces-redirect=true";
        
        else if(atualizacao && RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) {
                           
            if(item.getTipo() == 'p' && item.isAtualizar()) {
            SaidaProdutoDao sd = new SaidaProdutoDao();
            SaidaProduto s = new SaidaProduto();
            s = sd.consultar(item.getIdRegistro());
            sd.excluir(s);                

            }
            else if(item.getTipo() == 's' && item.isAtualizar()) {
                ServicoPrestadoDao sp = new ServicoPrestadoDao();
                ServicoPrestado servp = new ServicoPrestado();
                servp = sp.consultar(item.getIdRegistro());
                sp.excluir(servp);
            }            
            else itensAtualizar.remove(item);
            
                            
            atualizarListas();
        }
        
        else itensUtilizados.remove(item);
       
        
        return (atualizacao ? "view?i=6&faces-redirect=true" : "new?i=6&faces-redirect=true");
    }

    public String gravar(){
                
        OrdemDao ordemDao = new OrdemDao();
                
            if ((atualizacao ? true : ordemDao.gravar(criarOrdem()))){
            
                SaidaProdutoMB sMB = new SaidaProdutoMB();
                ServicoPrestadoMB servMB = new ServicoPrestadoMB();
                
                int ultimo = (atualizacao ? ordem.getIdOrdem() : ordemDao.ultimoId());
                
                for(ItemVenda i : (atualizacao ? itensAtualizar : itensUtilizados)) {
                    if(i.getTipo() == 'p') {

                        sMB.setIdProduto(i.getIdItem());
                        sMB.setDataSaida(dataOs);
                        sMB.setQuantidade(i.getQuantidade());
                        sMB.setValorUnitario(i.getValorUnitario());                        
                        sMB.setIdOrdem(ultimo);
                        sMB.gravar();
                        
                    }
                    else if(i.getTipo() == 's') {
                        
                        servMB.setIdOrdem(ultimo);
                        servMB.setIdServico(i.getIdItem());
                        servMB.setQuantidade(i.getQuantidade());
                        servMB.gravar();
                    }
                }
                            
                listarTodas();
                if(!atualizacao) {
                    TimelineMB tMB = new TimelineMB();
                    tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" registrou uma ordem de serviço.", TimelineTipo.INFO, NivelAcesso.TECNICO);
                    UtilMensagens.mensagemInfo("Ordem registrada com sucesso!");                    
                }
                               
                return (atualizacao ? "" : "list?i=6&faces-redirect=true");
            }
            else UtilMensagens.mensagemErro("Ocorreu um erro ao registrar a ordem!");
        
        return null;            
    }
    
    private Ordem buscaPorId(int id_Ordem) {
        
        Ordem ord = new Ordem();
        OrdemDao ordemDao = new OrdemDao();
        ord = ordemDao.consultar(id_Ordem);        
        
        return ord;
    }
    
    public String buscaProdutos(int id_ordem){
        
        return "from SaidaProduto where idOrdem = "+id_ordem+" order by idSaidaProduto";
    }
    
    public String buscaServicos(int id_ordem){
        
        return "from ServicoPrestado where idOrdem = "+id_ordem+" order by idServicoPrestado";
    }
    
    private void atualizarListas() {
        
        SaidaProdutoDao saidaDao = new SaidaProdutoDao();
        ServicoPrestadoDao servicoDao = new ServicoPrestadoDao();
         
        List<SaidaProduto> produtosUtilizados = new ArrayList<>();
        produtosUtilizados = saidaDao.consultarQuery(buscaProdutos(ordem.getIdOrdem()));
        
        List<ServicoPrestado> servicosUtilizados = new ArrayList<>();
        servicosUtilizados = servicoDao.consultarQuery(buscaServicos(ordem.getIdOrdem()));
        
        itensUtilizados = new ArrayList<>();
        ItemVenda i2 = new ItemVenda();
        
        for(SaidaProduto s : produtosUtilizados) {
            i2 = new ItemVenda();
            i2.setIdItem(s.getIdProduto().getIdProduto());
            i2.setQuantidade(s.getQuantidade());
            i2.setTipo('p');
            i2.setValorUnitario(s.getValorUnitario());            
            BigDecimal b = new BigDecimal(s.getQuantidade());            
            i2.setValorTotal(i2.getValorUnitario().multiply(b));
            i2.setIdRegistro(s.getIdSaidaProduto());
            i2.setAtualizar(true);
            itensUtilizados.add(i2);
        }
        
        for(ServicoPrestado s : servicosUtilizados) {
            i2 = new ItemVenda();
            i2.setIdItem(s.getIdServico().getIdServico());
            i2.setQuantidade(s.getQuantidade());
            i2.setTipo('s');
            i2.setValorUnitario(new BigDecimal(s.getIdServico().getValor()));            
            BigDecimal b = new BigDecimal(s.getQuantidade());            
            i2.setValorTotal(i2.getValorUnitario().multiply(b));
            i2.setIdRegistro(s.getIdServicoPrestado());
            i2.setAtualizar(true);
            itensUtilizados.add(i2);
        }
        
        itensExibir.clear();
        itensExibir.addAll(itensUtilizados);
        itensExibir.addAll(itensAtualizar);
        
    }
    
    

    public String detalharOrdem(int id_ordem) {
        
        ordem = buscaPorId(id_ordem);
        idOrdemConsulta = id_ordem;
        idCliente = ordem.getIdCliente().getIdCliente();
        idModelo = ordem.getIdModelo().getIdModelo();
        // idUsuario = 0; Usuario logado
        dataOs = ordem.getDataOrdem();
        descricaoProblema = ordem.getProblema();
        statusOrdem = ordem.getStatusOrdem();
        atualizacao = true;
        
        itensAtualizar = new ArrayList<>();
        itensExibir = new ArrayList<>();        
        
        atualizarListas();
        
        return "view?i=6&faces-redirect=true";
    }
    
    public Ordem alteraOS(){
                        
        ordem.setDataOrdem(dataOs);
        
        ClienteDao cliDao = new ClienteDao();
        Cliente c = new Cliente();
        c = cliDao.consultar(idCliente);
        ordem.setIdCliente(c);
        
        ModeloDao modDao = new ModeloDao();
        Modelo m = new Modelo();
        m = modDao.consultar(idModelo);
        ordem.setIdModelo(m);
        
        Usuario u = new Usuario();
        u.setIdUsuario(1);
        ordem.setIdUsuario(u);        
        
        ordem.setProblema(descricaoProblema);
        ordem.setStatusOrdem(statusOrdem);
                
                
        return ordem;
    }
    
    public String atualizarOrdem(){
        OrdemDao ordemDao = new OrdemDao();
        if (ordemDao.alterar(alteraOS())){
            
            gravar();
            
            listarTodas();
            
            atualizacao = false;
            
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" atualizou a ordem de Nº"+ordem.getIdOrdem()+".", TimelineTipo.NOTE, NivelAcesso.TECNICO);
            
            UtilMensagens.mensagemInfo("Os dados da ordem foram atualizados!");
            return "list?i=6&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao alterar os dados da ordem!");
            return null;
        }
    }
    
    public String pesquisarPorFiltro() {
       
       
        
        
       return "list?i=6&faces-redirect=true";
    }
    
    
    
    public void resumoOrdemSelecionada(SelectEvent event) {
        
        descricaoTemp = ((Ordem) event.getObject()).getProblema();
        
        UtilMensagens.mensagemInfo(descricaoTemp);        
    }
 
    public String voltar(){
        return "select?i=6&faces-redirect=true";
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
    }

    public int getIdProdutoOrdem() {
        return idProdutoOrdem;
    }

    public void setIdProdutoOrdem(int idProdutoOrdem) {
        this.idProdutoOrdem = idProdutoOrdem;
    }

    public Date getDataOs() {
        return dataOs;
    }

    public void setDataOs(Date dataOs) {
        this.dataOs = dataOs;
    }

    public BigDecimal getTotalOs() {
        return totalOs;
    }

    public void setTotalOs(BigDecimal totalOs) {
        this.totalOs = totalOs;
    }

    public List<Ordem> getOrdens() {
        return ordens;
    }

    public void setOrdens(List<Ordem> ordens) {
        this.ordens = ordens;
    }

    public Ordem getOrdem() {
        return ordem;
    }

    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }

    public Integer getIdOrdemConsulta() {
        return idOrdemConsulta;
    }

    public void setIdOrdemConsulta(Integer idOrdemConsulta) {
        this.idOrdemConsulta = idOrdemConsulta;
    }

    public int getIdServicoOrdem() {
        return idServicoOrdem;
    }

    public void setIdServicoOrdem(int idServicoOrdem) {
        this.idServicoOrdem = idServicoOrdem;
    }

    public int getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(int quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    public int getQuantidadeServico() {
        return quantidadeServico;
    }

    public void setQuantidadeServico(int quantidadeServico) {
        this.quantidadeServico = quantidadeServico;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getStatusOrdem() {
        return statusOrdem;
    }

    public void setStatusOrdem(String statusOrdem) {
        this.statusOrdem = statusOrdem;
    }

    public String getDescricaoTemp() {
        return descricaoTemp;
    }

    public void setDescricaoTemp(String descricaoTemp) {
        this.descricaoTemp = descricaoTemp;
    }

    public List<ItemVenda> getItensUtilizados() {
        return itensUtilizados;
    }

    public void setItensUtilizados(List<ItemVenda> itensUtilizados) {
        this.itensUtilizados = itensUtilizados;
    }

    public boolean isAtualizacao() {
        return atualizacao;
    }

    public void setAtualizacao(boolean atualizacao) {
        this.atualizacao = atualizacao;
    }

    public List<ItemVenda> getItensExibir() {
        return itensExibir;
    }

    public void setItensExibir(List<ItemVenda> itensExibir) {
        this.itensExibir = itensExibir;
    }

    public int getUsuarioRel() {
        return usuarioRel;
    }

    public void setUsuarioRel(int usuarioRel) {
        this.usuarioRel = usuarioRel;
    }

    public int getClienteRel() {
        return clienteRel;
    }

    public void setClienteRel(int clienteRel) {
        this.clienteRel = clienteRel;
    }
    
    
    
}
