package com.spring.boot.social.dto;

import com.spring.boot.social.entity.BundleMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {
    private int status;
    private BundleMessage bundleMessage;
    private String reason;
}
