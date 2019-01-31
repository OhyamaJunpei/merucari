package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.User;
import jp.co.sample.form.RegistUserForm;
import jp.co.sample.repository.UserRepository;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	/** ユーザリポジトリを注入. */
	@Autowired
	private UserRepository userRepository;

	/** パスワードエンコーダ */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@ModelAttribute
	public RegistUserForm setUpRegisterUserForm() {
		return new RegistUserForm();
	}
	
	@RequestMapping("/index")
	public String index() {
		return "jsp/register";
	}
	
	@RequestMapping("/register")
	public String register(RegistUserForm form) {
		
		User insertUser = new User();
		// 入力された生パスワード
		String rowPassword = form.getPassword();
		// パスワードを暗号化
		BeanUtils.copyProperties(form, insertUser);
		String encodePassword = passwordEncoder.encode(rowPassword);
		insertUser.setPassword(encodePassword);
		userRepository.insert(insertUser);
		return "redirect:/";
		
	}	
}
