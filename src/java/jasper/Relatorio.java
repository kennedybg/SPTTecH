package jasper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import model.Ordem;
import model.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Kennedy
 */
public class Relatorio {
    
    private HttpServletResponse response;
    private FacesContext context;
    private ByteArrayOutputStream baos;
    private InputStream stream;

    public Relatorio() {
        
        this.context = FacesContext.getCurrentInstance();
        this.response = (HttpServletResponse) context.getExternalContext().getResponse();
    }
    
    public void getRelatorioUsuario(List<Usuario> lista) {
        
        stream = this.getClass().getResourceAsStream("/jasper/reportUsuario.jasper");
        Map<String,Object> params =  new HashMap<String,Object>();
        baos = new ByteArrayOutputStream();
        try {
            
            JasperReport report = (JasperReport) JRLoader.loadObject(stream);
            JasperPrint print = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(lista));
            JasperExportManager.exportReportToPdfStream(print, baos);
            
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline; filename=relatorio.pdf");
            
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
            context.responseComplete();
            
        }
        catch (JRException | IOException ex) {
            
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void getRelatorioOrdem(List<Ordem> lista) {
        
        stream = this.getClass().getResourceAsStream("/jasper/reportOrdem.jasper");
        Map<String,Object> params =  new HashMap<String,Object>();
        baos = new ByteArrayOutputStream();
        try {
            
            JasperReport report = (JasperReport) JRLoader.loadObject(stream);
            JasperPrint print = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(lista));
            JasperExportManager.exportReportToPdfStream(print, baos);
            
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline; filename=relatorio.pdf");
            
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
            context.responseComplete();
            
        }
        catch (JRException | IOException ex) {
            
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void getOrdensPorUsuario(List<Ordem> lista) {
        
        stream = this.getClass().getResourceAsStream("/jasper/reportOrdensUsuario.jasper");
        Map<String,Object> params =  new HashMap<String,Object>();
        baos = new ByteArrayOutputStream();
        try {
            
            JasperReport report = (JasperReport) JRLoader.loadObject(stream);
            JasperPrint print = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(lista));
            JasperExportManager.exportReportToPdfStream(print, baos);
            
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline; filename=relatorio.pdf");
            
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
            context.responseComplete();
            
        }
        catch (JRException | IOException ex) {
            
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void getOrdensPorCliente(List<Ordem> lista) {
        
        stream = this.getClass().getResourceAsStream("/jasper/reportOrdensCliente.jasper");
        Map<String,Object> params =  new HashMap<String,Object>();
        baos = new ByteArrayOutputStream();
        try {
            
            JasperReport report = (JasperReport) JRLoader.loadObject(stream);
            JasperPrint print = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(lista));
            JasperExportManager.exportReportToPdfStream(print, baos);
            
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline; filename=relatorio.pdf");
            
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
            context.responseComplete();
            
        }
        catch (JRException | IOException ex) {
            
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    
}
