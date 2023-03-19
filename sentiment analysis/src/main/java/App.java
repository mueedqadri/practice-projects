public class App {
    public static void main(String[] args){
        String fileName = "file_mongo_tweets";
        S3Uploader s3 = new S3Uploader("twitterdatab00883865",
                "Assignment4",
                fileName+".txt",
                "./"+fileName+".txt");
        s3.start();
    }
}
