package marketlist.lista;

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

@Path("listas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ListaController {

    @Inject
    private ListaService listaService;

    @GET
    public List<Lista> findAll() {
        return this.listaService.findAll();
    }

    @GET
    @Path("{id}")
    public Lista findById(@PathParam("id") Long id) {
       Lista lista = this.listaService.findById(id);
        if (lista == null) {
            throw new WebApplicationException("Lista não encontrada", Response.Status.NOT_FOUND);
        }
        return lista;
    }

    @POST
    public Lista add(Lista lista) {
        return this.listaService.add(lista);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") long id) {
        Lista lista = this.listaService.findById(id);
        if (lista == null) {
            throw new NotFoundException("Produto não encontrado");
        }
        this.listaService.remove(lista);
    }

    @PUT
    @Path("{id}")
    public Lista update(@PathParam("id") Long id, Lista listaAtualizada){
        Lista lista = this.listaService.findById(id);
        if (listaAtualizada == null) {
            throw new NotFoundException("Lista não encontrada");
        }
        listaAtualizada.setId(id);
        return this.listaService.update(listaAtualizada);
    }

    @GET
    @Path("search")
    public List<Lista> search(@QueryParam("desc") String descricao) {
        if(descricao == null) {
            throw new BadRequestException("Paramêtro desc não informado");
        }
        return this.listaService.search(descricao);
    }
    
}
