package com.authentication.models.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.authentication.models.dto.AccountDTO;
import com.authentication.models.entities.Account;
import com.authentication.models.entities.Role;
import com.authentication.models.enums.RoleEnum;

@Mapper(componentModel = "spring")
public interface AccountMapper {

	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

	@Mapping(target = "id", source = "id")
	@Mapping(target = "name", source = "name")
	@Mapping(target = "surname", source = "surname")
	@Mapping(target = "email", source = "email")
	// Il mapping custom per roles viene gestito dai metodi default
	@Mapping(target = "roles", source = "roles")
	AccountDTO toDto(Account user);

	@Mapping(target = "id", source = "id")
	@Mapping(target = "name", source = "name")
	@Mapping(target = "surname", source = "surname")
	@Mapping(target = "email", source = "email")
	@Mapping(target = "roles", source = "roles")
	Account toEntity(AccountDTO userDTO);

	// Metodo custom per convertire un Set<Role> in Set<String>
	default Set<String> mapRolesToStrings(Set<Role> roles) {
		if (roles == null) {
			return null;
		}
		return roles.stream().map(Role::getAuthority) // oppure role -> role.getName().toString()
				.collect(Collectors.toSet());
	}

	// Metodo custom per convertire un Set<String> in Set<Role>
	default Set<Role> mapStringsToRoles(Set<String> roleNames) {
		if (roleNames == null) {
			return null;
		}
		return roleNames.stream().map(roleName -> {
			Role role = new Role();
			// Usa RoleEnum.valueOf per convertire la stringa nell'enum corrispondente
			role.setName(RoleEnum.valueOf(roleName));
			return role;
		}).collect(Collectors.toSet());
	}
}
