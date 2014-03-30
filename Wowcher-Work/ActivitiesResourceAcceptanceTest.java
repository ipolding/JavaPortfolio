	
package uk.co.wowcher.loyalty.api.resources;

import static org.junit.Assert.assertEquals;
import static uk.co.wowcher.loyalty.fixtures.VerbsFixtures.VERBS.OPEN_EMAIL;
import static uk.co.wowcher.loyalty.fixtures.VerbsFixtures.VERBS.SUBSCRIBE;
import static uk.co.wowcher.loyalty.fixtures.VerbsFixtures.VERBS.VIEW_DEAL;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import uk.co.wowcher.loyalty.api.AbstractApiAcceptanceTest;
import uk.co.wowcher.loyalty.api.transfers.ActivitiesTransfer;
import uk.co.wowcher.loyalty.data.model.VerbMetadataTypes;
import uk.co.wowcher.loyalty.fixtures.LevelsFixtures;
import uk.co.wowcher.loyalty.services.commands.ActivityCommand;
import uk.co.wowcher.loyalty.services.dto.ActivityDto;
import uk.co.wowcher.loyalty.services.dto.CustomerStatementDto;

public class ActivitiesResourceAcceptanceTest extends AbstractApiAcceptanceTest {

    private static final Long CUSTOMER_ID = 10L;
    private static final String SUBSCRIBE_LOCATION = "london";
    private static final String VERB_NAME = "subscribe";

    @Test
    public void testGet() {
        createActivity(CUSTOMER_ID, VIEW_DEAL, new Date());

        Response response = jersey.target("/activities/customer/{customerId}").resolveTemplate("customerId", CUSTOMER_ID).request().get();

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());

        ActivityDto activity = new ActivityDto();
        activity.setVerb(VIEW_DEAL.getName());
        activity.setCount(1);
        activity.setPoints(VIEW_DEAL.getPoints());

        ActivitiesTransfer expectedActivities = new ActivitiesTransfer(Arrays.asList(activity));
        assertEquals(expectedActivities, response.readEntity(ActivitiesTransfer.class));
    }

    @Test
    public void testGetForMultipleActivities() {
        createActivity(CUSTOMER_ID, VIEW_DEAL, new Date());
        createActivity(CUSTOMER_ID, VIEW_DEAL, new Date());
        createActivity(CUSTOMER_ID, OPEN_EMAIL, new Date());

        Response response = jersey.target("/activities/customer/{customerId}").resolveTemplate("customerId", CUSTOMER_ID).request().get();

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());

        ActivityDto viewDealActivity = new ActivityDto(VIEW_DEAL.getName(), 2, 2 * VIEW_DEAL.getPoints());
        ActivityDto openEmailActivity = new ActivityDto(OPEN_EMAIL.getName(), 1, 1 * OPEN_EMAIL.getPoints());

        ActivitiesTransfer expectedActivities = new ActivitiesTransfer(Arrays.asList(viewDealActivity, openEmailActivity));
        assertEquals(expectedActivities, response.readEntity(ActivitiesTransfer.class));
    }

    @Test
    public void testPost() {
        createBalance(CUSTOMER_ID, 0L, LevelsFixtures.LEVELS.PINK);

        ActivityCommand activityCommand = new ActivityCommand();
        activityCommand.setCustomerId(CUSTOMER_ID);
        activityCommand.setVerb(VERB_NAME);
        Map<String, String> metadata = new HashMap<>();
        metadata.put(VerbMetadataTypes.LOCATION_NAME.getName(), SUBSCRIBE_LOCATION);
        activityCommand.setMetadata(metadata);

        ActivityDto activityDto = new ActivityDto();
        activityDto.setVerb(SUBSCRIBE.getName());
        activityDto.setCount(1);
        activityDto.setPoints(SUBSCRIBE.getPoints());

        Entity<ActivityCommand> jsonEntity = Entity.json(activityCommand);
        Response response = jersey.target("/activities").request().post(jsonEntity);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

        CustomerStatementDto customerStatement = jersey.target("/statements/customer/{customerId}").resolveTemplate("customerId", CUSTOMER_ID).request().get().readEntity(CustomerStatementDto.class);
        assertEquals(Long.valueOf(200), customerStatement.getLifetimePoints());
        assertEquals(Long.valueOf(200), customerStatement.getCurrentPoints());

        Response activities = jersey.target("/activities/customer/{customerId}").resolveTemplate("customerId", CUSTOMER_ID).request().get();
        assertEquals(new ActivitiesTransfer(Arrays.asList(activityDto)), activities.readEntity(ActivitiesTransfer.class));
    }

}