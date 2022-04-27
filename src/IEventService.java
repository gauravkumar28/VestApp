import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IEventService<U, V> {
    double calculateVestedShares(List<U> eventList, Date targetDate, int decimalPrecision);

    Map<V, List<U>> groupVestingEventListByKey(List<U> eventList);
}
