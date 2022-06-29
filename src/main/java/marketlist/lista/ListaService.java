package marketlist.lista;

import marketlist.produto.*;
import marketlist.produto.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import marketlist.mercado.Mercado;
import marketlist.mercado.MercadoService;

@Stateless
public class ListaService {

    @Inject 
    private ProdutoService produtoService;
    @Inject 
    private MercadoService mercadoService;
     
    @PersistenceContext(unitName = "marketlistPU")
    private EntityManager entityManager;

    public Lista findById(Long id) {
        Lista lista = entityManager.find(Lista.class, id);
        if(lista == null) {
            throw new NotFoundException("Lista com o id " + id + " não encontrada");
        }
        return lista;
    }

    public Lista add(Lista lista) {
        validaExistenciaLista(lista);
        validaNome(lista);
        validaMercados(lista);
        validaProdutos(lista);
        entityManager.persist(lista);
        return lista;
    }

    public void remove(Lista lista) {
        entityManager.remove(findById(lista.getId()));
    }

    public Lista update(Lista listaAtualizada) {
        findById(listaAtualizada.getId());
        validaNome(listaAtualizada);
        validaMercados(listaAtualizada);
        validaProdutos(listaAtualizada);
        entityManager.merge(listaAtualizada);
        return listaAtualizada;
    }

    public List<Lista> findAll() {
        // JPQL         
        return entityManager
                .createQuery("SELECT l FROM Lista l", Lista.class)
                //                .createNativeQuery("SELECT * FROM produtos", Lista.class)
                .getResultList();
    }

    public List<Lista> search(String descricao) {
        return entityManager
                .createQuery("SELECT l FROM Lista l WHERE LOWER(p.descricao) LIKE :descricao", Lista.class) // JPQL
                .setParameter("descricao", "%" + descricao.toLowerCase() + "%")
                .getResultList();
    }
    
    private void validaNome(Lista jogo) {
        if (jogo.getDescricao().length() < 4) {
            throw new BadRequestException("O nome da lista não pode conter menos que quatro caracteres");
        }
    }

    private void validaExistenciaLista(Lista lista) {
        List<Lista> resultList = entityManager
                .createQuery("SELECT l FROM Lista l WHERE LOWER(l.descricao) = :descricao", Lista.class)
                .setParameter("descricao", lista.getDescricao().toLowerCase())
                .getResultList();
        
        if (resultList != null && !resultList.isEmpty()) {
            throw new BadRequestException("A lista  já está cadastrado em nossa base");
        }
    }
    
    
    private void validaProdutos(Lista lista) {
        if (lista.getProdutos().isEmpty()){
            throw new BadRequestException("Necessário inserir ao menos 1 produto na lista.");
        } else {
           for (Produto produto : lista.getProdutos()){
            produtoService.findById(produto.getId());
            } 
        } 
    }
    
    private void validaMercados(Lista lista) {              
        if (lista.getMercados().isEmpty()){
            throw new BadRequestException("Necessário inserir ao menos 1 Mercado na lista.");
        } else {
           for (Mercado mercado : lista.getMercados()){
             mercadoService.findById(mercado.getId());
           }          
        }     
    }
    
    public List<Lista> searchProdutos(String descricao){
         return entityManager
                .createQuery("SELECT l FROM Lista l INNER JOIN l.produtos lp WHERE LOWER(lp.descricao) like :nome", Lista.class)
                .setParameter("nome", "%" + descricao.toLowerCase() + "%")
                .getResultList();             
    }
    
    public List<Lista> searchMercados(String descricao){
         return entityManager
                .createQuery("SELECT l FROM Lista l INNER JOIN l.mercados lm WHERE LOWER(lm.nome) like :nome", Lista.class)
                .setParameter("nome", "%" + descricao.toLowerCase() + "%")
                .getResultList(); 
    }
    
    public double getPrecoMedio(){
        List<Lista> listas = findAll();
        double retorno = 0.00;
        if (listas.size() > 0 ){
         for(Lista lista : listas){
            for (Produto produto : lista.getProdutos()){
                retorno = retorno + produto.getPreco();
            }
         }
         retorno = retorno / listas.size();
        } else {
            retorno = 0.00;
        }       
        return retorno;        
    }
    
    
}
