package com.matrix.automation.screen.listener;

import com.matrix.automation.screen.listener.event.ScreenEvent;

public interface ScreenInterceptor {
    void beforeFillField(ScreenEvent event);

    void afterFillField(ScreenEvent event);

    void beforeClick(ScreenEvent event);

    void afterClick(ScreenEvent event);

    void screenChange(ScreenEvent event);

    void screenError(ScreenEvent event);
}
