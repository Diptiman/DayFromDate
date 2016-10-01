package com.java.daytodate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: Find out the day of the week based on the given date entry
 * 
 * @author Diptiman Adak
 * Date: 10-01-2016
 * 
 */
public class DayToDateWithOffsetLogic {

	/* Map for the day number and day name of the week */
	private static final HashMap<Integer, String> WEEKDAY_NUMBER = new HashMap<Integer, String>() {
		{
			put(0, "Sunday");
			put(1, "Monday");
			put(2, "Tuesday");
			put(3, "Wednesday");
			put(4, "Thursday");
			put(5, "Friday");
			put(6, "Saturday");
		}
	};

	/* Mapping between month number and month offset */
	private static final HashMap<Integer, Integer> MONTH_OFFSET = new HashMap<Integer, Integer>() {
		{
			put(1, 0);
			put(2, 3);
			put(3, 3);
			put(4, 6);
			put(5, 1);
			put(6, 4);
			put(7, 6);
			put(8, 2);
			put(9, 5);
			put(10, 0);
			put(11, 3);
			put(12, 5);
		}
	};

	public static void main(String args[]) throws NumberFormatException,
			IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		System.out.println("Enter the date in the number format(1-31):");
		int date = Integer.parseInt(reader.readLine().trim());
		System.out
				.println("Enter the month in the number format,e.g. 11 for November:");
		int month = Integer.parseInt(reader.readLine().trim());
		System.out.println("Enter the year in number format, e.g. 2009:");
		int year = Integer.parseInt(reader.readLine().trim());

		// int year = 2016;
		// int date = 1;
		// String month = "Oct";
		if (!validateInputDate(year, month, date)) {
			System.out.println("Invalid Entry");
			return;
		}
		int dayOffset = date % 7;
		int yearOffset = (year % 100 + (year % 100) / 4) % 7;
		if (checkLeapYear(year) && (month == 1 || month == 2)) {
			yearOffset = yearOffset - 1;
		}
		int monthOffset = 0;
		for (Map.Entry<Integer, Integer> entryMonth : MONTH_OFFSET.entrySet()) {
			if (entryMonth.getKey() == month)
				monthOffset = entryMonth.getValue();
		}
		DayToDateWithOffsetLogic dayToDateWithOffsetLogic = new DayToDateWithOffsetLogic();
		int centuryOffset = dayToDateWithOffsetLogic.findCenturyOffset(year);
		int totalOffset = yearOffset + monthOffset + dayOffset + centuryOffset;
		System.out.println("Day of the Week:"
				+ WEEKDAY_NUMBER.get(totalOffset % 7));
	}

	/**
	 * Check if a given year is a leap year
	 * 
	 * @param year
	 * @return true if leap year
	 */
	private static boolean checkLeapYear(int year) {
		if ((year % 400 == 0) || (year % 4 == 0) && (year % 100 != 0))
			return true;
		return false;
	}

	/**
	 * Calculate the century offset number
	 * 
	 * @param year
	 * @return
	 */
	private static int countryOffsetCreation(int year) {
		int firstTwoDigit = year / 100;
		int nextHighestNumber = DayToDateWithOffsetLogic
				.findNextMultipleOfFour(firstTwoDigit);
		return ((nextHighestNumber - 1) - firstTwoDigit) * 2;
	}

	/**
	 * Find the next multiple of four for a given number
	 * 
	 * @param number
	 * @return
	 */
	private static int findNextMultipleOfFour(int number) {
		for (int i = number + 1; i <= 100; i++) {
			if (i % 4 == 0) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * Find the century offset for the given year
	 * 
	 * @param year
	 * @return centuryOffset
	 */
	private int findCenturyOffset(int year) {
		return DayToDateWithOffsetLogic
				.countryOffsetCreation(smallerNumberDivisibleByHundred(year));
	}

	/**
	 * Find the immediate smaller number immediate to the given year
	 * 
	 * @param year
	 * @return number
	 */
	private static int smallerNumberDivisibleByHundred(int year) {
		for (int i = year; i >= 0; i--) {
			if (i % 100 == 0)
				return i;
		}
		return 0;
	}

	/**
	 * Validate the day,month and year as input
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return true if input is valid
	 */
	private static boolean validateInputDate(int year, int month, int date) {
		if (year < 0 || year > 9999 || date < 0 || date > 31 || month < 0
				|| month > 12) {
			return false;
		}
		return true;
	}

}
