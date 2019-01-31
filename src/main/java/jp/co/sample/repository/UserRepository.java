package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.User;

@Repository
public class UserRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setMail(rs.getString("mail"));
		user.setPassword(rs.getString("password"));
		user.setAuthority(rs.getInt("authority"));
		return user;
	};
	
	/**
	 * メールアドレスの１件検索を行うメソッド.
	 * 
	 * メールアドレス重複チェック、もしくはハッシュ化したパスワードチェックに使う。
	 * 
	 * @param mail
	 * @return
	 */
	public User findByMail(String mail) {
		String sql = "SELECT id, name, mail, password, authority FROM users where mail = :mail";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mail", mail);

		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		
		return userList.get(0);
	}
	
	/**
	 * ユーザ情報をDBに新規登録するメソッド.
	 * 
	 * @param ユーザ情報
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);

		String insertSql = "INSERT INTO users (name, mail, password) "
				+ "VALUES(:name, :mail, :password)";
		template.update(insertSql, param);

	}
	
}
