package com.pongsky.kit.sms.fail;

import com.pongsky.kit.common.global.response.processor.fail.BaseFailProcessor;
import com.pongsky.kit.sms.exception.AliyunSmsClientException;

/**
 * 短信连接异常处理器
 *
 * @author pengsenhao
 */
public class AliyunOssClientExceptionFailProcessor implements BaseFailProcessor<AliyunSmsClientException> {

    @Override
    public Integer code() {
        return 511;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return exception.getClass() == AliyunSmsClientException.class;
    }

}
