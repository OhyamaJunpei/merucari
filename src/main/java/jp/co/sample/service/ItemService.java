package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sample.domain.Item;
import jp.co.sample.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public void update(Integer id, Integer originalId) {
		itemRepository.update(id, originalId);
	}
	
	public void insert(Item item) {
		itemRepository.insert(item);
	}
	
	public Integer maxId() {
		return itemRepository.maxId();
	}
	
	public List<Item> findAllPage(Integer page) {
		return itemRepository.findAllPage(page);
	}
	
	public List<Item> findByBrand(String brand, Integer page) {
		return itemRepository.findByBrand(brand, page);
	}
	
	public Item load(Integer id) {
		return itemRepository.load(id);
	}
	
}
