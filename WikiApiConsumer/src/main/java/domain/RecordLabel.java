package domain;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Created by Ian on 30/03/14.
 */
public class RecordLabel {

    @Id
    private String name;

    @ManyToMany
    List<Act> acts;
}
