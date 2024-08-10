package org.example.utilities;

import java.time.LocalDate;

public class NationalIdUtils {


    public static LocalDate extractBirthDateFromNationalId(String nationalId) {
        if (nationalId == null || nationalId.length() != 14 || !nationalId.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid national ID format.");
        }

        int centuryIndicator = Character.getNumericValue(nationalId.charAt(0));
        int year = Integer.parseInt(nationalId.substring(1, 3));
        int month = Integer.parseInt(nationalId.substring(3, 5));
        int day = Integer.parseInt(nationalId.substring(5, 7));

        if (centuryIndicator == 2) {
            year += 1900; // 1900-1999
        } else if (centuryIndicator == 3) {
            year += 2000; // 2000-2099
        } else {
            throw new IllegalArgumentException("Unsupported century indicator in national ID.");
        }

        if (!isValidDate(year, month, day)) {
            throw new IllegalArgumentException("The extracted date is not valid.");
        }

        return LocalDate.of(year, month, day);
    }
    private static boolean isValidDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
