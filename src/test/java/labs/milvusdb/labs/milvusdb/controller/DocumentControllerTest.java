package labs.milvusdb.labs.milvusdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import labs.milvusdb.controller.DocumentController;
import labs.milvusdb.controller.dto.AddDocReq;
import labs.milvusdb.controller.mapper.DocumentVOMapper;
import labs.milvusdb.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentController.class)
class DocumentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @MockBean
    private DocumentVOMapper documentVOMapper;

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
}
