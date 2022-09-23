package africa.semicolon.yamiloanapp.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class YamiBank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @OneToMany(mappedBy = "yamiBank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Map<String, Customer> customers = new HashMap<>();
}
