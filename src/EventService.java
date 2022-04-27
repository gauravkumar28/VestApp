import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class EventService implements IEventService<VestingEvent, VestingKey> {
    @Override
    public double calculateVestedShares(List<VestingEvent> vestingEventList, Date targetDate, int decimalPrecision) {
        List<VestingEvent> vestedEvents = vestingEventList.stream().filter(vestingEvent -> vestingEvent.type.equals(VestingEventType.VEST.name()) && vestingEvent.date.getTime() <= targetDate.getTime()).collect(Collectors.toList());
        List<VestingEvent> cancelledVestingEvents = vestingEventList.stream().filter(vestingEvent -> vestingEvent.type.equals(VestingEventType.CANCEL.name()) && vestingEvent.date.getTime() <= targetDate.getTime()).collect(Collectors.toList());
        double totalVested = calculateShareValue(vestedEvents, decimalPrecision);
        double totalCanceld = calculateShareValue(cancelledVestingEvents, decimalPrecision);
        // assume there won't be cancel event before vest
        double result = (totalVested - totalCanceld) >= 0 ? totalVested - totalCanceld : 0;
        return new BigDecimal(result).setScale(decimalPrecision, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public Map<VestingKey, List<VestingEvent>> groupVestingEventListByKey(List<VestingEvent> vestingEventList) {
        Map<VestingKey, List<VestingEvent>> vestingEventMap = new HashMap<>();
        for (VestingEvent vestingEvent : vestingEventList) {
            VestingKey vestingKey = VestingKey.builder().employeeId(vestingEvent.employeeId).awardId(vestingEvent.awardId).build();
            List<VestingEvent> vestingEventListToAdd = new ArrayList<>();
            if (vestingEventMap.containsKey(vestingKey)) {
                vestingEventListToAdd = vestingEventMap.get(vestingKey);
            }
            vestingEventListToAdd.add(vestingEvent);
            vestingEventMap.put(vestingKey, vestingEventListToAdd);
        }
        return vestingEventMap;
    }


    private double calculateShareValue(List<VestingEvent> vestingEventList, int decimalPrecision) {
        return vestingEventList.stream().mapToDouble(vestingEvent -> vestingEvent.quantitiy).sum();
    }
}
