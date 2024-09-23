package com.sudasudacode.assistant.utils;

import com.sudasudacode.assistant.prompt.Suggestion;

import java.util.Comparator;

public class SortRestaurantByRating implements Comparator<Suggestion> {

    @Override
    public int compare(Suggestion o1, Suggestion o2) {
        return o2.rating().compareTo(o1.rating());
    }
}
