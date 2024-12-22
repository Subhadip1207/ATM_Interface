import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceCheck {
    public float checkBalance(Connection connection, int ATMNumber, int pin){
        try {
            String query = "SELECT balance FROM atm Where Atm_no = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,ATMNumber);
            //preparedStatement.setInt(2,pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("balance");

            }
            preparedStatement.close();
            resultSet.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
