package com.selimkundakcioglu.walletapi.stubbuilder;

import com.hazelcast.cp.CPGroupId;
import com.hazelcast.cp.lock.FencedLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class MockFencedLock implements FencedLock {
    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public long lockAndGetFence() {
        return 0;
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public long tryLockAndGetFence() {
        return 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        return false;
    }

    @Override
    public long tryLockAndGetFence(long time, TimeUnit unit) {
        return 0;
    }

    @Override
    public void unlock() {

    }

    @Override
    public long getFence() {
        return 0;
    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public boolean isLockedByCurrentThread() {
        return false;
    }

    @Override
    public int getLockCount() {
        return 0;
    }

    @Override
    public CPGroupId getGroupId() {
        return null;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public String getPartitionKey() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getServiceName() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
