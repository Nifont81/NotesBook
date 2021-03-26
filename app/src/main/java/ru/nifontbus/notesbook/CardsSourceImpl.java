package ru.nifontbus.notesbook;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class CardsSourceImpl implements CardsSource {
    private volatile static CardsSourceImpl sInstance;
    private List<CardData> dataSource = new LinkedList<>();
    private Resources resources;    // ресурсы приложения

    public static CardsSourceImpl getInstance(Resources resources) {
        CardsSourceImpl instance = sInstance;
        if (instance == null) {
            synchronized (CardsSourceImpl.class) {
                if (sInstance == null) {
                    instance = new CardsSourceImpl(resources);
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    public CardsSourceImpl(Resources resources) {
        this.resources = resources;
        //initRes();
    }

    public CardsSource init(CardsSourceResponse cardsSourceResponse){
        // строки заголовков из ресурсов
        String[] titles = resources.getStringArray(R.array.note_titles);
        // строки описаний из ресурсов
        String[] descriptions = resources.getStringArray(R.array.note_descriptions);
        // изображения
        int[] pictures = getImageArray();
        // заполнение источника данных
        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new CardData(i, titles[i], descriptions[i],
                    false, Calendar.getInstance().getTime(), pictures[i]));
        }

        if (cardsSourceResponse != null){
            cardsSourceResponse.initialized(this);
        }

        return this;
    }

    // Механизм вытаскивания идентификаторов картинок
    // https://stackoverflow.com/questions/5347107/creating-integer-array-of-resource-ids
    private int[] getImageArray(){
        TypedArray pictures = resources.obtainTypedArray(R.array.note_imgs);
        int length = pictures.length();
        int[] answer = new int[length];
        for(int i = 0; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }

    public CardData getCardData(int position) {
        if (position>=0) {
            return dataSource.get(position);
        }
        else
            return null;
    }

    public int size(){
        return dataSource.size();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(CardData cardData) {
        dataSource.set(cardData.getPos(), cardData);
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }

}
