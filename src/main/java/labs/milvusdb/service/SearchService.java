package labs.milvusdb.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SearchService {
    @Autowired
    private SimpleVectorStore vectorStore;



    public void fill() {
        Map<String, Object> metadata = new HashMap<>();
        List<Document> documents = List.of(
                new Document(UUID.randomUUID().toString(), "Artificial intelligence was founded as an academic discipline in 1956.", metadata),
                new Document(UUID.randomUUID().toString(), "Alan Turing was the first person to conduct substantial research in AI.", metadata),
                new Document(UUID.randomUUID().toString(), "Born in Maida Vale, London, Turing was raised in southern England.", metadata)
        );

        vectorStore.add(documents);
    }

    public List<Document> search(String query, int topK) {
        return vectorStore
                .similaritySearch(SearchRequest.query(query)
                        .withTopK(topK));
    }
}
