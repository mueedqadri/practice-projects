import java.sql.*;

public class DbConnection {

    public static Connection connect(){
        Connection con = null;
        try {
            String url="jdbc:mysql://34.93.125.64:3306/Data5408_vm1";
            String user="test100";
            String pass="Mqadri@1996";
            con = DriverManager.getConnection(url, user, pass);
            con.setAutoCommit(false);
        }
        catch (Exception e){
            System.out.println("*** Connection Error : "+e.getMessage());
        }
        return con;
    }
}

