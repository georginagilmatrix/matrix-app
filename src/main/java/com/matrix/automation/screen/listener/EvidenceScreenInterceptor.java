package com.matrix.automation.screen.listener;

import com.matrix.automation.screen.listener.event.ScreenEvent;

public class EvidenceScreenInterceptor implements ScreenInterceptor {

    private String fileName;
    private boolean field;
    private boolean click;
    private boolean page;
    private String evidenceDirectory;

    public EvidenceScreenInterceptor(String fileName, boolean field, boolean click, boolean page, String evidenceDir) {
        this.fileName = fileName;
        this.field = field;
        this.click = click;
        this.page = page;
        evidenceDirectory = evidenceDir;
    }

    @Override
    public void beforeFillField(ScreenEvent event) {

    }

    @Override
    public void afterFillField(ScreenEvent event) {

    }

    @Override
    public void beforeClick(ScreenEvent event) {

    }

    @Override
    public void afterClick(ScreenEvent event) {

    }

    @Override
    public void screenChange(ScreenEvent event) {

    }

    @Override
    public void screenError(ScreenEvent event) {

    }
}
