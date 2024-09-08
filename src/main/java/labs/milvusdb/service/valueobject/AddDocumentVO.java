package labs.milvusdb.service.valueobject;

import jakarta.validation.constraints.NotNull;
import labs.milvusdb.exception.MissingParameterException;
import labs.milvusdb.validation.OptionalHelper;

import java.util.Map;

@NotNull
public class AddDocumentVO {
    /**
     * The content of document
     */
    @NotNull
    private String content;

    /**
     * The metadata to add to the document
     */
    private Map<String, Object> metadata;

    public AddDocumentVO() {

    }

    public AddDocumentVO(String content) {
        this.content = OptionalHelper.getIfNotNull(content).orElseThrow(
                () -> new MissingParameterException("The content is required."));
    }

    public AddDocumentVO(String content, Map<String, Object> metadata) {
        this.content = OptionalHelper.getIfNotNull(content).orElseThrow(
                () -> new MissingParameterException("The content is required."));
        this.metadata = OptionalHelper.getMap(metadata)
                .orElseThrow(() -> new MissingParameterException("The Metadata key and value are required."));
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}