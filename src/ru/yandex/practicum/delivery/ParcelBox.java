package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private List<T> parcels = new ArrayList<>();
    private int maxWeight;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public boolean addParcel(T parcel) {
        if (getCurrentWeight() + parcel.getWeight() > maxWeight) {
            System.out.println("Превышает вес коробки. Посылка не добавлена.");
            return false;
        } else {
            parcels.add(parcel);
            return true;
        }
    }

    public List<T> getAllParcels() {
        return parcels;
    }

    private int getCurrentWeight() {
        int waight = 0;
        for (T parcel : parcels) {
            waight += parcel.getWeight();
        }
        return waight;
    }
}
