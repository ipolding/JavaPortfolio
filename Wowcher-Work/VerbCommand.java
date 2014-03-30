package uk.co.wowcher.loyalty.services.commands;

import org.apache.commons.lang.builder.ToStringBuilder;
import uk.co.wowcher.loyalty.data.model.VerbRule;
import uk.co.wowcher.loyalty.services.dto.VerbRuleDto;

import java.io.Serializable;
import java.util.List;

public class VerbCommand implements Serializable {

    private static final long serialVersionUID = 3984882306853751358L;

    private String name;
    private Long points;
    private List<VerbRuleDto> rules;
    private List<String> metadata;
    private String behaviour;


    public String toString(){
        return new ToStringBuilder(this)
                .append("verb",this.name)
                .append("points",this.points)
                .append("rules",this.rules)
                .append("metadata", this.metadata)
                .toString();
    }


   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public List<VerbRuleDto> getRules() {
        return rules;
    }

    public void setRules(List<VerbRuleDto> rules) {
        this.rules = rules;
    }

    public List<String> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<String> metadata) {
        this.metadata = metadata;
    }

    public String getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(String behaviour) {
        this.behaviour = behaviour;
    }
}

