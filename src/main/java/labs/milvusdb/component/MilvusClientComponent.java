package labs.milvusdb.component;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.DropCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.index.CreateIndexParam;
import org.springframework.stereotype.Component;

@Component
public class MilvusClientComponent {
    private final MilvusServiceClient milvusClient;

    public MilvusClientComponent(MilvusServiceClient milvusClient) {
        this.milvusClient = milvusClient;
    }

    public FieldType createPrimaryKeyType(String id, DataType dataType, boolean isAutoId) {
        return FieldType.newBuilder()
                .withName(id)
                .withDataType(dataType)
                .withPrimaryKey(true)
                .withAutoID(isAutoId)
                .build();
    }

    public FieldType createEmbeddingType(String id, int dimension) {
        return  FieldType.newBuilder()
                .withName(id)
                .withDataType(DataType.FloatVector)
                .withDimension(dimension)
                .build();
    }

    public CreateCollectionParam createCollection(
            String collectionName, FieldType primaryKeyField,
            FieldType embeddingField, int shardsNum,
            String description) {
        return CreateCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                .withDescription(description)
                .addFieldType(primaryKeyField)
                .addFieldType(embeddingField)
                .withShardsNum(shardsNum)
                .build();
    }

    public CreateIndexParam createIndexParam(String collectionName, String indexFieldName,
                                             IndexType indexType, MetricType metricType) {
        return CreateIndexParam.newBuilder()
                .withCollectionName(collectionName)
                .withFieldName(indexFieldName)
                .withIndexType(indexType)
                .withMetricType(metricType)
                .build();
    }

    public void createCollection(CreateCollectionParam createCollectionParam) {
        milvusClient.createCollection(createCollectionParam);
    }

    public void createIndex(CreateIndexParam createIndexParam) {
        milvusClient.createIndex(createIndexParam);
    }

    public void dropCollection(String collectionName) {
        DropCollectionParam dropCollectionParam = DropCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                .build();

        milvusClient.dropCollection(dropCollectionParam);

    }
}
