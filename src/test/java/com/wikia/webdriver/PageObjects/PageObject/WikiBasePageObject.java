package com.wikia.webdriver.PageObjects.PageObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.wikia.webdriver.Common.Core.CommonFunctions;
import com.wikia.webdriver.Common.Core.Global;
import com.wikia.webdriver.Common.Logging.PageObjectLogging;
import com.wikia.webdriver.PageObjects.PageObject.CreateNewWiki.CreateNewWikiPageObjectStep1;
import com.wikia.webdriver.PageObjects.PageObject.WikiPage.SpecialMultipleUploadPageObject;
import com.wikia.webdriver.PageObjects.PageObject.WikiPage.SpecialNewFilesPageObject;
import com.wikia.webdriver.PageObjects.PageObject.WikiPage.SpecialUploadPageObject;
import com.wikia.webdriver.PageObjects.PageObject.WikiPage.WikiArticleEditMode;
import com.wikia.webdriver.PageObjects.PageObject.WikiPage.WikiArticlePageObject;
import com.wikia.webdriver.PageObjects.PageObject.WikiPage.WikiCategoryPageObject;

public class WikiBasePageObject extends BasePageObject {

	@FindBy(css = "span.drop")
	private WebElement contributeButton;

	@FindBy(css = "textarea[id='ImageUploadCaption']")
	private WebElement captionTextArea;

	@FindBy(css = "a.createpage")
	private WebElement createArticleButton;

	@FindBy(css = "a[class='wikia-button createpage']")
	private WebElement addArticleButton;

	@FindBy(id = "wpCreatePageDialogTitle")
	private WebElement articleNameField;

	@FindBy(css = "article span.drop")
	private WebElement editDropDown;

	@FindBy(css = "a[data-id='delete']")
	private WebElement deleteButton;

	@FindBy(css = "input#wpConfirmB")
	private WebElement deleteConfirmationButton;

	@FindBy(xpath = "//div[@class='msg' and contains(text(), 'The comment has been deleted.')]")
	private WebElement deleteCommentConfirmationMessage;

	@FindBy(css = "a[data-canonical='random']")
	private WebElement randomPageButton;

	@FindBy(css = "div.msg a")
	private WebElement undeleteButton;

	@FindBy(css = "input#mw-undelete-submit")
	private WebElement restoreButton;

	@FindBy(css = "input#wpNewTitleMain")
	private WebElement renameArticleField;

	@FindBy(css = "input[name='wpMove']")
	private WebElement confirmRenamePageButton;

	@FindBy(css = "input#wpReason")
	private WebElement deleteCommentReasonField;

	@FindBy(css = "tr.ImageUploadFindLinks td a")
	private WebElement addThisPhotoLink;

	@FindBy(css = "div.reset[id='ImageUpload']")
	private WebElement imageUploadModal;

	@FindBy(css = "div.details input")
	private WebElement addPhotoButton;

	@FindBy(css = "input[id='VideoEmbedUrl']")
	private WebElement videoModalInput;

	@FindBy(css = "a[id='VideoEmbedUrlSubmit']")
	private WebElement videoNextButton;

	@FindBy(css = "div.input-group.VideoEmbedNoBorder input")
	private WebElement videoAddVideoButton;

	@FindBy(css = "#VideoEmbed")
	private WebElement videoDialog;

	@FindBy(css = "input[value='Return to editing']")
	private WebElement videoReturnToEditing;

	@FindBy(css = ".sprite.search")
	private WebElement searchButton;

	@FindBy(css = "section[id='WikiaPhotoGalleryEditor']")
	private WebElement objectModal;

	@FindBy(css = "input[name='search'][placeholder='Search photos on this wiki']")
	private WebElement searchFieldImageInLightBox;

	@FindBy(css = "img.sprite.search")
	private WebElement searchButtonImageInLightBox;

	@FindBy(css = "a[id='WikiaPhotoGallerySearchResultsSelect']")
	private WebElement galleryDialogSelectButton;

	@FindBy(css = "a[id='WikiaPhotoGalleryEditorSave']")
	private WebElement galleryDialogFinishButton;
	
	@FindBy(css="input[id='VideoEmbedCaption']")
	private WebElement videoCaptionTextArea;
	
	@FindBy(css="input#ImageQuery")
	private WebElement imageQuery;
	
	@FindBy(css="[value='Find']")
	private WebElement imageFindButton;

	private By galleryDialogPhotosList = By
			.cssSelector("ul[class='WikiaPhotoGalleryResults'][type='results'] li input");
	private By galleryDialogPhotoOrientationsList = By
			.cssSelector("ul.clearfix[id='WikiaPhotoGalleryOrientation'] li");
	private String videoAddVideoButtonSelector = "div.input-group.VideoEmbedNoBorder input";
	private String videoReturnToEditingSelector = "input[value=\"Return to editing\"]";
	private By galleryDialogSlideshowOrientationsList = By
			.cssSelector("ul.clearfix[id='WikiaPhotoGallerySliderType'] li");
	private By layoutList = By.cssSelector("ul#CreatePageDialogChoices li");

	public WikiBasePageObject(WebDriver driver, String Domain) {
		super(driver);
		this.Domain = Domain;
		PageFactory.initElements(driver, this);
	}

	public String getWikiName() {
		return Domain;
	}

	public void searchForImage(String name){
//		waitForElementByElement(imageFindButton);
		imageQuery.sendKeys(name);
//		waitForElementByElement(imageQuery);
		imageFindButton.click();
		PageObjectLogging.log("searchForImage", "search for image: "+name, true);
	}
	
	/**
	 * Left Click on add Object button.
	 *  
	 * @author Michal Nowierski
	 * @param Object Object = {Image, Gallery, Slideshow, Slider, Video}
	 */
	public void clickOnAddObjectButton(String Object) {
		// TODO Auto-generated method stub
		String ObjectCss = "span.cke_button.RTE"+Object+"Button a";
		WebElement ObjectButton;
		waitForElementByCss(ObjectCss);
		waitForElementClickableByCss(ObjectCss);
		ObjectButton = driver.findElement(By.cssSelector(ObjectCss));
		clickAndWait(ObjectButton);
		PageObjectLogging.log("ClickOnAddObjectButton", "Edit Article: "+articlename+", on wiki: "+Domain+"", true, driver);
		
	}
	
	/**
	 * Type given caption for the video
	 *  
	 * @author Michal Nowierski
	 */
	public void typeVideoCaption(String caption) {
		waitForElementByElement(videoCaptionTextArea);
		videoCaptionTextArea.clear();
		videoCaptionTextArea.sendKeys(caption);
		PageObjectLogging.log("TypeAcaption", "Type any caption for the photo", true, driver);
	}
	
	/**
	 * Set photo orientation option number n
	 * 
	 * @author Michal Nowierski
	 * @param n
	 *            = {1, 2}
	 *            <p>
	 *            1 - Horizontaal.
	 *            <p>
	 *            2 - Vertical
	 * */
	public void gallerySetSliderPosition(int n) {
		List<WebElement> List = driver
				.findElements(galleryDialogSlideshowOrientationsList);
		waitForElementByElement(List.get(n - 1));
		clickAndWait(List.get(n - 1));
		PageObjectLogging.log("GallerySetSliderPosition",
				"Set photo orientation option number " + n, true, driver);

	}

	/**
	 * Set photo orientation option number n
	 * 
	 * @author Michal Nowierski
	 * @param n
	 *            = {1,2,3,4}
	 *            <p>
	 *            1 - Original.
	 *            <p>
	 *            2 - Square.
	 *            <p>
	 *            3 - Landscape.
	 *            <p>
	 *            4 - Portrait
	 * */
	public void gallerySetPhotoOrientation(int n) {
		List<WebElement> List = driver
				.findElements(galleryDialogPhotoOrientationsList);
		waitForElementByElement(List.get(n - 1));
		clickAndWait(List.get(n - 1));
		PageObjectLogging.log("GallerySetPhotoOrientation",
				"Set photo orientation option number " + n, true, driver);

	}

	/**
	 * Set Object position to the wanted one
	 * 
	 * @author Michal Nowierski
	 * @param Object
	 *            {Gallery, Slideshow}
	 * @param WantedPosition
	 *            = {Left, Center, Right} !CASE SENSITIVITY! *
	 */
	public void gallerySetPositionGallery(String WantedPosition) {

		Select select = new Select(
				driver.findElement(By
						.cssSelector("select[id='WikiaPhotoGalleryEditorGalleryPosition']")));
		select.selectByVisibleText(WantedPosition);
		// below code will make sure that proper position is selected
		String category_name = select.getAllSelectedOptions().get(0).getText();
		while (!category_name.equalsIgnoreCase(WantedPosition)) {
			select.selectByVisibleText(WantedPosition);
			category_name = select.getAllSelectedOptions().get(0).getText();

		}
		PageObjectLogging.log("GallerySetPosition", "Set gallery position to "
				+ WantedPosition, true, driver);
	}

	/**
	 * Gallery dialog: Left click 'Finish' button
	 * 
	 * @author Michal Nowierski
	 * */
	public void galleryClickOnFinishButton() {
		waitForElementByElement(galleryDialogFinishButton);
		waitForElementClickableByElement(galleryDialogFinishButton);
		clickAndWait(galleryDialogFinishButton);
		PageObjectLogging.log("GalleryClickOnFinishButton",
				"Gallery dialog: Left click 'Finish' button ", true, driver);

	}

	public void gallerySetPositionSlideshow(String WantedPosition) {

		Select select = new Select(
				driver.findElement(By
						.cssSelector("select[id='WikiaPhotoGalleryEditorSlideshowAlign']")));
		select.selectByVisibleText(WantedPosition);
		// below code will make sure that proper position is selected
		String category_name = select.getAllSelectedOptions().get(0).getText();
		while (!category_name.equalsIgnoreCase(WantedPosition)) {
			select.selectByVisibleText(WantedPosition);
			category_name = select.getAllSelectedOptions().get(0).getText();
		}
		PageObjectLogging.log("GallerySetPosition",
				"Set slideshow position to " + WantedPosition, true, driver);
	}

	/**
	 * Gallery dialog: Left click 'Select' button
	 * 
	 * @author Michal Nowierski
	 * */
	public void galleryClickOnSelectButton() {
		waitForElementByElement(galleryDialogSelectButton);
		waitForElementClickableByElement(galleryDialogSelectButton);
		clickAndWait(galleryDialogSelectButton);
		PageObjectLogging.log("GalleryClickOnSelectButton",
				"Gallery dialog: Left click 'Select' button", true, driver);

	}

	/**
	 * Wait for Object and click on 'add this photo' under the first seen
	 * 
	 * @author Michal Nowierski
	 * @param n
	 *            n = parameter determining how many inputs the method should
	 *            check
	 * */
	public void galleryCheckImageInputs(int n) {
		driver.findElement(galleryDialogPhotosList);
		List<WebElement> List = driver.findElements(galleryDialogPhotosList);
		for (int i = 0; i < n; i++) {
			clickAndWait(List.get(i));
		}
		PageObjectLogging.log("CheckGalleryImageInputs", "Check first " + n
				+ " image inputs", true, driver);
	}

	public void searchImageInLightBox(String imageName) {
		waitForElementByElement(searchFieldImageInLightBox);
		searchFieldImageInLightBox.sendKeys(imageName);
		clickAndWait(searchButtonImageInLightBox);
		waitForElementByElement(searchButtonImageInLightBox);
	}

	/**
	 * Wait for Object and click on 'add this photo' under the first seen
	 * 
	 * @author Michal Nowierski
	 * @param Object
	 *            Object = {Gallery, GallerySlideshow, GallerySlider}
	 * */
	public void waitForObjectModalAndClickAddAphoto(String Object) {
		waitForElementClickableByBy(By.cssSelector("button[id='WikiaPhoto"
				+ Object + "AddImage']"));
		clickAndWait(driver.findElement(By.cssSelector("button[id='WikiaPhoto"
				+ Object + "AddImage']")));
		PageObjectLogging.log("WaitForObjectModalAndClickAddAphoto",
				"Wait for " + Object + " modal and click on 'add a photo'",
				true, driver);
		waitForElementByElement(objectModal);
	}

	/**
	 * Wait For Succes dialog and click on 'return to editing'
	 * 
	 * @author Michal Nowierski
	 * */
	public void waitForSuccesDialogAndReturnToEditing() {
		waitForElementByElement(videoReturnToEditing);
		waitForElementClickableByElement(videoReturnToEditing);
		jQueryClick(videoReturnToEditingSelector);
		// clickAndWait(videoReturnToEditing);
		waitForElementNotVisibleByCss(videoReturnToEditingSelector);
		PageObjectLogging.log("WaitForSuccesDialogAndReturnToEditing",
				"Wait For Succes dialog and click on 'return to editing'",
				true, driver);

	}

	/**
	 * Wait for video dialog
	 * 
	 * @author Michal Nowierski
	 * */
	public void waitForVideoDialog() {
		waitForElementByElement(videoDialog);
		PageObjectLogging.log("WaitForVideoDialog", "Wait for video dialog",
				true, driver);

	}

	/**
	 * Click 'Add a video'
	 * 
	 * @author Michal Nowierski
	 * */
	public void clickAddAvideo() {

		waitForElementByElement(videoAddVideoButton);
		// waitForElementClickableByElement(videoAddVideoButton);
		jQueryClick(videoAddVideoButtonSelector);
		// clickAndWait(videoAddVideoButton);
		PageObjectLogging.log("ClickAddAvideo", "Click 'Add a video'", true,
				driver);

	}

	/**
	 * Video Click Next button
	 * 
	 * @author Michal Nowierski
	 * */
	public void clickVideoNextButton() {
		waitForElementByElement(videoNextButton);
		waitForElementClickableByElement(videoNextButton);
		clickAndWait(videoNextButton);
		PageObjectLogging.log("ClickVideoNextButton", "Left Click Next button",
				true, driver);

	}

	/**
	 * Wait for Video modal and type in the video URL
	 * 
	 * @author Michal Nowierski
	 * */
	public void waitForVideoModalAndTypeVideoURL(String videoURL) {
		waitForElementByElement(videoModalInput);
		waitForElementClickableByElement(videoModalInput);
		videoModalInput.clear();
		videoModalInput.sendKeys(videoURL);
		PageObjectLogging.log("WaitForVideoModalAndTypeVideoURL",
				"Wait for Video modal and type in the video URL: " + videoURL,
				true, driver);
	}

	/**
	 * Left Click on add 'Photo' button.
	 * 
	 * @author Michal Nowierski
	 */
	public void clickOnAddPhotoButton2() {
		waitForElementByElement(addPhotoButton);
		waitForElementClickableByElement(addPhotoButton);
		clickAndWait(addPhotoButton);
		PageObjectLogging.log("ClickOnAddPhotoButton2",
				"Left Click on add 'Photo' button.", true, driver);
	}

	
	
	/**
	 * Wait for modal and click on 'add this photo' under the first seen photo
	 * 
	 * @author Michal Nowierski
	 */
	public void waitForModalAndClickAddThisPhoto() {
		waitForElementByElement(imageUploadModal);
		waitForElementClickableByElement(addThisPhotoLink);
		clickAndWait(addThisPhotoLink);
		PageObjectLogging
				.log("WaitForModalAndClickAddThisPhoto",
						"Wait for modal and click on 'add this photo' under the first seen photo",
						true, driver);
	}

	/**
	 * Type given caption for the photo
	 * 
	 * @author Michal Nowierski
	 */
	public void typePhotoCaption(String caption) {
		waitForElementByElement(captionTextArea);
		captionTextArea.clear();
		captionTextArea.sendKeys(caption);
		PageObjectLogging.log("TypeAcaption", "Type any caption for the photo",
				true, driver);
	}

	public SpecialNewFilesPageObject OpenSpecialNewFiles() {
		getUrl(Domain + "Special:NewFiles");
		return new SpecialNewFilesPageObject(driver, Domain);
	}

	public SpecialUploadPageObject OpenSpecialUpload() {
		getUrl(Domain + "Special:Upload");
		return new SpecialUploadPageObject(driver, Domain);
	}

	public SpecialMultipleUploadPageObject OpenSpecialMultipleUpload() {
		getUrl(Domain + "Special:MultipleUpload");
		return new SpecialMultipleUploadPageObject(driver, Domain);

	}

	public WikiArticlePageObject OpenArticle(String wikiArticle) {
		try {
			getUrl(Domain + "wiki/" + wikiArticle);
		} catch (TimeoutException e) {
			PageObjectLogging.log("OpenArticle",
					"page loads for more than 30 seconds", true);
		}
		return new WikiArticlePageObject(driver, Domain, wikiArticle);
	}

	public void openWikiPage() {
		String temp = Domain;
		try {
			temp = Domain + "?noexternals=1";
			getUrl(temp);
		} catch (TimeoutException e) {
			PageObjectLogging.log("logOut",
					"page loads for more than 30 seconds", true);
		}
		waitForElementByCss("a[class*=hub]");
		executeScript("$('ul#pagehistory li:nth-child(1) .mw-history-undo')");
	}

	public void openRandomArticle() {
		// String href = randomPageButton.getAttribute("href");
		// driver.navigate().to(href);
		clickAndWait(randomPageButton);
		waitForElementByElement(searchButton);
		PageObjectLogging.log("openRandomArticle",
				"random page button clicked", true, driver);
	}

	public void verifyEditDropDownAnonymous() {
		List<WebElement> list = driver.findElements(By
				.cssSelector("header#WikiaPageHeader ul.WikiaMenuElement li"));
		CommonFunctions.assertNumber(1, list.size(),
				"Edit drop-down number of items for anonymous user");
		CommonFunctions.assertString(
				"history",
				list.get(0).findElement(By.cssSelector("a"))
						.getAttribute("data-id"));
	}

	public void verifyEditDropDownLoggedInUser() {
		List<WebElement> list = driver.findElements(By
				.cssSelector("header#WikiaPageHeader ul.WikiaMenuElement li"));
		CommonFunctions.assertNumber(2, list.size(),
				"Edit drop-down number of items for admin user");
		CommonFunctions.assertString(
				"history",
				list.get(0).findElement(By.cssSelector("a"))
						.getAttribute("data-id"));
		CommonFunctions.assertString(
				"move",
				list.get(1).findElement(By.cssSelector("a"))
						.getAttribute("data-id"));
	}

	public void verifyEditDropDownAdmin() {
		List<WebElement> list = driver.findElements(By
				.cssSelector("header#WikiaPageHeader ul.WikiaMenuElement li"));
		CommonFunctions.assertNumber(4, list.size(),
				"Edit drop-down number of items for admin user");
		CommonFunctions.assertString(
				"history",
				list.get(0).findElement(By.cssSelector("a"))
						.getAttribute("data-id"));
		CommonFunctions.assertString(
				"move",
				list.get(1).findElement(By.cssSelector("a"))
						.getAttribute("data-id"));
		CommonFunctions.assertString(
				"protect",
				list.get(2).findElement(By.cssSelector("a"))
						.getAttribute("data-id"));
		CommonFunctions.assertString(
				"delete",
				list.get(3).findElement(By.cssSelector("a"))
						.getAttribute("data-id"));
	}

	private void clickContributeButton() {
		executeScript("document.querySelectorAll(\".wikia-menu-button\")[0].click()");
		executeScript("document.querySelectorAll(\".wikia-menu-button\")[0].click()");
		waitForElementByElement(createArticleButton);
		PageObjectLogging.log("clickOnContributeButton",
				"contribute button clicked", true);
	}

	private void clickCreateArticleButton() {
		waitForElementByElement(createArticleButton);
		waitForElementClickableByElement(createArticleButton);
		// jQueryClick(".createpage");
		executeScript("document.querySelectorAll('.createpage')[0].click()");
		waitForElementByElement(driver.findElement(layoutList));
		PageObjectLogging.log("clickCreateArticleButton",
				"create article button clicked", true);
	}

	private void selectPageLayout(int number) {
		List<WebElement> list = driver.findElements(layoutList);
		clickAndWait(list.get(number));
		PageObjectLogging.log("selectPageLayout", "wiki layout selected", true,
				driver);
	}

	private void typeInArticleName(String name) {
		waitForElementByElement(articleNameField);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		articleNameField.sendKeys(name);
	}

	private void clickAddPageButton() {
		clickAndWait(addArticleButton);
		PageObjectLogging.log("clickAddPageButton", "add button clicked", true,
				driver);
	}

	public void verifyDeletedArticlePage(String pageName) {
		pageName = pageName.replace("_", " ");
		waitForElementByXPath("//h1[contains(text(), '" + pageName + "')]");
		// waitForElementByXPath("//p[contains(text(), 'This page has been deleted.')]");
		// waitForElementByXPath("//a[@class='wikia-button' and contains(text(), 'Create')]");
		waitForElementByXPath("//b[contains(text(), 'This page needs content. You can help by adding a sentence or a photo!')]");
		PageObjectLogging.log("verifyDeletedArticlePage",
				"deleted article page verified", true);
	}

	public void clickEditDropDown() {
		waitForElementByElement(editDropDown);
		clickAndWait(editDropDown);
		PageObjectLogging.log("clickEditDropDown", "edit drop-down clicked",
				true, driver);
	}

	public WikiArticleEditMode clickEditButton(String pageName) {
		//two lines below prevent hubs drop-down on IE9
		waitForElementByElement(editButton);
		waitForElementClickableByElement(editButton);
		try{
			CommonFunctions.scrollToElement(editButton);
			mouseOver("#GlobalNavigation li:nth(1)");
			mouseRelease("#GlobalNavigation li:nth(1)");
			editButton.click();			
		}
		catch(TimeoutException e)
		{
			PageObjectLogging.log("clickAndWait", "page loaded for more then 30 seconds after click", true);
		}
		PageObjectLogging.log("clickEditButton", "edit button clicked", true, driver);
		return new WikiArticleEditMode(driver, Domain, pageName);
	}

	protected void clickDeleteButtonInDropDown() {
		waitForElementByElement(deleteButton);
		clickActions(deleteButton);
		PageObjectLogging.log("clickDeleteButtonInDropDown",
				"delete button in drop-down clicked", true);
	}

	protected void clickCommentDeleteConfirmationButton() {
		waitForElementByElement(deleteConfirmationButton);
		waitForElementByElement(deleteCommentReasonField);
		deleteCommentReasonField.clear();
		deleteCommentReasonField.sendKeys("QAReason");
		clickAndWait(deleteConfirmationButton);
		waitForElementByElement(deleteCommentConfirmationMessage);

	}

	protected void clickArticleDeleteConfirmationButton(String atricleName) {
		waitForElementByElement(deleteConfirmationButton);
		waitForElementByElement(deleteCommentReasonField);
		deleteCommentReasonField.clear();
		deleteCommentReasonField.sendKeys("QAReason");
		clickAndWait(deleteConfirmationButton);
		String temp = atricleName.replace("_", " ");
		waitForElementByXPath("//div[@class='msg' and contains(text(), '\""
				+ temp + "\" has been deleted.')]");
	}

	public void deleteArticle(String atricleName) {
		getUrl(driver.getCurrentUrl() + "?action=delete");
		// clickDeleteButtonInDropDown();
		clickArticleDeleteConfirmationButton(atricleName);
		waitForElementByXPath("//div[@class='msg' and contains(text(), 'has been deleted.')]");
		PageObjectLogging.log("deleteArticle", "article has been deleted",
				true, driver);
	}

	public void renameArticle(String articleName, String articleNewName) {
		getUrl(Global.DOMAIN + "wiki/Special:MovePage/" + articleName);
		waitForElementByElement(renameArticleField);
		waitForElementByElement(confirmRenamePageButton);
		renameArticleField.clear();
		renameArticleField.sendKeys(articleNewName);
		clickAndWait(confirmRenamePageButton);
		waitForElementByXPath("//b[contains(text(), '\"" + articleName
				+ "\" has been renamed \"" + articleNewName + "\"')]");
	}

	private void clickUndeleteArticle() {
		waitForElementByElement(undeleteButton);
		// jQuery didn't work here. The below workaround stimulates clicking on
		// 'undelete' button
		String href = undeleteButton.getAttribute("href");
		driver.navigate().to(href);
		// clickAndWait(undeleteButton);
		waitForElementByElement(restoreButton);
		PageObjectLogging.log("clickUndeleteArticle",
				"undelete article button clicked", true, driver);
	}

	private void clickRestoreArticleButton() {
		waitForElementByElement(restoreButton);
		clickAndWait(restoreButton);
		waitForElementByXPath("//div[@class='msg' and contains(text(), 'This page has been restored.')]");
		PageObjectLogging.log("clickUndeleteArticle",
				"undelete article button clicked", true, driver);
	}

	public void undeleteArticle() {
		clickUndeleteArticle();
		clickRestoreArticleButton();
	}

	public WikiArticleEditMode createNewArticle(String pageName,
			int layoutNumber) {
		// clickContributeButton();
		// clickCreateArticleButton();
		// selectPageLayout(layoutNumber);
		// typeInArticleName(pageName);
		// clickAddPageButton();
		getUrl(Global.DOMAIN + "index.php?title=" + pageName
				+ "&action=edit&useFormat=" + layoutNumber);
		String pageNameEnc = pageName.replace("_", " ");
		waitForElementByElement(driver.findElement(By.cssSelector("a[title='"
				+ pageNameEnc + "']")));
		return new WikiArticleEditMode(driver, Domain, pageName);
	}

	public WikiArticlePageObject openArticle(String articleName) {
		URI uri;
		try {
			uri = new URI(Global.DOMAIN + "wiki/" + articleName);
			String url = uri.toASCIIString();
			getUrl(url);
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}
		PageObjectLogging.log("openArticle", "article " + articleName
				+ " opened", true, driver);
		return new WikiArticlePageObject(driver, Domain, articleName);
	}

	public WikiCategoryPageObject clickOnCategory(String categoryName) {
		List<WebElement> lista = driver.findElements(By
				.cssSelector("#catlinks li a"));
		Boolean result = false;
		// there might be more than one category on a random page. Thus - loop
		// over all of them.
		if (lista.size() > 0) {

			for (WebElement webElement : lista) {
				waitForElementByElement(webElement);
				if (webElement.getText().equalsIgnoreCase(categoryName)) {
					waitForElementClickableByElement(webElement);
					clickAndWait(webElement);
					result = true;
				}
			}
		}
		if (result) {
			PageObjectLogging.log("clickOnCategory", "clicked on "
					+ categoryName, true, driver);
		} else {
			PageObjectLogging.log("clickOnCategory", "category " + categoryName
					+ " not found", false, driver);
		}
		return new WikiCategoryPageObject(driver, Domain);
	}

	public WikiCategoryPageObject openCategoryPage(String category) {
		getUrl(Global.DOMAIN + "wiki/" + "Category:" + category);
		PageObjectLogging.log("openCategoryPage", category + " page opened",
				true, driver);
		return new WikiCategoryPageObject(driver, Domain);
	}

	public CreateNewWikiPageObjectStep1 startAWiki() {
		return null;

	}

}
