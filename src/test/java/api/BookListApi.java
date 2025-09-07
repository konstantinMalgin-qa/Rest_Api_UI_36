package api;

import models.*;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

public class BookListApi {
    public void addBookToISBNCollection(List<String> isbnNumbers, LoginResponseModel loginResponse) {
        AddListOfBooksBodyModel bookData = new AddListOfBooksBodyModel();
        bookData.setUserId(loginResponse.getUserId());
        List<CollectionOfIsbnsModel> isbnList = new ArrayList<>();
        for (String isbn : isbnNumbers) {
            CollectionOfIsbnsModel isbnItem = new CollectionOfIsbnsModel();
            isbnItem.setIsbn(isbn);
            isbnList.add(isbnItem);
        }
        bookData.setCollectionOfIsbns(isbnList);
    }

    public AddListOfBooksResponseModel bookAdd(AddListOfBooksBodyModel bookData, LoginResponseModel loginResponse) {
        return given(requestSpec)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(201))
                .extract().as(AddListOfBooksResponseModel.class);
    }

    public void booksCheck(AddListOfBooksResponseModel bookResponse, List<String> expectedISBNS) {
        Assertions.assertEquals(expectedISBNS.size(), bookResponse.getBooks().size());

        for (int i = 0; i < expectedISBNS.size(); i++) {
            Assertions.assertEquals(expectedISBNS.get(i), bookResponse.getBooks().get(i).getIsbn()
                    );
        }
    }
}
