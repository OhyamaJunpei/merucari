package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Original;


@Repository
public class OriginalRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
//	private static final RowMapper<Original> ORIGINAL_ROW_MAPPER = (rs, i) -> {
//		Original original = new Original();
//		original.setId(rs.getInt("id"));
//		original.setName(rs.getString("name"));
//		original.setConditionId(rs.getInt("condition_id"));
//		original.setCategoryName(rs.getString("category_name"));
//		original.setBrand(rs.getString("brand"));
//		original.setPrice(rs.getDouble("price"));
//		original.setShipping(rs.getInt("shipping"));
//		original.setDescription(rs.getString("description"));
//		return original;
//	};
	
	private static final RowMapper<Original> ORIGINAL_ROW_MAPPER = (rs, i) -> {
		Original original = new Original();
		original.setId(rs.getInt("id"));
		original.setCategoryName(rs.getString("category_name"));
		return original;
	};
	
	/**
	 * 重複を除いたcategory_name一覧を取得するメソッド.
	 * 
	 * @return カテゴリー名リスト
	 *
	 */
//	public List<String> categoryName(){
//		String sql = "SELECT id, category_name FROM(SELECT DISTINCT MIN(id) as id, category_name FROM original GROUP BY category_name) as T ORDER BY id;";
//		
//		SqlParameterSource param = new MapSqlParameterSource();
//		
//		List<String> categoryNames = template.queryForList(sql, param, String.class);
//		
//		return categoryNames;
//	}
	public List<Original> categoryName(){
		String sql = "SELECT id, category_name FROM(SELECT DISTINCT MIN(id) as id, category_name FROM original GROUP BY category_name) as T ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Original> categoryNames = template.query(sql, param, ORIGINAL_ROW_MAPPER);
		return categoryNames;
	}
	
	/**
	 * 引数で受け取ったcategoryNameと一致するcategory_nameがある行のidを返すメソッド.
	 * 
	 * @param categoryName カテゴリー名
	 * @return カテゴリー名が一致したidリスト(同名のカテゴリー名が存在するため返り値のidはリスト)
	 */
	public List<Integer> findByCategoryName(String categoryName){
		String sql = "SELECT id FROM original WHERE category_name = :categoryName;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryName", categoryName);
		List<Integer> idList = template.queryForList(sql, param, Integer.class);
		return idList;
	}
	
}
