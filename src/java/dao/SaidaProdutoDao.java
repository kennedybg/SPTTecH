package dao;

import java.util.List;
import model.SaidaProduto;
import org.hibernate.Session;
import util.HibernateUtil;

public class SaidaProdutoDao {
    
    
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
        
    public List<SaidaProduto> listar(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            List<SaidaProduto> lista = session.createQuery("from SaidaProduto order by id_produto").list();
            session.getTransaction().commit();
            return lista;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
    
    public List<SaidaProduto> consultarQuery(String query){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            List<SaidaProduto> saidasProduto = session.createQuery(query).list();
            session.getTransaction().commit();
            return saidasProduto;
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }
    
    public SaidaProduto consultar(Integer idSaidaProduto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            SaidaProduto saidaProduto = (SaidaProduto)session.createQuery("from SaidaProduto where idSaidaProduto = " + idSaidaProduto).uniqueResult();
            session.getTransaction().commit();
            return saidaProduto;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean gravar(SaidaProduto saidaProduto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.save(saidaProduto);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean alterar(SaidaProduto saidaProduto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.update(saidaProduto);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir(SaidaProduto saidaProduto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.delete(saidaProduto);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }    
    
    
}
