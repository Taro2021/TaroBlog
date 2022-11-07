package com.taro.constant;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 正常状态
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 禁用状态
     */
    public static final String STATUS_DISABLE = "1";

    /**
     * 友联审核状态 0 表示通过
     */
    public static final int LINK_STATUS_NORMAL = 0;

    /**
     * 文章根评论为 -1
     */
    public static final int ROOT_COMMENT = -1;

    /**
     * 评论类型 0 普通评论， 1 友链评论
     */
    public static final String ARTICLE_COMMENT = "0";
    public static final String LINK_COMMENT = "1";

    /**
     * 头像大小限制 1M
     */
    public static final long AVATAR_SIZE = 1024 * 1024;

    /**
     * 菜单类型
     */
    public static final String MENU = "C";
    public static final String BUTTON = "F";

    /**
     * 用户类型 0 普通用户， 1 后台管理员
     */
    public static final String NORMAL_USER = "0";
    public static final String SYSTEM_ADMIN = "1";
}