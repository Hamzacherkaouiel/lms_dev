from langchain_text_splitters import RecursiveCharacterTextSplitter, SentenceTransformersTokenTextSplitter


def divide_text(pdf_texts):
    character_splitter = RecursiveCharacterTextSplitter(
        separators=["\n\n", "\n", ". ", " ", ""], chunk_size=1000, chunk_overlap=0
    )
    character_split_texts = character_splitter.split_text("\n\n".join(pdf_texts))
    return character_split_texts

def divide_by_token(pdf_texts):
    character_split_texts=better_divide_text(pdf_texts)
    token_splitter = SentenceTransformersTokenTextSplitter(
        chunk_overlap=0, tokens_per_chunk=256
    )
    token_split_texts = []
    for text in character_split_texts:
        token_split_texts += token_splitter.split_text(text)
    print(
        token_split_texts
    )
    return token_split_texts

def better_divide_text(pdf_texts):
    splitter = RecursiveCharacterTextSplitter(
        chunk_size=700,
        chunk_overlap=100,
        separators=["\n\n", "\n", ".", "!", "?"]
    )
    return splitter.split_text("\n\n".join(pdf_texts))

def chunk_text_token_based(pdf_texts):
    token_splitter = SentenceTransformersTokenTextSplitter(
            chunk_overlap=60,
            tokens_per_chunk=384
        )

    chunks = token_splitter.split_text("\n\n".join(clean_pdf_texts(pdf_texts)))
    return chunks
def clean_pdf_texts(texts):
    return [page.strip() for page in texts if page and page.strip()]

