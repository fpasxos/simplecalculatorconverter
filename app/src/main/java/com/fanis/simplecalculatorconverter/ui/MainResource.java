package com.fanis.simplecalculatorconverter.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainResource<T> {

    @NonNull
    public final AuthStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public MainResource(@NonNull AuthStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> MainResource<T> success (@Nullable T data) {
        return new MainResource<>(AuthStatus.SUCCESS, data, null);
    }

    public static <T> MainResource<T> error(@NonNull String msg, @Nullable T data) {
        return new MainResource<>(AuthStatus.ERROR, data, msg);
    }

    public static <T> MainResource<T> loading(@Nullable T data) {
        return new MainResource<>(AuthStatus.LOADING, data, null);
    }


    public enum AuthStatus { SUCCESS, ERROR, LOADING}

}