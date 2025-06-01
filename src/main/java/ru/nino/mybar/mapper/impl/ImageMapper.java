package ru.nino.mybar.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nino.mybar.entity.Image;

@Mapper(componentModel = "Spring")
public interface ImageMapper {

	@Mapping(target = "imagePath", source = ".")
	Image getImage(String path);

	default String getPath(Image image) {
		if (image == null) {
			return null;
		}
		return image.getImagePath();
	}
}
