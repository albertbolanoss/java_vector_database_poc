package labs.milvusdb.controller.mapper;

import labs.milvusdb.controller.dto.AddDocReq;
import labs.milvusdb.service.valueobject.DocumentVO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface DocumentVOMapper extends Converter<AddDocReq, DocumentVO> {
    @Override
    DocumentVO convert(AddDocReq addDocReq);
}
