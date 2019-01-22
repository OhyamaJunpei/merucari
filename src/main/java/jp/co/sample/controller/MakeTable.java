package jp.co.sample.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Category;
import jp.co.sample.domain.Original;
import jp.co.sample.service.CategoryService;
import jp.co.sample.service.ItemService;
import jp.co.sample.service.OriginalService;

@Controller
@RequestMapping("/")
public class MakeTable {

	@Autowired
	private OriginalService originalService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * sqlが動くか確認用のメソッド.
	 * 
	 * @param model モデル
	 * @return 確認画面
	 */
	@RequestMapping("/forConfirm")
	public String forConfirm(Model model) {

		List<Original> list = new ArrayList<>();
		list = originalService.categoryName();
		model.addAttribute("list", list);
		
		return "forConfirm";
	}
	
	/**
	 * categoryテーブルにデータを挿入するメソッド.
	 * 
	 * @return null
	 */
	@RequestMapping("/createCategory")
	public String insertCategory() {
		
		List<Original> categoryNameList = new ArrayList<>(); //originalテーブルのcategory_nameを格納するためのリストを生成
		categoryNameList = originalService.categoryName(); //重複を省いたcategory_nameをリストに代入 size=1288
		
		//category_nameを一つずつ取り出すfor文
		for(Original categoryNameObject : categoryNameList) {
			
			String categoryName = categoryNameObject.getCategoryName();
			
			//categoryNameがnullでなければ実行
			if(categoryName != null) {
				String[] names = categoryName.split("/", 3); // "/"で区切られているcategoryNameを3つ(親,子,孫)に分けて配列に格納
				
				//配列の長さだけfor文実行
				for(int i=0; i<names.length; i++) {
					
//					List<String> nameList = categoryService.searchName(); //categoryテーブルのnameを取得
//					List<String> nameAllList = categoryService.findNameAll(); //categoryテーブルのname_allを取得

					Category category = new Category(); //Categoryをインスタンス化
					Integer parentId = null; //初期値として
					String nameAll = null;   //parentId, nameAll に null を代入
					
					//配列の先頭は親
					if(i == 0) {
						parentId = null; //親なのでparent_idはnull
						nameAll = null;  //親なのでname_allはnull
						
						if(categoryService.findParent(names[0]) == null) {
							category.setParent(parentId);
							category.setName(names[0]);
							category.setNameAll(nameAll);
							categoryService.insert(category);	//categoryテーブルにinsert							
						}	
					}
					
					//配列の2つめは子
					if(i == 1) {
						//親のid
						parentId = categoryService.searchParentIdForChild(names[0]); //自分の親要素のidがほしいのでnames[i-1]
						nameAll = null; //子要素なのでname_allはnull
						
						if(categoryService.searchParentForChild(parentId, names[1]) == null) {
							category.setParent(parentId);
							category.setName(names[1]);
							category.setNameAll(nameAll);
							categoryService.insert(category);	//categoryテーブルにinsert							
						}
					}
					
					//配列の3つめの要素は孫
					if(i == 2) {
						//parentが親のid,nameが子の名前の行のidを取得
						parentId = categoryService.searchParentForChild(categoryService.searchParentIdForChild(names[0]), names[1]);
						nameAll = categoryName; //孫なのでname_allが存在
						
						if(categoryService.searchParentForChild(parentId, names[2]) == null) {							
							category.setParent(parentId);
							category.setName(names[2]);
							category.setNameAll(nameAll);
							categoryService.insert(category);	//categoryテーブルにinsert							
						}
					}
					
					
				}
			}
					
					//配列の2つ目以降の要素はparentIdがあるのでparentIdを検索
//					if(i > 0) { 
//						parentId = categoryService.searchParent(names[i-1]); //自分の親要素のidがほしいのでnames[i-1]
//					}
//					// iは最大で配列 -1 なので 配列の長さ - 1 == i は配列の最後の要素
//					//　それがcategoryテーブルのname_allに入る
//					if(names.length -1 == i) {
//						nameAll = categoryName;
//					}
//					
//					//各要素をcategoryにset
//					category.setParent(parentId);
//					category.setName(names[i]);
//					category.setNameAll(nameAll);
//					
//					//categoryテーブルのnameに/で分割したnameがなかったら実行
//					if(!(nameList.contains(names[i])) || nameAllList.contains(nameAll)){
//						categoryService.insert(category);	//categoryテーブルにinsert					
//					}
//					
//				}
//			
//			// category_nameがnullだったとき
//			}else {
//				List<String> nameList = categoryService.searchName(); //categoryテーブルのnameを取得
//				
//				Category category = new Category(); //Categoryをインスタンス化
//				Integer parentId = null; //初期値として
//				String name = null;
//				String nameAll = null;   //parentId, nameAll に null を代入
//				
//				category.setParent(parentId);
//				category.setName(name);
//				category.setNameAll(nameAll);
//				
//				//categoryテーブルのnameに/で分割したnameがなかったら実行
//				if(!(nameList.contains(null))){
//					categoryService.insert(category);	//categoryテーブルにinsert					
//				}
//				
//			}
		}		
		return null; //画面表示しないためnull
	}
	
	/**
	 * itemsテーブルにcategory(categoryテーブルのid)をinsertするメソッド.
	 * 
	 * @return null
	 */
	@RequestMapping("/createItem")
	public String createItem() {
		
		List<Original> categoryNameList = new LinkedList<>(); //originalテーブルのcategory_nameを格納するためのリストを作成
		categoryNameList = originalService.categoryName(); //重複を除いたcategory_nameのリスト([xxx/xxx/xxx, yyy/yyy/yyy, ・・・]の形)を代入
		
		List<String> nameAllList = new LinkedList<>(); //categoryテーブルのname_allを格納するためのメソッド
		nameAllList = categoryService.findNameAll(); //name_allのリスト([null or xxx/xxx/xxx, ・・・] の形)を取得
		
		//catagoryNameListを一つずつcategoryNameに入れる(categoryName = xxx/xxx/xxx)
		for(Original categoryName : categoryNameList) {
			
			//nameAllListにcategoryNameが含まれていたら実行
			if(nameAllList.contains(categoryName.getCategoryName())) {
				
				//categoryテーブルのname_allとoriginalテーブルのcategory_nameが一致したところのcategoryテーブルのidを取得	
				Integer categoryId = categoryService.searchCategoryId(categoryName.getCategoryName());			
				
				//originalテーブルのcategory_nameが引数のcategoryNameと一致したもののoriginalテーブルのidを取得
				List<Integer> originalIdList = originalService.findByCategoryName(categoryName.getCategoryName());
				
				//categoryテーブルのname_allとoriginalテーブルのcategory_nameが一致したところのcategoryテーブルのid
				//originalテーブルのcategory_nameが引数のcategoryNameと一致したもののoriginalテーブルのid
				//を使用してitemsテーブルのcategoryにidをinsert
				for(Integer originalId : originalIdList) {
					itemService.update(categoryId, originalId);						
				}
			
//			}else {
//				//originalテーブルのcategory_nameが引数のcategoryNameと一致したもののoriginalテーブルのidを取得
//				List<Integer> originalIdList = originalService.findByCategoryName(categoryName.getCategoryName());
//				
//				//category_nameがnullのときの処理
//				//とりあえずでid=999999を入れてるので修正必要
//				for(Integer originalId : originalIdList) {
//					itemService.update(9999999, originalId);						
//				}
			}
			
		}		
		
		return null; //画面表示しないためnull
	}
	
	/**
	 * originalテーブルのcategory_nameにあって
	 * categoryテーブルのname_allにないものをチェック.
	 * 
	 * @return null
	 */
	@RequestMapping("/listcheck")
	public String listCheck() {
		List<Original> categoryNameList = new LinkedList<>(); //originalテーブルのcategory_nameを格納するためのリストを作成
		categoryNameList = originalService.categoryName(); //重複を除いたcategory_nameのリスト([xxx/xxx/xxx, yyy/yyy/yyy, ・・・]の形)を代入
		System.out.println("categoryNameListSize = " + categoryNameList.size());
		
		List<String> nameAllList = new LinkedList<>(); //categoryテーブルのname_allを格納するためのメソッド
		nameAllList = categoryService.findNameAll(); //name_allのリスト([null or xxx/xxx/xxx, ・・・] の形)を取得
		System.out.println("nameAllListSize = " + nameAllList.size());
		
		//差を格納するリスト
		List<String> checkList = new LinkedList<>();
		
		for(Original original : categoryNameList) {
			if(!(nameAllList.contains(original.getCategoryName()))) {
//				System.out.println(original.getCategoryName());
				checkList.add(original.getCategoryName());
			}
		}
		
		System.out.println(checkList);
		System.out.println(checkList.size());
		
		return null;
	}
	
}
