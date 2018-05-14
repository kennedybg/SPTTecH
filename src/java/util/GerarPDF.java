package util;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Kennedy
 */
@ManagedBean(name = "gerarPDF")
@SessionScoped
public class GerarPDF {

    public GerarPDF() {
        
    }
        
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
 
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "icon-relatorio-1.png";
                
        pdf.add(Image.getInstance(logo));
    }
    
}
