package eu.activelogic.android.exu1;

public interface MessageConsumer<T> {
    public void consumeMessage(T message);
}