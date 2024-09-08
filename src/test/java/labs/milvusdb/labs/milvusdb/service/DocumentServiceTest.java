package labs.milvusdb.labs.milvusdb.service;

import labs.milvusdb.component.VectorStoreComponent;
import labs.milvusdb.exception.MissingParameterException;
import labs.milvusdb.service.DocumentService;
import labs.milvusdb.service.valueobject.AddDocumentVO;
import labs.milvusdb.service.valueobject.DocumentVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class DocumentServiceTest {

    @Mock
    private VectorStoreComponent vectorStoreComponent;

    @InjectMocks
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddDocumentsSuccessfully() {
        var doc1 = new AddDocumentVO("Content1", Map.of("key1", "value1"));
        var doc2 = new AddDocumentVO("Content2");
        var documentVOList = Arrays.asList(doc1, doc2);

        documentService.addDocuments(documentVOList);

        verify(vectorStoreComponent, times(1)).add(anyList());
    }

    @Test
    void testAddDocumentsWithNullOrEmptyList() {
        List<AddDocumentVO> emptyList = Collections.emptyList();

        assertThrows(MissingParameterException.class, () -> documentService.addDocuments(null));
        assertThrows(MissingParameterException.class, () -> documentService.addDocuments(emptyList));

        verifyNoInteractions(vectorStoreComponent);
    }

    @Test
    void shouldThrowExceptionWhenQueryIsNull() {
        String query = null;
        int topK = 5;

        assertThrows(MissingParameterException.class, () -> documentService.search(query, topK));
    }

    @Test
    void shouldThrowExceptionWhenQueryIsEmpty() {
        String query = "";
        int topK = 5;

        assertThrows(MissingParameterException.class, () -> documentService.search(query, topK));
    }

    @Test
    void shouldThrowExceptionWhenTopKIsOutOfRange() {
        String query = "test query";

        assertThrows(MissingParameterException.class, () -> documentService.search(query, 0));
        assertThrows(MissingParameterException.class, () -> documentService.search(query, 11));
    }

    @Test
    void shouldReturnDocumentVOList() {
        String query = "test query";
        int topK = 5;
        Document doc1 = new Document("1", "content1", Map.of("meta1", "value1"));
        Document doc2 = new Document("2", "content2", Map.of("meta2", "value2"));
        SearchRequest searchRequest = SearchRequest.defaults()
                .withQuery(query)
                .withTopK(topK);

        given(vectorStoreComponent.search(searchRequest)).willReturn(List.of(doc1, doc2));

        List<DocumentVO> result = documentService.search(query, topK);

        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("content1", result.get(0).getContent());
        assertEquals(Map.of("meta1", "value1"), result.get(0).getMetadata());

        assertEquals("2", result.get(1).getId());
        assertEquals("content2", result.get(1).getContent());
        assertEquals(Map.of("meta2", "value2"), result.get(1).getMetadata());

        verify(vectorStoreComponent).search(searchRequest);
    }

}