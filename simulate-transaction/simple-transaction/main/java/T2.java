import java.sql.*;


public class T2 implements Runnable{

    public static Logging log = null;

    public T2( Logging logObj){
        log = logObj;
    }
    public void run(){
        try {
            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.connect();

            String id = null;
            String trxNumString = "";
            Statement statement = connection.createStatement();

            //----Sequence 1
            statement.executeQuery("START TRANSACTION;");
            ResultSet result = statement.executeQuery("SELECT * FROM customers WHERE customer_zip_code_prefix = 1151;");
            while (result.next()){
                id = result.getString("customer_id");
            }
            trxNumString = Utlis.getTransactionNum(statement);
            log.LogTransactionState("Start",trxNumString);
            System.out.println("T2 READS");
            Thread.sleep(3000);


            //----Sequence 2
            Thread.sleep(3000);


            //----Sequence 3
            String updateQuery = "UPDATE customers set customer_city  = 'T2' where customer_id = '"+ id +"';";
            String [] list = updateQuery.split("\\s+");
            statement.executeUpdate(updateQuery);
            log.LogUpdateCustom(list,trxNumString);
            System.out.println("T2 Updates");
            Thread.sleep(3000);


            //----Sequence 4
            Thread.sleep(3000);


            //----Sequence 5
            log.LogTransactionState("Commit",trxNumString);
            connection.commit();
            System.out.println("T2 Commits");
            Thread.sleep(3000);
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }
}