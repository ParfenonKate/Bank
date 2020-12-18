package sample.Logic;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Model {

    Class<? extends Deposit> depositFilter = Deposit.class;

    ArrayList<Deposit> depositList = new ArrayList<>();
    public int counter = 1;

    public interface DataChangedListener {
        void dataChanged(ArrayList<Deposit> deposits);
    }

    private ArrayList<DataChangedListener> dataChangedListeners = new ArrayList<>();
    public void addDataChangedListener(DataChangedListener listener) {

        this.dataChangedListeners.add(listener);
    }
    public void setDrinksFilter(Class<? extends Deposit> depositFilter) {
        this.depositFilter = depositFilter;
        this.emitDataChanged();
    }


    public void add(Deposit deposit, boolean emit) {
        deposit.id = counter;
        counter += 1;

        this.depositList.add(deposit);

        if (emit) {
            this.emitDataChanged();
        }
    }

    public void add(Deposit deposit) {
        add(deposit, true);
    }

    private void emitDataChanged() {
        for (DataChangedListener listener : dataChangedListeners) {
            ArrayList<Deposit> filteredList = new ArrayList<>(
                    depositList.stream()
                            .filter(food -> depositFilter.isInstance(food))
                            .collect(Collectors.toList())
            );
            listener.dataChanged(filteredList);
        }
    }



    public void saveToFile(String path) {
        try (Writer writer =  new FileWriter(path)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerFor(new TypeReference<ArrayList<Deposit>>() { }) // указали какой тип подсовываем
                    .withDefaultPrettyPrinter() // кстати эта строчка чтобы в файлике все красиво печаталось
                    .writeValue(writer, depositList); // а это непосредственно запись
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String path) {
        try (Reader reader =  new FileReader(path)) {
            // создаем сериализатор
            ObjectMapper mapper = new ObjectMapper();

            // читаем из файла
            depositList = mapper.readerFor(new TypeReference<ArrayList<Deposit>>() { })
                    .readValue(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // оповещаем что данные загрузились
        this.emitDataChanged();
    }




}
