import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VestingServiceTest2 {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private VestingService vestingService;
    List<VestingEvent> vestingEventList;

    @Before
    public void setup() throws ParseException {
        vestingService = new VestingService();

        vestingEventList = Arrays.asList(
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-01")).quantitiy(100.0).build(),
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-02")).quantitiy(100.50).build(),
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-03")).quantitiy(400.90).build(),
                VestingEvent.builder().type("VEST").employeeId("employId2").awardId("awardId2").name("Employee 2").date(formatter.parse("2022-01-03")).quantitiy(400.90).build(),
                VestingEvent.builder().type("VEST").employeeId("employId2").awardId("awardId1").name("Employee 2").date(formatter.parse("2022-01-03")).quantitiy(400.90).build(),
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId2").name("Employee 1").date(formatter.parse("2022-01-01")).quantitiy(400.90).build()
        );
    }

    //@Test
    public void testVestingScheduleWithoutCancellation() throws ParseException, FileNotFoundException {
        // todo - use mockito and mock calls to parser and event service
        List<VestingSchedule> vestingScheduleList = vestingService.generateVestingSchedule("test.txt", formatter.parse("2022-01-03"), 2);
    }

    //@Test
    public void testVestingScheduleWithCancellation() throws ParseException, FileNotFoundException {
        // todo - use mockito and mock calls to parser and event service
        vestingEventList.add(VestingEvent.builder().type("CANCEL").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-01")).quantitiy(48.69).build());
        List<VestingSchedule> vestingScheduleList = vestingService.generateVestingSchedule("test.txt", formatter.parse("2022-01-03"), 2);
    }
}
