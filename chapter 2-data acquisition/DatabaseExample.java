package packt.databasemavenexample;

/*
    <dependencies><!-- http://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>6.0.2</version>
        </dependency>
        <dependency>
            <!-- jsoup HTML parser library @ http://jsoup.org/ -->
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.9.1</version>
        </dependency>
    </dependencies>
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.lang.System.out;

public class DatabaseExample {

    private Connection connection;

    public DatabaseExample() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/example";
            connection = DriverManager.getConnection(url, "root", "explore");

            // Needed to reset the contents of the table
            Statement statement = connection.createStatement();
            statement.execute("TRUNCATE URLTABLE;");
            
            String insertSQL = "INSERT INTO  `example`.`URLTABLE` "
                    + "(`url`) VALUES " + "(?);";
            PreparedStatement stmt = connection.prepareStatement(insertSQL);
            
            stmt.setString(1, "https://en.wikipedia.org/wiki/Data_science");
            stmt.execute();
            stmt.setString(1, "https://en.wikipedia.org/wiki/Bishop_Rock,_Isles_of_Scilly");
            stmt.execute();

//            String selectSQL = "select * from Record where URL = '" + url + "'";
            String selectSQL = "select * from URLTABLE";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);
            
            out.println("List of URLs");
            while (resultSet.next()) {
                out.println(resultSet.getString(2));
            } 
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DatabaseExample();
    }
}
