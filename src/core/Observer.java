package core;

public interface Observer<T> {
    void update(T value);
}