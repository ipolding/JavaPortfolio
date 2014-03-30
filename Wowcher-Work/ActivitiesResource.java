package uk.co.wowcher.loyalty.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.wowcher.loyalty.api.transfers.ActivitiesTransfer;
import uk.co.wowcher.loyalty.services.ActivitiesService;
import uk.co.wowcher.loyalty.services.commands.ActivityCommand;

@Path("/activities")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class ActivitiesResource {

    private static final String DEFAULT_HISTORY_DAYS = "30";

    private final ActivitiesService activitiesService;

    @Autowired
    public ActivitiesResource(ActivitiesService activitiesService) {
        this.activitiesService = activitiesService;
    }

    @GET
    @Path("customer/{customerId}")
    public ActivitiesTransfer get(@PathParam("customerId") Long customerId, @QueryParam("days") @DefaultValue(DEFAULT_HISTORY_DAYS) Integer days) {
        return new ActivitiesTransfer(activitiesService.getByCustomerIdAndPeriod(customerId, days));
    }

    @Deprecated
    // TODO Post method for testing purposes until messaging is available
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addActivityForCustomer(ActivityCommand activityCommand) {
        activitiesService.persist(activityCommand);
    }

}