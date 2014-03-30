package data;

/**
 * Created by Ian on 30/03/14.
 */
public class RecordLabel {

    @Id
    private String name;

    @ManyToMany
    List<Act> acts;
}
