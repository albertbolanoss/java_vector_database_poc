package labs.milvusdb.component;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Provide operations such as add and search documents.
 */
@Component
public class VectorStoreComponent {
    /**
     * The instance of the vector store for collection vector_store
     */
    private final VectorStore vectorStore;

    public VectorStoreComponent(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /**
     * Add a list of documents to the vector collection
     * @param documents the documents to be added
     */
    public void add(List<Document> documents) {
        vectorStore.add(documents);
    }

    /**
     * Search in the vector collection and return by distance metric and SearchRequest configuration
     * @param searchRequest the search request configuration
     * @return a list of returned Documents
     */
    public List<Document> search(SearchRequest searchRequest) {
        return vectorStore.similaritySearch(searchRequest);
    }
}
