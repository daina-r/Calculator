import java.util.Scanner;

public class Main {
    public static String calc(String input) throws Exception {
        Boolean roman = false;
        String operator = "";
        String part1 = "";
        String part2 = "";
        int num1;
        int num2;
        String result = "";

        // проверяем полученную строку и получаем данные для вычисления
        for (char s : input.toCharArray()) {
            String symbol = s + "";

            if (isNumeric(symbol)) {
                if (operator.equals("")) {
                    part1 = getNumeric(symbol, part1);
                } else if (roman == false) {
                    part2 = getNumeric(symbol, part2);
                } else {
                    throw new Exception("Одновременное использование арабских и римских чисел");
                }

            } else if (isRomanNumeral(symbol)) {
                if (operator.equals("")) {
                    part1 = getRomanNumeral(symbol, part1);
                    roman = true;
                } else if (roman == true) {
                    part2 = getRomanNumeral(symbol, part2);
                } else {
                    throw new Exception("Одновременное использование арабских и римских чисел");
                }

            } else if (isOperator(symbol)) {
                if (operator.equals("")) {
                    operator += symbol;
                } else {
                    throw new Exception("Допустимо использование только одного опертора");
                }

            } else {
                throw new Exception("Некорректный ввод. Допустимы арабские или римские числа и опертор \"+\", \"-\", \"*\" или \"/\"");
            }
        }

        // приводим тип переменных к целочисленному
        if (operator != "" && part1 != "" && part2 != "") {
            if (roman == true) {
                num1 = convertRomanToArabic(part1);
                num2 = convertRomanToArabic(part2);
            } else {
                num1 = Integer.parseInt(part1);
                num2 = Integer.parseInt(part2);
            }
        } else {
                throw new Exception("Некорректный ввод. Выражение должно иметь вид: a + b, a - b, a * b, a / b");
        }

        // выполняем арифметическую операцию
        switch (operator) {
            case "+":
                result = String.valueOf(num1 + num2);
                break;
            case "-":
                result = String.valueOf(num1 - num2);
                break;
            case "*":
                result = String.valueOf(num1 * num2);
                break;
            case "/":
                result = String.valueOf(num1 / num2);
        }

        // возвращаем результат 
        if (roman == true) {
            return convertArabicToRoman(result);
        } else {
            return result;
        }
    }

    // метод для проверки, соответствует ли символ арабской цифре
    public static boolean isNumeric(String symbol) {
        return symbol.matches("[0-9]");
    }

    // метод для проверки, соответствует ли символ римской цифре
    public static boolean isRomanNumeral(String symbol) {
        return symbol.matches("[IVXLCDM]");
    }

    // метод для проверки, является ли символ одним из операторов: +, -, *, /
    public static boolean isOperator(String symbol) {
        return symbol.matches("[+\\-*/]");
    }

    // метод для сборки арабских чисел из входящих символов
    public static String getNumeric(String symbol, String num) throws Exception {
        if (num.equals("") && !symbol.equals("0") || num.equals("1") && symbol.equals("0")) {
            return num += symbol;
        } else {
            throw new Exception("Допустимы операции только с числами от 1 до 10 включительно");
        }
    }

    // метод для сборки римских чисел из входящих символов
    public static String getRomanNumeral(String symbol, String num) throws Exception {
        if (num.equals("")) {
            num += symbol;
        } else {
            num += symbol;
        }

        if (num.matches("X|IX|IV|V?I{0,3}")) {
            return num;
        } else {
            throw new Exception("Допустимы операции только с числами от I до X включительно");
        }
    }

    // Метод для конвертации римского числа в арабское
    public static int convertRomanToArabic(String input) {
        String[] keys = {"I", "V", "X"};
        int[] values = {1, 5, 10};
        int result = 0;
        int prevValue = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            for (int j = 0; j < keys.length; j++) {
                if (keys[j].equals(String.valueOf(input.charAt(i)))) {
                    int currentValue = values[j];
                    
                    if (currentValue < prevValue) {
                        result -= currentValue;
                    } else {
                        result += currentValue;
                    }
                    prevValue = currentValue;
                    break;
                }
            }
        }
        return result;
    }

    // метод для конвертации арабского числа в римское
    public static String convertArabicToRoman(String number) throws Exception {
        String[] keys = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        int num = Integer.parseInt(number);

        if (num < 1) {
            throw new Exception("Римские числа не могут быть меньше единицы");
        } else {
            String roman = "";

            for (int i = 0; i < values.length; i++) {
                int arabic = values[i];
                while (num >= arabic) {
                    roman += keys[i];
                    num -= arabic;
                }
            }
            return roman;
        }
    }

    public static void main(String[] args) {
        System.out.println("\nКалькулятор работает с арабскими (1,2,3,4,5...) или римскими (I,II,III,IV,V...) числами,");
        System.out.println("выполняет операции сложения, вычитания, умножения и деления между двух чисел от 1 до 10 включительно: a + b, a - b, a * b, a / b");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\nВведите арифметическое выражение (например, 2 + 2):");

                // получаем строку, убираем пробелы, приводим к верхнему регистру
                String input = scanner.nextLine().replaceAll("\\s+", "").toUpperCase();
                String result = calc(input);
                System.out.println("Результат: " + result);

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                break;
            }
        }
        scanner.close();
    }
}
