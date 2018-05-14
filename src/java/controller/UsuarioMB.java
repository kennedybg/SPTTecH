package controller;

import dao.UsuarioDao;
import jasper.Relatorio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.HashMaker;
import util.NivelAcesso;
import util.TimelineTipo;
import util.UtilMensagens;

@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB implements Serializable {

     
    private String nome;
    private String login;
    private String senha;
    private String nivel_acesso;
    private String endereco;
    private String numero;
    private String bairro;
    private String telefone;
    private String cpf;
    private Usuario usuario;
    private List<Usuario> usuarios;
    private int usuarioRel;
    
    public UsuarioMB() {
        UsuarioDao.inicializarSistema();
        listar();
    }
    
    
    public void listar(){
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarios = new ArrayList();
        usuarios = usuarioDao.listar();
    }
        
    private Usuario buscaPorId(int id_usuario) {
        
        Usuario user = new Usuario();
        UsuarioDao userDao = new UsuarioDao();
        user = userDao.consultar(id_usuario);        
        
        return user;
    }
    
    public String alteracao(int id_usuario){
    
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=1&faces-redirect=true";
        
        usuario = buscaPorId(id_usuario);
        
        nome = usuario.getNome();        
        login = usuario.getLogin();        
        nivel_acesso = usuario.getNivelAcesso();
        endereco = usuario.getEndereco();
        numero = usuario.getNumero();
        telefone = usuario.getTelefone();
        cpf = usuario.getCpf();
        bairro = usuario.getBairro();
        
        return "alter?i=1&faces-redirect=true";
    }
    
    public String excluir(){
                
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=1&faces-redirect=true";
                        
        UsuarioDao usuarioDao = new UsuarioDao();
        if (usuarioDao.excluir(usuario)){
            listar();
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" removeu o usuario "+usuario.getNome()+".", TimelineTipo.AVISO, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Usuário excluído com sucesso!");
            return "list?i=1&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao excluir o usuário!");
            return null;
        }
                
    }
    

    public String novo(){
        
        if(!RedQueenMB.temPermissao(NivelAcesso.ADMINISTRADOR)) return "list?i=1&faces-redirect=true";
        
        nome = null;
        login = null;
        senha = null;
        nivel_acesso = null;
        endereco = null;
        numero = null;
        telefone = null;
        cpf = null;
        bairro = null;
        
        return "new?i=1&faces-redirect=true";
    }
    
    public Usuario criarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setNome(nome);
        usuario.setNivelAcesso(nivel_acesso);        
        usuario.setEndereco(endereco);
        usuario.setNumero(numero);
        usuario.setBairro(bairro);
        usuario.setCpf(cpf);
        usuario.setTelefone(telefone); 
        
        String senhaMd5 = HashMaker.stringHexa(HashMaker.gerarHash((senha)));
        usuario.setSenha(senhaMd5);
        return usuario;
    }

    
    public String gravar(){
        UsuarioDao usuarioDao = new UsuarioDao();
        
        if (usuarioDao.gravar(criarUsuario())){
            listar();
            usuario = new Usuario();
            usuario.setNome(nome);
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" cadastrou o usuario "+usuario.getNome()+".", TimelineTipo.INFO, NivelAcesso.ADMINISTRADOR);
            
            UtilMensagens.mensagemInfo("Usuário cadastrado com sucesso!");
            return "list?i=1&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao cadastrar o usuário!");
            return null;
        }            
    }
    
    public Usuario alterarUsuario(){
        
        usuario.setLogin(login);
        usuario.setNome(nome);
        usuario.setNivelAcesso(nivel_acesso);        
        usuario.setEndereco(endereco);
        usuario.setNumero(numero);
        usuario.setBairro(bairro);
        usuario.setCpf(cpf);
        usuario.setTelefone(telefone);
        usuario.setNivelAcesso(nivel_acesso);
        
        if (!senha.isEmpty()) {
            
            String senhaMd5 = HashMaker.stringHexa(HashMaker.gerarHash((senha)));
            usuario.setSenha(senhaMd5);            
        }
        
        return usuario;
    }
    
    
    public String alterar(){
        UsuarioDao usuarioDao = new UsuarioDao();
        if (usuarioDao.alterar(alterarUsuario())){
            listar();
            TimelineMB tMB = new TimelineMB();
            tMB.inserir(RedQueenMB.getUsuarioSessao().getNome()+" atualizou o usuario "+usuario.getNome()+".", TimelineTipo.ALERTA, NivelAcesso.ADMINISTRADOR);
            UtilMensagens.mensagemInfo("Os dados do usuário foram atualizados!");
            return "list?i=1&faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Ocorreu um erro ao alterar os dados do usuário!");
            return null;
        }
    }
    
    public String autenticacao(){
        UsuarioDao usuarioDao = new UsuarioDao();
        if (!usuarioDao.existeUsuario(nome)){
            UtilMensagens.mensagemErro("Usuário inexistente");
        }else{
            if (!usuarioDao.validaSenha(nome, senha)){
                UtilMensagens.mensagemErro("Senha inválida");
            }else{
                return "index?faces-redirect=true";
            }
        }
        return null;
    }
    
    public void gerarRelatorio() {
                       
        listar();        
        Relatorio relatorio = new Relatorio();
        relatorio.getRelatorioUsuario(usuarios);
    }
 
    public String voltar(){
        return "list?i=1&faces-redirect=true";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivel_acesso() {
        return nivel_acesso;
    }

    public void setNivel_acesso(String nivel_acesso) {
        this.nivel_acesso = nivel_acesso;
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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
