package uk.co.wowcher.loyalty.services.dto;

import java.io.Serializable;

public class VerbRuleDto implements Serializable{

    private static final long serialVersionUID = 6529434461514229798L;

    private String type;
    private Integer limit;
    private Long timeLimit;
    private String metadataName;

    public String getMetadataName() {
        return metadataName;
    }

    public void setMetadataName(String metadataName) {
        this.metadataName = metadataName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Long timeLimit) {
        this.timeLimit = timeLimit;
    }

}
