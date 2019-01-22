package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Item;
import jp.co.sample.service.CategoryService;
import jp.co.sample.service.ItemService;

@Controller
@RequestMapping("/itemDetail")
public class ItemDetailController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/detail")
	public String detail(Integer id, Model model) {
		
		Item item = itemService.load(id);
		model.addAttribute("item", item);
		
		Integer itemCategory = item.getCategory();
		String nameAll = categoryService.findByItemCategory(itemCategory);
		model.addAttribute("nameAll", nameAll);
		
		return "jsp/itemDetail";
	}
}
