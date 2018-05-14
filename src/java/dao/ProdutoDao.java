package dao;

import java.util.List;
import model.Produto;
import org.hibernate.Session;
import util.HibernateUtil;

public class ProdutoDao {
    
    
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
        
    public List<Produto> listar(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            List<Produto> lista = session.createQuery("from Produto order by idProduto").list();
            session.getTransaction().commit();
            return lista;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
    
    public Produto consultar(Integer idProduto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            Produto produto = (Produto)session.createQuery("from Produto where idProduto = " + idProduto).uniqueResult();
            session.getTransaction().commit();
            return produto;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean gravar(Produto produto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.save(produto);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean alterar(Produto produto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.update(produto);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir(Produto produto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.delete(produto);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }    
}
