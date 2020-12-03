package pvt.study.view;

import lombok.Data;

import java.util.Date;

@Data
public class NewReward implements Payload {
    @Override
    public boolean isValid() {
        return true;
    }

    private String content;
    private Date approvalDate;
}
