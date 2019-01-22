package jp.co.sample.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/item")
public class ItemAddController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ItemService itemService;
	
	@ModelAttribute
	public ItemForm setUpItemForm() {
		return new ItemForm();
	}
	
	/**
	 * 商品登録画面を表示するメソッド.
	 * 
	 * @return 商品登録画面
	 */
	@RequestMapping("/addindex")
	public String addIndex(Model model) {
		
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
		
		return "jsp/addItem";
	}
	
	
	
	/**
	 * 商品をinsertするメソッド.
	 * 
	 * @param itemForm 商品情報を受け取るフォーム
	 * @return 商品登録完了画面
	 */
	/**
	 * 商品をinsertするメソッド.
	 * 
	 * @param itemForm 商品情報を受け取るフォーム
	 * @return 商品登録完了画面
	 */
	@RequestMapping("/addComplete")
	public String addComplete(ItemForm itemForm) {
		System.out.println(itemForm);
		
		String parentName = itemForm.getParentCategory();
		String childName = itemForm.getChildCategory();
		String grandchildName = itemForm.getGrandchildCategory();
		
		Integer categoryId = categoryService.findId(parentName, childName, grandchildName);
		
		Item item = new Item();
		BeanUtils.copyProperties(itemForm, item);
		item.setCategory(categoryId);
		item.setShipping(0);
		
		Integer maxId = itemService.maxId();
		item.setId(maxId + 1);
		
		itemService.insert(item);
		
		return "check";
	}
	
}
