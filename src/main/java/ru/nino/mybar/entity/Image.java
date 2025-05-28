package ru.nino.mybar.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Image extends IdEntity {

	@Column(columnDefinition = "TEXT")
	@Schema(description = "Путь до изображения", example = "ingredient/Водка.png")
	private String imagePath;

}
