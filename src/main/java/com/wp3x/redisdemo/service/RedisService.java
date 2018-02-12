package com.wp3x.redisdemo.service;

import com.wp3x.redisdemo.BatchDTO;
import com.wp3x.redisdemo.JSONMapper;
import lombok.extern.log4j.Log4j;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Log4j
public class RedisService {

    public static final int MAP_SIZE = 2000000;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private JSONMapper jsonMapper;

    @Async
    public void execute() throws InterruptedException {
        RLock lock = redissonClient.getLock( "anyLock" );
        log.info( "Locking execution" );
        boolean res = lock.tryLock( 5, 20, TimeUnit.SECONDS );
        if (res) {
            log.info( "Locked" );
            process();
            lock.unlock();
            log.info( "Unlocking execution" );
        } else {
            log.info( "Skipping" );
        }
    }

    private void process() {
        log.info( "Started execute" );
        long start = System.currentTimeMillis();
        RMap<String, String> map = redissonClient.getMap( "myMap" );
        List<String> keys = new ArrayList<>();
        Long count = 0L;
        int total = map.size();
        for (String key : map.keySet()) {
            keys.add( key );
            count++;
            if (keys.size() == 100 || count == total) {
                String message = jsonMapper.toJson( new BatchDTO( keys ) );
                if (count % 10000 == 0) {
                    System.out.print( "." );
                }

                keys.clear();
            }
        }
        log.info( "Finished execute at " + (System.currentTimeMillis() - start) );
    }

    @Async
    public void populate() {
        log.info( "Started populating" );
        long start = System.currentTimeMillis();

        RMap<String, String> map = redissonClient.getMap( "myMap" );
        for (Integer i = 1; i <= MAP_SIZE; i++) {
            map.put( i.toString(), UUID.randomUUID().toString() );
        }

        log.info( "Finished populating at " + (System.currentTimeMillis() - start) );
    }

    public Integer getSize() {
        log.info( "Started getting size" );
        long start = System.currentTimeMillis();
        int size = redissonClient.getMap( "myMap" ).size();
        log.info( "Finished getting size at " + (System.currentTimeMillis() - start) );
        return size;
    }

    @Async
    public void clear() {
        log.info( "Started getting size" );
        long start = System.currentTimeMillis();
        redissonClient.getMap( "myMap" ).clear();
        log.info( "Finished getting size at " + (System.currentTimeMillis() - start) );
    }
}
