package study.testablespring.mock;

import study.testablespring.common.service.port.ClockHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final long millis;

    @Override
    public long millis() {
        return millis;
    }
}
