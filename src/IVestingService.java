import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IVestingService {
    List<VestingSchedule> generateVestingSchedule(String filePath, Date targetDate, int decimalPrecision) throws FileNotFoundException, ParseException;
    List<VestingSchedule> generateVestingSchedule(String filePath, Date targetDate) throws FileNotFoundException, ParseException;
}
