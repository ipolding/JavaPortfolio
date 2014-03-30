package uk.co.wowcher.loyalty.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import uk.co.wowcher.loyalty.data.model.*;
import uk.co.wowcher.loyalty.data.repositories.VerbsRepository;
import uk.co.wowcher.loyalty.services.commands.VerbCommand;
import uk.co.wowcher.loyalty.services.dto.VerbRuleDto;

import java.lang.String;import java.util.ArrayList;
import java.util.List;

@Service
public class VerbsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesService.class);

    private final VerbsRepository verbsRepository;
    private Verb verb;

    @Autowired
    public VerbsService(VerbsRepository verbsRepository) {
        this.verbsRepository = verbsRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createVerb(VerbCommand request) {
        Assert.notNull(request);

        LOGGER.debug("Persisting verb: {}", request);

        verb = new Verb();
        verb.setName(request.getName());
        verb.setPoints(request.getPoints());
        verb.setBehaviour(request.getBehaviour());

        if (request.getRules() != null) {
            verb.setRules(createRules(request));
        }

        if (request.getMetadata() != null) {
            verb.setMetadata(createVerbMetadata(request));
        }

        verbsRepository.save(verb);
        }

    private List<VerbRule> createRules(VerbCommand request) {

        List<VerbRule> verbRulesList = new ArrayList<>();
        for (VerbRuleDto verbRuleDto : request.getRules()) {
            VerbRule verbRule = new VerbRule();
            verbRule.setVerb(verb);
            verbRule.setType(VerbRuleType.valueOf(verbRuleDto.getType()));
            verbRule.setLimit(verbRuleDto.getLimit());

            switch (verbRule.getType()){
                case METADATA_LIMIT:
                    verbRule.setMetadataName(verbRuleDto.getMetadataName());
                    break;
                case LIFETIME_LIMIT:
                    break;
                case TIME_LIMIT:
                    verbRule.setTimeLimit(verbRuleDto.getTimeLimit());
                    break;
            }

            verbRulesList.add(verbRule);
        }
        return verbRulesList;
    }

    private List<VerbMetadata> createVerbMetadata(VerbCommand request) {

        List<VerbMetadata> verbMetadataList = new ArrayList<>();
        for (String requestMetadata : request.getMetadata() ) {
            VerbMetadata verbMetadata = new VerbMetadata();
            verbMetadata.setVerb(verb);
            verbMetadata.setName(requestMetadata);
            verbMetadataList.add(verbMetadata);
        }
        return verbMetadataList;
    }
}