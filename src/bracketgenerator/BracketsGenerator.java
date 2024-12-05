package bracketgenerator;

import java.util.ArrayList;
import java.util.List;

public class BracketsGenerator implements Generator {
    int numberOfBrackets;

    public BracketsGenerator(int numberOfBrackets){
        this.numberOfBrackets = numberOfBrackets;
    }

    /**
     * Generates all possible valid brackets combinations.
     * */
    @Override
    public List<String> generate()
    {
        return generateRecursively("", numberOfBrackets, numberOfBrackets);
    }

    /**
     * Recursively generates all possible valid brackets combinations.
     * @param partialCombination the partial combination of brackets being formed during recursion.
     * @param openBracketsRemain the number of opening brackets remaining to be used.
     * @param closeBracketsRemain the number of closing brackets remaining to be used.
     * @return a list of all valid brackets combinations that can be formed with the given partial combination
     */
    private List<String> generateRecursively(String partialCombination, int openBracketsRemain, int closeBracketsRemain){
        List<String> result = new ArrayList<>();

        // Exit from recursion
        if(openBracketsRemain == 0 && closeBracketsRemain == 0) {
            result.add(partialCombination);
            return result;
        }

        // If open brackets limit is not reached then add '(' but only if a new combination is valid
        if (openBracketsRemain > 0) {
            var newCombination = partialCombination + '(';
            if(isPartialCombinationValid(newCombination))
                result.addAll(generateRecursively(partialCombination + '(', openBracketsRemain - 1, closeBracketsRemain));
        }

        // If close brackets limit is not reached then add ')' but only if a new combination is valid
        if (closeBracketsRemain > 0) {
            var newCombination = partialCombination + ')';
            if(isPartialCombinationValid(newCombination))
                result.addAll(generateRecursively(partialCombination + ")", openBracketsRemain, closeBracketsRemain - 1));
        }

        return result;
    }

    /**
     * Checks whether a given combination of brackets is valid up to this point.
     * A valid combination ensures that at no point the number of closing parentheses
     * exceeds the number of opening parentheses.
     *
     * @param combination the partial combination of parentheses to be validated.
     *                    It can contain any sequence of opening '(' and closing ')' parentheses.
     *
     @return true if part of the combination is correct e.g. '((()'
     */
    private boolean isPartialCombinationValid(String combination)
    {
        int numberOfOpenBrackets = 0;
        int numberOfClosedBrackets = 0;
        for (char ch : combination.toCharArray()) {
            if(ch == '(') numberOfOpenBrackets++;
            if(ch == ')') numberOfClosedBrackets++;
            if(numberOfClosedBrackets > numberOfOpenBrackets)
                return false;
        }
        return true;
    }
}
