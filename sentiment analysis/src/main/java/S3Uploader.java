import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class S3Uploader {

private static String bucketName ;
private static String folderName ;
private static String fileNameInS3;
private static String fileNameInLocalPC;
private final Regions clientRegion = Regions.US_EAST_1;
private final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
        .withRegion(clientRegion)
        .build();

public S3Uploader(String bucketName, String folderName, String fileNameInS3, String fileNameInLocalPC){
    S3Uploader.bucketName = bucketName;
    S3Uploader.folderName = folderName;
    S3Uploader.fileNameInS3 = fileNameInS3;
    S3Uploader.fileNameInLocalPC = fileNameInLocalPC;
}

    public void start()
    {
        try {
            boolean isUploaded = false;
            while(!isUploaded){
                if (s3Client.doesBucketExistV2(bucketName)) {
                    uploadFile();
                    isUploaded =true;
                } else {
                    createBucket();
                }
            }
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        }
    }

    private void createBucket(){
        try {
            s3Client.createBucket(bucketName);
        } catch (AmazonS3Exception e) {
            System.err.println(e.getErrorMessage());
        }
    }

    public void  uploadFile(){
    try {

        PutObjectRequest request = new PutObjectRequest(bucketName, folderName + "/" + fileNameInS3, new File(fileNameInLocalPC));
        s3Client.putObject(request);
        System.out.println("--Uploading "+fileNameInLocalPC+" done");
    }catch (Exception e) {
        e.printStackTrace();
    }
}}
