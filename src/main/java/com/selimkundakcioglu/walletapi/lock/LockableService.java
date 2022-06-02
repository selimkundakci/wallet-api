package com.selimkundakcioglu.walletapi.lock;

import com.selimkundakcioglu.walletapi.exception.LockException;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.lock.FencedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockableService {
    private final HazelcastInstance hazelcastInstance;

    public FencedLock tryLock(String lockKey, String methodName) {
        FencedLock lock = hazelcastInstance.getCPSubsystem().getLock(lockKey);
        try {
            boolean result = lock.tryLock();
            if (result) {
                return lock;
            }
        } catch (Exception exception) {
            log.error("tryLockError => lockKey:{}, methodName:{}", lockKey, methodName);
            exception.printStackTrace();
        }
        log.error("alreadyLocked => lockKey:{}, methodName:{}", lockKey, methodName);
        throw new LockException("lock failed for lockKey:" + lockKey + ", methodName:" + methodName);
    }

    public void unlock(FencedLock lock, String methodName, String lockKey) {
        try {
            if (lock == null) {
                log.warn("alreadyUnlocked => lockKey:{}, methodName:{}", lockKey, methodName);
            } else {
                lock.unlock();
            }
        } catch (Exception exception) {
            log.error("unlockError => lockKey:{}, methodName:{}", lock != null ? lock.getName() : null, methodName);
            exception.printStackTrace();
        }
    }


}
