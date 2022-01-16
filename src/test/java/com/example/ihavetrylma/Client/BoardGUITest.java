package com.example.ihavetrylma.Client;

import javafx.application.Platform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class BoardGUITest {

    @Test
    public void heightTest() {
        BoardGUI boardGUI = new BoardGUI();
        boardGUI.calculateHeight(4);
        Assertions.assertEquals(13, boardGUI.getHeight());

        boardGUI.calculateHeight(5);
        Assertions.assertEquals(17, boardGUI.getHeight());

        boardGUI.calculateHeight(6);
        Assertions.assertEquals(21, boardGUI.getHeight());
    }

    @Test
    public void widthTest(){
        BoardGUI boardGUI = new BoardGUI();
        boardGUI.calculateWidth(4);
        Assertions.assertEquals(19, boardGUI.getWidth());

        boardGUI.calculateWidth(5);
        Assertions.assertEquals(25, boardGUI.getWidth());

        boardGUI.calculateWidth(6);
        Assertions.assertEquals(31, boardGUI.getWidth());
    }

    @Test
    public void createBoardTest() throws InterruptedException {
        final BoardGUI boardGUI = new BoardGUI();
        Platform.startup(() -> boardGUI.createBoard());
        CountDownLatch cdl = new CountDownLatch(1);
        Platform.runLater(cdl::countDown);
        cdl.await();
    }

}