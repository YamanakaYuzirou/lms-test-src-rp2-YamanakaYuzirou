package jp.co.sss.lms.ct.f04_attendance;

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
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		//closeDriver();
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
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		//リンクを押下して直接編集画面に遷移
		webDriver.findElement(By.linkText("勤怠情報を直接編集する")).click();
		//直接編集画面の文字を取得
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
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		//出勤時間を取得する
		WebElement startHour = webDriver.findElement(By.id("startHour0"));
		//出勤分を取得する
		WebElement startMinute = webDriver.findElement(By.id("startMinute0"));
		//退勤時間を取得する
		WebElement endHour = webDriver.findElement(By.id("endHour0"));
		//退勤分を取得する
		WebElement endMinute = webDriver.findElement(By.id("endMinute0"));

		//出勤時間を設定
		Select select = new Select(startHour);
		select.selectByIndex(10);

		//出勤分を設定
		select = new Select(startMinute);
		select.selectByIndex(1);

		//退勤時間を設定
		select = new Select(endHour);
		select.selectByIndex(19);

		//退勤分を設定
		select = new Select(endMinute);
		select.selectByIndex(1);

		//更新ボタンクリックのためにスクロール
		scrollBy("300");

		//更新ボタンを押下する
		webDriver.findElement(By.cssSelector("input[value='更新']")).click();
		//更新確認メッセージを押下
		webDriver.switchTo().alert().accept();

		//勤怠管理画面の文字を取得
		WebElement attendance = webDriver.findElement(By.tagName("h2"));
		//出退勤時刻を取得
		List<WebElement> times = webDriver.findElements(By.cssSelector("td[class='w80']"));

		//Titleを取得し、勤怠管理画面にアクセスできたか検証する
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		//勤怠管理画面上部の文字が期待値通りか検証する
		assertEquals("勤怠管理", attendance.getText());

		//出勤時間が設定した時間と同じか検証する
		assertEquals("09:00", times.get(0).getText());
		//退勤時間が設定した時間と同じか検証する
		assertEquals("18:00", times.get(1).getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
