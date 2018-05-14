package dao;

import java.util.List;
import model.Ordem;
import org.hibernate.Session;
import util.HibernateUtil;

public class OrdemDao {
    
    public List<Ordem> listar(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            List<Ordem> ordensServico = session.createQuery("from Ordem order by idOrdem").list();
            session.getTransaction().commit();
            return ordensServico;
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }
    
    public List<Ordem> listarRelCliente(Integer id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            List<Ordem> ordensServico = session.createQuery("from Ordem where idCliente = "+id).list();
            session.getTransaction().commit();
            return ordensServico;
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }
    
    public List<Ordem> listarRelUsuario(Integer id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            List<Ordem> ordensServico = session.createQuery("from Ordem where idUsuario = "+id).list();
            session.getTransaction().commit();
            return ordensServico;
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }
    
    public List<Ordem> consultarQuery(String query){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            List<Ordem> ordensServico = session.createQuery(query).list();
            session.getTransaction().commit();
            return ordensServico;
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }
    
    public Ordem consultar(Integer idOrdemServico){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            Ordem ordemServico = (Ordem)session.createQuery("from Ordem where idOrdem = " + idOrdemServico).uniqueResult();
            session.getTransaction().commit();
            return ordemServico;
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }
    
    public boolean gravar(Ordem os){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.save(os);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }
    
    public boolean alterar(Ordem os){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.update(os);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }
    
    public boolean excluir(Ordem os){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.delete(os);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }
    
    public Integer ultimoId(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            Integer idOrdemServico = (Integer)session.createSQLQuery("select max(id_ordem) from Ordem").uniqueResult();
            session.getTransaction().commit();
            return idOrdemServico;
        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }
    
}
