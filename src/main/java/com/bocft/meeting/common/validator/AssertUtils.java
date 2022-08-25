/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.bocft.meeting.common.validator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bocft.meeting.common.ConferenceException;
import com.bocft.meeting.common.exception.ErrorCode;


import java.util.List;
import java.util.Map;

/**
 * 校验工具类
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
public class AssertUtils {

    public static void isBlank(String str, String... params) {
        isBlank(str, ErrorCode.NOT_NULL, params);
    }

    public static void isBlank(String str, Integer code, String... params) {
        if (code == null) {
            throw new ConferenceException("code", ErrorCode.NOT_NULL);
        }

        if (StringUtils.isBlank(str)) {
            throw new ConferenceException(code, params);
        }
    }

    public static void isNull(Object object, String... params) {
        isNull(object, ErrorCode.NOT_NULL, params);
    }

    public static void isNull(Object object, Integer code, String... params) {
        if (code == null) {
            throw new ConferenceException("code", ErrorCode.NOT_NULL);
        }

        if (object == null) {
            throw new ConferenceException(code, params);
        }
    }

    public static void isArrayEmpty(Object[] array, String... params) {
        isArrayEmpty(array, ErrorCode.NOT_NULL, params);
    }

    public static void isArrayEmpty(Object[] array, Integer code, String... params) {
        if (code == null) {
            throw new ConferenceException("code", ErrorCode.NOT_NULL);
        }

        if (ArrayUtil.isEmpty(array)) {
            throw new ConferenceException(code, params);
        }
    }

    public static void isListEmpty(List<?> list, String... params) {
        isListEmpty(list, ErrorCode.NOT_NULL, params);
    }

    public static void isListEmpty(List<?> list, Integer code, String... params) {
        if (code == null) {
            throw new ConferenceException("code", ErrorCode.NOT_NULL);
        }

        if (CollUtil.isEmpty(list)) {
            throw new ConferenceException(code, params);
        }
    }

    public static void isMapEmpty(Map map, String... params) {
        isMapEmpty(map, ErrorCode.NOT_NULL, params);
    }

    public static void isMapEmpty(Map map, Integer code, String... params) {
        if (code == null) {
            throw new ConferenceException("code", ErrorCode.NOT_NULL);
        }

        if (MapUtil.isEmpty(map)) {
            throw new ConferenceException(code, params);
        }
    }
}