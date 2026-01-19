package com.urise.webapp.util;

public class TransliterateUtil {
    public static String makeEmail(String input) {
        char[] charArray = input.toLowerCase().replaceAll(" ", "").toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : charArray) {
            switch (c) {
                case 'а':
                    result.append("a");
                    break;
                case 'б':
                    result.append("b");
                    break;
                case 'в':
                    result.append("v");
                    break;
                case 'г':
                    result.append("g");
                    break;
                case 'д':
                    result.append("d");
                    break;
                case 'е':
                    result.append("e");
                    break;
                case 'ё':
                    result.append("yo");
                    break;
                case 'ж':
                    result.append("zh");
                    break;
                case 'з':
                    result.append("z");
                    break;
                case 'и':
                    result.append("i");
                    break;
                case 'й':
                    result.append("j");
                    break;
                case 'к':
                    result.append("k");
                    break;
                case 'л':
                    result.append("l");
                    break;
                case 'м':
                    result.append("m");
                    break;
                case 'н':
                    result.append("n");
                    break;
                case 'о':
                    result.append("o");
                    break;
                case 'п':
                    result.append("p");
                    break;
                case 'р':
                    result.append("r");
                    break;
                case 'с':
                    result.append("s");
                    break;
                case 'т':
                    result.append("t");
                    break;
                case 'у':
                    result.append("u");
                    break;
                case 'ф':
                    result.append("f");
                    break;
                case 'х':
                    result.append("h");
                    break;
                case 'ц':
                    result.append("c");
                    break;
                case 'ч':
                    result.append("ch");
                    break;
                case 'ш':
                    result.append("sh");
                    break;
                case 'щ':
                    result.append("sh");
                    break;
                case 'ъ':
                    result.append("");
                    break;
                case 'ы':
                    result.append("y");
                    break;
                case 'ь':
                    result.append("");
                    break;
                case 'э':
                    result.append("e");
                    break;
                case 'ю':
                    result.append("yu");
                    break;
                case 'я':
                    result.append("ya");
                    break;
            }
        }
        return result.toString();
    }
}
