package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import jp.co.sample.domain.Category;
import jp.co.sample.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public void insert(Category category) {
		categoryRepository.insert(category);
	}
	
	public Integer searchParentIdForChild(String categoryName) {
		return categoryRepository.searchParentIdForChild(categoryName);
	}
	
	public List<String> searchName() {
		return categoryRepository.searchName();
	}
	
	public Integer searchCategoryId(String categoryName) {
		return categoryRepository.searchCategoryId(categoryName);
	}
	
	public List<String> findNameAll() {
		return categoryRepository.findNameAll();
	}
	
	public Integer searchParentForChild(Integer parentId, String name) {
		return categoryRepository.searchParentForChild(parentId, name);
	}
	
	public Integer findParent(String name) {
		return categoryRepository.findParent(name);
	}
	
	//以下アプリ作成用
	
	public List<Category> findAllCategory(){
		return categoryRepository.findAllCategory();
	}
	
	public Integer findId(String parentName, String childName, String grandchildName) {
		return categoryRepository.findId(parentName, childName, grandchildName);
	}
	
	public List<Category> findChild(Integer parentId){
		return categoryRepository.findChild(parentId);
	}
	
	public String findByItemCategory(Integer itemCategory) {
		return categoryRepository.findByItemCategory(itemCategory);
	}
	
}
