package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nino.mybar.entity.Image;

@Mapper(componentModel = "Spring")
public interface ImageMapper {

	@Mapping(target = "imagePath", source = ".")
	Image getImage(String path);

	@Mapping(target = ".", source = "imagePath")
	public String getPath(Image image);
}
