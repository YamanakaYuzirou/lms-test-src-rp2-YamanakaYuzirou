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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		//提出済研修日の要素を取得
		WebElement detail = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2)"));
		//詳細ボタンを押下
		detail.findElement(By.cssSelector("input[value='詳細']")).submit();
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
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//提出済み週報を確認するボタンを押下
		webDriver.findElements(By.cssSelector("input[type='submit']")).get(2).submit();

		//レポート登録画面の文字を取得
		WebElement report = webDriver.findElement(By.tagName("legend"));

		//Titleを取得し、セクション詳細画面にアクセスできたか検証する
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		//セクション詳細画面の文字が期待値通りか検証する
		assertEquals("学習理解度", report.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		//レポート入力内容を変更する
		webDriver.findElement(By.id("intFieldName_0")).sendKeys("_変更");
		webDriver.findElement(By.id("intFieldValue_0")).sendKeys("3");
		webDriver.findElement(By.id("content_0")).clear();
		webDriver.findElement(By.id("content_0")).sendKeys("4");
		webDriver.findElement(By.id("content_1")).sendKeys("_変更");
		webDriver.findElement(By.id("content_2")).sendKeys("_変更");

		//提出するボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).submit();

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
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
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
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		webDriver.findElements(By.cssSelector("input[type='submit']")).get(20).submit();
		//レポート登録画面の文字を取得
		WebElement report = webDriver.findElement(By.tagName("h3"));
		//学習度
		List<WebElement> studies = webDriver.findElements(By.tagName("td"));

		String weekly = "日報・週報などの入力項目は管理者権限で作成・変更することが可能です。"
				+ "「週報」の入力項目は管理画面のレポート作成機能を用いて設定し、"
				+ "登録されたレポートのフォーマットはデータベースの「m_daily_report」テーブルと"
				+ "「m_daily_report_detail」テーブルに登録されています。_変更";

		//Titleを取得し、セクション詳細画面にアクセスできたか検証する
		assertEquals("レポート詳細 | LMS", webDriver.getTitle());
		//セクション詳細画面の文字が期待値通りか検証する
		assertEquals("学習理解度", report.getText());

		//レポート登録画面で修正した内容になっているか検証する
		//学習項目
		assertEquals("ITリテラシー①_変更", studies.get(1).getText());
		//理解度
		assertEquals("3", studies.get(2).getText());
		//目標の達成度
		assertEquals("4", studies.get(3).getText());
		//所感
		assertEquals("週報のサンプルです。_変更", studies.get(4).getText());
		//一週間の振り返り
		assertEquals(weekly, studies.get(5).getText());

		getEvidence(new Object() {
		});
	}

}
