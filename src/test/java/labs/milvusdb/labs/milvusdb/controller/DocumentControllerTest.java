package labs.milvusdb.labs.milvusdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import labs.milvusdb.controller.DocumentController;
import labs.milvusdb.controller.dto.AddDocReq;
import labs.milvusdb.controller.dto.DocumentRes;
import labs.milvusdb.controller.mapper.AddDocumentVOMapper;
import labs.milvusdb.service.DocumentService;
import labs.milvusdb.service.valueobject.DocumentVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentController.class)
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @MockBean
    private AddDocumentVOMapper addDocumentVOMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void shouldAddDocuments() throws Exception {
        var addDocReqList = List.of(
                new AddDocReq("content1", Map.of("key1", "value1")),
                new AddDocReq("content2"));

        var requestBody = objectMapper.writeValueAsString(addDocReqList);

        mockMvc.perform(post("/document")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        verify(documentService, times(1)).addDocuments(anyList());
    }

    @Test
    void shouldReturnDocumentList() throws Exception {
        String query = "test query";
        int topK = 5;
        Map<String, Object> metadata = Map.of(
                "key1", "value1",
                "key2", 123,
                "key3", true);

        DocumentVO documentVO = new DocumentVO("id1", "content1", metadata);

        given(documentService.search(query, topK)).willReturn(List.of(documentVO));

        mockMvc.perform(get("/document")
                        .param("query", query)
                        .param("topK", String.valueOf(topK))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("id1"))
                .andExpect(jsonPath("$[0].content").value("content1"));
    }

    @Test
    void shouldUseDefaultTopKWhenNotProvided() throws Exception {
        String query = "test query";
        int defaultTopK = 10;
        Map<String, Object> metadata = Map.of(
                "key1", "value1",
                "key2", 123,
                "key3", true);

        DocumentVO documentVO = new DocumentVO("id1", "content1", null);
        DocumentRes documentRes = new DocumentRes("id1", "content1", metadata);

        given(documentService.search(query, defaultTopK)).willReturn(List.of(documentVO));


        mockMvc.perform(get("/document")
                        .param("query", query)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("id1"))
                .andExpect(jsonPath("$[0].content").value("content1"));
    }

    @Test
    void shouldReturnBadRequestWhenQueryIsMissing() throws Exception {
        mockMvc.perform(get("/document")
                        .param("topK", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
