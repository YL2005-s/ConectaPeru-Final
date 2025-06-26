package core;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {
    private final List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> o) {
        observers.add(o);
    }

    public void deleteObserver(Observer<T> o) {
        observers.remove(o);
    }

    protected void notifyObserver(T val) {
        for (Observer<T> o : observers) {
            o.update(val);
        }
    }
}