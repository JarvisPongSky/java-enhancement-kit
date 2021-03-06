package com.pongsky.kit.common.exception;

/**
 * 删除异常
 *
 * @author pengsenhao
 */
public class DeleteException extends RuntimeException {

    private static final long serialVersionUID = 8412329462247258183L;

    public DeleteException(String message) {
        super(message);
    }

    /**
     * 校验删除 SQL
     *
     * @param exceptionMessage 异常信息
     * @param result           结果
     */
    public static void validation(String exceptionMessage, Boolean result) {
        if (!result) {
            throw new DeleteException(exceptionMessage);
        }
    }

    /**
     * 校验删除 SQL
     *
     * @param exceptionMessage 异常信息
     * @param deleteCount      删除总数
     */
    public static void validation(String exceptionMessage, Integer deleteCount) {
        validation(exceptionMessage, deleteCount, 1);
    }

    /**
     * 校验删除 SQL
     *
     * @param exceptionMessage 异常信息
     * @param deleteCount      删除总数
     * @param dataCount        数据总数
     */
    public static void validation(String exceptionMessage, Integer deleteCount, Integer dataCount) {
        if (!deleteCount.equals(dataCount)) {
            throw new DeleteException(exceptionMessage);
        }
    }

}
