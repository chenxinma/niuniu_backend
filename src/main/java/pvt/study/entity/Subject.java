package pvt.study.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Subject {
    @Id
    private int id;
    private String subject;
    @JsonIgnore
    private Date loadTime;
    @JsonIgnore
    private String loadSource;
}
