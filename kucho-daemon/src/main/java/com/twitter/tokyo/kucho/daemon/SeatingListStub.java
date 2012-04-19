package com.twitter.tokyo.kucho.daemon;

import com.twitter.tokyo.kucho.SeatingList;

import java.util.ArrayList;
import java.util.List;

class SeatingListStub extends SeatingList {
    public SeatingListStub(){
        super(null);
    }

    public List<String> getVentilationModules(String userName) {
        List<String> modules = new ArrayList<String>();
        if ("yusukey".equals(userName)) {
            modules.add("VAV17E-13");
            modules.add("VAV17E-14");
        }
        if ("higepon".equals(userName)) {
            modules.add("VAV17E-13");
            modules.add("VAV17E-14");
        }
        if ("tksohishi".equals(userName)) {
            modules.add("VAV17E-04");
            modules.add("VAV17E-03");
        }
        if ("imsuten".equals(userName)) {
            modules.add("VAV17E-13");
        }
        if ("haru703".equals(userName)) {
            modules.add("VAV17E-14");
        }
        if ("marotan".equals(userName)) {
            modules.add("VAV17E-14");
        }
        if ("josolennoso".equals(userName)) {
            modules.add("VAV17E-08");
            modules.add("VAV17E-07");
        }
        return modules;
    }
}
