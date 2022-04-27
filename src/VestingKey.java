import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class VestingKey {
    @NonNull
    String employeeId;
    @NonNull
    String awardId;
}
