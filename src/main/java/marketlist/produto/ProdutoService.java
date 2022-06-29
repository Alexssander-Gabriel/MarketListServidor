package marketlist.produto;

import java.beans.Statement;
import java.sql.ResultSet;
import marketlist.produto.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import marketlist.lista.Lista;
import marketlist.lista.ListaService;
import marketlist.produto.dashboard.DashBoardCategoria;


@Stateless
public class ProdutoService {

    @PersistenceContext(unitName = "marketlistPU")
    private EntityManager entityManager;
    
    @Inject
    private ListaService listaService;

    public List<Produto> findAll() {
        // JPQL         
        return entityManager
                .createQuery("SELECT p FROM Produto p", Produto.class)
                .getResultList();
    }
    
    public List<DashBoardCategoria> findCategories(){                
        String sql = "SELECT p.categoria, count(*) FROM Produto p group by p.categoria ";
        return  entityManager.createQuery(sql).getResultList(); 
    }
    
    
    
    public double getPrecoMedio(){
        List<Produto> produto = findAll();
        double retorno = 0.00;
        if (produto.size() > 0) {
         for (Produto prod : produto) {
            retorno = retorno + prod.getPreco();
         }           
         retorno = (retorno / produto.size());
        } else {
            retorno = 0.00;
        }
         
        return retorno;
         
    }
     
    public Produto findById(Long id) {
        Produto produto = entityManager.find(Produto.class, id);
        if(produto == null) {
            throw new NotFoundException("Produto com o id " + id + " não encontrado");
        }
        return produto;
    }

    public Produto add(Produto produto) {
        validaExistenciaProduto(produto);
        validaNome(produto);
        validaPreco(produto);
        validaUrl(produto);
        validaAtivo(produto);
        entityManager.persist(produto);
        return produto;
    }

    public void remove(long id) {
       Produto produtinho = findById(id);
       validaDependencia(produtinho);
        if (produtinho == null){
            throw new NotFoundException("Produto não encontrato na base de dados.");
        }
        entityManager.remove(produtinho);
    }

    public Produto update(Produto produtoAtualizada) {
        long id = produtoAtualizada.getId();
        findById(id);
        validaNome(produtoAtualizada);
        validaPreco(produtoAtualizada);
        validaUrl(produtoAtualizada);
        entityManager.merge(produtoAtualizada);
        return produtoAtualizada;
    }


    public List<Produto> search(String descricao) {
        return entityManager
                .createQuery("SELECT p FROM Produto p WHERE LOWER(p.descricao) LIKE :descricao", Produto.class) // JPQL
                .setParameter("descricao", "%" + descricao.toLowerCase() + "%")
                .getResultList();
    }
    
   private void validaNome(Produto produto) {
        if (produto.getDescricao().length() < 3) {
            throw new BadRequestException("O nome do produto não pode conter menos que 3 caracteres.");
        } else if (produto.getDescricao().length() > 50) {
            throw new BadRequestException("O Nome do produto não pode conter mais que  50 caracteres.");
        }
    }
   
   private void validaUrl(Produto produto) {
        if (produto.getUrlFoto() == null) {
            throw new BadRequestException("O Produto deve conter uma URL de Foto.");
        } else if (produto.getUrlFoto().length() < 20){
            throw new BadRequestException("UrlFoto: conter ao menos 20 caracteres.");
        } else if (produto.getUrlFoto().trim().length() > 200) {
            throw  new BadRequestException("A Url da Foto não pode conter mais que 200 caracteres.");
        }            
    } 
   
   private void validaPreco(Produto produto) {
        if (produto.getPreco() <= 0.00) {
            throw new BadRequestException("O Produto deve conter um Preço maior que 0.");
        }
    }     

    private void validaExistenciaProduto(Produto produto) {
        List<Produto> resultList = entityManager
                .createQuery("SELECT p  FROM Produto p WHERE LOWER(p.descricao) = :descricao", Produto.class)
                .setParameter("descricao", produto.getDescricao().toLowerCase())
                .getResultList();
        
        if (resultList != null && !resultList.isEmpty()) {
            throw new BadRequestException("O Produto já está cadastrado em nossa base.");
        }
    }
    
    private void validaDependencia(Produto produto){
        List<Lista> produtos = listaService.searchProdutos(produto.getDescricao()) ;
        if (!produtos.isEmpty()){
            throw new BadRequestException("Erro ao deletar, Produto Relacionado a uma lista de compra. Inative o Produto!");
        }
    }
    
    private void validaAtivo(Produto produto){
        if (produto.getAtivo() == null){
            produto.setAtivo("sim");
        }
    }
   
}
