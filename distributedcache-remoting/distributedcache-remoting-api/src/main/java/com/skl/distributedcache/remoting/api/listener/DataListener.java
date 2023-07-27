package com.skl.distributedcache.remoting.api.listener;

import com.skl.distributedcache.remoting.api.DataEvent;

/**
 * @author skl
 */
public interface DataListener {
    /**
     * data changed listener
     * @param dataEvent data
     */
    void dataEvent(DataEvent dataEvent);
}
