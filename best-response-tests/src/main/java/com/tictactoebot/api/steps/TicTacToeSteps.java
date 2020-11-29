package com.tictactoebot.api.steps;

import io.qameta.allure.Step;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicTacToeSteps {

    public int[][] ticTacToeMatrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n = ticTacToeMatrix.length;

    @Step("Verify that entered value {value} displayed at corner")
    public boolean verifyThatEnteredSequenceContainCornerValue(int value) {
        int[] corner = new int[]{ticTacToeMatrix[0][0], ticTacToeMatrix[0][n - 1], ticTacToeMatrix[n - 1][0], ticTacToeMatrix[n - 1][n - 1]};
        return verifyValueExistInMatrix(corner, value);
    }

    @Step("Verify that entered value {value} displayed at edge")
    public boolean verifyThatEnteredSequenceContainEdgeValue(int value) {
        int[] edge = new int[]{ticTacToeMatrix[0][n - 2], ticTacToeMatrix[n - 2][n - 1], ticTacToeMatrix[n - 2][0], ticTacToeMatrix[n - 1][n - 2]};
        return verifyValueExistInMatrix(edge, value);
    }

    @Step("Verify select row by mentioned values {values}")
    public String checkWhetherValuesExistInSomeRow(String values) {
        int[] actSequence = Arrays.stream(values.split("")).mapToInt(Integer::parseInt).toArray();
        List<Integer> predefinedValues = Arrays.asList(actSequence[0], actSequence[n - 1]);
        return getMatchedRowColums(predefinedValues);
    }

    private boolean verifyValueExistInMatrix(int[] matrix, int value) {
        return Arrays.stream(matrix).anyMatch(it -> it == value);
    }

    private Set<Integer> getColumnOfGivenMatrix(int index) {
        return IntStream.range(0, n)
                .map(i -> ticTacToeMatrix[i][index]).boxed().collect(Collectors.toSet());

    }

    private String getMatchedRowColums(List<Integer> predefinedValues) {
        Set<Integer> res = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (getColumnOfGivenMatrix(i).containsAll(predefinedValues)) {
                res = getColumnOfGivenMatrix(i);
            } else if (Arrays.stream(ticTacToeMatrix[i]).boxed().collect(Collectors.toSet()).containsAll(predefinedValues)) {
                res = Arrays.stream(ticTacToeMatrix[i]).boxed().collect(Collectors.toSet());
            }
        }
        res.removeAll(predefinedValues);
        return res.toString();
    }

}
