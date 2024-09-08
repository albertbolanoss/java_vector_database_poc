package labs.milvusdb.service.valueobject;

import java.util.Map;

public class DocumentVO {
    private String id;

    private String content;

    private Map<String, Object> metadata;

    public DocumentVO() { }

    public DocumentVO(String id, String content, Map<String, Object> metadata) {
        this.id = id;
        this.content = content;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
