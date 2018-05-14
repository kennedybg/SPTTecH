package controller;

import dao.FornecedorDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.Fornecedor;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.NivelAcesso;
import util.TimelineTipo;
import util.UtilMensagens;

@ManagedBean(name = "fornecedorMB")
@SessionScoped
public class FornecedorMB implements Serializable {

     
    private String nome;
    private String cnpj;
    private String telefone;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;   
    private String estado;
    private String pais;
    private String email;
    private String site;
    private Fornecedor fornecedor;
    private List<Fornecedor> fornecedores;
    
    public FornecedorMB() {
        FornecedorDao.inicializarSistema();
        listar();
    }
    
    public void listar(){
        FornecedorDao fornecedorDao = new FornecedorDao();
        fornecedores = new ArrayList();
        fornecedores = fornecedorDao.listar();
    }
    
    private Fornecedor buscaPorId(int id_Fornecedor) {
        
        Fornecedor client = new Fornecedor();
        FornecedorDao clientDao = new FornecedorDao();
        client = clientDao.consultar(id_Fornecedor);        
        
        return client;
    }
    
    public String alteracao(int id_Fornecedor){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=9&faces-redirect=true";
        
        fornecedor = buscaPorId(id_Fornecedor);
        
        nome = fornecedor.getNome();  
        cnpj = fornecedor.getCnpj();        
        telefone = fornecedor.getTelefone();
        endereco = fornecedor.getEndereco();
        numero = fornecedor.getNumero();
        bairro = fornecedor.getBairro();
        cidade = fornecedor.getCidade();
        estado = fornecedor.getEstado();
        pais = fornecedor.getPais();
        email = fornecedor.getEmail();
        site = fornecedor.getSite();
        
        return "alter?i=9&faces-redirect=true";
    }
    
    public String excluir(){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=9&faces-redirect=true";
        
        FornecedorDao fornecedorDao = new FornecedorDao();
        if (fornecedorDao.excluir(fornecedor)){
            listar();
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" removeu o fornecedor ["+fornecedor.getNome()+"].", TimelineTipo.AVISO, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Fornecedor excluído com sucesso!");
            return "list?i=9&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao excluir o fornecedor!");
            return null;
        }
    }
    

    public String novo(){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=9&faces-redirect=true";
        
        nome = null;
        cnpj = null;
        telefone = null;
        endereco = null;
        numero = null;
        bairro = null;
        cidade = null;        
        estado = null;
        pais = null;
        email = null;
        site = null;
        
        return "new?i=9&faces-redirect=true";
    }
    
    public Fornecedor criarFornecedor(){
        
        Fornecedor fornecedor = new Fornecedor();
        
        fornecedor.setNome(nome);
        fornecedor.setCnpj(cnpj);
        fornecedor.setTelefone(telefone); 
        fornecedor.setEndereco(endereco);
        fornecedor.setNumero(numero);
        fornecedor.setBairro(bairro);
        fornecedor.setCidade(cidade); 
        fornecedor.setEstado(estado);
        fornecedor.setPais(pais);
        fornecedor.setEmail(email);
        fornecedor.setSite(site);             
        
        return fornecedor;
    }

    
    public String gravar(){
        FornecedorDao fornecedorDao = new FornecedorDao();
        
        if (fornecedorDao.gravar(criarFornecedor())){
            listar();
            fornecedor = new Fornecedor();
            fornecedor.setNome(nome);
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" cadastrou o fornecedor ["+fornecedor.getNome()+"].", TimelineTipo.INFO, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Fornecedor cadastrado com sucesso!");
            return "list?i=9&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao cadastrar o fornecedor!");
            return null;
        }            
    }
    
    public Fornecedor alterarFornecedor(){
        
        fornecedor.setNome(nome);
        fornecedor.setCnpj(cnpj);
        fornecedor.setTelefone(telefone); 
        fornecedor.setEndereco(endereco);
        fornecedor.setNumero(numero);
        fornecedor.setBairro(bairro);
        fornecedor.setCidade(cidade); 
        fornecedor.setEstado(estado);
        fornecedor.setPais(pais);
        fornecedor.setEmail(email);
        fornecedor.setSite(site);
        
        return fornecedor;
    }
    
    
    public String alterar(){
        FornecedorDao fornecedorDao = new FornecedorDao();
        if (fornecedorDao.alterar(alterarFornecedor())){
            listar();
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" atualizou o fornecedor ["+fornecedor.getNome()+"].", TimelineTipo.ALERTA, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Os dados do fornecedor foram atualizados!");
            return "list?i=9&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao alterar os dados do fornecedor!");
            return null;
        }
    }
    
    public String voltar(){
        return "list?i=9&faces-redirect=true";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }        
}
