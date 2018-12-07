package com.hepsiburada.ecommerce.utility;

public class Time {
    private static long TIME = 0;

    public static long getTime() {
        return TIME;
    }


    public static void increase(long hours) throws InvalidTimeException {
        if (hours <= 0) {
            throw new InvalidTimeException("nothing chaged. negative or zero time not accepted");
        } else if ((TIME + hours) > 24) {
            throw new InvalidTimeException("nothing chaged. negative or zero time not accepted");
        } else {
            TIME += hours;
        }
    }

    public static void resetTime() {
        TIME = 0;
    }

}
