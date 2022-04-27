import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

@Builder
@NonNull
public class VestingSchedule implements Comparable<VestingSchedule> {
    @NonNull
    String employeeId;
    @NonNull
    String name;
    @NonNull
    String awardId;
    @NonNull
    Double quantitiy;

    public String  toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(employeeId).append(",").append(name).append(",").append(awardId).append(",").append(quantitiy);
        return sb.toString();
    }

    @Override
    public int compareTo(VestingSchedule o) {
        if (this.employeeId.equals(o.employeeId)) {
            return this.awardId.compareTo(o.awardId);
        }
        return this.employeeId.compareTo(o.employeeId);
    }
}
