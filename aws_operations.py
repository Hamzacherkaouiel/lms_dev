from io import BytesIO

import boto3
from pypdf import PdfReader




def createS3client(access_key,secret_key):
    s3=boto3.client(
        's3',
        aws_access_key_id=access_key,
        aws_secret_access_key=secret_key,
        region_name="us-east-1"
    )

    return s3

def download_element(object_key,s3,bucket_name):
   response= s3.get_object(Bucket=bucket_name,Key=object_key)
   pdf_stream = BytesIO(response['Body'].read())
   reader = PdfReader(pdf_stream)
   pdf_texts = [p.extract_text().strip() for p in reader.pages]
   pdf_texts = [text for text in pdf_texts if text]
   return pdf_texts


