package net.amcintosh.freshbooks;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Utility functions. Mostly around dates and timezones.
 */
public class Util {
    public static final ZoneId UTC_ZONE = ZoneId.of("UTC");
    public static final ZoneId ACCOUNTING_LOCAL_ZONE = ZoneId.of("US/Eastern");

    /**
     * Get a DateTimeFormmatter configured for accounting endpoint format ("yyyy-MM-dd HH:mm:ss").
     *
     * @return The formatter for accounting endpoints. Eg. 2021-01-08 20:39:52
     */
    public static DateTimeFormatter getAccountingDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Get a datetime zoned to UTC from an accounting endpoint date string.
     * The accounting service stores almost all dates in the US/Eastern timezone.
     *
     * @param dateString An accounting date-string. eg. 2021-01-08 20:39:52
     * @return UTC-zoned datetime
     */
    public static ZonedDateTime getZonedDateTimeFromAccountingLocalTime(String dateString) {
        LocalDateTime accountingLocalTime = LocalDateTime.parse(dateString, Util.getAccountingDateTimeFormatter());
        return accountingLocalTime.atZone(ACCOUNTING_LOCAL_ZONE).withZoneSameInstant(UTC_ZONE);
    }

    /**
     * Get a date string in the accounting endpoint format and timezone from a ZonedDateTime object.
     * The accounting service stores almost all dates in the US/Eastern timezone.
     *
     * @param datetime datetime object from any timezone
     * @return String An accounting date-string in US/Eastern. eg. 2021-01-08 20:39:52
     */
    public static String getAccountingLocalTimeFromZonedDateTime(ZonedDateTime datetime) {
        return datetime.withZoneSameInstant(ACCOUNTING_LOCAL_ZONE).format(Util.getAccountingDateTimeFormatter());
    }

    /**
     * Get a datetime zoned to UTC from an project-like endpoint date string.
     *
     * The project services store their dates in UTC, but depending on the resource do not
     * indicate that in the response. Eg. "2020-09-13T03:10:13" rather than "2020-09-13T03:10:13Z".
     *
     * @param dateString A project-like date-string. eg. 2020-09-13T03:10:13
     * @return UTC-zoned datetime
     */
    public static ZonedDateTime getZonedDateTimeFromProjectNaiveUTC(String dateString) {
        return LocalDateTime.parse(dateString).atZone(UTC_ZONE);
    }

    /**
     * Add a key/value to the provided Map only if the value is not null.
     *
     * @param data The Map to add to
     * @param key The key to add
     * @param value The value of the key if not null
     */
    public static void putIfNotNull(Map<String, Object> data, String key, Object value){
        if (value != null) {
            data.put(key, value);
        }
    }
}
