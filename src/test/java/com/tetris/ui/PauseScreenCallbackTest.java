//package com.tetris.ui;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class PauseScreenCallbackTest {
//
//    private boolean onResumeGameCalled = false;
//    private boolean onHideFrameCalled = false;
//
//    @Test
//    void onResumeGame() {
//        // Create an instance of PauseScreenCallback and call onResumeGame
//        PauseScreenCallback callback = new PauseScreenCallback() {
//            @Override
//            public void onResumeGame() {
//                onResumeGameCalled = true;
//            }
//
//            @Override
//            public void onHideFrame() {
//                // No implementation needed for this test
//            }
//        };
//
//        // Call onResumeGame method
//        callback.onResumeGame();
//
//        // Assert that onResumeGame was called
//        assertTrue(onResumeGameCalled);
//    }
//
//    @Test
//    void onHideFrame() {
//        // Create an instance of PauseScreenCallback and call onHideFrame
//        PauseScreenCallback callback = new PauseScreenCallback() {
//            @Override
//            public void onResumeGame() {
//                // No implementation needed for this test
//            }
//
//            @Override
//            public void onHideFrame() {
//                onHideFrameCalled = true;
//            }
//        };
//
//        // Call onHideFrame method
//        callback.onHideFrame();
//
//        // Assert that onHideFrame was called
//        assertTrue(onHideFrameCalled);
//    }
//}
