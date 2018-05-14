package controller;

import dao.ClienteDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.NivelAcesso;
import util.TimelineTipo;
import util.UtilMensagens;

@ManagedBean(name = "clienteMB")
@SessionScoped
public class ClienteMB implements Serializable {

     
    private String nome;
    private String cpf;    
    private String telefone;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;   
    private Cliente cliente;
    private List<Cliente> clientes;
    private String pessoa;
    
    public ClienteMB() {
        ClienteDao.inicializarSistema();
        listar();               
        pessoa = "Física";
    }
    
    public void listar(){
        ClienteDao clienteDao = new ClienteDao();
        clientes = new ArrayList();
        clientes = clienteDao.listar();
    }
    
    public Cliente buscaPorId(int id_Cliente) {
        
        Cliente client = new Cliente();
        ClienteDao clientDao = new ClienteDao();
        client = clientDao.consultar(id_Cliente);        
        
        return client;
    }
    
    public String alteracao(int id_Cliente){
        
        cliente = buscaPorId(id_Cliente);
        
        nome = cliente.getNome();  
        cpf = cliente.getCpf();
        telefone = cliente.getTelefone();
        endereco = cliente.getEndereco();
        numero = cliente.getNumero();
        bairro = cliente.getBairro();
        cidade = cliente.getCidade();
        
        return "alter?i=4&faces-redirect=true";
    }
    
    public String excluir(){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=4&faces-redirect=true";
        
        ClienteDao clienteDao = new ClienteDao();
        if (clienteDao.excluir(cliente)){
            listar();
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" removeu o cliente ["+cliente.getNome()+"].", TimelineTipo.AVISO, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Cliente excluído com sucesso!");
            return "list?i=4&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao excluir o cliente!");
            return null;
        }
    }
    

    public String novo(){
        nome = null;
        cpf = null;
        telefone = null;
        endereco = null;
        numero = null;
        bairro = null;
        cidade = null;
        pessoa = "Física";
        
        return "new?i=4&faces-redirect=true";
    }
    
    public Cliente criarCliente(){
        
        Cliente cliente = new Cliente();
        
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone); 
        cliente.setEndereco(endereco);
        cliente.setNumero(numero);
        cliente.setBairro(bairro);
        cliente.setCidade(cidade);        
        
        return cliente;
    }

    
    public String gravar(){
        ClienteDao clienteDao = new ClienteDao();
        
        if (clienteDao.gravar(criarCliente())){
            listar();
            cliente = new Cliente();
            cliente.setNome(nome);
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" cadastrou o cliente ["+cliente.getNome()+"].", TimelineTipo.INFO, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Cliente cadastrado com sucesso!");
            return "list?i=4&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao cadastrar o cliente!");
            return null;
        }            
    }
    
    public Cliente alterarCliente(){
        
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        cliente.setNumero(numero);
        cliente.setBairro(bairro);
        cliente.setCidade(cidade);
        
        return cliente;
    }
    
    
    public String alterar(){
        ClienteDao clienteDao = new ClienteDao();
        if (clienteDao.alterar(alterarCliente())){
            listar();
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" atualizou o cliente ["+cliente.getNome()+"].", TimelineTipo.ALERTA, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Os dados do cliente foram atualizados!");
            return "list?i=4&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao alterar os dados do cliente!");
            return null;
        }
    }
    
    public String voltar(){
        return "list?i=4&faces-redirect=true";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String getPessoa() {
        return pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = pessoa;
    } 
    
    
}
