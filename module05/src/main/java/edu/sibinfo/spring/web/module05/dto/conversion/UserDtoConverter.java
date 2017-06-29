package edu.sibinfo.spring.web.module05.dto.conversion;

import edu.sibinfo.spring.web.module05.domain.User;
import edu.sibinfo.spring.web.module05.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserDtoConverter extends BaseDtoConverter<UserDTO, User> {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", ignore = true)
  public abstract User convert(UserDTO user);

  @Mapping(target = "confirm", ignore = true)
  public abstract UserDTO convert(User user);
}
