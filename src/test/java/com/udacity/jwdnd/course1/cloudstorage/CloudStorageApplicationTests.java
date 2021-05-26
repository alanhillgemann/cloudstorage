package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	public static String baseURL;
	private static WebDriver driver;
	private static HomePage homePage;
	private static LoginPage loginPage;
	private static ResultPage resultPage;
	private static SignupPage signupPage;

	public void click(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void sendKeys(WebElement element, String value) {
		click(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + value + "';", element);
	}

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		resultPage = new ResultPage(driver);
		signupPage = new SignupPage(driver);
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;
	}

	@Test
	@DisplayName("Verify the home page is not accessible without logging in")
	public void test1() {
		driver.get(baseURL + "/home");
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
	}

	@Test
	@DisplayName("Sign up a new user, log that user in, verify that they can access the home page, /" +
			"then log out and verify that the home page is no longer accessible")
	public void test2() {
		String username = "alanhillgemann";
		String password = "assignmentistoolong";
		driver.get(baseURL + "/signup");
		signupPage.signup("Alan", "Hillgemann", username, password);
		signupPage.login();
		loginPage.login(username, password);
		homePage.logout();
		driver.get(baseURL + "/home");
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
	}

	@Test
	@DisplayName("Log in an existing user, create a note and verify that the note details are visible in the note list")
	public void test3() {
		String username = "alanhillgemann";
		String password = "assignmentistoolong";
		String noteTitle = "noteTitle";
		String noteDescription = "noteDescription";
		driver.get(baseURL + "/login");
		loginPage.login(username, password);
		homePage.notesTab();
		homePage.addNote(noteTitle, noteDescription);
		resultPage.home();
		homePage.notesTab();
		assertNotNull(homePage.getNote(noteTitle, noteDescription));
	}

	@Test
	@DisplayName("Log in an existing user with existing notes, click the edit note button on an existing note, /" +
			"change the note data, save the changes, and verify that the changes appear in the note list")
	public void test4() {
		String username = "alanhillgemann";
		String password = "assignmentistoolong";
		String existingNoteTitle = "noteTitle";
		String existingNoteDescription = "noteDescription";
		String newNoteTitle = "updatedNoteTitle";
		String newNoteDescription = "updatedNoteDescription 2";
		driver.get(baseURL + "/login");
		loginPage.login(username, password);
		homePage.notesTab();
		homePage.editNote(existingNoteTitle, existingNoteDescription, newNoteTitle, newNoteDescription);
		resultPage.home();
		homePage.notesTab();
		assertNull(homePage.getNote(existingNoteTitle, existingNoteDescription));
		assertNotNull(homePage.getNote(newNoteTitle, newNoteDescription));
	}

	@Test
	@DisplayName("On successful note deletion, the user should be shown a success message and the deleted note /" +
			"should disappear from the list")
	public void test5() {
		String username = "alanhillgemann";
		String password = "assignmentistoolong";
		String noteTitle = "updatedNoteTitle";
		String noteDescription = "updatedNoteDescription 2";
		driver.get(baseURL + "/login");
		loginPage.login(username, password);
		homePage.notesTab();
		homePage.deleteNote(noteTitle, noteDescription);
		resultPage.home();
		homePage.notesTab();
		assertNull(homePage.getNote(noteTitle, noteDescription));
	}
}
