from google.cloud import storage
from time import sleep

client = storage.Client()
count = 1
for i in range(299) :
    print(count)
    bucket = client.bucket('sourcedatab00883865')
    blob = bucket.blob('sourcedatab00883865/remote/'+str(count).zfill(3)+'.txt')
    blob.upload_from_filename(filename='./Dataset/Train/'+str(count).zfill(3)+'.txt')
    count = count + 1
    sleep(0.1)
    
for i in range(2) :
    bucket = client.bucket('sourcedatab00883865')
    blob = bucket.blob('sourcedatab00883865/remote/'+str(count).zfill(3)+'.txt')
    blob.upload_from_filename(filename='./Dataset/Test/'+str(count).zfill(3)+'.txt')
    count = count + 1
    sleep(0.1)


# $env:GOOGLE_APPLICATION_CREDENTIALS="D:\Dalhousie\Serverless\A4\key\onyx-codex-316505-621b1b0e5648.json"