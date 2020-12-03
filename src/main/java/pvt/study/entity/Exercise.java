package pvt.study.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Exercise {
    @Id
    private int id;

    private Subject subject;
    private String grade;
    private String title;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date approvalDate;

    @JsonIgnore
    private Date loadTime;
    @JsonIgnore
    private String loadSource;
}
