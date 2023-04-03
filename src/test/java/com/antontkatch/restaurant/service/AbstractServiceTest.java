package com.antontkatch.restaurant.service;

import com.antontkatch.restaurant.TimingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.antontkatch.restaurant.util.ValidationUtil.getRootCause;

@SpringBootTest
@Transactional
@ExtendWith(TimingExtension.class)
public abstract class AbstractServiceTest {

    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    protected <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable runnable) {
        Assertions.assertThrows(rootExceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }
}