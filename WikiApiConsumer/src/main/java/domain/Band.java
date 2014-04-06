package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ian on 23/03/14.
 */

@Entity
@Table (name = "BANDS")
public class Band {

    @Id
    @Column(nullable=false, precision = 15, updatable = false)
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy ="increment")
    private Long id;
    public long getId(){
        return id;
    }



    private String name;

    @ManyToMany
    List<String> genres;

    @ManyToMany
    List<String> associated_acts;

    @ManyToMany
    List<String> record_labels;

}
