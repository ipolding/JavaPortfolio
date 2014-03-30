package data;

import java.util.List;

/**
 * Created by Ian on 30/03/14.
 */
public class Act {

    @Id
    @GeneratedKey
    private long id;

    @Column
    private String name;

    @ManyToMany
    private List<Act> associated_acts;

    @ManyToMany
    private List<RecordLabel> record_labels;

}
