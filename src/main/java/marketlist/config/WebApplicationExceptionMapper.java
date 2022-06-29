package marketlist.config;

// import javax.json.Json;
// import javax.json.JsonObject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException e) {
        int status = e.getResponse().getStatus();
        String message = e.getMessage();
        
        // JsonObject jsonObject = Json.createObjectBuilder().add("erro", message).build();
        return Response
                .status(status)
                // .entity(jsonObject)
                .entity(new WebApplicationExceptionResponse(message))
                .build();
    }
    
}
