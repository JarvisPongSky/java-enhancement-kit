package com.pongsky.kit.global.response.handler.processor.fail;

import com.pongsky.kit.common.global.response.processor.fail.BaseFailProcessor;

/**
 * 默认【失败】全局响应处理器
 *
 * @author pengsenhao
 */
public class DefaultFailProcessor implements BaseFailProcessor<Throwable> {

    @Override
    public Integer code() {
        return 1000;
    }

    @Override
    public boolean isSprintStackTrace() {
        return true;
    }

    @Override
    public boolean isHitProcessor(Throwable exception) {
        return true;
    }

    @Override
    public int order() {
        return -1;
    }

}
