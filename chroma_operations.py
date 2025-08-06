import chromadb
from chromadb.utils.embedding_functions import SentenceTransformerEmbeddingFunction

def create_chroma_client():
    sentence_transformer_ef = SentenceTransformerEmbeddingFunction(
        model_name="BAAI/bge-base-en-v1.5"
    )
    chroma_client = chromadb.PersistentClient(path="chroma_persistent_storage")
    collection_name = "collection_qa"
    collection = chroma_client.get_or_create_collection(
        name=collection_name,
        embedding_function=sentence_transformer_ef
    )
    return collection


def add_data(token_split_texts, collection, source_id):
    ids = [f"{source_id}_{i}" for i in range(len(token_split_texts))]
    metadatas = [{"source": source_id} for _ in token_split_texts]

    collection.add(
        ids=ids,
        documents=["Passage: " + text for text in token_split_texts],
        metadatas=metadatas
    )



def retrieve_data(query, collection):
    print("start of operation")

    results = collection.query(
        query_texts=query,
        n_results=5,
        include=["documents", "embeddings", "metadatas"]
    )

    retrieved_documents = results["documents"]

    for i, documents in enumerate(retrieved_documents):
        print(f"Query: {query[i]}")
        print("\nResults:")
        for doc, meta in zip(documents, results["metadatas"][i]):
            print(f"[Source: {meta['source']}]\n{word_wrap(doc)}")
            print("\n" + "-" * 100)

    print("end of operation")
    return retrieved_documents


def delete():
    chroma_client = chromadb.PersistentClient(path="chroma_persistent_storage")
    collection_name = "collection_qa"
    chroma_client.delete_collection(collection_name)

    print(f"Collection '{collection_name}' deleted.")


def word_wrap(text, width=87):
    return "\n".join([text[i: i + width] for i in range(0, len(text), width)])


def project_embeddings(embeddings, umap_transform):
    projected_embeddings = umap_transform.transform(embeddings)
    return projected_embeddings




