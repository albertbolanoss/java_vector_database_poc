package labs.milvusdb.labs.milvusdb.component;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.DropCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.index.CreateIndexParam;
import labs.milvusdb.component.MilvusClientComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MilvusClientComponentTest {

    @Mock
    private MilvusServiceClient milvusClient;

    @InjectMocks
    private MilvusClientComponent milvusClientComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreatePrimaryKeyType() {
        String id = "testId";
        DataType dataType = DataType.Int64;
        boolean isAutoId = false;

        FieldType fieldType = milvusClientComponent.createPrimaryKeyType(id, dataType, isAutoId);

        assert fieldType != null;
        assert fieldType.getName().equals(id);
        assert fieldType.getDataType().equals(dataType);
        assert fieldType.isPrimaryKey();
        assert fieldType.isAutoID() == isAutoId;
    }

    @Test
    void shouldCreateEmbeddingType() {
        String id = "embeddingId";
        int dimension = 128;

        FieldType fieldType = milvusClientComponent.createEmbeddingType(id, dimension);

        assert fieldType != null;
        assert fieldType.getName().equals(id);
        assert fieldType.getDataType().equals(DataType.FloatVector);
        assert fieldType.getDimension() == dimension;
    }

    @Test
    void shouldCreateCollection() {
        String collectionName = "testCollection";
        FieldType primaryKeyField = FieldType.newBuilder().withName("id").withDataType(DataType.Int64).build();
        FieldType embeddingField = FieldType.newBuilder().withName("embedding").withDataType(DataType.FloatVector)
                .withDimension(1536).build();
        int shardsNum = 2;
        String description = "Test collection";

        CreateCollectionParam createCollectionParam = milvusClientComponent.createCollection(
                collectionName, primaryKeyField, embeddingField, shardsNum, description);

        assert createCollectionParam != null;
        assert createCollectionParam.getCollectionName().equals(collectionName);
        assert createCollectionParam.getDescription().equals(description);
        assert createCollectionParam.getShardsNum() == shardsNum;
        assert createCollectionParam.getFieldTypes().contains(primaryKeyField);
        assert createCollectionParam.getFieldTypes().contains(embeddingField);
    }

    @Test
    void shouldCreateIndexParam() {
        String collectionName = "testCollection";
        String indexFieldName = "embedding";
        IndexType indexType = IndexType.IVF_FLAT;
        MetricType metricType = MetricType.L2;

        CreateIndexParam createIndexParam = milvusClientComponent.createIndexParam(collectionName, indexFieldName, indexType, metricType);

        assert createIndexParam != null;
        assert createIndexParam.getCollectionName().equals(collectionName);
        assert createIndexParam.getFieldName().equals(indexFieldName);
        assert createIndexParam.getIndexType().equals(indexType);
    }

    @Test
    void shouldCallCreateCollection() {
        CreateCollectionParam createCollectionParam = mock(CreateCollectionParam.class);

        milvusClientComponent.createCollection(createCollectionParam);

        verify(milvusClient, times(1)).createCollection(createCollectionParam);
    }

    @Test
    void shouldCallCreateIndex() {
        CreateIndexParam createIndexParam = mock(CreateIndexParam.class);

        milvusClientComponent.createIndex(createIndexParam);

        verify(milvusClient, times(1)).createIndex(createIndexParam);
    }

    @Test
    void shouldCallDropCollection() {
        String collectionName = "testCollection";

        milvusClientComponent.dropCollection(collectionName);

        verify(milvusClient, times(1)).dropCollection(any(DropCollectionParam.class));
    }
}
