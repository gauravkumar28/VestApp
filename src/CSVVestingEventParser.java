import lombok.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVVestingEventParser implements IEventParser<VestingEvent> {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private EventEntryTokenizer tokenizer;

    public CSVVestingEventParser() {
        this.tokenizer = new EventEntryTokenizer();
    }

    public CSVVestingEventParser(EventEntryTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public List<VestingEvent> parse(@NonNull final File file) throws ParseException, FileNotFoundException {
        List<VestingEvent> vestingEventList = new ArrayList<>();
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            vestingEventList.add(parse(s));
        }
        return vestingEventList;
    }

    public VestingEvent parse(final String vestingEventEntry) throws ParseException {
        String[] vestingEventEntryItems = tokenizer.tokenize(vestingEventEntry);
        // todo: we can add some validations to check data is in the expected format
        assert (vestingEventEntryItems.length == 6);
        assert (VestingEventType.CANCEL.name() == vestingEventEntryItems[0] || VestingEventType.VEST.name() == vestingEventEntryItems[0]);
        return VestingEvent
                .builder()
                .type(vestingEventEntryItems[0])
                .employeeId(vestingEventEntryItems[1])
                .name(vestingEventEntryItems[2])
                .awardId(vestingEventEntryItems[3])
                .date(formatter.parse(vestingEventEntryItems[4]))
                .quantitiy(Double.valueOf(vestingEventEntryItems[5])).
                build();
    }
}
