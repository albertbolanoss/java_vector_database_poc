package labs.milvusdb.service;

import jakarta.validation.Valid;
import labs.milvusdb.component.VectorStoreComponent;
import labs.milvusdb.exception.MissingParameterException;
import labs.milvusdb.service.valueobject.DocumentVO;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    private final VectorStoreComponent vectorStoreComponent;

    public DocumentService(VectorStoreComponent vectorStoreComponent) {
        this.vectorStoreComponent = vectorStoreComponent;
    }

    /**
     * Add documents
     * @param documents the list of document to add
     */
    public void addDocuments(@Valid List<DocumentVO> documents) {
        if (documents == null || documents.isEmpty() )
            throw new MissingParameterException("There is not documents to be added.");

        var documentsToAdd = documents.stream()
                .map(this::convertDocument)
                .toList();

        vectorStoreComponent.add(documentsToAdd);
    }

    /**
     * Verify and return the fields of DocumentVO
     * @param documentVO the Document VO
     * @return a DocumentVO
     */
    private Document convertDocument(@Valid DocumentVO documentVO) {
        if (documentVO.getMetadata() == null)
            return new Document(documentVO.getContent());

        return new Document(documentVO.getContent(), documentVO.getMetadata());
    }
}
