package pvt.study.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Homework {
    @Id
    private int id;

    private Subject subject;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date publishDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date completeTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date begin;
    @JsonIgnore
    private Date loadTime;
    @JsonIgnore
    private String loadSource;
}
