package controller;

import dao.UsuarioDao;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Usuario;
import util.NivelAcesso;
import util.UtilMensagens;

/**
 *
 * @author Kennedy
 */


@ManagedBean(name = "redqueenMB")
@SessionScoped
public class RedQueenMB implements Serializable {
    
    static private Usuario usuarioSessao;
    private UsuarioMB usuarioMB;
    private UsuarioDao usuarioDao;
    private String loginTemp;
    private String senhaTemp;

    public RedQueenMB() {
        
        UsuarioDao.inicializarSistema();
    }
    
    public static boolean temPermissao(String nivel) {
        
        if(usuarioSessao.getNivelAcesso().equals(nivel)) return true;
        
        else {
            
            UtilMensagens.mensagemErro("Você não tem permissão!");
            return false;
        }
    }
    
    
    
    public String autenticacao(){
        
        UsuarioDao usuarioDao = new UsuarioDao();
        
        if (!usuarioDao.existeUsuario(loginTemp)){
            loginTemp = null;
            senhaTemp = null;
            UtilMensagens.mensagemErro("Usuário ou senha inválidos!");
        }
        else{
            
            if (!usuarioDao.validaSenha(loginTemp, senhaTemp)){
                loginTemp = null;
                senhaTemp = null;
                UtilMensagens.mensagemErro("Usuário ou senha inválidos!");
            }
            else{
                usuarioSessao = usuarioDao.buscaUsuario(loginTemp, senhaTemp);
                return "index?faces-redirect=true";
            }
        }
        return null;
    }

    public static Usuario getUsuarioSessao() {
        return usuarioSessao;
    }
    
    public Usuario getUsuarioSessaoView() {
        return usuarioSessao;
    }

    public String getLoginTemp() {
        return loginTemp;
    }

    public void setLoginTemp(String loginTemp) {
        this.loginTemp = loginTemp;
    }

    public String getSenhaTemp() {
        return senhaTemp;
    }

    public void setSenhaTemp(String senhaTemp) {
        this.senhaTemp = senhaTemp;
    }   
}
