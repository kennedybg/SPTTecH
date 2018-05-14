package dao;

import java.util.List;
import model.Usuario;
import org.hibernate.Session;
import util.HashMaker;
import util.HibernateUtil;

public class UsuarioDao {
    
    public static void inicializarSistema(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.getTransaction().commit();
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    
    public List<Usuario> listar(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            List<Usuario> lista = session.createQuery("from Usuario order by idUsuario").list();
            session.getTransaction().commit();
            return lista;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Usuario> listarRel(Integer idUsuario){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            List<Usuario> lista = session.createQuery("from Usuario where idUsuario = "+idUsuario).list();
            session.getTransaction().commit();
            return lista;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
    
    public Usuario consultar(Integer idUsuario){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            Usuario usuario = (Usuario)session.createQuery("from Usuario where idUsuario = " + idUsuario).uniqueResult();
            session.getTransaction().commit();
            return usuario;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean gravar(Usuario usuario){
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.save(usuario);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
        
        
    }
    
    public boolean alterar(Usuario usuario){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.update(usuario);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir(Usuario usuario){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.delete(usuario);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }    
    
    public boolean existeUsuario(String login){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            Usuario usuario = (Usuario)session.createQuery("from Usuario where login = '" + login + "'").uniqueResult();
            session.getTransaction().commit();
            if (usuario != null)
                return true;
            else
                return false;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean validaSenha(String login, String senha){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            Usuario usuario = (Usuario)session.createQuery("from Usuario where login = '" + login + "'").uniqueResult();
            session.getTransaction().commit();
            String senhaMd5 = HashMaker.stringHexa(HashMaker.gerarHash(senha));
            if (usuario.getSenha().equals(senhaMd5))
                return true;
            else
                return false;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    
    public Usuario buscaUsuario(String login, String senha){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        try{
            Usuario usuario = (Usuario)session.createQuery("from Usuario where login = '" + login + "'").uniqueResult();
            session.getTransaction().commit();
            String senhaMd5 = HashMaker.stringHexa(HashMaker.gerarHash(senha));
            if (usuario.getSenha().equals(senhaMd5))
                return usuario;
            else
                return null;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
}
