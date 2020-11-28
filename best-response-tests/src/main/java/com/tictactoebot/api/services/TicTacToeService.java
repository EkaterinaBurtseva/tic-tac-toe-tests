package com.tictactoebot.api.services;

import io.qameta.allure.Step;

public class TicTacToeService extends BaseService {

    @Step("insert values {values} as a next move")
    public String insertValuesMove(String values) {
       return setUp().when()
                .get("makeMove?map=" + values)
                .then().statusCode(200).extract().asString();

    }
}
