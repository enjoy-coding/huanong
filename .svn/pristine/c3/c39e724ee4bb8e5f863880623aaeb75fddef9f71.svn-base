package com.feather.smart.config;

import java.security.SecureRandom;
import java.util.Random;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

/**
 * 验证码算式生成器
 * 
 * @author feather
 */
public class KaptchaMathCreator extends DefaultTextCreator {
    @Override
    public String getText() {
        char[] chars = getConfig().getTextProducerCharString();
        int size = chars.length;
        Integer result = 0;
        Random random = new SecureRandom();
        int x = random.nextInt(size - 1);
        int y = random.nextInt(size - 1);
        StringBuilder suChinese = new StringBuilder();
        int randomoperands = (int) Math.round(Math.random() * 2);
        if (randomoperands == 0) {
            result = x * y;
            suChinese.append(chars[x]);
            suChinese.append("*");
            suChinese.append(chars[y]);
        } else if (randomoperands == 1) {
            if (!(x == 0) && y % x == 0) {
                result = y / x;
                suChinese.append(chars[y]);
                suChinese.append("/");
                suChinese.append(chars[x]);
            } else {
                result = x + y;
                suChinese.append(chars[x]);
                suChinese.append("+");
                suChinese.append(chars[y]);
            }
        } else if (randomoperands == 2) {
            if (x >= y) {
                result = x - y;
                suChinese.append(chars[x]);
                suChinese.append("-");
                suChinese.append(chars[y]);
            } else {
                result = y - x;
                suChinese.append(chars[y]);
                suChinese.append("-");
                suChinese.append(chars[x]);
            }
        } else {
            result = x + y;
            suChinese.append(chars[x]);
            suChinese.append("+");
            suChinese.append(chars[y]);
        }
        suChinese.append("=?@" + result);
        return suChinese.toString();
    }
}
