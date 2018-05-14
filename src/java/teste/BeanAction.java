package teste;

/**
 *
 * @author Kennedy
 * @param <Bean>
 * @param <Tipo>
 */
public interface BeanAction<Bean,Tipo> {
    
    public void cadastrar(Bean b);
    
    public void remover(Tipo tp);
    
}
