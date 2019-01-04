package com.wonder.bring.owner.model;

import com.wonder.bring.owner.utils.Message;
import com.wonder.bring.owner.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class DefaultRes<T> {

    private int status;

    private String message;

    private T data;

    public DefaultRes(final int status, final String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public static<T> DefaultRes<T> res(final int status, final String message) {
        return res(status, message, null);
    }

    public static<T> DefaultRes<T> res(final int status, final String message, final T t) {
        return DefaultRes.<T>builder()
                .data(t)
                .status(status)
                .message(message)
                .build();
    }

    public static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(Status.INTERNAL_SERVER_ERROR, Message.INTERNAL_SERVER_ERROR);
}
