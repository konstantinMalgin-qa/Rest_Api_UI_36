package tests;

import api.BookListApi;
import api.LoginApi;
import models.*;
import org.junit.jupiter.api.Test;
import ui.DeleteUI;

import java.util.Arrays;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static tests.TestData.PASSWORD;
import static tests.TestData.USERNAME;

public class DemoQATests extends TestBase {
    LoginApi loginApi = new LoginApi();
    BookListApi bookApi = new BookListApi();

    @Test
    public void deleteOneOfItemsTest() {
        LoginBodyModel userData = new LoginBodyModel(USERNAME, PASSWORD);

        List<String> isbnList = Arrays.asList("9781449325862", "9781449331818");

        DeleteUI deleteUI = new DeleteUI();

        LoginResponseModel loginResponse = step("Make login request", () ->
                loginApi.login(userData));


        bookApi.addBookToISBNCollection(isbnList, loginResponse);
        AddListOfBooksResponseModel bookResponse = step("Make request to add list of books to profile", () ->
                bookApi.bookAdd(null, loginResponse));

        step("Check books are added", () -> {
            bookApi.booksCheck(bookResponse, isbnList);
        });

        step("Delete a book with UI", () -> {
            deleteUI.deleteBookWithUI(userData, bookResponse);
        });

        GetListOfBooksResponseModel userBookResponse = step("Make request to get a list of user's books", () ->
                loginApi.getUserBookResponse(loginResponse));

        step("Confirm removal with api by response", () -> {
            loginApi.usersBookListCheck(userData, loginResponse, bookResponse, userBookResponse);
        });
    }
}
