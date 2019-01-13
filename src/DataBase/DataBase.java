package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataBase {
	    /**
	     * connect to the game DataBase
	     * compare the last game played to other games played on the same map
	     * compare to other games played by class
	     */
	    public static void CompareToAll()
	    {
	        String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	        String jdbcUser="student";
	        String jdbcPassword="student";
	        try {
	        	//CONNECTION
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
	            Statement statement = connection.createStatement();

	            String Query = "SELECT * FROM logs where FirstID = 323230102 and SecondID = 302499652;";
	            ResultSet resultSet = statement.executeQuery(Query);
	            resultSet.last(); // select last game
	            double LastGameScore = resultSet.getDouble("Point"); //get last game score
	            double GameID = resultSet.getDouble("SomeDouble");	//get last game ID

	            Query = "SELECT * FROM logs where FirstID = 323230102 and SecondID = 302499652 and SomeDouble = "
	                                       + GameID + " ORDER BY point DESC;"; 
	            resultSet = statement.executeQuery(Query);	// get all games with same ID
	            int i = 1;	//counter
	            while (resultSet.next())
	            {
	                if(resultSet.getDouble("Point") > LastGameScore)	//count how many got higher score
	                    i++;
	                else
	                    break;	//everyone else are lower.
	            }

	            System.out.println("Last game rank:" + i + " compare to our games in game number: " + GetGameNumByID(GameID));

	            Query = "SELECT * FROM logs where SomeDouble = " + GameID + " ORDER BY point DESC;"; 
	            resultSet = statement.executeQuery(Query);	// get all games played by class in GameID
	            i = 1;
	            
	            while (resultSet.next())	//count how many got higher score
	            {
	                if(resultSet.getDouble("Point") > LastGameScore)
	                    i++;
	                else
	                    break;
	            }

	            System.out.println("Last game rank:" + i + " compare to all games in game number: " + GetGameNumByID(GameID));


	            resultSet.close();
	            statement.close();
	            connection.close();
	        }
	        catch (SQLException sqle) {
	            System.out.println("SQLException: " + sqle.getMessage());
	            System.out.println("Vendor Error: " + sqle.getErrorCode());
	        }

	        catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * received GameID and translate it to the Game Number. only work on jar v0.2!
	     * @param GameID The Game identification number
	     * @return a number between 1 and 9 that represent a game
	     */
	    private static int GetGameNumByID(double GameID)
	    {
	        if(GameID == 2.12825983E9)
	            return 1;
	        if(GameID == 1.149748017E9)
	            return 2;
	        if(GameID == -6.8331707E8)
	            return 3;
	        if(GameID == 1.193961129E9)
	            return 4;
	        if(GameID == 1.577914705E9)
	            return 5;
	        if(GameID == -1.315066918E9)
	            return 6;
	        if(GameID == -1.377331871E9)
	            return 7;
	        if(GameID == 3.06711633E8)
	            return 8;
	        if(GameID == 9.19248096E8)
	            return 9;
	        else return -1;

	    }

}
