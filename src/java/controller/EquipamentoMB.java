package controller;

import dao.EquipamentoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.Equipamento;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.NivelAcesso;
import util.TimelineTipo;
import util.UtilMensagens;

@ManagedBean(name = "equipamentoMB")
@SessionScoped
public class EquipamentoMB implements Serializable {

     
    private String nome;   
    private Equipamento equipamento;
    private List<Equipamento> equipamentos;
    
    public EquipamentoMB() {
        EquipamentoDao.inicializarSistema();
        listar();
    }
    
    public void listar(){
        EquipamentoDao equipamentoDao = new EquipamentoDao();
        equipamentos = new ArrayList();
        equipamentos = equipamentoDao.listar();
    }
    
    public Equipamento buscaPorId(int id_Equipamento) {
        
        Equipamento equipament = new Equipamento();
        EquipamentoDao clientDao = new EquipamentoDao();
        equipament = clientDao.consultar(id_Equipamento);        
        
        return equipament;
    }
    
    public String buscaNomeEquipamento(Equipamento e){
        
        Equipamento eq = new Equipamento();
        EquipamentoDao clientDao = new EquipamentoDao();
        eq = clientDao.consultar(e.getIdEquipamento());
        
        return eq.getNome();
    }
    
    public String alteracao(int id_Equipamento){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=2&faces-redirect=true";
        
        equipamento = buscaPorId(id_Equipamento);
        
        nome = equipamento.getNome();
        
        return "alter?i=2&faces-redirect=true";
    }
    
    public String excluir(){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=2&faces-redirect=true";
        
        EquipamentoDao equipamentoDao = new EquipamentoDao();
        if (equipamentoDao.excluir(equipamento)){
            listar();
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" removeu o equipamento "+equipamento.getNome()+".", TimelineTipo.AVISO, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Equipamento excluído com sucesso!");
            return "list?i=2&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao excluir o equipamento!");
            return null;
        }
    }
    

    public String novo(){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=2&faces-redirect=true";
        
        nome = null;
        
        return "new?i=2&faces-redirect=true";
    }
    
    public Equipamento criarEquipamento(){
        
        Equipamento equipamento = new Equipamento();
        
        equipamento.setNome(nome);
        
        return equipamento;
    }

    
    public String gravar(){
        EquipamentoDao equipamentoDao = new EquipamentoDao();
        
        if (equipamentoDao.gravar(criarEquipamento())){
            listar();
            equipamento =  new Equipamento();
            equipamento.setNome(nome);
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" cadastrou um novo equipamento ["+equipamento.getNome()+"].", TimelineTipo.INFO, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Equipamento cadastrado com sucesso!");
            return "list?i=2&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao cadastrar o equipamento!");
            return null;
        }            
    }
    
    public Equipamento alterarEquipamento(){
        
        equipamento.setNome(nome);
        
        return equipamento;
    }
    
    
    public String alterar(){
        EquipamentoDao equipamentoDao = new EquipamentoDao();
        if (equipamentoDao.alterar(alterarEquipamento())){
            listar();
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" atualizou o equipamento ["+equipamento.getNome()+"].", TimelineTipo.ALERTA, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Os dados do equipamento foram atualizados!");
            return "list?i=2&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao alterar os dados do equipamento!");
            return null;
        }
    }
    
    public String voltar(){
        return "list?i=2&faces-redirect=true";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }

}
