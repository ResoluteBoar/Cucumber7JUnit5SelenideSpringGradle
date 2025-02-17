package com.unsplash.cucumber.steps;

import ch.qos.logback.classic.Logger;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unsplash.cucumber.pages.CollectionPage;
import com.unsplash.cucumber.pages.CreateNewCollectionForm;
import com.unsplash.cucumber.pages.HomePage;
import com.unsplash.cucumber.pages.SearchResultPage;
import com.unsplash.utils.DataItem;
import com.unsplash.utils.DataStorage;
import com.unsplash.utils.PropertyUtils;
import com.unsplash.utils.RandomUtils;
import com.unsplash.utils.ScenarioContext;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class CollectionSteps {
  private static final Logger logger = (Logger) LoggerFactory.getLogger(CollectionSteps.class);

  @Autowired
  private DataStorage dataStorage;
  @Autowired
  private HomePage homePage;
  @Autowired
  private SearchResultPage searchResultPage;
  @Autowired
  private CreateNewCollectionForm createNewCollectionForm;
  @Autowired
  private CollectionPage collectionPage;
  @Autowired
  private ScenarioContext scenarioContext;

  @When("^он ищет коллекцию по ключевому слову \"(.*)\"$")
  public void search_pics_with_keyword(String keyword) throws Throwable {
    homePage.enterSearch(keyword);
  }

  @When("^он добавляет первое изображение в новую коллекцию \"(.*)\"$")
  public void add_first_image_to_collection(String collectionName) throws Throwable {
    searchResultPage.addFirstPicToCollection();
    searchResultPage.createNewCollection();
    collectionName = collectionName + RandomUtils.getUniqueNumber();
    logger.info("New created Collection Name: " + collectionName);
    createNewCollectionForm.createNewCollection(collectionName, "", false);
    scenarioContext.setContext(DataItem.COLLECTION_NAME, collectionName);
    dataStorage.setCollectionNames(collectionName);
  }

  @When("^он ищет по странице его коллекции$")
  public void navigate_collection_page() throws Throwable {
    open(PropertyUtils.getBaseUrl() + "@" + PropertyUtils.getUserProfile() + "/collections");
  }

  @Then("^он может видеть, что новая коллекция добавлена в его коллекцию$")
  public void check_collection_added() throws Throwable {
    String collectionName = (String) scenarioContext.getContext(DataItem.COLLECTION_NAME);
    assertThat(collectionPage.getCollectionNameList()).contains(collectionName);
  }
}
