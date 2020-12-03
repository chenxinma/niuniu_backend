package pvt.study.view;

import lombok.Data;

import java.util.Date;

@Data
public class NewExercise implements Payload {
    @Override
    public boolean isValid() {
        return true;
    }

    private int subjectId;
    private String grade;
    private String title;
    private Date approvalDate;
}
