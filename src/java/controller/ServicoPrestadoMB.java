package controller;

import dao.OrdemDao;
import dao.ServicoDao;
import dao.ServicoPrestadoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.ServicoPrestado;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Ordem;
import model.Servico;
import util.UtilMensagens;

@ManagedBean(name = "servicoPrestadoMB")
@SessionScoped
public class ServicoPrestadoMB implements Serializable {

    private Integer idOrdem;
    private Integer idServico;
    private Integer quantidade;
    private ServicoPrestado servicoPrestado;
    private List<ServicoPrestado> servicosPrestados;
    
    public ServicoPrestadoMB() {
        ServicoPrestadoDao.inicializarSistema();
        listar();
    }
    
    public void listar(){
        ServicoPrestadoDao servicoPrestadoDao = new ServicoPrestadoDao();
        servicosPrestados = new ArrayList();
        servicosPrestados = servicoPrestadoDao.listar();
    }
    
    private ServicoPrestado buscaPorId(int id_ServicoPrestado) {
        
        ServicoPrestado client = new ServicoPrestado();
        ServicoPrestadoDao clientDao = new ServicoPrestadoDao();
        client = clientDao.consultar(id_ServicoPrestado);        
        
        return client;
    }
    
    public String alteracao(int id_ServicoPrestado){
        
        servicoPrestado = buscaPorId(id_ServicoPrestado);
        
        idOrdem = servicoPrestado.getIdOrdem().getIdOrdem();
        idServico = servicoPrestado.getIdServico().getIdServico();
        quantidade = servicoPrestado.getQuantidade();
        return "alter?i=4&faces-redirect=true";
    }
    
    public String excluir(){
        ServicoPrestadoDao servicoPrestadoDao = new ServicoPrestadoDao();
        if (servicoPrestadoDao.excluir(servicoPrestado)){
            listar();
            UtilMensagens.mensagemInfo("ServicoPrestado excluído com sucesso!");
            return "list?i=4&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao excluir o servicoPrestado!");
            return null;
        }
    }
    

    public String novo(){
        
        idOrdem = null;
        idServico = null;
        quantidade = null;
        servicoPrestado = null;
        servicosPrestados = new ArrayList();
        
        return "new?i=4&faces-redirect=true";
    }
    
    public ServicoPrestado criarServicoPrestado(){
        
        ServicoPrestado servicoPrestado = new ServicoPrestado();
        
        OrdemDao ordDao = new OrdemDao();
        Ordem ord = new Ordem();
        ord = ordDao.consultar(idOrdem);
        servicoPrestado.setIdOrdem(ord);
        
        ServicoDao servDao = new ServicoDao();
        Servico sv = new Servico();
        sv = servDao.consultar(idServico);
        servicoPrestado.setIdServico(sv);       
        
        servicoPrestado.setQuantidade(quantidade);
        
        return servicoPrestado;
    }

    
    public void gravar(){
        
        ServicoPrestadoDao servicoPrestadoDao = new ServicoPrestadoDao();        
        servicoPrestadoDao.gravar(criarServicoPrestado());                 
    }
    
    public ServicoPrestado alterarServicoPrestado(){
        
        OrdemDao ordDao = new OrdemDao();
        Ordem ord = new Ordem();
        ord = ordDao.consultar(idOrdem);
        servicoPrestado.setIdOrdem(ord);
        
        ServicoDao servDao = new ServicoDao();
        Servico sv = new Servico();
        sv = servDao.consultar(idServico);
        servicoPrestado.setIdServico(sv);
        
        servicoPrestado.setQuantidade(quantidade);
        
        return servicoPrestado;
    }
    
    
    public String alterar(){
        ServicoPrestadoDao servicoPrestadoDao = new ServicoPrestadoDao();
        if (servicoPrestadoDao.alterar(alterarServicoPrestado())){
            listar();
            UtilMensagens.mensagemInfo("Os dados do servicoPrestado foram atualizados!");
            return "list?i=4&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao alterar os dados do servicoPrestado!");
            return null;
        }
    }
    
    public String voltar(){
        return "list?i=4&faces-redirect=true";
    }

    public Integer getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(Integer idOrdem) {
        this.idOrdem = idOrdem;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public ServicoPrestado getServicoPrestado() {
        return servicoPrestado;
    }

    public void setServicoPrestado(ServicoPrestado servicoPrestado) {
        this.servicoPrestado = servicoPrestado;
    }

    public List<ServicoPrestado> getServicosPrestados() {
        return servicosPrestados;
    }

    public void setServicosPrestados(List<ServicoPrestado> servicosPrestados) {
        this.servicosPrestados = servicosPrestados;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
