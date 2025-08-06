
import httpx
from dotenv import load_dotenv

#OLLAMA_URL=os.getenv("OLLAMA_URL")


def generate_multi_query(query):
    print("start the queries ")

    prompt = """
    You are a helpful support assistant or tutor guiding a user who is asking about something explained in a technical document (such as a PDF guide or report).

    The user has asked a question related to this document.

    Your task is to suggest **three related questions** that could help guide their search or deepen their understanding.

    ⚠️ Important rules:
        - Only output the three suggested questions — nothing else.
        - Do not add any introductions like "Here are some questions..." or "Depending on the context..."
        - Do not include any explanations or commentary.
        - Format: each question should be on a new line, no bullet points, no numbering.

    Begin immediately:

                """

    messages = [
        {"role": "system", "content": prompt},
        {"role": "user", "content": query},
    ]

    response = chat_with_ollama(messages)
    content = response.strip().split("\n")
    print("achieved the queries ")
    return content

def generate_response(question, relevant_chunks):


    #system_prompt = (
     #   "You are a helpful support assistant.\n"
      #  "If the user's message is casual or general (such as greetings or small talk), respond naturally using your own knowledge.\n\n"
       # "If the user asks a technical or topic-specific question, use the following instruction:\n"
        #"\"You are an assistant for question-answering tasks. Use the following pieces of retrieved context to answer the question. "
        #"If you don't know the answer, say that you don't know. Use three sentences maximum and keep the answer concise.\"\n\n"
        #f"Context:\n{context}"
    #)
    context = "\n\n".join(relevant_chunks)
    print(" start of answer")
    messages = [
        {"role": "system", "content": "You are a helpful assistant."},
        {"role": "user", "content": f"Here are some pieces of context to help you answer:\n{context}"},
        {"role": "user", "content": f"Question: {question}\nAnswer concisely in maximum 3 sentences."}
    ]

    #messages = [
     #   {"role": "system", "content": system_prompt},
      #  {"role": "user", "content": question},
    #]

    response = chat_with_ollama(messages)
    print("end of answer")
    return response



def generate_support_answer(query):
    prompt = """You are a highly capable assistant trained to analyze and summarize information.
               Generate an answer that could be found in a trustworthy and informative source related to the topic."""

    messages = [
        {
            "role": "system",
            "content": prompt,
        },
        {"role": "user", "content": query},
    ]

    response= chat_with_ollama(messages)
    return response


def chat_with_ollama(messages, model="llama3:8b",):
    payload = {
        "model": model,
        "messages": messages,
        "stream": False
    }
    OLLAMA_URL="http://localhost:11434/api/chat"

    response = httpx.post(OLLAMA_URL, json=payload,timeout=120.0)
    response.raise_for_status()
    data = response.json()
    return data["message"]["content"]

