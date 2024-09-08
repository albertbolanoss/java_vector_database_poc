package labs.milvusdb.controller.dto;

import java.util.Map;

public class AddDocReq {
    /**
     * The content of document
     */
    private String content;

    /**
     * The metadata to add to the document
     */
    private Map<String, Object> metadata;

    public AddDocReq(String content) {
        this.content = content;
    }

    public AddDocReq() {

    }

    public AddDocReq(String content, Map<String, Object> metadata) {
        this.content = content;
        this.metadata = metadata;
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
