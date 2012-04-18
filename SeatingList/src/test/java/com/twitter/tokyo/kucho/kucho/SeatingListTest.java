package com.twitter.tokyo.kucho.kucho;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.twitter.tokyo.kucho.SeatingList;
import com.twitter.tokyo.kucho.VentilationModule;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Ken Kawamoto
 */
public class SeatingListTest {
  private SeatingList seatingList;
  private final String userName = "kentaro";

  @Before
  public void setUp() {
    seatingList = new SeatingList();
  }

  @Test
  public void testMain() {
    String[] args = {
        "kentaro"
    };
    SeatingList.main(args);
  }

  @Test
  public void testGetVentilationModule() throws Exception {
    List<VentilationModule> modules = seatingList.getVentilationModules(userName);
    assertNotNull(modules);
    assertEquals(2, modules.size());
    VentilationModule vm0 = modules.get(0);
    assertNotNull(vm0);
    assertEquals("E-13", vm0.getName());

    VentilationModule vm1 = modules.get(1);
    assertNotNull(vm1);
    assertEquals("E-14", vm1.getName());
  }



}
