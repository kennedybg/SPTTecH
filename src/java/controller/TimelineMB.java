package controller;

import dao.TimelineDao;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Timeline;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author Kennedy
 */

@ManagedBean(name = "timelineMB")
@ViewScoped
public final class TimelineMB implements Serializable{
    
    private List<Timeline> registros = new ArrayList<>();
    
    
    public TimelineMB() {
                
    }
    
    @PostConstruct
    public void listar() {
        
        try {
        
            TimelineDao timeD = new TimelineDao();    
            registros = new ArrayList<>();
            registros = timeD.listar();            
        }
        catch(Exception e) {
            System.out.println("ERRO: A listagem da timeline não foi completada!");
            e.printStackTrace();
        }        
    }
    
    public void inserir(String mensagem, String tipo, String nivel){
        
        TimelineDao timeD = new TimelineDao();         
        Timeline timeline = new Timeline();
        
        timeline.setTipo(tipo);
        timeline.setRegistro(mensagem);
        timeline.setData(new Date());        
        timeline.setNivel(nivel);
        timeD.gravar(timeline);        
        listar();                
    }
    
    public String formatarData(Date dt) {
        
        Date dataAtual = new Date();
        Date dataRegistro = dt;
        
        SimpleDateFormat horas = new SimpleDateFormat( "HH:mm" );
        SimpleDateFormat diaRegistro = new SimpleDateFormat( "dd/MM/yyyy" );
        
        String horaFormatada = horas.format(dt);               
        String dataFormatada;
        String dia;
                        
        int diferenca = Days.daysBetween(new DateTime(dataRegistro),new DateTime(dataAtual)).getDays();
        
        switch (diferenca) {
            case 1:
                dia = "ONTEM";
                break;
            case 0:
                dia = "HOJE";
                break;
            default:
                dia = diaRegistro.format(dataRegistro);
                break;
        }        
                
        //Retorno
        dataFormatada = "["+dia+" às "+horaFormatada+"] ";        
            
        return dataFormatada;
    }
    
    
    public List<Timeline> getRegistros() {
        return registros;
    }   
}
