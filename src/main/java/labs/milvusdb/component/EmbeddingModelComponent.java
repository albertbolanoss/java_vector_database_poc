package labs.milvusdb.component;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmbeddingModelComponent {
    private final EmbeddingModel embeddingModel;

    public EmbeddingModelComponent(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public EmbeddingResponse embeddingText(String text) {
        return this.embeddingModel.embedForResponse(List.of(text));
    }
}
