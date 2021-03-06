package domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Created by Ian on 30/03/14.
 */
public class Act {

    @Id
//    @GeneratedKey
    private long id;

    @Column
    private String name;

    @ManyToMany
    private List<Act> associated_acts;

    @ManyToMany
    private List<RecordLabel> record_labels;

}
