package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.view.ConsoleService;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	//private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	//private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static AuthenticatedUser currentUser;
	private ConsoleService console;
    private AuthenticationService authenticationService;
    private AccountService accountService;
	NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new AccountService());
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService, AccountService accountService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.accountService=accountService;

	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			}  else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			}  else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private BigDecimal viewCurrentBalance() {
		int userId = currentUser.getUser().getId();
		System.out.println("Your userid: " + userId);
		Long id = Long.valueOf(userId);
		Account account = accountService.getAccount(id);
		BigDecimal balance = account.getBalance();

		String money = formatter.format(balance);
		System.out.println("Your balance is: " + money);
		return balance;
	}

	private void viewTransferHistory() {

        int userId = currentUser.getUser().getId();
        System.out.println("Your userid: " + userId);
        Long id = Long.valueOf(userId);
        Account account = accountService.getAccount(id);
        Transfer[] transfers = accountService.getAll(account.getAccountId());

        //create a list of just transfer ids
        List<Integer> transferIds = new ArrayList<>();
        for (Transfer transfer : transfers) {
        	int transferId = transfer.getId().intValue();
            transferIds.add(transferId);
        }

        if (transfers.length == 0) {
            System.out.println("There are no transfers available for this account.");
            mainMenu();
        }

        for (Transfer transfer : transfers) {
            System.out.println("Amount transferred:  " + formatter.format(transfer.getAmount()) + "\nFrom account: "
                    + transfer.getAccountFrom() + "\nTo account: " + transfer.getAccountTo() + "\nTransaction Id: "
                    + transfer.getId());
            System.out.println("_________________________________________");

        }

		int transactionID = console.getUserInputInteger("Enter the transaction id to view its full details");
		if (!transferIds.contains(transactionID)){
			System.out.println("You did not enter a valid id.");
			mainMenu();
		}

		//get full results of one transfer
		Long transferId = Long.valueOf(transactionID);
		Transfer transfer = accountService.getTransfer(id, transferId);

        System.out.println("\nTransaction Id: " + transfer.getId() +
                "\nAmount transferred:  " + formatter.format(transfer.getAmount()) +
                "\nFrom account: " + transfer.getAccountFrom() +
                "\nTo account: " + transfer.getAccountTo() +
                "\nStatus: " + transfer.getStatus() +
                "\nType: " + transfer.getType());
        System.out.println("_________________________________________");


	}

	//optional
	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}


	private void sendBucks() {
		Integer userIdToSendTo = 0;
		BigDecimal amountToSend = null;
		// display list of all users and their ids
		User[] users = accountService.getAllUsers();
		//create a new list of just all the userIds.
		List<Integer> userIds = new ArrayList<>();
		for (User user : users){
			userIds.add(user.getId());
		}
		System.out.println("List of users to send $$ to:");
		for (User user : users) {
			if (!user.getUsername().equals(currentUser.getUser().getUsername())) {
				System.out.println("Username: " + user.getUsername() + ", UserId: " + user.getId());
				System.out.println("*************************************************************");
			}
		}

		// select a user id to send TO. check first if valid.
		userIdToSendTo = console.getUserInputInteger("Enter the UserId of your recipient");
		Scanner scanner = new Scanner(System.in);
		if (!userIds.contains(userIdToSendTo)) {
			System.out.println("You did not select a valid userid.");
			mainMenu();

		}

		// collect amount of the transfer. check first if valid.
		Integer numberEntered = console.getUserInputInteger("Enter the amount of the transfer");
		amountToSend = new BigDecimal(numberEntered);
		// check to see if current balance is enough to cover amount
		// if not, ask them to add more money in their account or just tell them
		// they can't complete their request at this time and return to main menu.
		int userId = currentUser.getUser().getId();
		Long id = (long) userId;
		Account userAccount = accountService.getAccount(id);
		Long accountId = userAccount.getAccountId();
		if (amountToSend.compareTo(userAccount.getBalance()) == 1){
			System.out.println("You do not have enough funds to complete this transaction.");
			mainMenu();
		}
		//check if number is greater than 0.
		if (numberEntered <= 0){
			System.out.println("You did not enter a positive number.");
			mainMenu();
		}


		// else POST request to create the transfer
		Long recipientId = Long.valueOf(userIdToSendTo);
		Account recipientAccount = accountService.getAccount(recipientId);
		Long recipientAccountId = recipientAccount.getAccountId();
		Transfer transfer = new Transfer(amountToSend, accountId, recipientAccountId);
		Long createdTransferID = accountService.createTransfer(transfer);
		System.out.println("Success! Your Transaction ID is: " + createdTransferID);

		// PUT method to update recipient account balance
		accountService.updateBalance(recipientId, amountToSend);
		recipientAccount = accountService.getAccount(recipientId);


		// PUT method to update from account balance
		accountService.updateBalance(id, amountToSend.negate());
		userAccount = accountService.getAccount(id);
		System.out.println("Your balance is now: " + formatter.format(userAccount.getBalance()));
		
	}




	//optional
	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private AuthenticatedUser login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
				System.out.println("Current logged in user: " + currentUser.getUser().getUsername());
				return currentUser;
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
		//should never get here
		return null;
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}

	public static AuthenticatedUser getCurrentUser(){
    	return currentUser;
	}


}
