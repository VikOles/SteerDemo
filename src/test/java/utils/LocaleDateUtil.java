package utils;

import java.time.format.DateTimeFormatter;

public class LocaleDateUtil {
    public static  DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
    public static DateTimeFormatter monthDayYearFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy, ");
}
