package pvt.study.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class NewExercise implements Payload {
    @Override
    public boolean isValid() {
        if (id == null) {
            return newSubjectId == null || subjectId == newSubjectId;
        }
        return true;
    }

    private Integer id;

    private int subjectId;
    private Integer newSubjectId;
    private String grade;
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date approvalDate;
}
