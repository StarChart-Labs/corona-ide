package com.coronaide.test.ui;

import org.junit.Before;
import org.junit.Test;

import com.coronaide.ui.CoronaUIApplication;

public class AboutDialogTest extends CoronaUITest {

    @Before
    public void setup() throws Exception {
        launch(CoronaUIApplication.class);
    }

    @Test
    public void showAboutTest() {
        clickOn("#menu-corona").clickOn("#menu-corona-about");
        clickOn("OK");
    }

}
