package org.example;

import java.util.*;

public class Task1 {
    public static void main(String[] args) {
        List<String> candidatesData = readData();
        printCodes(candidatesData);
    }

    static List<String> readData() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<String> candidateData = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            candidateData.add(scanner.next());
        }
        return candidateData;
    }

    static String computeCode(String candidate) {
        String[] candidateData = candidate.split(",");
        Set<Character> characterSet = new HashSet<>();
        char[] surname = candidateData[0].toCharArray();
        char[] name = candidateData[1].toCharArray();
        char[] patronymic = candidateData[2].toCharArray();
        for (char c : surname) {
            characterSet.add(c);
        }
        for (char c : name) {
            characterSet.add(c);
        }
        for (char c : patronymic) {
            characterSet.add(c);
        }
        int symbolsCount = characterSet.size();
        char[] day = candidateData[3].toCharArray();
        char[] month = candidateData[4].toCharArray();
        int sumDayMonth = 0;
        for (char c : day) {
            sumDayMonth += Character.getNumericValue(c);
        }
        for (char c : month) {
            sumDayMonth += Character.getNumericValue(c);
        }
        int firstSymbolCode = (surname[0] - 'A') + 1;
        int code = symbolsCount + sumDayMonth * 64 + firstSymbolCode * 256;
        String codeString = Integer.toHexString(code);
        return codeString.substring(codeString.length()-3).toUpperCase();
    }

    static void printCodes(List<String> candidatesData) {
        for (String candidatesDatum : candidatesData) {
            System.out.print(computeCode(candidatesDatum) + " ");
        }
    }

}
