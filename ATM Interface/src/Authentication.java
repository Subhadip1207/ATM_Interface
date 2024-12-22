import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
    public boolean isvalidUser(Connection connection, int ATMNumber, int pin){
        try {
            String query = "SELECT Atm_no,pin FROM atm Where Atm_no = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,ATMNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int fetchATMNumber = resultSet.getInt("Atm_no");
                int fetchPassword = resultSet.getInt("pin");
                if(fetchATMNumber == ATMNumber && fetchPassword == pin){
                    return true;
                }else{
                    System.out.println("Wrong ATM Number or Password !!!");
                    return false;
                }
            }
            preparedStatement.close();
            resultSet.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
