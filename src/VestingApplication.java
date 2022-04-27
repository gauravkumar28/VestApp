import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VestingApplication {
    public static void main(String... args) {

        try {
            ValidateArguments(args);
            System.out.println("Arguments Provided { csvFilePath=" + args[0] + ",targetDate=" + args[1] + (args.length == 3 ? ",decimalPrecision=" + args[2] + "}" : "}"));
            String csvFilePath = args[0];
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date targetDate = formatter.parse(args[1]);

            VestingService vestingService = new VestingService();
            List<VestingSchedule> vestingScheduleList = vestingService.generateVestingSchedule(csvFilePath, targetDate, args.length == 3 ? Integer.valueOf(args[2]) : 0);
            displayResult(vestingScheduleList);

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void ValidateArguments(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
    }

    private static void displayResult(List<VestingSchedule> vestingScheduleList) {
        // we can move display logic to some other class if we want to have different formats of display
        for (VestingSchedule vestingSchedule : vestingScheduleList) {
            System.out.println(vestingSchedule.toString());
        }
    }
}
