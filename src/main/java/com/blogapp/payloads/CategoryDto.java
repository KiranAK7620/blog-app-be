package com.blogapp.payloads;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {


	private Integer categoryId;
	
	@NotBlank
	@Size(min=4 , message = "Min size of title must be 4 chars")
	private String categoryTitle;

	@NotBlank
	@Size(min=10 , message = "Min size of desc must be 10 chars")
	private String categoryDescription;
}
