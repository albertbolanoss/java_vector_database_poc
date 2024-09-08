package labs.milvusdb.controller;

import jakarta.validation.Valid;
import labs.milvusdb.controller.dto.AddDocReq;
import labs.milvusdb.controller.dto.DocumentRes;
import labs.milvusdb.controller.mapper.AddDocumentVOMapper;
import labs.milvusdb.controller.mapper.DocumentVOMapper;
import labs.milvusdb.service.DocumentService;
import org.springframework.ai.document.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("document")
public class DocumentController {
    private final DocumentService documentService;

    private final AddDocumentVOMapper addDocumentVOMapper;

    private final DocumentVOMapper documentVOMapper;

    public DocumentController(DocumentService documentService,
                              AddDocumentVOMapper addDocumentVOMapper,
                              DocumentVOMapper documentVOMapper) {
        this.documentService = documentService;
        this.addDocumentVOMapper = addDocumentVOMapper;
        this.documentVOMapper = documentVOMapper;
    }

    @PostMapping
    public ResponseEntity<Document> add(@Valid @RequestBody List<AddDocReq> addDocReq) {
        var documents = addDocReq.stream().map(addDocumentVOMapper::convert).toList();

        documentService.addDocuments(documents);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping ResponseEntity<List<DocumentRes>> get(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "topK", defaultValue =  "10") int topK) {

        return ResponseEntity.ok(documentService.search(query, topK).stream()
                .map(documentVOMapper::convert).toList());
    }
}
