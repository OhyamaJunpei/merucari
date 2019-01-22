package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Item;

@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setCondition(rs.getInt("condition"));
		item.setCategory(rs.getInt("category"));
		item.setBrand(rs.getString("brand"));
		item.setPrice(rs.getDouble("price"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
		return item;
	};
	
	/**
	 * itemsテーブルのcategory(categoryテーブルのname_allが存在するところのid)をupdateするメソッド.
	 * 
	 * @param id categoryテーブルのid
	 * @param originalId originalテーブルのid
	 */
	public void update(Integer id, Integer originalId) {
		String sql = "UPDATE items SET category = :id WHERE id = :originalId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id).addValue("originalId", originalId);
		template.update(sql, param);
	}
	
	/**
	 * 商品を登録するメソッド.
	 * 
	 * @param item 商品情報
	 */
	public void insert(Item item) {
		String sql = "INSERT INTO items(id, name, condition, category, brand, price, shipping, description) "
				+ "VALUES(:id, :name, :condition, :category, :brand, :price, :shipping, :description);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql, param);
	}
	
	/**
	 * itemsテーブルに入っているidの最大値を取得するメソッド.
	 * 
	 * @return maxid
	 */
	public Integer maxId() {
		String sql = "SELECT MAX(id) FROM items;";
		SqlParameterSource param = new MapSqlParameterSource();
		Integer maxId = template.queryForObject(sql, param, Integer.class);
		return maxId;
	}
	
	/**
	 * 商品一覧を表示するメソッド.
	 * 
	 * @param page ページ
	 * @return 商品リスト
	 */
	public List<Item> findAllPage(Integer page) {
		String sql = "SELECT id, name, condition, category, brand, price, shipping, description FROM items ORDER BY id LIMIT 30 OFFSET :page;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("page", page);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * ブランド名で商品を検索するメソッド.
	 * 
	 * @param brand ブランド名
	 * @param page ページ
	 * @return 該当する商品リスト
	 */
	public List<Item> findByBrand(String brand, Integer page) {
		String sql = "SELECT id, name, condition, category, brand, price, shipping, description FROM items WHERE brand = :brand ORDER BY id LIMIT 30 OFFSET :page;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("page", page).addValue("brand", brand);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * 商品の詳細情報を取得するメソッド.
	 * 
	 * @param id 
	 * @return item
	 */
	public Item load(Integer id) {
		String sql = "SELECT id, name, condition, category, brand, price, shipping, description FROM items WHERE id = :id ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}
	
}
