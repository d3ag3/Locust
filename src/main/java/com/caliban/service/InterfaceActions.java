package com.caliban.service;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import com.caliban.helper.Randomizer;
import com.github.joonasvali.naturalmouse.api.MouseMotionFactory;
import com.github.joonasvali.naturalmouse.util.FactoryTemplates;

public class InterfaceActions {

    private MouseMotionFactory factory = FactoryTemplates.createFastGamerMotionFactory();
    private Robot robot;
	private Randomizer randomizer;

    public InterfaceActions() {
        randomizer = new Randomizer();

        try {
            robot = new Robot();
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void moveMouse(Rectangle rectangle) {
        int randomX = randomizer.generateRandom(rectangle.x, rectangle.x + rectangle.width);
        int randomY = randomizer.generateRandom(rectangle.y, rectangle.y + rectangle.height);
        moveMouse(randomX, randomY);
    }

    public void moveMouse(int x, int y) {
        try {
            factory.move(x,y);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public void click() {
        robot.delay(randomizer.generateRandom(100, 500));
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(randomizer.generateRandom(100, 200));
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void doubleClick() {
        robot.delay(randomizer.generateRandom(100, 500));
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void rightClick() {
        robot.delay(randomizer.generateRandom(100, 500));
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(randomizer.generateRandom(100, 300));
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    public void hold() {
        robot.delay(randomizer.generateRandom(100, 500));
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(randomizer.generateRandom(100, 200));
    }

    public void release() {
        robot.delay(randomizer.generateRandom(100, 500));
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(randomizer.generateRandom(100, 200));
    }

    public void selectAll() {
        robot.delay(randomizer.generateRandom(100, 500));
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(randomizer.generateRandom(100, 300));
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.delay(randomizer.generateRandom(100, 300));
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public void simulateMouseWiggle()  {
        moveMouse(randomizer.generateRandom(515, 550), randomizer.generateRandom(250, 350));
        moveMouse(randomizer.generateRandom(1158, 1211), randomizer.generateRandom(750, 880));
        moveMouse(randomizer.generateRandom(515, 550), randomizer.generateRandom(250, 350));
        moveMouse(randomizer.generateRandom(1158, 1211), randomizer.generateRandom(750, 880));
	}
	
	public void simulateMouseWheelFiddle() {
        moveMouse(randomizer.generateRandom(1158, 1211), randomizer.generateRandom(750, 880));

        int loop = randomizer.generateRandom(1, 5);

        while (loop < 5) {
            robot.mouseWheel(randomizer.generateRandom(1, 5));
            robot.mouseWheel(-randomizer.generateRandom(1, 5));
            loop++;
        }
	}

    public void simulateWait(int min, int max) {
        robot.delay(randomizer.generateRandom(min, max));
    }

    public void holdControl() {
        robot.keyPress(KeyEvent.VK_CONTROL);
    }

    public void releaseControl() {
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public void pressF1() {
        robot.keyPress(KeyEvent.VK_F1);
        robot.delay(randomizer.generateRandom(100, 500));
        robot.keyRelease(KeyEvent.VK_F1);
        robot.delay(randomizer.generateRandom(100, 500));
    }

    public void pressF2() {
        robot.keyPress(KeyEvent.VK_F2);
        robot.delay(randomizer.generateRandom(100, 500));
        robot.keyRelease(KeyEvent.VK_F2);
        robot.delay(randomizer.generateRandom(100, 500));
    }

    public void dragAndDrop(Rectangle cargoholditem1, Rectangle firstitemfleethanger) {
        moveMouse(cargoholditem1);
        robot.delay(randomizer.generateRandom(100, 300));
        hold();
        robot.delay(randomizer.generateRandom(300, 600));
        moveMouse(firstitemfleethanger);
        robot.delay(randomizer.generateRandom(100, 300));
        release();
        robot.delay(randomizer.generateRandom(300, 600));
    }
}