import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

import java.util.Date;

@Builder
@ToString
@NonNull
public class VestingEvent {
    @NonNull
    String type;
    @NonNull
    String employeeId;
    @NonNull
    String name;
    @NonNull
    String awardId;
    @NonNull
    Date date;
    @NonNull
    Double quantitiy;
}
