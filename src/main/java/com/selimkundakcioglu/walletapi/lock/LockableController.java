package com.selimkundakcioglu.walletapi.lock;

import com.hazelcast.cp.lock.FencedLock;

public abstract class LockableController {

    private final LockableService lockableService;

    protected LockableController(LockableService lockableService) {
        this.lockableService = lockableService;
    }

    public FencedLock tryLock(String lockKey, String methodName) {
        return lockableService.tryLock(lockKey, methodName);
    }

    public void unlock(FencedLock lock, String methodName, String lockKey) {
        lockableService.unlock(lock, methodName, lockKey);
    }

}
