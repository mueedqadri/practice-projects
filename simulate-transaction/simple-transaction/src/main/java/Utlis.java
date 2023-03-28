import java.sql.ResultSet;
import java.sql.Statement;

public class Utlis {
    public static String getTransactionNum(Statement statement){
        String trxNumString = "";
        try {
            ResultSet trxNum = statement.executeQuery("SELECT tx.trx_id FROM information_schema.innodb_trx tx WHERE tx.trx_mysql_thread_id = connection_id()");
            while (trxNum.next()){
                trxNumString = trxNum.getString("trx_id");
            }
        }
        catch (Exception e){
            throw new RuntimeException();
        }
        return trxNumString;
    }
}
