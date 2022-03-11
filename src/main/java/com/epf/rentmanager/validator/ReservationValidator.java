package com.epf.rentmanager.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.epf.rentmanager.model.Reservation;

public class ReservationValidator {
	
	public static boolean rentalIsPossible(Reservation rentToCheck, List<Reservation> listOfRentsByVehicleOrClientId) {
		LocalDate startingDateToCheck = rentToCheck.getDateStart();
		for (Reservation rent : listOfRentsByVehicleOrClientId) {
			LocalDate startingDate = rent.getDateStart();
			LocalDate endingDate = rent.getDateEnd();
			if (startingDateToCheck.isAfter(startingDate) & startingDateToCheck.isBefore(endingDate)
					| startingDateToCheck.isEqual(startingDate) | startingDateToCheck.isEqual(endingDate)) {
				return false;
			}
		}
		return true;
	}

	public static boolean startDateIsValid(Reservation rentToCheck) {
		LocalDate startingDateToCheck = rentToCheck.getDateStart();
		LocalDate Today = LocalDate.now();
		if (startingDateToCheck.isAfter(Today) | startingDateToCheck.isEqual(Today)) {
			return true;
		}
		return false;
	}

	public static boolean endDateIsValid(Reservation rentToCheck) {
		LocalDate startingDateToCheck = rentToCheck.getDateStart();
		LocalDate endingDateToCheck = rentToCheck.getDateEnd();
		LocalDate limitEndingDate = startingDateToCheck.plus(8, ChronoUnit.DAYS);
		if (endingDateToCheck.isAfter(startingDateToCheck) & endingDateToCheck.isBefore(limitEndingDate)) {
			return true;
		}
		return false;
	}

	public static boolean maxRentalTimeForOneUser(Reservation rentToCheck, List<Reservation> listOfRentsByClientId) {
		Comparator<Reservation> compareByStartDate = new Comparator<Reservation>() {
			@Override
			public int compare(Reservation rent1, Reservation rent2) {
				return rent1.getDateStart().compareTo(rent2.getDateStart());
			}
		};

		int numberOfRents = listOfRentsByClientId.size();
		if (numberOfRents != 0) {
			Collections.sort(listOfRentsByClientId, compareByStartDate);

			Reservation firstRent = listOfRentsByClientId.get(0);
			LocalDate startDate = firstRent.getDateStart();
			LocalDate endDate = firstRent.getDateEnd();
			long totalSuccessiveRentDuration = ChronoUnit.DAYS.between(startDate, endDate);

			for (int i = 0; i < numberOfRents - 1; i++) {
				Reservation rent1 = listOfRentsByClientId.get(i);
				LocalDate startDate1 = rent1.getDateStart();
				LocalDate endDate1 = rent1.getDateEnd();
				long rentDuration1 = ChronoUnit.DAYS.between(startDate1, endDate1);

				Reservation rent2 = listOfRentsByClientId.get(i + 1);
				LocalDate startDate2 = rent2.getDateStart();
				LocalDate endDate2 = rent2.getDateEnd();
				long rentDuration2 = ChronoUnit.DAYS.between(startDate2, endDate2);

				if (endDate1.isEqual(startDate2.plus(1, ChronoUnit.DAYS))) {
					totalSuccessiveRentDuration = totalSuccessiveRentDuration + rentDuration2;
				} else {
					totalSuccessiveRentDuration = Math.max(rentDuration1, rentDuration2);
				}

				if (rentDuration1 > 7 | rentDuration2 > 7 | totalSuccessiveRentDuration > 7) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean maxRentalTimeOfVehicleByOneUser(Reservation rentToCheck, List<Reservation> listOfRentsByClientId) {
		Comparator<Reservation> compareByStartDate = new Comparator<Reservation>() {
			@Override
			public int compare(Reservation rent1, Reservation rent2) {
				return rent1.getDateStart().compareTo(rent2.getDateStart());
			}
		};

		int numberOfRents = listOfRentsByClientId.size();
		if (numberOfRents != 0) {
			int vehicleId = rentToCheck.getVehicleId();
			Collections.sort(listOfRentsByClientId, compareByStartDate);

			LocalDate startDate = rentToCheck.getDateStart();
			LocalDate endDate = rentToCheck.getDateEnd();
			long totalSuccessiveRentDuration = ChronoUnit.DAYS.between(startDate, endDate);

			Reservation lastRent = listOfRentsByClientId.get(numberOfRents - 1);
			LocalDate lastStartDate = lastRent.getDateStart();
			LocalDate lastEndDate = lastRent.getDateEnd();

			if (startDate.isEqual(lastEndDate.plus(1, ChronoUnit.DAYS)) & vehicleId == lastRent.getVehicleId()) {
				totalSuccessiveRentDuration += ChronoUnit.DAYS.between(lastStartDate, lastEndDate);

				for (int i = numberOfRents - 1; i > 0; i--) {
					Reservation rent1 = listOfRentsByClientId.get(i);
					LocalDate startDate1 = rent1.getDateStart();

					Reservation rent2 = listOfRentsByClientId.get(i - 1);
					LocalDate startDate2 = rent2.getDateStart();
					LocalDate endDate2 = rent2.getDateEnd();
					long rentDuration2 = ChronoUnit.DAYS.between(startDate2, endDate2);

					if (startDate1.isEqual(endDate2.plus(1, ChronoUnit.DAYS)) & vehicleId == rent2.getVehicleId()) {
						totalSuccessiveRentDuration += rentDuration2;
					} else {
						break;
					}
				}
				return totalSuccessiveRentDuration <= 7;
			}
		}
		return true;
	}

	public static boolean maxRentalTimeForOneVehicle(Reservation rentToCheck, List<Reservation> listOfRentsByVehicleId) {
		Comparator<Reservation> compareByStartDate = new Comparator<Reservation>() {
			@Override
			public int compare(Reservation rent1, Reservation rent2) {
				return rent1.getDateStart().compareTo(rent2.getDateStart());
			}
		};

		int numberOfRents = listOfRentsByVehicleId.size();
		if (numberOfRents != 0) {
			Collections.sort(listOfRentsByVehicleId, compareByStartDate);

			long totalSuccessiveRentDuration = 0;
			for (int i = 0; i < numberOfRents - 1; i++) {
				Reservation rent1 = listOfRentsByVehicleId.get(i);
				LocalDate startDate1 = rent1.getDateStart();
				LocalDate endDate1 = rent1.getDateEnd();
				long rentDuration1 = ChronoUnit.DAYS.between(startDate1, endDate1);

				Reservation rent2 = listOfRentsByVehicleId.get(i + 1);
				LocalDate startDate2 = rent2.getDateStart();
				LocalDate endDate2 = rent2.getDateEnd();
				long rentDuration2 = ChronoUnit.DAYS.between(startDate2, endDate2);

				if (endDate1.isEqual(startDate2.plus(1, ChronoUnit.DAYS))) {
					totalSuccessiveRentDuration = totalSuccessiveRentDuration + rentDuration1 + rentDuration2;
				} else {
					totalSuccessiveRentDuration = Math.max(rentDuration1, rentDuration2);
				}

				if (rentDuration1 > 30 | rentDuration2 > 30 | totalSuccessiveRentDuration > 30) {
					return false;
				}
			}
		}
		return true;
	}
}
