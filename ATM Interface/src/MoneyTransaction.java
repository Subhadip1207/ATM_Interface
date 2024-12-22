import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MoneyTransaction {
    public int transaction (Connection connection,BalanceCheck balanceCheck, int ATMNumber, int pin, int amount, int creditedATM){
        if(amount % 100 != 0){
            System.out.println("Please Enter amount multiple of 100. ");
            return 0;
        }
        if(amount<=0){
            System.out.println("Please enter positive amount.");
            return 0;
        }
        float selfCurrbalance = balanceCheck.checkBalance(connection,ATMNumber,pin);
        float creditCurrbalance = balanceCheck.checkBalance(connection,creditedATM,0);
        try {
            if(selfCurrbalance>amount){
                String debitQuery ="UPDATE atm SET balance = balance - ? WHERE Atm_no = ? AND pin = ?";
                String creditQuery ="UPDATE atm SET balance = balance + ? WHERE Atm_no = ?";
                PreparedStatement debitPreparedStatement = connection.prepareStatement(debitQuery);
                PreparedStatement creditPreparedStatement = connection.prepareStatement(creditQuery);
                debitPreparedStatement.setInt(1,amount);
                debitPreparedStatement.setInt(2,ATMNumber);
                debitPreparedStatement.setInt(3,pin);
                creditPreparedStatement.setInt(1,amount);
                creditPreparedStatement.setInt(2,creditedATM);
                int rowAffected1 =  debitPreparedStatement.executeUpdate();
                int rowAffected2 = creditPreparedStatement.executeUpdate();
                float selfCurrbalance1 = balanceCheck.checkBalance(connection,ATMNumber,pin);
                float creditCurrbalance1= balanceCheck.checkBalance(connection,creditedATM,0);
                if((selfCurrbalance+creditCurrbalance == selfCurrbalance1+creditCurrbalance1) && (rowAffected1 == rowAffected2)){
                    connection.commit();
                    System.out.println("Transaction Successfull");
                    System.out.println("Current Balance : ₹ "+balanceCheck.checkBalance(connection,ATMNumber,pin));
                }else{
                    connection.rollback();
                }
                creditPreparedStatement.close();
                debitPreparedStatement.close();
            }else{
                System.out.println("Unefficient Balance!!!");
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
