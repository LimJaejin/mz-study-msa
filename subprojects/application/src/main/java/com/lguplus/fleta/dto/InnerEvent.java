package com.lguplus.fleta.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
@ToString
public class InnerEvent<T> implements Serializable {

    private final String type;
    private final T dto;

}
