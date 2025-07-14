package lk.ijse.education.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


    /**
     *
     * @author mskku
     */
    public class DBConnection {
        private static DBConnection dBConnection;

        private Connection connection;

        private DBConnection() throws ClassNotFoundException, SQLException{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/education", "root", "9876");

        }

        public static DBConnection getInstance() throws ClassNotFoundException, SQLException{
            if(dBConnection == null){
                dBConnection = new DBConnection();
            }
            return dBConnection;
        }

        public Connection getConnection(){
            return this.connection;
        }
    }

