package com.tangzhangss.commonservice.menu;

import com.tangzhangss.commonservice.domain.DomainEntity;
import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;

/**
 * 菜单
 */
@Entity
@Table(name = "tbl_common_service_menu")
@Data
public class MenuEntity extends SysBaseEntity {

    // 当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
    private Boolean hidden = false;
    //当设置 noRedirect 的时候该路由在面包屑导航中不可被点击 (仅一级菜单可用， 其他菜单设置会重定向)
    private String redirect = "";//默认跳转自己
    // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
    // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
    // 若你想不管路由下面的 children 声明的个数都显示你的根路由
    // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由-只有下面有孩子节点的需要这个
    private Boolean alwaysShow = true;
    private String title = "";// 设置该路由在侧边栏和面包屑中展示的名字
    private String icon = "";// 设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon
    private Boolean noCache = false; // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    private Boolean breadcrumb = true; //  如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)
    private Boolean affix = false; // 若果设置为true，它则会固定在tags-view中(默认 false)
    // 当路由设置了该属性，则会高亮相对应的侧边栏。
    // 这在某些场景非常有用，比如：一个文章的列表页路由为：/article/list
    // 点击文章进入文章详情页，这时候路由为/article/1，但你想在侧边栏高亮文章列表的路由，就可以进行如下设置
    private String activeMenu = "";
    private String path = "";
    private String name = "";
    private String url = ""; //views目录下的路径 /开头

    private Long parentId;//父角色Id--可以为null

    private int orderNo;//排序序号

    private Long domainId;//服务域名Id

    @OneToOne
    @JoinColumn(name = "parentId", referencedColumnName = "id", insertable = false, updatable = false)
    private MenuEntity parent;

    @OneToOne
    @JoinColumn(name = "domainId", referencedColumnName = "id", insertable = false, updatable = false)
    private DomainEntity domain;

    /*
        系统菜单每一个租户都有，但是非系统菜单只有某一些租户有
     */
    private String clientIds = "";//租户Ids(仅仅当菜单是非系统的时候该值有效)

    @Transient
    private String[] clientIdsArray = new String[]{};

    public String[] getClientIdsArray() {
        if (StringUtils.isNotBlank(clientIds)) {
            this.clientIdsArray = clientIds.split(",");
        }
        return clientIdsArray;
    }

    public void setClientIdsArray(String[] clientIdsArray) {
        this.clientIds = StringUtils.join(clientIdsArray, ",");
        this.clientIdsArray = clientIdsArray;
    }
}
