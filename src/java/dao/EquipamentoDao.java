package dao;

import java.util.List;
import model.Equipamento;
import org.hibernate.Session;
import util.HibernateUtil;

public class EquipamentoDao {
    
    
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
        
    public List<Equipamento> listar(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            List<Equipamento> lista = session.createQuery("from Equipamento order by idEquipamento").list();
            session.getTransaction().commit();
            return lista;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
    
    public Equipamento consultar(Integer idEquipamento){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            Equipamento equipamento = (Equipamento)session.createQuery("from Equipamento where idEquipamento = " + idEquipamento).uniqueResult();
            session.getTransaction().commit();
            return equipamento;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean gravar(Equipamento equipamento){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.save(equipamento);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean alterar(Equipamento equipamento){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.update(equipamento);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir(Equipamento equipamento){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try{
            session.delete(equipamento);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }    
}
