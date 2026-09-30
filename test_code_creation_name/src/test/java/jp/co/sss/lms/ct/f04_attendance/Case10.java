package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		//勤怠リンクを押下して勤怠管理画面に遷移
		webDriver.findElement(By.linkText("勤怠")).click();
		//勤怠未入力メッセージを押下
		webDriver.switchTo().alert().accept();
		//勤怠管理画面の文字を取得
		WebElement attendance = webDriver.findElement(By.tagName("h2"));

		//Titleを取得し、勤怠管理画面にアクセスできたか検証する
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		//勤怠管理画面上部の文字が期待値通りか検証する
		assertEquals("勤怠管理", attendance.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		//出勤ボタンを押下
		webDriver.findElement(By.cssSelector("input[value='出勤']")).click();
		//確認メッセージを押下
		webDriver.switchTo().alert().accept();
		//出勤時刻を取得
		List<WebElement> start = webDriver.findElements(By.cssSelector("td[class='w80']"));

		//日付を取得
		LocalDateTime date = LocalDateTime.now();
		//時刻のみのフォーマット
		DateTimeFormatter now = DateTimeFormatter.ofPattern("HH:mm");

		//出勤時刻が登録されたか、現在時刻と比較して検証する
		assertEquals(now.format(date), start.get(2).getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		//退勤ボタンを押下
		webDriver.findElement(By.cssSelector("input[value='退勤']")).click();
		//確認メッセージを押下
		webDriver.switchTo().alert().accept();
		//退勤時刻を取得
		List<WebElement> end = webDriver.findElements(By.cssSelector("td[class='w80']"));

		//日付を取得
		LocalDateTime date = LocalDateTime.now();
		//時刻のみのフォーマット
		DateTimeFormatter now = DateTimeFormatter.ofPattern("HH:mm");

		//退勤時刻が登録されたか、現在時刻と比較して検証する
		assertEquals(now.format(date), end.get(3).getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
