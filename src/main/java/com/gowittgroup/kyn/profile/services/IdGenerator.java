package com.gowittgroup.kyn.profile.services;

import java.util.Calendar;
import java.util.Random;

public class IdGenerator {

    public synchronized static String generateUsername() {
        Random random = new Random();
        int segment1 = random.nextInt(10, 99);
        String segment2 = random.ints(48, 122 + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(4).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        Calendar cal = Calendar.getInstance();
        long start = cal.getTimeInMillis();
        cal.add(Calendar.SECOND, 5);
        long end = cal.getTimeInMillis();
        Long segment3 = random.nextLong(start, end);
        return segment3 + segment2 + segment1;
    }
}
