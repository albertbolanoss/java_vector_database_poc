package labs.milvusdb.service;

import jakarta.validation.Valid;
import labs.milvusdb.component.VectorStoreComponent;
import labs.milvusdb.exception.MissingParameterException;
import labs.milvusdb.service.valueobject.AddDocumentVO;
import labs.milvusdb.service.valueobject.DocumentVO;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
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
    public void addDocuments(@Valid List<AddDocumentVO> documents) {
        if (documents == null || documents.isEmpty())
            throw new MissingParameterException("There is not documents to be added.");

        var documentsToAdd = documents.stream()
                .map(this::convertDocument)
                .toList();

        vectorStoreComponent.add(documentsToAdd);
    }

    /**
     * Search the query in the vector collection
     * @param query the query to search
     * @param topK the max of the result to return
     * @return a list of the Document VO
     */
    public List<DocumentVO> search(String query, int topK) {
        if (query == null || query.isEmpty())
            throw new MissingParameterException("The query must be specified.");

        if (topK < 1 || topK > 10)
            throw new MissingParameterException("The value of topK must be between 1 and 10.");

        var searchRequest = SearchRequest.defaults()
                .withQuery(query)
                .withTopK(topK);

        return vectorStoreComponent.search(searchRequest)
                .stream().map(doc -> new DocumentVO(
                        doc.getId(),
                        doc.getContent(),
                        doc.getMetadata())
                ).toList();

    }

    /**
     * Verify and return the fields of DocumentVO
     * @param addDocumentVO the Document VO
     * @return a DocumentVO
     */
    private Document convertDocument(@Valid AddDocumentVO addDocumentVO) {
        if (addDocumentVO.getMetadata() == null)
            return new Document(addDocumentVO.getContent());

        return new Document(addDocumentVO.getContent(), addDocumentVO.getMetadata());
    }
}
