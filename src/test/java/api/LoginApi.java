package api;

import models.AddListOfBooksResponseModel;
import models.GetListOfBooksResponseModel;
import models.LoginBodyModel;
import models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

public class LoginApi {
    public LoginResponseModel login(LoginBodyModel userData) {
        return given(requestSpec)
                .body(userData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec(200))
                .extract().as(LoginResponseModel.class);
    }

    public void loginCheck(LoginBodyModel userData, LoginResponseModel loginResponse) {
        assertEquals(userData.getUserName(), loginResponse.getUsername());
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6Imtvc3R5YV9pdmFub3YiLCJwYXNzd29yZCI6Iktvc3R5YTkkIiwiaWF0IjoxNzU2MTg5Mjk0fQ.00NdiMNyZUOHmSKe_r_i86ElJthJH2yknOSwUKSBtd0", loginResponse.getToken());
        assertEquals("4e98fd61-cab7-4ef5-b66a-690c3a8add5c", loginResponse.getUserId());
    }

    public GetListOfBooksResponseModel getUserBookResponse(LoginResponseModel loginResponse) {
        return given(requestSpec)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .when()
                .get("/Account/v1/User/" + loginResponse.getUserId())
                .then()
                .spec(responseSpec(200))
                .extract().as(GetListOfBooksResponseModel.class);
    }

    public void usersBookListCheck(LoginBodyModel userData, LoginResponseModel loginResponse, AddListOfBooksResponseModel bookResponse, GetListOfBooksResponseModel userBookResponse) {
        assertEquals(loginResponse.getUserId(), userBookResponse.getUserId());
        assertEquals(userData.getUserName(), userBookResponse.getUsername());
        assertEquals(bookResponse.getBooks().get(0).getIsbn(), userBookResponse.getBooks().get(0).getIsbn());
        assertFalse(userBookResponse.getBooks().stream().anyMatch(books -> bookResponse.getBooks().get(1).getIsbn().equals(books.getIsbn())));
    }
}
