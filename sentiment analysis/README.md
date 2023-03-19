AWS Sentiment Analysis Function
===============================

This is a Node.js AWS Lambda function that analyzes the sentiment of a batch of tweets stored in an S3 bucket. The function uses the AWS SDK to interact with AWS services such as S3 and Comprehend.

Requirements
------------

This function requires the following dependencies to be installed:

-   `aws-sdk`

Usage
-----

To use this function, you need to deploy it as an AWS Lambda function and set up an S3 bucket to trigger it when a new object is uploaded. The function expects the uploaded object to be a text file containing a batch of tweets separated by line breaks.

When triggered, the function will read the contents of the uploaded file, filter out empty lines and keep only a subset of the tweets (from line 10 to line 30). It will then use AWS Comprehend to analyze the sentiment of each tweet in the batch and create a JSON object with the results. Finally, it will upload the JSON object to a different S3 bucket with a specified key.

Configuration
-------------

This function requires the following environment variables to be set:

-   `BUCKET_NAME`: the name of the S3 bucket that will trigger the function.
-   `OUTPUT_BUCKET_NAME`: the name of the S3 bucket where the JSON output will be stored.
