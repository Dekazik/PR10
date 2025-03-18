import java.util.Scanner;

public class Main {
    public static final int UsersLimit = 15;
    public static String[] usernames = new String[UsersLimit];
    public static String[] passwords = new String[UsersLimit];
    public static int usersCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("1. Додати користувача");
                System.out.println("2. Видалити користувача");
                System.out.println("3. Аутентифікація");
                System.out.println("4. Вихід");
                System.out.print("Оберіть дію: ");

                if (sc.hasNextInt()) {
                    int choice = sc.nextInt();
                    sc.nextLine();

                    if (choice == 1) {
                        addUser(sc);
                    } else if (choice == 2) {
                        removeUser(sc);
                    } else if (choice == 3) {
                        authenticateUser(sc);
                    } else if (choice == 4) {
                        System.out.println("Вихід з програми.");
                        break;
                    } else {
                        System.out.println("Невірний вибір.");
                    }
                } else {
                    System.out.println("Будь ласка, введіть число.");
                    sc.nextLine();
                }
            } catch (IllegalStateException e) {
                System.out.println("Помилка: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    public static void addUser(Scanner sc) {
        if (usersCount >= UsersLimit) {
            throw new IllegalStateException("Не можна додати більше користувачів.");
        }

        System.out.print("Введіть ім’я користувача: ");
        String username = sc.nextLine();
        if (username.length() < 5 || isSpace(username)) {
            throw new IllegalArgumentException("Ім’я користувача має містити не менше 5 символів і не повинно містити пробілів.");
        }

        System.out.print("Введіть пароль: ");
        String password = sc.nextLine();
        validatePassword(password);

        usernames[usersCount] = username;
        passwords[usersCount] = password;
        usersCount++;

        System.out.println("Користувача додано.");
    }

    public static void removeUser(Scanner sc) {
        System.out.print("Введіть ім’я користувача для видалення: ");
        String username = sc.nextLine();

        boolean found = false;
        for (int i = 0; i < usersCount; i++) {
            if (usernames[i].equals(username)) {
                usernames[i] = usernames[usersCount - 1];
                passwords[i] = passwords[usersCount - 1];
                usernames[usersCount - 1] = null;
                passwords[usersCount - 1] = null;
                usersCount--;
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Користувача з таким ім’ям не знайдено.");
        }

        System.out.println("Користувача видалено.");
    }

    public static void authenticateUser(Scanner sc) {
        System.out.print("Введіть ім’я користувача для аутентифікації: ");
        String username = sc.nextLine();
        System.out.print("Введіть пароль: ");
        String password = sc.nextLine();

        for (int i = 0; i < usersCount; i++) {
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                System.out.println("Аутентифікація успішна.");
                return;
            }
        }

        throw new IllegalStateException("Невірне ім’я користувача або пароль.");
    }

    public static void validatePassword(String password) {
        if (password.length() < 10 || isSpace(password)) {
            throw new IllegalArgumentException("Пароль має бути не менше 10 символів і не містити пробілів.");
        }

        boolean hasSpecialChar = false;
        boolean hasDigit = false;
        int digitsCount = 0;

        String[] forbiddenPasswords = {"admin", "pass", "password", "qwerty", "ytrewq"};
        for (int i = 0; i < forbiddenPasswords.length; i++) {
            if (password.contains(forbiddenPasswords[i])) {
                throw new IllegalArgumentException("Пароль не може містити заборонені слова.");
            }
        }

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (isDigit(c)) {
                digitsCount++;
                hasDigit = true;
            } else if (!isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        if (!(hasDigit && hasSpecialChar && digitsCount >= 3)) {
            throw new IllegalArgumentException("Пароль має містити хоча б 1 спеціальний символ та хоча б 3 цифри.");
        }
    }

    public static boolean isSpace(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                return true;
            }
        }
        return false;
    }

    public static boolean isDigit(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLetterOrDigit(char c) {
        if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
            return true;
        } else {
            return false;
        }
    }
}
