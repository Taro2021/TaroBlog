package com.taro.utils;

import com.taro.constant.SystemConstants;
import io.jsonwebtoken.lang.Strings;

import java.util.Objects;

/**
 * ClassName StatusCheckUtils
 * Author taro
 * Date 2022/11/9 14:50
 * Version 1.0
 */

public class StatusCheckUtils {

    private StatusCheckUtils(){};
    /**
     * 检验状态是否非法
     * @param status
     * @return
     */
    public static boolean statusIllegal(String status) {
        return !Strings.hasText(status) ||
                (!SystemConstants.STATUS_NORMAL.equals(status) && !SystemConstants.STATUS_DISABLE.equals(status));
    }
}
