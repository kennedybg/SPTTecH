package controller;

import dao.EquipamentoDao;
import dao.ModeloDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.Modelo;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Equipamento;
import util.NivelAcesso;
import util.TimelineTipo;
import util.UtilMensagens;

@ManagedBean(name = "modeloMB")
@SessionScoped
public class ModeloMB implements Serializable {

     
    private String nome;
    private String descricao_tecnica;
    private int tipoEquipamento;
       
    private Modelo modelo;
    private List<Modelo> modelos;
    
    public ModeloMB() {
        ModeloDao.inicializarSistema();
        listar();
    }
    
    public void listar(){
        ModeloDao modeloDao = new ModeloDao();
        modelos = new ArrayList();
        modelos = modeloDao.listar();
    }
    
    public Modelo buscaPorId(int id_Modelo) {
        
        Modelo client = new Modelo();
        ModeloDao clientDao = new ModeloDao();
        client = clientDao.consultar(id_Modelo);        
        
        return client;
    }
    
    public String alteracao(int id_Modelo){
        
        modelo = buscaPorId(id_Modelo);
        
        nome = modelo.getNome();  
        descricao_tecnica = modelo.getDescricaoTecnica();
        tipoEquipamento = modelo.getTipoEquipamento().getIdEquipamento();
        
        return "alter?i=3&faces-redirect=true";
    }
    
    public String excluir(){
        ModeloDao modeloDao = new ModeloDao();
        if (modeloDao.excluir(modelo)){
            listar();            
            UtilMensagens.mensagemInfo("Modelo excluído com sucesso!");
            return "list?i=3&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao excluir o modelo!");
            return null;
        }
    }
    

    public String novo(){
        nome = null;
        descricao_tecnica = null;
        tipoEquipamento = 0;
        
        return "new?i=3&faces-redirect=true";
    }
    
    public Modelo criarModelo(){
        
        Modelo modelo = new Modelo();
        
        modelo.setNome(nome);
        modelo.setDescricaoTecnica(descricao_tecnica);
        
        EquipamentoDao equipamentoDao = new EquipamentoDao();
        Equipamento equipamento = equipamentoDao.consultar(tipoEquipamento);
        modelo.setTipoEquipamento(equipamento);
                
        return modelo;
    }

    
    public String gravar(){
        ModeloDao modeloDao = new ModeloDao();
        
        if (modeloDao.gravar(criarModelo())){
            listar();
            modelo = new Modelo();
            modelo.setNome(nome);
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" cadastrou um novo modelo ["+modelo.getNome()+"]", TimelineTipo.INFO, NivelAcesso.TECNICO);
            
            UtilMensagens.mensagemInfo("Modelo cadastrado com sucesso!");
            return "list?i=3&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao cadastrar o modelo!");
            return null;
        }            
    }
    
    public Modelo alterarModelo(){
        
        modelo.setNome(nome);
        modelo.setDescricaoTecnica(descricao_tecnica);
        EquipamentoDao equipamentoDao = new EquipamentoDao();
        Equipamento equipamento = equipamentoDao.consultar(tipoEquipamento);
        modelo.setTipoEquipamento(equipamento);
        
        return modelo;
    }
    
    
    public String alterar(){
        ModeloDao modeloDao = new ModeloDao();
        if (modeloDao.alterar(alterarModelo())){
            listar();                       
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" atualizou o modelo de Nº "+modelo.getIdModelo()+".", TimelineTipo.NOTE, NivelAcesso.TECNICO);
            UtilMensagens.mensagemInfo("Os dados do modelo foram atualizados!");
            return "list?i=3&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao alterar os dados do modelo!");
            return null;
        }
    }
    
    public String voltar(){
        return "list?i=3&faces-redirect=true";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao_tecnica() {
        return descricao_tecnica;
    }

    public void setDescricao_tecnica(String descricao_tecnica) {
        this.descricao_tecnica = descricao_tecnica;
    }

    public int getTipoEquipamento() {
        return tipoEquipamento;
    }

    public void setTipoEquipamento(int tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }
}
