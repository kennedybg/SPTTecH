package controller;

import dao.ServicoDao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;
import model.Servico;
import util.NivelAcesso;
import util.UtilMensagens;

@ManagedBean(name = "servicoMB")
@SessionScoped
public class ServicoMB implements Serializable {

    private String descricao;
    private double valor;
    private String prazo_minimo;
    private String garantia;
    private Servico servico;
    private List<Servico> servicos;
    
    public ServicoMB() {
        listar();
    }
    
    public void listar(){
        ServicoDao servicoDao = new ServicoDao();
        servicos = servicoDao.listar();
    }
    
    public String novo(){
        servico = new Servico();
        descricao = null;
        valor = 0;
        prazo_minimo = null;
        garantia = null;
        return "new?i=5&faces-redirect=true";
    }
    
    public Servico buscaPorId(int id_servico) {
        
        Servico service = new Servico();
        ServicoDao serviceDao = new ServicoDao();
        service = serviceDao.consultar(id_servico);        
        
        
        return service;
    }

    
    public String alteracao(int id_servico){
        
        servico = buscaPorId(id_servico);
        descricao = servico.getDescricao();
        valor = servico.getValor();
        prazo_minimo = servico.getPrazoMinimo();
        garantia = servico.getGarantia();
        return "alter?i=5&faces-redirect=true";
    }
    
    public Servico criarServico(){
        servico.setDescricao(descricao);
        servico.setValor(valor);
        servico.setPrazoMinimo(prazo_minimo);
        servico.setGarantia(garantia);
        return servico;
    }
    
    public Servico alterarServico(){
        servico.setDescricao(descricao);
        servico.setValor(valor);
        servico.setPrazoMinimo(prazo_minimo);
        servico.setGarantia(garantia);
        return servico;
    }
    
    public String gravar(){
        ServicoDao servicoDao = new ServicoDao();
        if (servicoDao.gravar(criarServico())){
            UtilMensagens.mensagemInfo("Serviço gravado com sucesso!");
            listar();
            return "list?i=5&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Erro ao gravar o serviço!");
            return "new?i=5&faces-redirect=true";
        }
    }
    
    public String alterar(){
        ServicoDao servicoDao = new ServicoDao();
        if (servicoDao.alterar(alterarServico())){
            UtilMensagens.mensagemInfo("Serviço alterado com sucesso!");
            listar();
            return "list?i=5&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Erro ao alterar o serviço!");
            return "alter?i=5&faces-redirect=true";
        }
    }
    
    public String excluir(){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=5&faces-redirect=true";
        
        ServicoDao servicoDao = new ServicoDao();
        if (servicoDao.excluir(servico)){
            UtilMensagens.mensagemInfo("Serviço excluído com sucesso!");
            listar();
            return "list?i=5&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Erro ao excluir o serviço!");
            return "alter?i=5&faces-redirect=true";
        }
    }

    public String voltar(){
        return "list?i=5&faces-redirect=true";
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getPrazo_minimo() {
        return prazo_minimo;
    }

    public void setPrazo_minimo(String prazo_minimo) {
        this.prazo_minimo = prazo_minimo;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
    
    
}
