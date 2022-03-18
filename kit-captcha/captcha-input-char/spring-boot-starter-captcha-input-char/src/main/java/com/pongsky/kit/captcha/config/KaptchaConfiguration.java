package com.pongsky.kit.captcha.config;

import com.pongsky.kit.captcha.utils.KaptchaUtils;
import org.springframework.context.annotation.Bean;


/**
 * 输入型字符 验证码 配置
 *
 * @author pengsenhao
 */
public class KaptchaConfiguration {

    /**
     * 创建验证码工具类
     *
     * @param properties 验证码参数配置
     * @return 验证码工具类
     */
    @Bean
    public KaptchaUtils kaptchaUtils(KaptchaProperties properties) {
        return new KaptchaUtils(properties.getCodeNum(),
                properties.getCodeNum() * properties.getCodeWidthSpace(),
                properties.getImageHeight(),
                properties.getDrawCount(), properties.getLineWidth());
    }

}
