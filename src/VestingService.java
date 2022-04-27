import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

public class VestingService implements IVestingService {
    private IEventParser<VestingEvent> vestingEventParser;
    private IEventService<VestingEvent, VestingKey> eventService;

    public VestingService() {
        this.vestingEventParser = new CSVVestingEventParser();
        this.eventService = new EventService();
    }

    public VestingService(IEventParser<VestingEvent> vestingEventParser, IEventService<VestingEvent, VestingKey> eventService) {
        this.vestingEventParser = vestingEventParser;
        this.eventService = eventService;
    }

    @Override
    public List<VestingSchedule> generateVestingSchedule(String filePath, Date targetDate, int decimalPrecision) throws FileNotFoundException, ParseException {
        List<VestingSchedule> vestingScheduleList = new ArrayList<VestingSchedule>();
        List<VestingEvent> vestingEventList = vestingEventParser.parse(new File(filePath));
        Map<VestingKey, List<VestingEvent>> vestingEventListMap = eventService.groupVestingEventListByKey(vestingEventList);
        for (Map.Entry<VestingKey, List<VestingEvent>> vestingEventEntry : vestingEventListMap.entrySet()) {
            double shareVested = eventService.calculateVestedShares(vestingEventEntry.getValue(), targetDate, decimalPrecision);
            vestingScheduleList.add(
                    VestingSchedule
                            .builder()
                            .employeeId(vestingEventEntry.getKey().employeeId)
                            .name(vestingEventEntry.getValue().get(0).name)
                            .awardId(vestingEventEntry.getKey().awardId)
                            .quantitiy(shareVested).
                            build()
            );
        }

        // sort by employeeId and awardId
        Collections.sort(vestingScheduleList);
        return vestingScheduleList;
    }

    @Override
    public List<VestingSchedule> generateVestingSchedule(String filePath, Date targetDate) throws FileNotFoundException, ParseException {
        return generateVestingSchedule(filePath, targetDate, 0);
    }

}
