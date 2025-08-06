import os

from dotenv import load_dotenv
from fastapi import FastAPI, BackgroundTasks
from openai import OpenAI
from pydantic import BaseModel
from starlette import status
from starlette.responses import JSONResponse

from aws_operations import createS3client, download_element
from chroma_operations import create_chroma_client, add_data, retrieve_data, delete
from chunks_handler import chunk_text_token_based
from expansion_querie import generate_multi_query, generate_response, generate_support_answer

load_dotenv()
access_key=os.getenv("ACCESS_KEY")
secret_key=os.getenv("SECRET_KEY")
bucket_name=os.getenv("BUCKET_NAME")
openai_key=os.getenv("OPENAI_API_KEY")


class Message_Client(BaseModel):
    message: str


server = FastAPI()
s3=createS3client(access_key,secret_key)
#collection=create_chroma_client()
client = OpenAI(api_key=openai_key,
                base_url="https://openrouter.ai/api/v1")

collection=create_chroma_client()



@server.get("/")
async def root():
    return {"message": "Hello World"}


@server.get("/hello/{name}/{nom}")
async def say_hello(name: str,nom:str):
    return {"message": f"Hello {name}, {nom}"}

@server.post("/upload/{object_key}")
async def upload_file(object_key:str,background_tasks: BackgroundTasks):
    background_tasks.add_task(store_process,object_key)

    return JSONResponse(
        content={"message":"data_uploaded"},
        status_code=status.HTTP_202_ACCEPTED
    )

@server.post("/ask")
async def ask_question(message:Message_Client):
    original_query=message.message
    augmented_query= generate_multi_query(original_query)
    joint_query = [
                      original_query
                  ] + augmented_query
    #hypothetical_answer = generate_support_answer(original_query,client)
    #joint_query = f"{original_query} {hypothetical_answer}"

    retrieved_documents=retrieve_data(joint_query,collection)

    updated_retrieved = [doc for sublist in retrieved_documents for doc in sublist]
    answer = generate_response(original_query, updated_retrieved)
    print(answer)
    return {"answers":answer}

@server.delete("/delete")
def deletedb():
    delete()


def store_process(object_key):
    pdf_texts = download_element(object_key, s3, bucket_name)
    chunks = chunk_text_token_based(pdf_texts)
    source_id = object_key.replace(".pdf", "")
    add_data(chunks, collection,source_id)

