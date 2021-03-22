package ru.nifontbus.notesbook;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderCardAdapter
        extends RecyclerView.Adapter<ViewHolderCardAdapter.ViewHolder> {

    private final static String TAG = "Card Adapter";
    private CardsSource dataSource;
    private fragmentSendDataListener itemClickListener;

    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД
    public ViewHolderCardAdapter(CardsSource dataSource) {
        this.dataSource = dataSource;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public ViewHolderCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Создаём новый элемент пользовательского интерфейса
        // через Inflater
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        Log.d(TAG, "onCreateViewHolder");
        // Здесь можно установить всякие параметры
        return new ViewHolder(v);
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull ViewHolderCardAdapter.ViewHolder viewHolder, int position) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран используя ViewHolder
        viewHolder.setData(dataSource.getCardData(position));

        CardData cardData = dataSource.getCardData(position);
        Note note = new Note(position, cardData.getTitle(), cardData.getDescription());

        viewHolder.title.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.showDetailNote(note);
            }
        });
        Log.d(TAG, "onBindViewHolder POS = " + position);
    }

    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    // Сеттер слушателя нажатий
    public void SetOnItemClickListener(fragmentSendDataListener onFragmentSendDataListener) {
        itemClickListener = onFragmentSendDataListener;
    }


    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на
    // один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        //        private AppCompatImageView image;
        private CheckBox like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            like = itemView.findViewById(R.id.like);
        }

        public void setData(CardData cardData) {
            title.setText(cardData.getTitle());

            String str = cardData.getDescription();
            str = (str.length() < 60) ? str : str.substring(0, 59) + "...";
            description.setText(str);
            like.setChecked(cardData.isLike());
        }
    }
}
