package labs.milvusdb.controller;

import labs.milvusdb.service.VectorStoreService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class VectorStoreController {
    @Autowired
    private VectorStoreService vectorStoreService;

    @PostMapping("/ai/create")
    public void createCollection() {
        vectorStoreService.createCollection();
    }

    @PostMapping("/ai/add")
    public void addDocuments() {
        vectorStoreService.addDocuments();
    }

    @DeleteMapping("/ai/del")
    public void dropCollection() {
        vectorStoreService.dropCollection();
    }

    @GetMapping("ai/search")
    public List<Document> search(@RequestParam(value = "query", defaultValue = "Efficient data processing and retrieval") String query) {
        return vectorStoreService.searchSimilarDocuments(query);
    }
}
