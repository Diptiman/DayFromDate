package com.java.daytodate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DayToDateWithOffsetLogic {

	private static final HashMap<Integer, String> WEEKDAY_NUMBER = new HashMap<Integer, String>() {
		{
			put(0, "Sunday");
			put(1, "Monday");
			put(2, "Tuesday");
			put(3, "Wednesday");
			put(4, "Thrusday");
			put(5, "Friday");
			put(6, "Saturday");
		}
	};

	private static final HashMap<Integer, String> MONTH_OFFSET = new HashMap<Integer, String>() {
		{
			put(0, "Jan");
			put(3, "Feb");
			put(3, "Mar");
			put(6, "Apr");
			put(1, "May");
			put(4, "Jun");
			put(6, "Jul");
			put(2, "Aug");
			put(5, "Sep");
			put(0, "Oct");
			put(3, "Nov");
			put(5, "Dec");
		}
	};

	public static void main(String args[]) throws NumberFormatException,
			IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.println("Enter the date in the number format(1-31):");
		int date = Integer.parseInt(reader.readLine().trim());
		System.out
				.println("Enter the month with first three letters,e.g. Nov for November");
		String month = reader.readLine().trim();
		System.out.println("Enter the year in number format, e.g. 2009");
		int year = Integer.parseInt(reader.readLine().trim());
		if(!validateInputDate(year, month, date))
			System.out.println("Invalid Entry");
		int dayOffset = date % 7;
		int yearOffset = (year % 100 + (year % 100) / 4) % 7;
		if (checkLeapYear(year)) {
			yearOffset = yearOffset - 1;
		}
		int monthOffset = 0;
		for (Map.Entry<Integer, String> entryMonth : MONTH_OFFSET.entrySet()) {
			if (entryMonth.getValue().equalsIgnoreCase(month))
				monthOffset = entryMonth.getKey();
		}
		DayToDateWithOffsetLogic dayToDateWithOffsetLogic = new DayToDateWithOffsetLogic();
		int centuryOffset = dayToDateWithOffsetLogic.findCenturyOffset(year);
		int totalOffset = yearOffset + monthOffset + dayOffset + centuryOffset;
		System.out.println("Day of the Week:"
				+ WEEKDAY_NUMBER.get(totalOffset % 7));
	}

	private static boolean checkLeapYear(int year) {
		if ((year % 400 == 0) || (year % 4 == 0) && (year % 100 != 0))
			return true;
		return false;
	}

	private static int countryOffsetCreation(int year) {
		int firstTwoDigit = year / 100;
		int nextHighestNumber = DayToDateWithOffsetLogic
				.findNextMultipleOfFour(firstTwoDigit);
		return ((nextHighestNumber - 1) - firstTwoDigit) * 2;
	}

	private static int findNextMultipleOfFour(int number) {
		for (int i = number + 1; i <= 100; i++) {
			if (i % 4 == 0) {
				return i;
			}
		}
		return 0;
	}

	private int findCenturyOffset(int year) {
		return DayToDateWithOffsetLogic
				.countryOffsetCreation(smallerNumberDivisibleByHundred(year));
	}

	private static int smallerNumberDivisibleByHundred(int year) {
		for (int i = year; i >= 0; i--) {
			if (i % 100 == 0)
				return i;
		}
		return 0;
	}

	private static boolean validateInputDate(int year, String month, int date) {
		if (year < 0 || year > 9999 || date < 0 || date > 31) {
			return false;
		}
		boolean monthFlag = false;
		for (Map.Entry<Integer, String> entryMonth : MONTH_OFFSET.entrySet()) {
			if (entryMonth.getValue().equalsIgnoreCase(month)) {
				monthFlag = true;
			}

		}
		if (!monthFlag)
			return false;
		return true;
	}

}
