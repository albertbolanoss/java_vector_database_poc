package labs.milvusdb.labs.milvusdb.component;

import labs.milvusdb.component.EmbeddingModelComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class EmbeddingModelComponentTest {

    @Mock
    private EmbeddingModel embeddingModel;

    @InjectMocks
    private EmbeddingModelComponent embeddingModelComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnEmbeddingResponse() {
        String text = "test text";

        given(embeddingModel.embedForResponse(List.of(text))).willReturn(new EmbeddingResponse(Collections.emptyList()));

        EmbeddingResponse response = embeddingModelComponent.embeddingText(text);


        verify(embeddingModel, times(1)).embedForResponse(anyList());
    }
}
