package labs.milvusdb.controller;

import jakarta.validation.Valid;
import labs.milvusdb.controller.dto.AddDocReq;
import labs.milvusdb.controller.mapper.DocumentVOMapper;
import labs.milvusdb.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("document")
public class DocumentController {
    private final DocumentService documentService;

    private final DocumentVOMapper documentVOMapper;

    public DocumentController(DocumentService documentService, DocumentVOMapper documentVOMapper) {
        this.documentService = documentService;
        this.documentVOMapper = documentVOMapper;
    }

    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody List<AddDocReq> addDocReq) {
        var documents = addDocReq.stream().map(documentVOMapper::convert).toList();
        documentService.addDocuments(documents);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
