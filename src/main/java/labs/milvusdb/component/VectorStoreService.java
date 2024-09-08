package labs.milvusdb.component;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.DropCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.index.CreateIndexParam;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VectorStoreService {
    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private MilvusServiceClient milvusClient;

    private static final String COLLECTION_NAME = "java_demo";

    public void createCollection() {

        FieldType idField = FieldType.newBuilder()
                .withName("id")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(true)
                .build();

        FieldType embeddingField = FieldType.newBuilder()
                .withName("embedding")
                .withDataType(DataType.FloatVector)
                .withDimension(1536)  // Dimensión del embedding
                .build();

        FieldType textField = FieldType.newBuilder()
                .withName("text")
                .withDataType(DataType.VarChar)
                .withMaxLength(1024)
                .build();


        CreateCollectionParam createCollectionParam = CreateCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withDescription("A collection for storing AI-related documents")
                .addFieldType(idField)
                .addFieldType(embeddingField)
                .addFieldType(textField)
                .withShardsNum(2)
                .build();

        milvusClient.createCollection(createCollectionParam);


        CreateIndexParam createIndexParam = CreateIndexParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName("embedding")
                .withIndexType(IndexType.IVF_FLAT)
                .withMetricType(MetricType.COSINE)  // Métrica de similitud
                .withExtraParam("{\"nlist\": 1024}")  // Ajusta el valor de nlist
                .build();

        milvusClient.createIndex(createIndexParam);

    }

    public void addDocuments() {
        List<Document> documents = List.of(
                new Document("Exploring the depths of AI with Spring", Map.of("id", "1", "category", "AI")),
                new Document("Navigating through large datasets efficiently", Map.of("id", "2", "category", "AI")),
                new Document("Harnessing the power of vector search", Map.of("id", "3", "importance", "high"))
        );

        vectorStore.add(documents);
    }

    public void dropCollection() {
        DropCollectionParam dropCollectionParam = DropCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build();

        milvusClient.dropCollection(dropCollectionParam);

    }

    public List<Document> searchSimilarDocuments(String query) {
        return vectorStore.similaritySearch(
                SearchRequest.query(query)
                        .withTopK(3)

                        .withSimilarityThreshold(0.1)
        );
    }
}
