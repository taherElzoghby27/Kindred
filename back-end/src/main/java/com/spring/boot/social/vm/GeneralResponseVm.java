package com.spring.boot.social.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class GeneralResponseVm<T> {
    private List<T> data;
    private int page;
    private int size;
}
