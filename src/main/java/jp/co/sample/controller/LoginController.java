package jp.co.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.sample.domain.User;

@Controller
@RequestMapping("/")
@SessionAttributes(types = { User.class })
public class LoginController {

	// FIXME:不要なコメントはpush時は削除しましょう
//	/**
//	 * セッションスコープを注入.
//	 */
//	@Autowired
//	private HttpSession session;
//
//	/**
//	 * ユーザDBを操作するリポジトリを注入.
//	 */
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	/**
	 * 空のログインフォームを作成.
	 * 
	 * ログイン認証フィルターが機能するため不要に。？
	 * 
	 * @return
	 */
//	@ModelAttribute
//	public LoginForm setUpLoginForm() {
//		return new LoginForm();
//	}

	/**
	 * ログイン画面を表示.
	 * 
	 * @return ログイン画面を表示
	 */
//	@RequestMapping
//	public String toLogin() {
//		return "login";
//	}

	/**
	 * ログイン画面を表示します.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping
	public String toLogin(/**
							 * LoginForm form BindingResult result,
							 */
	Model model, @RequestParam(required = false) String error) {
		System.err.println("login error:" + error);
		if (error != null) {
			System.err.println("member: login failed");
			// result.addError(new ObjectError("loginError", "メールアドレスまたはパスワードが違います。"));
			model.addAttribute("loginError", "メールアドレスまたはパスワードが不正です。");
		}
		return "jsp/login";
	}

//	/**
//	 * ログイン作業を行うメソッド.
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/login")
//
//	public String login(@Validated LoginForm loginForm, BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			return toLogin();
//		}
//
//		String email = loginForm.getEmail();
//		User user = userRepository.findByEmail(email);
//		String rawPassword = loginForm.getPassword();
//		String encodedPassword = user.getPassword();
//		System.out.println(encodedPassword);
//
//		// DB上にユーザーがなかったり、暗号化したパスワードとマッチしない場合エラー。
//		if (user == null || !(passwordEncoder.matches(rawPassword, encodedPassword))) {
//			model.addAttribute("error","メールアドレスまたはパスワードが違います。");
//			// result.rejectValue("email", null, "メールアドレスまたはパスワードが違います。");
//			return toLogin();
//		}
//
//		session.setAttribute("user", user);
//
//		// 直接ログインページにアクセスされた場合（hidden送られていない）を想定
//		if (loginForm.getForwardFromWhere() == null || loginForm.getForwardFromWhere().equals("common")) {
//			return "redirect:/showItem/index";
//		}
//		if (loginForm.getForwardFromWhere().equals("cart")) {
//			return "order"; // TODO:リダイレクトする
//		}
//
//		return "itemList";
//	}
}
