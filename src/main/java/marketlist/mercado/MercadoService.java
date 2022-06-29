package marketlist.mercado;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import marketlist.lista.Lista;
import marketlist.lista.ListaService;
import marketlist.mercado.Mercado;

@Stateless
public class MercadoService {

    @PersistenceContext(unitName = "marketlistPU")
    private EntityManager entityManager;
    
    @Inject
    private ListaService listaService;

    public Mercado findById(Long id) {
        Mercado mercado = entityManager.find(Mercado.class, id);
        if(mercado == null) {
            throw new NotFoundException("Mercado com o id " + id + " não encontrado");
        }
        return mercado;
    }

    public Mercado add(Mercado mercado) {
        validaExistenciaMercado(mercado);
        validaNome(mercado);
        validaContato(mercado);
        validaEndereco(mercado);
        entityManager.persist(mercado);
        return mercado;
    }

    public void remove(long id) {
        Mercado mercadinho = findById(id);
        validaDependencia(mercadinho);
        entityManager.remove(mercadinho);
    }

    public Mercado update(Mercado mercadoAtualizada) {
        findById(mercadoAtualizada.getId());
        validaContato(mercadoAtualizada);
        validaNome(mercadoAtualizada);
        validaEndereco(mercadoAtualizada);
        entityManager.merge(mercadoAtualizada);
        return mercadoAtualizada;
    }

    public List<Mercado> findAll() {
        // JPQL         
        return entityManager
                .createQuery("SELECT m FROM Mercado m", Mercado.class)
                .getResultList();
    }

    public List<Mercado> search(String nome) {
        return entityManager
                .createQuery("SELECT m FROM Mercado m WHERE LOWER(m.nome) LIKE :nome", Mercado.class) // JPQL
                .setParameter("nome", "%" + nome.toLowerCase() + "%")
                .getResultList();
    }
    
   private void validaNome(Mercado mercado) {
       if (mercado.getNome() == null){
         throw new BadRequestException("Nome do mercado não preenchido.");
       } else  if (mercado.getNome().trim().length() < 5) {
         throw new BadRequestException("O nome do Mercado não pode conter menos que 5 caracteres");
       } else if (mercado.getNome().trim().length() > 80) {
          throw new BadRequestException("O nome do Mercado não pode conter mais que 80 caracteres"); 
       } 

    }
   
   private void validaContato(Mercado mercado) {
       if (mercado.getContato() == null ){
           throw new BadRequestException("O Contato deve ser preenchido.");
       } else if (mercado.getContato().length() < 8) {
           throw new BadRequestException("Contato deve conter ao menos 8 caracteres."); 
       }
    }
   
   private void validaEndereco(Mercado mercado) {
       if (mercado.getEndereco() == null){
           throw  new BadRequestException("O Endereço deve ser preenchido.");
       } else if (mercado.getEndereco().trim().length() < 5) {
           throw new BadRequestException("O Endeceço não pode conter menos que 5 caraceteres."); 
        } else if (mercado.getEndereco().trim().length() > 80) {
           throw new BadRequestException("O Endeceço não deve conter  mais  que 80 caraceteres.");
        }
    }   

    private void validaExistenciaMercado(Mercado mercado) {
        List<Mercado> resultList = entityManager
                .createQuery("SELECT m FROM Mercado m WHERE LOWER(m.nome) = :nome", Mercado.class)
                .setParameter("nome", mercado.getNome().toLowerCase())
                .getResultList();
        
        if (resultList != null && !resultList.isEmpty()) {
            throw new BadRequestException("O Mercado já está cadastrado em nossa base");
        }
    }
    
        private void validaDependencia(Mercado mercado){
            List<Lista> mercados = listaService.searchMercados(mercado.getNome());
            if (!mercados.isEmpty()){
                throw new BadRequestException("Erro ao deletar, Mercado Relacionado a uma lista de compra. Inative o Mercado");
        }
    }

}
