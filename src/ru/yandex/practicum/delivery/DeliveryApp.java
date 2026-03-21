package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackables = new ArrayList<>();

    private static final int MAX_WEIGHT = 20;

    private static ParcelBox<StandardParcel> standardBox = new ParcelBox<>(MAX_WEIGHT);
    private static ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(MAX_WEIGHT);
    private static ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(MAX_WEIGHT);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    System.out.print("Введите новое местоположение: ");
                    String newLocashion = scanner.nextLine();
                    for (Trackable parcel : trackables) {
                        parcel.reportStatus(newLocashion);
                    }
                    break;
                case 5:
                    System.out.println("Выберите тип коробки для отображения содержимого:");
                    System.out.println("1 — Стандартные посылки");
                    System.out.println("2 — Хрупкие посылки");
                    System.out.println("3 — Скоропортящиеся посылки");
                    int boxType = scanner.nextInt();
                    scanner.nextLine();

                    switch (boxType) {
                        case 1:
                            showBoxContents(standardBox);
                            break;
                        case 2:
                            showBoxContents(fragileBox);
                            break;
                        case 3:
                            showBoxContents(perishableBox);
                            break;
                        default:
                            System.out.println("Неверный выбор.");
                    }
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Обновить местоположение посылки");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    // реализуйте методы ниже

    private static void addParcel() {

        System.out.println("Какой тип посылки хотите отправить?(1 - Стандартная, 2 - Хрупкая, 3 - Скоропортящаяся )");
        int type = Integer.parseInt(scanner.nextLine());
        System.out.println("Краткое описание посылки:");
        String description = scanner.nextLine();
        System.out.println("Какой вес у посылки?(кг)");// Подсказка: спросите тип посылки и необходимые поля, создайте объект и добавьте в allParcels
        int weight = scanner.nextInt();
        scanner.nextLine();
        System.out.println("На какой адрес нужно отправить посылку?");
        String deliveryAddress = scanner.nextLine();
        System.out.println("В какое число нужно отправить посылку?");
        int sendDay = scanner.nextInt();
        scanner.nextLine();


        switch (type) {
            case 1:
               StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
                if (standardBox.addParcel(standardParcel)) {
                    allParcels.add(standardParcel);
                    System.out.println("Посылка добавлена: " + standardParcel.description);
                }
                break;
            case 2:
               FragileParcel fragileParcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
                if (fragileBox.addParcel(fragileParcel)) {
                    allParcels.add(fragileParcel);
                    System.out.println("Посылка добавлена: " + fragileParcel.description);
                    trackables.add(fragileParcel);
                }
                break;
            case 3:
                System.out.println("Сколько дней может храниться?");
                int timeToLive = scanner.nextInt();
                scanner.nextLine();
                PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);
                if (perishableBox.addParcel(perishableParcel)) {
                    allParcels.add(perishableParcel);
                    System.out.println("Посылка добавлена: " + perishableParcel.description);
                }
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private static void sendParcels() {
        System.out.println("Введите текущий день:");
        int currentDay = scanner.nextInt();
        scanner.nextLine();

        for (Parcel parcel : allParcels) {
                if (parcel.isExpired(currentDay)) {
                    System.out.println("Посылка " + parcel.getDescription() + " просрочена!");
                } else {
                    parcel.packageItem();
                    parcel.deliver();
                }
        } // Пройти по allParcels, вызвать packageItem() и deliver()
    }

    private static void calculateCosts() {
        int sum = 0;
        for (Parcel parcel : allParcels) {
            sum += parcel.calculateDeliveryCost();
        }
        System.out.println("Общая сумма доставки: " + sum);
        // Посчитать общую стоимость всех доставок и вывести на экран
    }

    private static void showBoxContents(ParcelBox<? extends Parcel> box) {
        List<? extends Parcel> parcels = box.getAllParcels();
        for (Parcel parcel : parcels) {
            System.out.println(parcel.getDescription());
        }
    }
}

