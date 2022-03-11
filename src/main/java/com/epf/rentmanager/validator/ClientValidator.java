package com.epf.rentmanager.validator;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;
import com.epf.rentmanager.model.Client;

public class ClientValidator {
	
	public static boolean isLegal(Client client) {
        Period period = Period.between(client.getBirthdate(), LocalDate.now());
        int clientAge = period.getYears();
		return clientAge > 18;
	}
	
	
	public static boolean isEmailNotTaken(Client clientToCheck, List<Client> clientList) {
		Pattern pattern = Pattern.compile("^[\\w!#$%&’+/=?`{|}~^-]+(?:\\.[\\w!#$%&’+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
		boolean isEmailValid = pattern.matcher(clientToCheck.getEmail()).matches();
		for (Client client : clientList) {
			boolean isEmailTaken = client.getEmail().equals(clientToCheck.getEmail());
			if (isEmailTaken || !isEmailValid) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isFirstNameValid(Client clientToCheck) {
		return clientToCheck.getFirstname().length() > 2;
	}
	
	public static boolean isLastNameValid(Client clientToCheck) {
		return clientToCheck.getLastname().length() > 2;
	}
	
	
}
