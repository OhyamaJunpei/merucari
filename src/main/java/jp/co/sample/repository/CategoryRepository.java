package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Category;

@Repository
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, i) ->{
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setParent(rs.getInt("parent"));
		category.setName(rs.getString("name"));
		category.setNameAll(rs.getString("name_all"));
		return category;
	};
	
	/**
	 * categoryテーブルにinsertするメソッド.
	 * 
	 * @param category カテゴリー情報
	 */
	public void insert(Category category) {
		String sql = "INSERT INTO category(parent, name, name_all) VALUES(:parent, :name, :nameAll);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(category);
		template.update(sql, param);
	}
	
	/**
	 * 子要素が親要素のidを取得するメソッド.
	 * 
	 * @param categoryName カテゴリー名
	 * @return 親id
	 */
	public Integer searchParentIdForChild(String categoryName) {
		String sql = "SELECT id FROM category WHERE name = :categoryName AND parent IS NULL;"; //親要素を見つけるのでparentはnull 
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryName", categoryName);
		List<Integer> parentIdList = template.queryForList(sql, param, Integer.class);
		
		if(parentIdList.size() == 0) {
			return null;
		}
		
		return parentIdList.get(0);
	}
	
	/**
	 * 孫が子要素を探すときのメソッド.
	 * 
	 * @param parentId 子要素のid
	 * @param name 子の名前
	 * @return 子要素のid
	 */
	public Integer searchParentForChild(Integer parentId, String name) {
		String sql = "SELECT id FROM category WHERE parent = :parentId AND name = :name;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentId", parentId).addValue("name", name);
		List<Integer> idList = template.queryForList(sql, param, Integer.class);
		
		if(idList.size() == 0) {
			return null;
		}
		
		return idList.get(0);
	}

	/**
	 * categoryテーブルのnameカラムを取得するメソッド.
	 * 
	 * @return nameList
	 */
	public List<String> searchName() {
		String sql = "SELECT name FROM category;";
		SqlParameterSource param = new MapSqlParameterSource();
		List<String> nameList = template.queryForList(sql, param, String.class);
		return nameList;
	}
	
	/**
	 * categoryテーブルのname_allが引数と同じもののidを取得するメソッド.
	 * 
	 * @param categoryName originalテーブルから取得するcategory_name
	 * @return id categoryテーブルのid(name_allが入っているところ)
	 */
	public Integer searchCategoryId(String categoryName) {
		String sql = "SELECT id FROM category WHERE name_all = :categoryName ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryName", categoryName);
		List<Integer> categoryIdList = template.queryForList(sql, param, Integer.class);
		if(categoryIdList.size() == 0) {
			return null;
		}		
		return categoryIdList.get(0);
	}
	
	/**
	 * categoryテーブルのname_allカラムを取得するメソッド.
	 * 
	 * @return name_allが入ったリスト
	 */
	public List<String> findNameAll() {
		String sql = "SELECT DISTINCT name_all FROM category;";
		SqlParameterSource param = new MapSqlParameterSource();
		List<String> nameList = template.queryForList(sql, param, String.class);
		return nameList;
	}
	
	/**
	 * 親要素のidを取得するメソッド.
	 * 
	 * @param name
	 * @return
	 */
	public Integer findParent(String name) {
		String sql = "SELECT id FROM category WHERE name = :name AND parent IS NULL;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		List<Integer> idList = template.queryForList(sql, param, Integer.class);
		
		if(idList.size() == 0) {
			return null;
		}
		return idList.get(0);
	}
	
	//以下アプリ作成用
	
	/**
	 * categoryテーブルの情報を全て取得するメソッド.
	 * 
	 * @return categoryリスト
	 */
	public List<Category> findAllCategory() {
		String sql = "SELECT id, parent, name, name_all FROM category ORDER BY name;";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return categoryList;
	}
	
	/**
	 * 該当するname_allのidを取得するメソッド.
	 * 
	 * @param parentName 親の名前
	 * @param childName 子の名前
	 * @param grandchildName 孫の名前
	 * @return 該当するname_allのid
	 */
	public Integer findId(String parentName, String childName, String grandchildName) {
		String sql = "SELECT id FROM category WHERE name = :grandchildName AND parent = "
				+ "(SELECT id FROM category WHERE name = :childName AND name_all IS null AND parent = "
				+ "(SELECT id FROM category WHERE parent IS null AND name_all IS null AND name = :parentName));" ;
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentName", parentName).addValue("childName", childName).addValue("grandchildName", grandchildName);
		Integer id = template.queryForObject(sql, param, Integer.class);
		return id;
	}
	
	
	/**
	 * 親のidで子要素を取得するメソッド.
	 * 
	 * @param parentId 親id
	 * @return 子カテゴリーリスト
	 */
	public List<Category> findChild(Integer parentId){
		String sql = "SELECT id FROM category WHERE parent = :parentId AND name_all IS null;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentId", parentId);
		List<Category> childCategoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return childCategoryList;
	}
	
	/**
	 * itemsのcategoryでcategoryテーブルのname_allを取得するメソッド.
	 * 
	 * @param itemCategory itemsテーブルのcategory
	 * @return name_all
	 */
	public String findByItemCategory(Integer itemCategory) {
		String sql = "SELECT name_all FROM category WHERE id = :itemCategory;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemCategory", itemCategory);
		String nameAll = template.queryForObject(sql, param, String.class);
		return nameAll;
	}
	
}
