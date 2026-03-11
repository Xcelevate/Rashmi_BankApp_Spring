
package org.example;

import org.example.config.SpringConfig;
import org.example.Entities.*;
import org.example.service.BankingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class BankingApp {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringConfig.class);

        BankingService service = ctx.getBean(BankingService.class);
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Welcome to Java Modern Banking System ===");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("3. Create New User");

        System.out.println("Choose: ");
        int choice = sc.nextInt();
        User user = null;

        if (choice == 2) {
            ctx.close();
            return;
        }

        if (choice == 3) {
            sc.nextLine(); // clear buffer
            System.out.print("Enter name: ");
            String name = sc.nextLine();

            System.out.print("Create password: ");
            String pwd = sc.next();

            user = service.createUser(name, pwd);
            System.out.println("User created successfully. Your ID is: " + user.getId());
        }


        else if (choice == 1) {
            System.out.print("User ID: ");
            int uid = sc.nextInt();

            System.out.print("Password: ");
            String pwd = sc.next();

            user = service.login(uid, pwd);

            if (user == null) {
                System.out.println("Invalid credentials");
                ctx.close();
                return;
            }
        }
        else {
            System.out.println("Invalid option");
            ctx.close();
            return;
        }

        System.out.println("Login successful. Welcome, User ID: " + user.getId());
        while (true) {
            System.out.println("""
                    --- MAIN MENU ---
                    1. View My Accounts
                    2. Create New Account
                    3. View Balance
                    4. Deposit Money
                    5. Withdraw Money
                    6. Transfer Money
                    7. Logout
                    """);
            System.out.println("Choose: ");

            int op = sc.nextInt();

            switch (op) {
                case 1 :{
                    List<Account> accounts = service.getAccounts(user);
                    accounts.forEach(a ->
                            System.out.println("Account ID: " + a.getId() +
                                    ", Balance: " + a.getBalance()));
                    break;
                }

                case 2 : {
                    Account acc = service.createAccount(user);
                    System.out.println("New Account Created. ID: " + acc.getId());
                    break;
                }

                case 3 : {
                    System.out.print("Account ID: ");
                    int id = sc.nextInt();
                    System.out.println("Balance: " +
                            service.getAccounts(user).stream()
                                    .filter(a -> a.getId() == id)
                                    .findFirst().get().getBalance());
                    break;
                }

                case 4 : {
                    System.out.print("Account ID: ");
                    System.out.println("Enter Amount");
                    service.deposit(sc.nextInt(), sc.nextDouble());
                    break;
                }

                case 5 : {
                    System.out.println("Account ID: ");
                    System.out.println("Enter Amount");
                    service.withdraw(sc.nextInt(), sc.nextDouble());
                    break;
                }

                case 6 : {
                    System.out.print("From ID: ");
                    int from = sc.nextInt();
                    System.out.print("To ID: ");
                    int to = sc.nextInt();
                    System.out.print("Amount: ");
                    service.transfer(from, to, sc.nextDouble());
                    break;
                }

                case 7 : {
                    System.out.println("Logged out. Thank you!");
                    ctx.close();
                    return;
                }
            }
        }
    }
}
