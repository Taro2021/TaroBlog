package com.taro.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
     PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_NOT_NULL(506,"评论不能为空"),
    FILE_TYPE_ERROR (507,"图片格式错误"),
    FILE_SIZE_ERROR(508,"图片大小错误"),
    USERNAME_NOT_NULL(509,"用户名不能为空"),
    PASSWORD_NOT_NULL(510,"密码不能为空"),
    EMAIL_NOT_NULL(510,"邮箱不能为空"),
    NICKNAME_NOT_NULL(510,"昵称不能为空"),
    NICKNAME_EXIST(511,"昵称已存在"),
    LINK_ID_NOT_NULL(512, "友链id不能为空"),
    DELETE_EXIT_CHILDREN(513, "存在子菜单不允许删除"),
    ROLE_ID_NOT_NULL(514, "角色id不能为空"),
    STATUS_ERR(515, "不合法的状态码"),
    TAG_NAME_NOT_NULL(516, "标签名不能为空"),
    TAG_REMARK_NOT_NULL(517, "标签备注不能为空"),
    LINK_NAME_NOT_NULL(518, "友链名不能为空"),
    LINK_ADDRESS_NOT_NULL(519,"友链地址不能为空"),
    LINK_DESCRIPTION_NOT_NULL(519,"友链描述不能为空"),
    LINK_STATUS_ILLEGAL(520, "友链状态不合法"),
    CATEGORY_NAME_NOT_NULL(521, "分类名不能为空"),
    CATEGORY_DESCRIPTION_NOT_NULL(522, "分类描述不能为空"),
    CATEGORY_STATUS_ILLEGAL(523, "分类状态不合法"),
    ARTICLE_TITLE_NOT_NULL(524, "文章标题不能为空"),
    ARTICLE_SUMMARY_NOT_NULL(525, "文章摘要不能为空"),
    ARTICLE_STATUS_ILLEGAL(526, "文章状态不合法"),
    ARTICLE_IS_TOP_ILLEGAL(527, "文章置顶状态不合法"),
    ARTICLE_IS_COMMENT_ILLEGAL(528, "文章允许评论状态不合法"),
    SYS_MENU_FORMAT_ILLEGAL(529, "菜单格式不合法"),
    SYS_ROLE_FORMAT_ILLEGAL(530, "系统角色信息格式不合法"),
    ARTICLE_CATEGORY_NOT_NULL(531, "文章未分类");

    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
