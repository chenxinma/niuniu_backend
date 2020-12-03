package pvt.study.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class NewHomework implements Payload {
    public boolean isValid() {
        if (completeTime.before(beginTime)) {
            return false;
        }
        return true;
    }

    private Integer id;

    private int subjectId;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date publishDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date completeTime;
}
