package labs.milvusdb.labs.milvusdb.service;

import labs.milvusdb.component.VectorStoreComponent;
import labs.milvusdb.exception.MissingParameterException;
import labs.milvusdb.service.DocumentService;
import labs.milvusdb.service.valueobject.DocumentVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        var doc1 = new DocumentVO("Content1", Map.of("key1", "value1"));
        var doc2 = new DocumentVO("Content2");
        var documentVOList = Arrays.asList(doc1, doc2);

        documentService.addDocuments(documentVOList);

        verify(vectorStoreComponent, times(1)).add(anyList());
    }

    @Test
    void testAddDocumentsWithNullOrEmptyList() {
        List<DocumentVO> emptyList = Collections.emptyList();

        assertThrows(MissingParameterException.class, () -> documentService.addDocuments(null));
        assertThrows(MissingParameterException.class, () -> documentService.addDocuments(emptyList));

        verifyNoInteractions(vectorStoreComponent);
    }

}