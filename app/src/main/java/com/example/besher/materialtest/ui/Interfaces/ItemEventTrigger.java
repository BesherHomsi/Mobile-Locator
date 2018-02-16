package com.example.besher.materialtest.ui.Interfaces;

import com.example.besher.materialtest.models.SilenceItem;

import java.util.ArrayList;

/**
 * Created by Besher on 02/12/18.
 */

public class ItemEventTrigger {
    public static ArrayList<ItemEvent> listeners = new ArrayList<>();


    public static void addListener(ItemEvent ls) {
        if (!listeners.contains(ls)) {
            listeners.add(ls);
        }
    }

    public static void removeListener(ItemEvent ls) {
        listeners.remove(ls);
    }

    public static void notifyListenerOnNewItem(SilenceItem silenceItem) {
        for (ItemEvent ls : listeners) {
            ls.onNewItem(silenceItem);

        }
    }

    public interface ItemEvent {
        void onNewItem(SilenceItem silenceItem);
    }

}
