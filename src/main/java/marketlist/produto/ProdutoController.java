package marketlist.produto;

import marketlist.produto.*;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoController {

    @Inject
    private ProdutoService produtoService;

    @GET
    public List<Produto> findAll() {
        return this.produtoService.findAll();
    }

    @GET
    @Path("{id}")
    public Produto findById(@PathParam("id") Long id) {
       return this.produtoService.findById(id);
    }

    @POST
    public Produto add(Produto produto) {       
        return this.produtoService.add(produto);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") long id) {
        this.produtoService.remove(id);
    }

    @PUT
    @Path("{id}")
    public Produto update(@PathParam("id") Long id, Produto produtoAtualizada) {
        produtoAtualizada.setId(id);
        return this.produtoService.update(produtoAtualizada);
    }

    @GET
    @Path("search")
    public List<Produto> search(@QueryParam("desc") String descricao) {
        if(descricao == null) {
            throw new BadRequestException("Paramêtro desc não informado");
        }
        return this.produtoService.search(descricao);
    }
}
