package com.example.angelneria_final_project.mapper;

import com.example.angelneria_final_project.domain.entities.User;
import com.example.angelneria_final_project.domain.enums.Roles;
import com.example.angelneria_final_project.infraestructure.dto.input.RegistryInputDto;
import com.example.angelneria_final_project.infraestructure.dto.input.UserInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.UserComplexOutputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.UserSimpleOutputDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CountryMapper.class, OrderMapper.class}, imports = Roles.class)
public interface UserMapper {

    List<UserSimpleOutputDto> userListToUserDtoList(List<User> userList);

    UserSimpleOutputDto userToUserDto(User user);

    UserComplexOutputDto userToComplexUserDto(User user);

    User userDtoToUser(UserInputDto userInputDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserInputDto userInputDto, @MappingTarget User user);

    @Mapping(target = "rol", expression = "java(Roles.USER)")
    @Mapping(target = "isActive", expression = "java(true)")
    User registryDtoToPerson(RegistryInputDto registryInputDto);
}
