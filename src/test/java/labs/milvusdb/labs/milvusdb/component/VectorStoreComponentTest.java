package labs.milvusdb.labs.milvusdb.component;

import labs.milvusdb.component.VectorStoreComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VectorStoreComponentTest {

    @Mock
    private VectorStore vectorStore;

    @InjectMocks
    private VectorStoreComponent vectorStoreComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddDocumentsSuccessfully() {
        List<Document> documents = Arrays.asList(mock(Document.class), mock(Document.class));

        vectorStoreComponent.add(documents);

        verify(vectorStore, times(1)).add(documents);
    }

    @Test
    void testSearchSuccessfully() {
        SearchRequest searchRequest = mock(SearchRequest.class);
        List<Document> expectedDocuments = Arrays.asList(mock(Document.class), mock(Document.class));

        when(vectorStore.similaritySearch(searchRequest)).thenReturn(expectedDocuments);

        List<Document> result = vectorStoreComponent.search(searchRequest);

        verify(vectorStore, times(1)).similaritySearch(searchRequest);
        assertEquals(expectedDocuments, result);
    }
}