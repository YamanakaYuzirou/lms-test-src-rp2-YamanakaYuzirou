package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		//ようこそ○○さんリンクを押下
		webDriver.findElement(By.partialLinkText("ようこそ")).click();
		//ユーザ詳細画面の文字を取得
		WebElement userDetail = webDriver.findElement(By.tagName("h2"));

		//Titleを取得し、セクション詳細画面にアクセスできたか検証する
		assertEquals("ユーザー詳細", webDriver.getTitle());
		//セクション詳細画面の文字が期待値通りか検証する
		assertEquals("ユーザー詳細", userDetail.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//修正するボタンを押下
		webDriver.findElements(By.cssSelector("input[type='submit']")).get(21).submit();
		//レポート登録画面の文字を取得
		WebElement report = webDriver.findElement(By.tagName("legend"));

		//Titleを取得し、レポート登録画面にアクセスできたか検証する
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		//レポート登録画面の文字が期待値通りか検証する
		assertEquals("学習理解度", report.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		//学習項目欄を未入力（空）にする
		webDriver.findElement(By.id("intFieldName_0")).clear();

		//提出するボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).submit();

		//エラーが出ている要素を取得する
		WebElement errorInput = webDriver.findElement(By.cssSelector("input[class='form-control errorInput']"));

		//エラーが表示されているか検証する
		assertTrue(errorInput.isDisplayed());

		//エビデンス取得
		getEvidence(new Object() {
		});

		//次のテスト用に学習項目欄に再入力する
		webDriver.findElement(By.id("intFieldName_0")).sendKeys("ITリテラシー①");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {

		//理解度欄の要素を取得する
		WebElement formControl = webDriver.findElement(By.cssSelector("select[class='form-control']"));
		//Selectクラスを呼び出す
		Select select = new Select(formControl);

		//理解度欄を未入力（空）にする
		select.selectByIndex(0);

		//提出するボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).submit();
		//エラーが出ている要素を取得する
		WebElement errorInput = webDriver.findElement(By.cssSelector("select[class='form-control errorInput']"));

		//エラーが表示されているか検証する
		assertTrue(errorInput.isDisplayed());

		//エビデンス取得
		getEvidence(new Object() {
		});

		//次のテスト用に理解度欄に再入力する
		select = new Select(errorInput);
		select.selectByIndex(3);
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		//目標の達成度に数値以外を入力する
		webDriver.findElement(By.id("content_0")).clear();
		webDriver.findElement(By.id("content_0")).sendKeys("数値チェックテスト");

		//提出するボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).submit();
		//エラーが出ている要素を取得する
		WebElement errorInput = webDriver.findElement(By.cssSelector("textarea[class='form-control errorInput']"));

		//エラーが表示されているか検証する
		assertTrue(errorInput.isDisplayed());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		//目標の達成度に範囲外の数値を入力する
		webDriver.findElement(By.id("content_0")).clear();
		webDriver.findElement(By.id("content_0")).sendKeys("0");

		//提出するボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).submit();
		//エラーが出ている要素を取得する
		WebElement errorInput = webDriver.findElement(By.cssSelector("textarea[class='form-control errorInput']"));

		//エラーが表示されているか検証する
		assertTrue(errorInput.isDisplayed());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		//目標の達成度を未入力（空）にする
		webDriver.findElement(By.id("content_0")).clear();
		//所感を未入力（空）にする
		webDriver.findElement(By.id("content_1")).clear();

		//提出するボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).submit();

		//エラーが出ている要素を取得する
		List<WebElement> errorInputs = webDriver
				.findElements(By.cssSelector("textarea[class='form-control errorInput']"));

		for (WebElement errorInput : errorInputs) {
			//エラーが表示されているか検証する
			assertTrue(errorInput.isDisplayed());
		}

		//エビデンス取得のためにスクロール
		scrollBy("400");

		//エビデンス取得
		getEvidence(new Object() {
		});

		//次のテスト用に目標の達成度・所感に再入力する
		webDriver.findElement(By.id("content_0")).sendKeys("4");
		webDriver.findElement(By.id("content_1")).sendKeys("週報のサンプルです。_変更");
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		//入力用2000文字
		String randomString = "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";

		//所感・一週間の振り返りに2000文字を入力する
		webDriver.findElement(By.id("content_1")).clear();
		webDriver.findElement(By.id("content_1")).sendKeys(randomString);
		webDriver.findElement(By.id("content_2")).clear();
		webDriver.findElement(By.id("content_2")).sendKeys(randomString);

		//提出するボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).submit();

		//エラーが出ている要素を取得する
		List<WebElement> errorInputs = webDriver
				.findElements(By.cssSelector("textarea[class='form-control errorInput']"));

		for (WebElement errorInput : errorInputs) {
			//エラーが表示されているか検証する
			assertTrue(errorInput.isDisplayed());
		}

		//エビデンス取得のためにスクロール
		scrollBy("400");

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
