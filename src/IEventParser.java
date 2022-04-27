import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

public interface IEventParser<T> {
    T parse(String event) throws ParseException;
    List<T> parse(File file) throws FileNotFoundException, ParseException;

}
