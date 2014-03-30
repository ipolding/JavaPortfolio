package uk.co.wowcher.loyalty.services;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static uk.co.wowcher.loyalty.data.model.VerbRuleType.*;


import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;


import org.mockito.runners.MockitoJUnitRunner;
import uk.co.wowcher.loyalty.data.model.Verb;
import uk.co.wowcher.loyalty.data.model.VerbRule;
import uk.co.wowcher.loyalty.data.repositories.VerbsRepository;
import uk.co.wowcher.loyalty.services.commands.VerbCommand;
import uk.co.wowcher.loyalty.services.dto.VerbRuleDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class VerbsServiceTest {

    private static final String VERB_NAME = "new_verb";
    private static final Long POINTS = 999L;
    private static final Integer LIMIT = 10;
    private static final Long TIME_LIMIT_VALUE = 9999L;
    private static final String RULE_METADATA = "deal_id";
    private static final String VERB_METADATA = "metadata a";
    private static final String BEHAVIOUR = "givePointsBehaviour";

    private VerbsService testObj;
    private VerbCommand verbCommand;

    @Mock
    private VerbsRepository verbsRepositoryMock;

    @Before
    public void setup() {
        testObj = new VerbsService(verbsRepositoryMock);
        verbCommand = new VerbCommand();
        verbCommand.setName(VERB_NAME);
        verbCommand.setPoints(POINTS);
        verbCommand.setBehaviour(BEHAVIOUR);
    }

    @Test
    public void testCreateVerbWithMetadataNoRules() {

        verbCommand.setMetadata(Arrays.asList(VERB_METADATA));

        testObj.createVerb(verbCommand);

        ArgumentCaptor<Verb> verbBeingSaved = ArgumentCaptor.forClass(Verb.class);
        verify(verbsRepositoryMock).save(verbBeingSaved.capture());

        Verb savedVerb = verbBeingSaved.getValue();

        assertEquals(VERB_NAME, savedVerb.getName());
        assertEquals(POINTS, savedVerb.getPoints());
        assertEquals(BEHAVIOUR, savedVerb.getBehaviour());

        assertEquals(1, savedVerb.getMetadata().size());
        assertEquals(VERB_NAME, savedVerb.getMetadata().get(0).getVerb().getName());
        assertEquals(VERB_METADATA, savedVerb.getMetadata().get(0).getName());
    }

    @Test
    public void testCreateVerbOneTimeLimitRule() {

        VerbRuleDto verbRuleDto = new VerbRuleDto();
        verbRuleDto.setType(TIME_LIMIT.toString());
        verbRuleDto.setLimit(LIMIT);
        verbRuleDto.setTimeLimit(TIME_LIMIT_VALUE);

        List<VerbRuleDto> verbRules = new ArrayList<>();
        verbRules.add(verbRuleDto);

        verbCommand.setRules(verbRules);

        testObj.createVerb(verbCommand);

        ArgumentCaptor<Verb> verbBeingSaved = ArgumentCaptor.forClass(Verb.class);
        verify(verbsRepositoryMock).save(verbBeingSaved.capture());

        Verb savedVerb = verbBeingSaved.getValue();

        assertEquals(VERB_NAME, savedVerb.getName());
        assertEquals(POINTS, savedVerb.getPoints());
        assertEquals(BEHAVIOUR, savedVerb.getBehaviour());

        VerbRule firstVerbRule = verbBeingSaved.getValue().getRules().get(0);

        assertEquals(1, verbRules.size());
        assertEquals(VERB_NAME, firstVerbRule.getVerb().getName());
        assertEquals(LIMIT.intValue(), firstVerbRule.getLimit());
        assertEquals(TIME_LIMIT, firstVerbRule.getType());
        assertEquals(TIME_LIMIT_VALUE, firstVerbRule.getTimeLimit());
        assertNull(firstVerbRule.getMetadata());
    }

    @Test
    public void testCreateVerbWithAllThreeRules() {

        VerbRuleDto timeLimitRule = new VerbRuleDto();
        timeLimitRule.setType(TIME_LIMIT.toString());
        timeLimitRule.setLimit(LIMIT);
        timeLimitRule.setTimeLimit(TIME_LIMIT_VALUE);

        VerbRuleDto metadataRule = new VerbRuleDto();
        metadataRule.setType(METADATA_LIMIT.toString());
        metadataRule.setLimit(LIMIT);
        metadataRule.setMetadataName(RULE_METADATA);

        VerbRuleDto lifetimeLimitRule = new VerbRuleDto();
        lifetimeLimitRule.setType(LIFETIME_LIMIT.toString());
        lifetimeLimitRule.setLimit(LIMIT);

        List<VerbRuleDto> verbRules = new ArrayList<>();
        verbRules.add(timeLimitRule);
        verbRules.add(metadataRule);
        verbRules.add(lifetimeLimitRule);

        verbCommand.setRules(verbRules);

        testObj.createVerb(verbCommand);

        ArgumentCaptor<Verb> verbBeingSaved = ArgumentCaptor.forClass(Verb.class);
        verify(verbsRepositoryMock).save(verbBeingSaved.capture());

        Verb savedVerb = verbBeingSaved.getValue();

        assertEquals(VERB_NAME, savedVerb.getName());
        assertEquals(POINTS, savedVerb.getPoints());
        assertEquals(BEHAVIOUR, savedVerb.getBehaviour());

        assertEquals(3, verbRules.size());

        VerbRule firstRule = verbBeingSaved.getValue().getRules().get(0);
        VerbRule secondRule = verbBeingSaved.getValue().getRules().get(1);
        VerbRule thirdRule = verbBeingSaved.getValue().getRules().get(2);

        assertEquals(VERB_NAME, firstRule.getVerb().getName());
        assertEquals(LIMIT.intValue(), firstRule.getLimit());
        assertEquals(TIME_LIMIT, firstRule.getType());
        assertEquals(TIME_LIMIT_VALUE, firstRule.getTimeLimit());

        assertEquals(VERB_NAME, firstRule.getVerb().getName());
        assertEquals(LIMIT.intValue(), secondRule.getLimit());
        assertEquals(METADATA_LIMIT, secondRule.getType());
        assertEquals(RULE_METADATA, secondRule.getMetadataName());

        assertEquals(VERB_NAME, firstRule.getVerb().getName());
        assertEquals(LIMIT.intValue(), thirdRule.getLimit());
        assertEquals(LIFETIME_LIMIT, thirdRule.getType());
        assertNull(firstRule.getMetadata());
    }

}