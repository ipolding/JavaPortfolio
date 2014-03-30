package uk.co.wowcher.loyalty.api.jersey.error.mappers;

import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.wowcher.loyalty.api.transfers.ErrorTransfer;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonProcessingErrorMapper implements ExceptionMapper<UnrecognizedPropertyException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonProcessingErrorMapper.class);

    static final String UNRECOGNISED_PROPERTY_EXCEPTION_ERROR = "unrecognised_property";

    @Override
    public Response toResponse(UnrecognizedPropertyException e) {
        LOGGER.error("Mapping exception: " + e.toString(), e);

        ErrorTransfer entity = new ErrorTransfer(UNRECOGNISED_PROPERTY_EXCEPTION_ERROR, e.getUnrecognizedPropertyName()
                + " is not a valid property");
        return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
    }

}