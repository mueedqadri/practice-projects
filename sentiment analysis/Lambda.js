let AWS = require('aws-sdk');

exports.handler = (event, context, callback) => {
    let s3 = new AWS.S3();
    let comprehend = new AWS.Comprehend({apiVersion: '2017-11-27'});
    let Key = event.Records[0].s3.object.key;
    let Bucket = event.Records[0].s3.bucket.name;
    let body;
    let jsonData = []
    s3.getObject({ Bucket, Key }, function(err, data) {
        if (err) {
            console.log(err, err.stack);
            callback(err);
        } else {
            body = String(data.Body);
            const tweets = body
            .replace(/[^\x00-\x7F]/g, "")
            .split(/[\n\r]+/g)
            .filter((p) => p.trim())
            .slice(10, 30);
            var params = {
              LanguageCode: "en" ,
              TextList: tweets
            };
            comprehend.batchDetectSentiment(params, function(err, data) {
              if (err) {
                  console.log(err, err.stack); 
              }
              else   {
                data.ResultList.forEach((result, idx) =>{
                    let currentTweetJSON = {
                        Tweet: tweets[idx],
                        Sentiment: result.Sentiment,
                        PositiveScore: result.SentimentScore.Positive,
                        NegativeScore: result.SentimentScore.Negative,
                        NuetralScore: result.SentimentScore.Neutral,
                        MixedScore: result.SentimentScore.Mixed,
                    };
                    jsonData.push(currentTweetJSON);
                })
                console.log( jsonData)
                let bucketParams = {
                    Bucket : 'twitterdatab00883865',
                    Key : "newSentiments.json",
                    Body : JSON.stringify(jsonData),
                };
                s3.putObject(bucketParams, function(err, data) {
                  if (err) console.log(err, err.stack); 
                });
              }         
            });
        };
    });   
}