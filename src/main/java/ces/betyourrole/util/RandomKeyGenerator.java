package ces.betyourrole.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomKeyGenerator {
    public static String generateRandomKey() {
        return RandomStringUtils.randomAlphanumeric(8); // Base62로 랜덤 키 생성
    }
}