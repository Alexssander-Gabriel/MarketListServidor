package marketlist.mercado;

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

@Path("mercados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MercadoController {

    @Inject
    private MercadoService mercadoService;

    @GET
    public List<Mercado> findAll() {
        return this.mercadoService.findAll();
    }

    @GET
    @Path("{id}")
    public Mercado findById(@PathParam("id") Long id) {
        Mercado mercado = this.mercadoService.findById(id);
        if (mercado == null) {
            throw new WebApplicationException("Mercado não encontrado", Response.Status.NOT_FOUND);
        }
        return mercado;
    }

    @POST
    public Mercado add(Mercado mercado) {
        return this.mercadoService.add(mercado);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") long id) {
        Mercado mercado = this.mercadoService.findById(id);
        if (mercado == null) {
            throw new NotFoundException("Mercado não encontrado");
        }
        this.mercadoService.remove(id);
    }

    @PUT
    @Path("{id}")
    public Mercado update(@PathParam("id") Long id, Mercado mercadoAtualizada) {
        Mercado mercadoEncontrada = this.mercadoService.findById(id);
        if (mercadoEncontrada == null) {
            throw new NotFoundException("Mercado não encontrado");
        }
        mercadoAtualizada.setId(id);
        return this.mercadoService.update(mercadoAtualizada);
    }

    @GET
    @Path("search")
    public List<Mercado> search(@QueryParam("desc") String descricao) {
        if(descricao == null) {
            throw new BadRequestException("Paramêtro desc não informado");
        }
        return this.mercadoService.search(descricao);
    }
}
