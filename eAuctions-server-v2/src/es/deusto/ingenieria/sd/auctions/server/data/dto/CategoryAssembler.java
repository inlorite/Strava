package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.util.ArrayList;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Category;

//This class is part of the DTO pattern. It also implements Singleton Pattern.
public class CategoryAssembler {
	private static CategoryAssembler instance;

	private CategoryAssembler() { }
	
	public static CategoryAssembler getInstance() {
		if (instance == null) {
			instance = new CategoryAssembler();
		}
		
		return instance;
	}

	public CategoryDTO categoryToDTO(Category category) {
		CategoryDTO dto = new CategoryDTO();		
		dto.setName(category.getName());
		return dto;
	}

	public List<CategoryDTO> categoryToDTO(List<Category> categories) {		
		List<CategoryDTO> dtos = new ArrayList<>();
		
		for (Category category : categories) {
			dtos.add(this.categoryToDTO(category));
		}
		
		return dtos;
	}
}