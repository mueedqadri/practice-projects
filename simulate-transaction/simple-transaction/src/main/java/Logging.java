import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logging {

    public void write(final String s){
        try {
            Files.writeString(
                    Path.of( "./Log.txt"),
                    s + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
            );
        }
        catch (IOException e){
            e.getMessage();
        }

    }
    public void LogUpdateCustom( String [] list,  String trxNum)  {
        try {
            String tableName = list[1];
            String newValue = list[5];
            String attributeName = list[3];
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
            write(String.format("%-20s %-20s %-40s %-20s %-20s %-20s ", trxNum,  "UPDATE", tableName,newValue.replaceAll("^[\"']+|[\"']+$", ""), attributeName, timeStamp));
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }
    public void LogTransactionState(String transactionState, String trxNum ){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        switch(transactionState) {
            case "Start":
                write(String.format("%-20s %-20s %-40s %-20s %-20s  %-20s", trxNum, "START", "*****Start Transaction", "", "",  timeStamp));
                break;
            case "Commit":
                write(String.format("%-20s %-20s %-40s %-20s %-20s  %-20s", trxNum, "COMMIT", "*****End of Transaction", "", "", timeStamp));
                break;
            case "RollBack":
                write(String.format("%-20s %-20s %-40s %-20s %-20s  %-20s", trxNum,"ROLLBACK", "*****End of Transaction", "","", timeStamp));
                break;
            default:
        }
    }


}