from google.cloud import storage
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
import nltk

storage_client = storage.Client()
nltk.download('stopwords')
nltk.download('punkt')

def hello_gcs(event, context):
    last_file = False
    file_number = int(event['name'].split('/')[2].split('.')[0] )
    if file_number > 299:
        print(file_number)
        if file_number == 401:
            last_file = True
        to_bucket(event, 'testdatab00883865', 'testdata.csv', last_file)
    else:
        to_bucket(event, 'traindatab00883865', 'traindata.csv', last_file)


def to_bucket(event, bucket_name, file_name, last_file):
    if last_file == True:
        data='EOF\n'
    else: 
        data = ''
    print(data)
    print(last_file)
    print(file_name)
    if(blob_exists(bucket_name, file_name) == True):
        data = data + read_bucket(bucket_name, file_name)
    else: 
        data = 'Current_Word,Next_Word,Levenshtein_distance\n'
    word_tokens = word_tokenize(read_bucket(event['bucket'], event['name']))
    first_filtered_sentence = [i for i in word_tokens if i.isalpha()]
    final_words = []
    for word in first_filtered_sentence:
        if(word not in get_stop_words()):
            final_words.append(word)
    for idx, val in enumerate(final_words):
        if(idx > 0):
            data = data + f'{final_words[idx-1]},{final_words[idx]},{nltk.edit_distance(final_words[idx-1],  final_words[idx])}\n'
    upload_data(bucket_name, file_name, data)

def upload_data(bucket_name, blob_name, data):
    bucket = storage_client.get_bucket(bucket_name)
    blob = bucket.blob(blob_name)
    blob.upload_from_string(data)

def get_stop_words():
    symbols = [',','.',':']
    stop_words = set(stopwords.words('english'))
    stop_words.update(symbols)
    return stop_words

def read_bucket(bucket_name, blob_name):
    bucket = storage_client.get_bucket(bucket_name)
    blob = bucket.blob(blob_name)
    return blob.download_as_string().decode('utf-8')

def blob_exists(bucket_name, blob_name):
    bucket = storage_client.get_bucket(bucket_name)
    blob = bucket.blob(blob_name)
    return blob.exists()