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

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//コース詳細画面のtr要素を取得
		List<WebElement> rows = webDriver.findElements(By.tagName("tr"));

		//tr要素の分繰り返し、未提出の研修日を確認する
		for (WebElement row : rows) {

			//tr要素内のspan要素を取得
			WebElement isSubmitted = row.findElement(By.tagName("span"));

			//未提出の研修日の場合
			if (isSubmitted.getText().equals("未提出")) {
				//詳細ボタンを押下
				row.findElement(By.cssSelector("input[value='詳細']")).submit();
				//forを抜ける
				break;
			}
		}

		//セクション詳細画面の文字を取得
		WebElement section = webDriver.findElement(By.className("active"));

		//Titleを取得し、セクション詳細画面にアクセスできたか検証する
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		//セクション詳細画面の文字が期待値通りか検証する
		assertEquals("セクション詳細", section.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//日報を提出するボタンを押下
		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

		//レポート登録画面の文字を取得
		WebElement report = webDriver.findElement(By.tagName("legend"));

		//Titleを取得し、レポート登録画面にアクセスできたか検証する
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		//レポート登録画面の文字が期待値通りか検証する
		assertEquals("報告レポート", report.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		//本日の報告内容をお書きください。欄に入力
		webDriver.findElement(By.tagName("textarea")).sendKeys("レポート登録テスト");
		//提出するボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).submit();

		//ボタン要素を取得
		WebElement button = webDriver.findElement(By.cssSelector("input[type='submit']"));

		//ボタンの名前が変更されていることを検証する
		assertEquals("提出済み日報【デモ】を確認する", button.getAttribute("value"));

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
