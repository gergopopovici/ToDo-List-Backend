package edu.bbte.idde.pgim2289.spring.mapper;

import edu.bbte.idde.pgim2289.spring.dto.RequestUserDTO;
import edu.bbte.idde.pgim2289.spring.dto.ResponseUserDTO;
import edu.bbte.idde.pgim2289.spring.model.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @IterableMapping(elementTargetType = User.class)
    User toEntity(RequestUserDTO requestUserDTO);

    ResponseUserDTO toDTO(User user);
}
