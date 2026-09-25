package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//ログイン画面を開く
		goTo("http://localhost:8080/lms");

		//Titleを取得し、ログイン画面にアクセスできたか確認する
		assertEquals("ログイン | LMS", webDriver.getTitle());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//ユーザーIDを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		//パスワードを入力
		webDriver.findElement(By.id("password")).sendKeys("StudentAA0123");
		//ログインボタンを押下
		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();
		//コース詳細画面上部の文字を取得
		WebElement coursHeader = webDriver.findElement(By.cssSelector("li[class='active']"));

		//Titleを取得し、コース詳細画面にアクセスできたか検証する
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		//コース詳細画面上部の文字が期待値通りか検証する
		assertEquals("コース詳細", coursHeader.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//ヘッダーメニューの機能を押下する
		webDriver.findElement(By.linkText("機能")).click();
		//ドロップダウンリストの「ヘルプ」リンクを押下する
		webDriver.findElement(By.linkText("ヘルプ")).click();
		//ヘルプ画面の文字を取得
		WebElement help = webDriver.findElement(By.tagName("h2"));

		//Titleを取得し、ヘルプ画面にアクセスできたか検証する
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		//ヘルプ画面の文字が期待値通りか検証する
		assertEquals("ヘルプ", help.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//元のウィンドウハンドルを取得
		String oldWindow = webDriver.getWindowHandle();

		//よくある質問リンクを押下する
		webDriver.findElement(By.linkText("よくある質問")).click();

		//開かれているウィンドウハンドルを全て取得
		Set<String> newWindows = webDriver.getWindowHandles();

		//元のウィンドウ以外のハンドルに切り替える
		for (String windowHandle : newWindows) {
			if (!windowHandle.equals(oldWindow)) {
				webDriver.switchTo().window(windowHandle);
				break;
			}
		}

		//よくある質問画面の文字を取得
		WebElement faq = webDriver.findElement(By.tagName("h2"));

		//Titleを取得し、よくある質問画面にアクセスできたか検証する
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		//よくある質問画面の文字が期待値通りか検証する
		assertEquals("よくある質問", faq.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		//キーワードを入力する
		webDriver.findElement(By.cssSelector("input[type='text']")).sendKeys("キャンセル料");
		//検索ボタンを押下
		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();
		//検索結果を取得する
		List<WebElement> question = webDriver.findElements(By.id("question-h[${status.index}]"));

		//検索結果が期待値通りか検証する
		assertEquals("Q.キャンセル料・途中退校について", question.get(0).getText());
		//検索結果が表示されているか検証する
		assertTrue(question.get(0).isDisplayed());
		//表示された検索結果が１件のみか検証する
		assertEquals(1, question.size());

		//エビデンス取得用にスクロール
		scrollBy("100");
		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		//クリアボタンを押下する
		webDriver.findElement(By.cssSelector("input[type='button']")).click();
		//キーワードを取得する
		WebElement keyword = webDriver.findElement(By.cssSelector("input[type='text']"));

		//入力したキーワードが消去されていることを検証する
		assertThat(keyword.getText()).isNullOrEmpty();

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
