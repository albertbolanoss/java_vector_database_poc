package labs.milvusdb.controller;

import labs.milvusdb.service.SearchService;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EmbeddingController {

    private final EmbeddingModel embeddingModel;

    private final SearchService searchService;

    @Autowired
    public EmbeddingController(EmbeddingModel embeddingModel, SearchService searchService) {
        this.embeddingModel = embeddingModel;
        this.searchService = searchService;
    }


    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "query", defaultValue = "Tell me a joke") String query) {
        EmbeddingResponse embeddingResponse = this.embeddingModel.embedForResponse(List.of(query));
        return Map.of("embedding", embeddingResponse);
    }

    @GetMapping("ai/fill")
    public void fill() {
        searchService.fill();
    }

    @GetMapping("ai/search")
    public List<Document> search(@RequestParam(value = "query", defaultValue = "Alan Turing") String query) {
        return searchService.search(query, 5);
    }
}