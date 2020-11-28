package com.tictactoebot.api.steps;

import io.qameta.allure.Step;

import java.util.Arrays;

public class TicTacToeSteps {

    int[][] ticTacToeMatrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[] edge = new int[]{2, 4, 6, 8};
    int[] diagonal = new int[]{1, 5, 9};
    int[] diagonal2 = new int[]{3, 5, 7};
    int center = 5;
    int n = ticTacToeMatrix.length;

    @Step("Verify that entered value {value} displayed at corner")
    public boolean verifyThatEnteredSequenceContainCornerValue(int value) {
        int[] corner = new int[]{ticTacToeMatrix[0][0], ticTacToeMatrix[0][n - 1], ticTacToeMatrix[n - 1][0], ticTacToeMatrix[n - 1][n - 1]};
        return verifyValueExistInMatrix(corner, value);
    }

    @Step("Verify that entered value {value} displayed at edge")
    public boolean verifyThatEnteredSequenceContainEdgeValue(int value) {
        int[] edge = new int[]{ticTacToeMatrix[0][n-2], ticTacToeMatrix[n-2][n - 1], ticTacToeMatrix[n - 1][0], ticTacToeMatrix[n - 1][n - 1]};
        return verifyValueExistInMatrix(edge, value);
    }

    @Step("Verify that entered value {value} displayed at first diagonal")
    public boolean verifyThatEnteredSequenceContainDiagonalValue(int value) {
        return verifyValueExistInMatrix(diagonal, value);
    }

    @Step("Verify that entered value {value} displayed at second diagonal")
    public boolean verifyThatEnteredSequenceContainDiagonal2Value(int value) {
        return verifyValueExistInMatrix(diagonal2, value);
    }

    private boolean verifyValueExistInMatrix(int[] matrix, int value) {
        return Arrays.stream(matrix).anyMatch(it -> it == value);
    }

}
