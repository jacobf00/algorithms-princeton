/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    // @Test public void appHasAGreeting() {
    //     App classUnderTest = new App();
    //     assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    // }
    @Test public void testCoordToN() {
        Percolation perc = new Percolation(20);
        assertEquals(1, perc.coordToN(1,1));
        assertEquals((20*20), perc.coordToN(20,20));
    }
}
