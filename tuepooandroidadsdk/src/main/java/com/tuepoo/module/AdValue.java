package com.tuepoo.module;

import com.tuepoo.module.monitor.Monitor;
import com.tuepoo.module.monitor.emevent.EMEvent;

import java.util.ArrayList;

/**

 * @function: 广告json value节点
 */
public class AdValue {

    public String resourceID;
    public String adid;
    public String resource;
    public String thumb;
    public ArrayList<Monitor> startMonitor;
    public ArrayList<Monitor> middleMonitor;
    public ArrayList<Monitor> endMonitor;
    public String clickUrl;
    public ArrayList<Monitor> clickMonitor;
    public EMEvent event;
    public String type;
}
