package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sample.domain.Original;
import jp.co.sample.repository.OriginalRepository;

@Service
public class OriginalService {

	@Autowired
	private OriginalRepository repository;

	public List<Original> categoryName(){
		return repository.categoryName();
	}
	
	public List<Integer> findByCategoryName(String categoryName){
		return repository.findByCategoryName(categoryName);
	}
	
}
