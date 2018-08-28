package com.baemin.nanumchan.domain;

import java.time.LocalDateTime;

public interface DateTimeExpirable {

    public LocalDateTime getStartDateTime();

    public LocalDateTime getEndDateTime();

}
