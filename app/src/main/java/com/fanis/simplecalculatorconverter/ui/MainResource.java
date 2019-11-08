package com.fanis.simplecalculatorconverter.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainResource<T> {

    @NonNull
    public final ResponseStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public MainResource(@NonNull ResponseStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> MainResource<T> success(@Nullable T data) {
        return new MainResource<>(ResponseStatus.SUCCESS, data, null);
    }

    public static <T> MainResource<T> error(@NonNull String msg, @Nullable T data) {
        return new MainResource<>(ResponseStatus.ERROR, data, msg);
    }

    public static <T> MainResource<T> loading(@Nullable T data) {
        return new MainResource<>(ResponseStatus.LOADING, data, null);
    }

    public enum ResponseStatus {SUCCESS, ERROR, LOADING}

}