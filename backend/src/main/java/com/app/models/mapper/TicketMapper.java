package com.app.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.app.models.dtos.TicketDto;
import com.app.models.entities.Ticket;
import com.authentication.models.mapper.AccountMapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface TicketMapper {
	
	TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);
	
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "title", source = "title")
	@Mapping(target = "description", source = "description")
	@Mapping(target = "status", source = "status")
	@Mapping(target = "createdAt", source = "createdAt")
	@Mapping(target = "updatedAt", source = "updatedAt")
	Ticket toEntity(TicketDto ticketDto);
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "title", source = "title")
	@Mapping(target = "description", source = "description")
	@Mapping(target = "status", source = "status")
	@Mapping(target = "createdAt", source = "createdAt")
	@Mapping(target = "updatedAt", source = "updatedAt")
	@Mapping(target = "account",  source = "account")
	TicketDto toDto(Ticket ticket);

	
}
