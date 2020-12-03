package pvt.study.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Reward {
    @Id
    private int id;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date approvalDate;
    @JsonIgnore
    private Date loadTime;
    @JsonIgnore
    private String loadSource;
}
