package domain;

import java.util.List;

import javax.persistence.Id;

/**
 * Created by Ian on 30/03/14.
 */
public class Genre {

    @Id
    private String name;
    private List<Act> acts;
}
