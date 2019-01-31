package jp.co.sample.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.sample.domain.LoginUser;
import jp.co.sample.domain.User;
import jp.co.sample.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * DBから情報を得るためのリポジトリ.
	 */
	@Autowired
	private UserRepository userRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String) loadUserByUsername(java.lang.String)
	 * DBから検索をし、ログイン情報を構成して返す。
	 */
	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		System.out.println("UserDetailsServiceImplのloadUserByUsernameが呼ばれた");
		User user = userRepository.findByMail(mail);
		if (user == null) {
			System.out.println("そのメールありません");
			throw new UsernameNotFoundException("そのEmailは登録されていません。");
		}
		
		Collection<GrantedAuthority> authorityList =  new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		
		System.out.println("UserDetailsServiceImplのloadUserByUsernameが終了");
		return new LoginUser(user,authorityList);
	}

}
