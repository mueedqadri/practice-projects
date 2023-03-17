from google.cloud import storage
from typing import Dict
from google.cloud import aiplatform
from google.protobuf import json_format
from google.protobuf.struct_pb2 import Value

storage_client = storage.Client()

def hello_gcs(event, context):
    data = read_bucket(event['bucket'], event['name']).split('\n')
    input_data = []
    if data[0] == 'EOF':
      i = 0
      for row in data:
        if i > 1:
          cloumns = row.split(',')
          current_row = {
            "CurrentWord": cloumns[0],
            "NextWord": cloumns[1]
          }
          input_data.append(row)
        i = i +1
      print(input_data)
      predicted_values = predict_tabular_classification_sample(project="357212121523", endpoint_id="1241172705898659840", location="us-central1", instance_dict= input_data)
      count = 1
      results = ''
      for value in predicted_values:
        results = results + data[count] +  ','+ value['value'] + '\n'
      upload_data(event['bucket'], 'results.csv', results)

def upload_data(bucket_name, blob_name, data):
  bucket = storage_client.get_bucket(bucket_name)
  blob = bucket.blob(blob_name)
  blob.upload_from_string(data)
  
def read_bucket(bucket_name, blob_name):
    bucket = storage_client.get_bucket(bucket_name)
    blob = bucket.blob(blob_name)
    return blob.download_as_string().decode('utf-8')

def predict_tabular_classification_sample(project,endpoint_id,instance_dict,location = "us-central1",api_endpoint= "us-central1-aiplatform.googleapis.com",):
    client_options = {"api_endpoint": api_endpoint}
    client = aiplatform.gapic.PredictionServiceClient(client_options=client_options)
    inc=[]
    for i in instance_dict:
         inc.append( json_format.ParseDict(i, Value()))
    instances = inc
    parameters_dict = {}
    parameters = json_format.ParseDict(parameters_dict, Value())
    endpoint = client.endpoint_path(project=project, location=location, endpoint=endpoint_id)
    response = client.predict(endpoint=endpoint, instances=instances, parameters=parameters)
    predictions = response.predictions
    mylist = []
    for prediction in predictions:
        prediction=prediction
        mylist.append(dict(prediction))
    return mylist