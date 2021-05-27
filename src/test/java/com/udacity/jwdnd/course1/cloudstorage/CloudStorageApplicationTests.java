package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	public static String baseURL;
	private static WebDriver driver;
	private static HomePage homePage;
	private static LoginPage loginPage;
	private static ResultPage resultPage;
	private static SignupPage signupPage;

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
	public void beforeEach() { baseURL = "http://localhost:" + port; }

	@Test
	@Order(1)
	@DisplayName("Verify the home page is not accessible without logging in")
	public void testUnauthorized() {
		driver.get(baseURL + "/home");
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
	}

	@Test
	@Order(2)
	@DisplayName("Sign up a new user, log that user in, verify that they can access the home page, then log out and /" +
			"verify that the home page is no longer accessible")
	public void testSignupLoginLogoutSuccess() {
		String username = "alanhillgemann";
		String password = "password1";
		driver.get(baseURL + "/signup");
		signupPage.signup("Alan", "Hillgemann", username, password);
		signupPage.login();
		loginPage.login(username, password);
		homePage.logout();
		driver.get(baseURL + "/home");
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
	}

	@Test
	@Order(3)
	@DisplayName("Log in an existing user, create a note and verify that the note details are visible in the note list")
	public void testCreateNoteSuccess() {
		String username = "alanhillgemann";
		String password = "password1";
		String noteTitle = "noteTitle";
		String noteDescription = "noteDescription";
		loginPage.login(username, password);
		homePage.notesTab();
		homePage.addNote(noteTitle, noteDescription);
		assertTrue(resultPage.isSuccessAlert());
		resultPage.home();
		homePage.notesTab();
		assertNotNull(homePage.getNote(noteTitle, noteDescription));
		homePage.logout();
	}

	@Test
	@Order(4)
	@DisplayName("Log in an existing user with existing notes, click the edit note button on an existing note, /" +
			"change the note data, save the changes, and verify that the changes appear in the note list")
	public void testEditNoteSuccess() {
		String username = "alanhillgemann";
		String password = "password1";
		String existingNoteTitle = "noteTitle";
		String existingNoteDescription = "noteDescription";
		String newNoteTitle = "updatedNoteTitle";
		String newNoteDescription = "updatedNoteDescription";
		loginPage.login(username, password);
		homePage.notesTab();
		homePage.editNote(existingNoteTitle, existingNoteDescription, newNoteTitle, newNoteDescription);
		assertTrue(resultPage.isSuccessAlert());
		resultPage.home();
		homePage.notesTab();
		assertNull(homePage.getNote(existingNoteTitle, existingNoteDescription));
		assertNotNull(homePage.getNote(newNoteTitle, newNoteDescription));
		homePage.logout();
	}

	@Test
	@Order(5)
	@DisplayName("On successful note deletion, the user should be shown a success message and the deleted note /" +
			"should disappear from the list")
	public void testDeleteNoteSuccess() {
		String username = "alanhillgemann";
		String password = "password1";
		String noteTitle = "updatedNoteTitle";
		String noteDescription = "updatedNoteDescription";
		loginPage.login(username, password);
		homePage.notesTab();
		homePage.deleteNote(noteTitle, noteDescription);
		assertTrue(resultPage.isSuccessAlert());
		resultPage.home();
		homePage.notesTab();
		assertNull(homePage.getNote(noteTitle, noteDescription));
		homePage.logout();
	}

	@Test
	@Order(6)
	@DisplayName("Users should be notified of errors if they occur")
	public void testDeleteNoteError() {
		String username = "alanhillgemann";
		String password = "password1";
		loginPage.login(username, password);
		driver.get(baseURL + "/notes/delete/999");
		assertTrue(resultPage.isErrorAlert());
		resultPage.home();
		homePage.logout();
	}

	@Test
	@Order(7)
	@DisplayName("On successful file upload, the user should be shown a success message and the uploaded file /" +
			"should appear in the list")
	public void testUploadFileSuccess() {
		String username = "alanhillgemann";
		String password = "password1";
		String fileName = "test.txt";
		loginPage.login(username, password);
		homePage.uploadFile(fileName);
		assertTrue(resultPage.isSuccessAlert());
		resultPage.home();
		assertNotNull(homePage.getFile(fileName));
		homePage.logout();
	}

	@Test
	@Order(8)
	@DisplayName("On successful file download, the file should download to the user's system")
	public void testDownloadFileSuccess() {
		String username = "alanhillgemann";
		String password = "password1";
		String filename = "test.txt";
		loginPage.login(username, password);
		homePage.downloadFile(filename);
		homePage.logout();
	}

	@Test
	@Order(9)
	@DisplayName("On successful file deletion, the user should be shown a success message and the deleted file /" +
			"should disappear from the list")
	public void testDeleteFileSuccess() {
		String username = "alanhillgemann";
		String password = "password1";
		String filename = "test.txt";
		loginPage.login(username, password);
		homePage.deleteFile(filename);
		assertTrue(resultPage.isSuccessAlert());
		resultPage.home();
		assertNull(homePage.getFile(filename));
		homePage.logout();
	}

	@Test
	@Order(10)
	@DisplayName("Users should be notified of errors if they occur")
	public void testDeleteFileError() {
		String username = "alanhillgemann";
		String password = "password1";
		loginPage.login(username, password);
		driver.get(baseURL + "/files/delete/999");
		assertTrue(resultPage.isErrorAlert());
		resultPage.home();
		homePage.logout();
	}

	@Test
	@Order(11)
	@DisplayName("On successful credential creation, the user should be shown a success message and the created /" +
			"credential should appear in the list")
	public void testCreateCredentialSuccess() {
		String username = "alanhillgemann";
		String password = "password1";
		String credentialUrl = "credentialUrl";
		String credentialUsername = "credentialUsername";
		String credentialPassword = "credentialPassword";
		loginPage.login(username, password);
		homePage.credentialsTab();
		homePage.addCredential(credentialUrl, credentialUsername, credentialPassword);
		assertTrue(resultPage.isSuccessAlert());
		resultPage.home();
		homePage.credentialsTab();
		assertNotNull(homePage.getCredential(credentialUrl, credentialUsername, null));
		assertNull(homePage.getCredential(credentialUrl, credentialUsername, credentialPassword));
		homePage.logout();
	}

	@Test
	@Order(12)
	@DisplayName("When a user selects update, they should be shown a view with the unencrypted credentials. When /" +
			"they select save, the list should be updated with the edited credential details")
	public void testEditCredentialSuccess() {
		String username = "alanhillgemann";
		String password = "password1";
		String existingCredentialUrl = "credentialUrl";
		String existingCredentialUsername = "credentialUsername";
		String existingCredentialPassword = "credentialPassword";
		String newCredentialUrl = "updatedCredentialUrl";
		String newCredentialUsername = "updatedCredentialUsername";
		String newCredentialPassword = "updatedCredentialPassword";
		loginPage.login(username, password);
		homePage.credentialsTab();
		homePage.editCredential(existingCredentialUrl, existingCredentialUsername);
		assertTrue(homePage.isPasswordDecrypted(existingCredentialPassword));
		homePage.saveCredential(newCredentialUrl, newCredentialUsername, newCredentialPassword);
		assertTrue(resultPage.isSuccessAlert());
		resultPage.home();
		homePage.credentialsTab();
		assertNull(homePage.getCredential(existingCredentialUrl, existingCredentialUsername, null));
		assertNotNull(homePage.getCredential(newCredentialUrl, newCredentialUsername, null));
		assertNull(homePage.getCredential(newCredentialUrl, newCredentialUsername, newCredentialPassword));
		homePage.logout();
	}

	@Test
	@Order(13)
	@DisplayName("On successful credential deletion, the user should be shown a success message and the deleted /" +
			"credential should disappear from the list")
	public void testDeleteCredentialSuccess() {
		String username = "alanhillgemann";
		String password = "password1";
		String credentialUrl = "updatedCredentialUrl";
		String credentialUsername = "updatedCredentialUsername";
		loginPage.login(username, password);
		homePage.credentialsTab();
		homePage.deleteCredential(credentialUrl, credentialUsername);
		assertTrue(resultPage.isSuccessAlert());
		resultPage.home();
		homePage.credentialsTab();
		assertNull(homePage.getCredential(credentialUrl, credentialUsername, null));
		homePage.logout();
	}

	@Test
	@Order(14)
	@DisplayName("Users should be notified of errors if they occur")
	public void testDeleteCredentialError() {
		String username = "alanhillgemann";
		String password = "password1";
		loginPage.login(username, password);
		driver.get(baseURL + "/credentials/delete/999");
		assertTrue(resultPage.isErrorAlert());
		resultPage.home();
		homePage.logout();
	}
}
