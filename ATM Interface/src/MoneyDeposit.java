import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MoneyDeposit {
    public int deposit (Connection connection,BalanceCheck balanceCheck, int ATMNumber, int pin, int amount){
        if(amount % 100 != 0){
            System.out.println("Please Enter amount multiple of 100. ");
            return 0;
        }
        if(amount<=0){
            System.out.println("Please enter positive amount.");
            return 0;
        }
        float currBlance = balanceCheck.checkBalance(connection,ATMNumber,pin);
        try {
            String query ="UPDATE atm SET balance = balance + ? WHERE Atm_no = ? AND pin = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,amount);
            preparedStatement.setInt(2,ATMNumber);
            preparedStatement.setInt(3,pin);
            preparedStatement.executeUpdate();
            float currBlance1 = balanceCheck.checkBalance(connection,ATMNumber,pin);
            if(currBlance1 == currBlance+amount){
                connection.commit();
                System.out.println("Deposit Successful");
                System.out.println("Current Balance : ₹ "+balanceCheck.checkBalance(connection,ATMNumber,pin));
            }else{
                connection.rollback();
                System.out.println("Deposit Does not Completed!!!");
            }
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return  0;
    }
}
