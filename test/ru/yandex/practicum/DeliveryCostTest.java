package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DeliveryCostTest {

    @Test
    public void testCalculateDeliveryCostStandard() {
        Parcel parcel = new StandardParcel("описание",3, "адрес", 4);
        assertEquals(parcel.calculateDeliveryCost(), 6);
    }
    @Test
    public void testCalculateDeliveryCostFragile() {
        Parcel parcel = new FragileParcel("описание",5, "адрес", 4);
        assertEquals(parcel.calculateDeliveryCost(), 20);
    }
    @Test
    public void testCalculateDeliveryCostPerishable() {
        Parcel parcel = new PerishableParcel("описание",4, "адрес", 4, 4);
        assertEquals(parcel.calculateDeliveryCost(), 12);
    }

    @Test
    public void testIsExpired() {
        PerishableParcel parcel = new PerishableParcel("описание", 3, "адрес", 4, 2);
        assertFalse(parcel.isExpired(5));
        assertTrue(parcel.isExpired(7));
    }

    @Test
    public void testAddParcel() {
        ParcelBox<StandardParcel> standardBox = new ParcelBox<>(20);
        StandardParcel parcel = new StandardParcel("описание", 20, "адрес", 4);
        assertTrue(standardBox.addParcel(parcel));

        parcel = new StandardParcel("описание2", 21, "адрес2", 4);
        assertFalse(standardBox.addParcel(parcel));
    }
}
