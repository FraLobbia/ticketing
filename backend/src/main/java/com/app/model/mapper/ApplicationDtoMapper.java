package com.app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.app.model.dtos.CommentDto;
import com.app.model.dtos.TicketDto;
import com.app.model.entities.Comment;
import com.app.model.entities.Ticket;
import com.authentication.models.mapper.AccountMapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface ApplicationDtoMapper  {
	
	ApplicationDtoMapper INSTANCE = Mappers.getMapper(ApplicationDtoMapper.class);
	
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "title", source = "title")
	@Mapping(target = "description", source = "description")
	@Mapping(target = "status", source = "status")
	@Mapping(target = "createdAt", source = "createdAt")
	@Mapping(target = "updatedAt", source = "updatedAt")
	@Mapping(target = "account",  source = "author")
	Ticket toEntity(TicketDto dto);
	
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "title", source = "title")
	@Mapping(target = "description", source = "description")
	@Mapping(target = "status", source = "status")
	@Mapping(target = "createdAt", source = "createdAt")
	@Mapping(target = "updatedAt", source = "updatedAt")
	@Mapping(target = "author",  source = "account")
	TicketDto toDto(Ticket e);

	@Mapping(target = "id", source = "id")
	@Mapping(target = "content", source = "content")
	@Mapping(target = "createdAt", source = "createdAt")
	@Mapping(target="updatedAt", source="updatedAt")
	@Mapping(target = "account", source = "author")
	@Mapping(target = "ticket", source = "ticket")
	Comment toEntity(CommentDto dto);
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "content", source = "content")
	@Mapping(target = "createdAt", source = "createdAt")
	@Mapping(target="updatedAt", source="updatedAt")
	@Mapping(target = "author", source = "account")
	@Mapping(target = "ticket", source = "ticket")
	CommentDto toDto(Comment e);
	
}
