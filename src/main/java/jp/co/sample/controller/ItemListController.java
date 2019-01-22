package jp.co.sample.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Category;
import jp.co.sample.domain.Item;
import jp.co.sample.form.ItemForm;
import jp.co.sample.service.CategoryService;
import jp.co.sample.service.ItemService;

@Controller
@RequestMapping("/itemlist")
public class ItemListController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public ItemForm setUpItemForm() {
		return new ItemForm();
	}
	
	@RequestMapping("/page")
	public String page(Model model, Integer page) {
		
		if(page == null || page == 0) {
			page = 1;
		}
		
		List<Item> itemList = itemService.findAllPage((page-1) * 30);
		
		model.addAttribute("itemList", itemList);
		model.addAttribute("page", page);
		
		List<Category> categoryList = categoryService.findAllCategory();
		
		List<Category> parentList = new LinkedList<>();
		List<Category> childList = new LinkedList<>();
		List<Category> grandchildList = new LinkedList<>();
		
		for(Category category : categoryList) {
			if(category.getParent() == 0 && category.getNameAll() == null){
				parentList.add(category);
			}else if(category.getParent() != 0 && category.getNameAll() == null) {
				childList.add(category);
			}else {
				grandchildList.add(category);
			}
		}
		
		model.addAttribute("parentList", parentList);
		model.addAttribute("childList", childList);
		model.addAttribute("grandchildList", grandchildList);
		
		model.addAttribute("categoryList", categoryList);
		
		return "jsp/itemList";
	}
	
	/**
	 * brandによる検索結果を表示するメソッド.
	 * 
	 * @param brand
	 * @param model
	 * @return
	 */
	@RequestMapping("/findbrand")
	public String findbrand(String brand, Integer page, Model model) {
		
		if(page == null || page == 0) {
			page = 1;
		}
		
		List<Item> itemList = itemService.findByBrand(brand, (page-1) * 30);
		
		model.addAttribute("itemList", itemList);
		model.addAttribute("page", page);
		
		List<Category> categoryList = categoryService.findAllCategory();
		
		List<Category> parentList = new LinkedList<>();
		List<Category> childList = new LinkedList<>();
		List<Category> grandchildList = new LinkedList<>();
		
		for(Category category : categoryList) {
			if(category.getParent() == 0 && category.getNameAll() == null){
				parentList.add(category);
			}else if(category.getParent() != 0 && category.getNameAll() == null) {
				childList.add(category);
			}else {
				grandchildList.add(category);
			}
		}
		
		model.addAttribute("parentList", parentList);
		model.addAttribute("childList", childList);
		model.addAttribute("grandchildList", grandchildList);
		
		model.addAttribute("categoryList", categoryList);
		
		return "jsp/itemList";
	}
	
}
