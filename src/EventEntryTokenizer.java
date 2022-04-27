public class EventEntryTokenizer implements IEntryTokenizer<String, String> {
    private static String DEFAULT_SEPERATOR = ",";

    @Override
    public String[] tokenize(String entry) {
        return entry.split(DEFAULT_SEPERATOR);
    }

    public String[] tokenize(String entry, String seperator) {
        return entry.split(seperator);
    }
}
