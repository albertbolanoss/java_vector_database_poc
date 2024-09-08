package labs.milvusdb.controller.mapper;

import labs.milvusdb.controller.dto.AddDocReq;
import labs.milvusdb.service.valueobject.AddDocumentVO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface AddDocumentVOMapper extends Converter<AddDocReq, AddDocumentVO> {
    @Override
    AddDocumentVO convert(AddDocReq addDocReq);
}
