package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {
    @NotNull
    private Status status;
}
