import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EventServiceTest {
    private IEventService<VestingEvent, VestingKey> eventService;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setup() throws ParseException {
        eventService = new EventService();
    }

    @Test
    public void testGroupVestingEventListByKey() throws ParseException {
        List<VestingEvent> vestingEventList = Arrays.asList(
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-01")).quantitiy(100.0).build(),
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-02")).quantitiy(100.50).build(),
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-03")).quantitiy(400.90).build(),
                VestingEvent.builder().type("VEST").employeeId("employId2").awardId("awardId2").name("Employee 2").date(formatter.parse("2022-01-03")).quantitiy(400.90).build(),
                VestingEvent.builder().type("VEST").employeeId("employId2").awardId("awardId1").name("Employee 2").date(formatter.parse("2022-01-03")).quantitiy(400.90).build(),
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId2").name("Employee 1").date(formatter.parse("2022-01-01")).quantitiy(400.90).build()
        );

        Map<VestingKey, List<VestingEvent>> vestingEventListMap = eventService.groupVestingEventListByKey(vestingEventList);
        Assert.assertEquals(4, vestingEventListMap.keySet().size());
        Assert.assertEquals(3, vestingEventListMap.get(VestingKey.builder().employeeId("employId1").awardId("awardId1").build()).size());
        Assert.assertEquals(1, vestingEventListMap.get(VestingKey.builder().employeeId("employId1").awardId("awardId2").build()).size());
        Assert.assertEquals(1, vestingEventListMap.get(VestingKey.builder().employeeId("employId2").awardId("awardId2").build()).size());
        Assert.assertFalse(vestingEventListMap.containsKey(VestingKey.builder().employeeId("employId3").awardId("awardId2").build()));
    }

    @Test
    public void calculateVestedSharesWithNoCancelledEntry() throws ParseException {
        List<VestingEvent> vestingEventList = Arrays.asList(
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-01")).quantitiy(100.0).build(),
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-02")).quantitiy(100.50).build(),
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-03")).quantitiy(400.90).build()
        );
        Assert.assertEquals(0.0, eventService.calculateVestedShares(vestingEventList, formatter.parse("2021-01-01"), 0), 0);
        Assert.assertEquals(100.0, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-01"), 0), 0);
        Assert.assertEquals(100.0, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-01"), 2), 0);
        Assert.assertEquals(200.50, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-02"), 2), 0);
        Assert.assertEquals(601.40, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-03"), 2), 0);
        Assert.assertEquals(601.0, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-03"), 0), 0);
        Assert.assertEquals(601.40, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-04"), 2), 0);
        Assert.assertEquals(601.40000, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-04"), 5), 0);
    }

    @Test
    public void calculateVestedSharesWithCancelledEntry() throws ParseException {
        List<VestingEvent> vestingEventList = Arrays.asList(
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-01")).quantitiy(100.0).build(),
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-02")).quantitiy(100.50).build(),
                VestingEvent.builder().type("CANCEL").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-02")).quantitiy(49.99).build(),
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-03")).quantitiy(400.90).build(),
                VestingEvent.builder().type("CANCEL").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-04")).quantitiy(90.50).build(),
                VestingEvent.builder().type("VEST").employeeId("employId1").awardId("awardId1").name("Employee 1").date(formatter.parse("2022-01-05")).quantitiy(100.50).build()
        );
        Assert.assertEquals(0.0, eventService.calculateVestedShares(vestingEventList, formatter.parse("2021-01-01"), 0), 0);
        Assert.assertEquals(100.0, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-01"), 0), 0);
        Assert.assertEquals(100.0, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-01"), 2), 0);
        Assert.assertEquals(150.51, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-02"), 2), 0);
        Assert.assertEquals(551.41, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-03"), 2), 0);
        Assert.assertEquals(551.0, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-03"), 0), 0);
        Assert.assertEquals(460.91, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-04"), 2), 0);
        Assert.assertEquals(460.91000, eventService.calculateVestedShares(vestingEventList, formatter.parse("2022-01-04"), 5), 0);
    }

}
