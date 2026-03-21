package ru.yandex.practicum.delivery;

public abstract class Parcel {
    protected String description;
    protected int weight;
    protected String deliveryAddress;
    protected int sendDay;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    public void packageItem() {
        System.out.println("Посылка " + description + " упакована");
    }

    public void deliver() {
        System.out.println("Посылка <<" + description + ">> доставлена по адресу " + deliveryAddress);
    }

    protected abstract int getBaseCost();

    public boolean isExpired(int currentDay){
        return false;
    }//добавьте реализацию и другие необходимые классы

    public int calculateDeliveryCost() {
        return weight * getBaseCost();
    }

    public int getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }
}
