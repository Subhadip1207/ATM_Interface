import java.sql.*;
import java.util.Scanner;

public class ATM_Testing{
    private static final String url = "jdbc:mysql://localhost:3306/atm_project";
    private static final String userName = "root";
    private static final String password = "Subhadip@1272003";
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection connection = DriverManager.getConnection(url,userName,password);
            connection.setAutoCommit(false);
            Authentication authentication = new Authentication();
            BalanceCheck balanceCheck = new BalanceCheck();
            MoneyWithdrawl moneyWithdrawl = new MoneyWithdrawl();
            MoneyDeposit moneyDeposit = new MoneyDeposit();
            MoneyTransaction moneyTransaction = new MoneyTransaction();
            Scanner input = new Scanner(System.in);
            int ATMNumber;
            int pin;
            int amount;
            do{
                System.out.print("Enter ATM Number : ");
                ATMNumber = input.nextInt();
                System.out.print("Enter Pin Number : ");
                pin = input.nextInt();
                boolean validity = authentication.isvalidUser(connection,ATMNumber,pin);
                if(validity){
                    break;
                }
            }while(true);
            System.out.println("Welcome To ATM Machine...");
            do{System.out.print("1.Check Balance\n2.Withdraw Money\n3.Deposit Money\n4.Cash Transcation\n5.Exit");
                System.out.print("\nEnter Your Choice: ");
                int choice = input.nextInt();
                switch (choice){
                    case 1 : float fetchAmount = balanceCheck.checkBalance(connection,ATMNumber,pin);
                        System.out.println("Current Balance : ₹ "+fetchAmount);
                        break;
                    case 2 :
                        System.out.print("Enter The Amount : ");
                        amount = input.nextInt();
                        moneyWithdrawl.withdraw(connection,balanceCheck,ATMNumber,pin,amount);
                        break;
                    case 3 :
                        System.out.print("Enter The Amount : ");
                        amount = input.nextInt();
                        moneyDeposit.deposit(connection,balanceCheck,ATMNumber,pin,amount);
                        break;
                    case 4 :
                        System.out.print("Enter The Amount : ");
                        amount = input.nextInt();
                        System.out.println("Enter the credited ATMNumber: ");
                        int creditedATM = input.nextInt();
                        moneyTransaction.transaction(connection,balanceCheck,ATMNumber,pin,amount,creditedATM);
                        break;
                    case 5 :
                        System.out.println("Collect Your Card");
                        System.out.println("Thank's for using.");
                        connection.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid Choice...");
                }}while (true);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}