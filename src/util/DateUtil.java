package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private DateUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    public static int getCurrentMonth() {
        return LocalDate.now().getMonthValue();
    }

    public static int getCurrentDay() {
        return LocalDate.now().getDayOfMonth();
    }

    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            return 0;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public static long getDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static long getMonthsBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.MONTHS.between(startDate, endDate);
    }

    public static long getYearsBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.YEARS.between(startDate, endDate);
    }

    public static LocalDate addDays(LocalDate date, long days) {
        return date.plusDays(days);
    }

    public static LocalDate addMonths(LocalDate date, long months) {
        return date.plusMonths(months);
    }

    public static LocalDate addYears(LocalDate date, long years) {
        return date.plusYears(years);
    }

    public static LocalDate subtractDays(LocalDate date, long days) {
        return date.minusDays(days);
    }

    public static LocalDate subtractMonths(LocalDate date, long months) {
        return date.minusMonths(months);
    }

    public static LocalDate subtractYears(LocalDate date, long years) {
        return date.minusYears(years);
    }

    public static boolean isBefore(LocalDate date1, LocalDate date2) {
        return date1.isBefore(date2);
    }

    public static boolean isAfter(LocalDate date1, LocalDate date2) {
        return date1.isAfter(date2);
    }

    public static boolean isEqual(LocalDate date1, LocalDate date2) {
        return date1.isEqual(date2);
    }

    public static boolean isToday(LocalDate date) {
        return date.isEqual(LocalDate.now());
    }

    public static boolean isPast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    public static boolean isFuture(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    public static boolean isWeekend(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek == 6 || dayOfWeek == 7;
    }

    public static boolean isWeekday(LocalDate date) {
        return !isWeekend(date);
    }

    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "N/A";
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATETIME_FORMATTER) : "N/A";
    }

    public static String formatTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(TIME_FORMATTER) : "N/A";
    }

    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (Exception e) {
            System.out.println("Error parsing date: " + dateString);
            return null;
        }
    }

    public static LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, DATETIME_FORMATTER);
        } catch (Exception e) {
            System.out.println("Error parsing date time: " + dateTimeString);
            return null;
        }
    }

    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.withDayOfMonth(1);
    }

    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    public static LocalDate getFirstDayOfYear(LocalDate date) {
        return date.withDayOfYear(1);
    }

    public static LocalDate getLastDayOfYear(LocalDate date) {
        return date.withDayOfYear(date.lengthOfYear());
    }

    public static boolean isLeapYear(LocalDate date) {
        return date.isLeapYear();
    }

    public static Period getPeriod(LocalDate startDate, LocalDate endDate) {
        return Period.between(startDate, endDate);
    }

    public static String getPeriodDetails(LocalDate startDate, LocalDate endDate) {
        Period period = getPeriod(startDate, endDate);
        return String.format("%d years, %d months, %d days", 
            period.getYears(), period.getMonths(), period.getDays());
    }
}
