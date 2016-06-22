package tv.caratech.wechat;

import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

/*
一个 Mvc 框架可以通过 Ioc接口 同一个 Ioc容器 挂接，挂接的方法很简单： 在主模块上声明 @IocBy

ComboIocProvider的args参数, 星号开头的是类名或内置缩写,剩余的是各加载器的参数
*js 是JsonIocLoader,负责加载js/json结尾的ioc配置文件
*anno 是AnnotationIocLoader,负责处理注解式Ioc, 例如@IocBean
*tx 是TransIocLoader,负责加载内置的事务拦截器定义, 1.b.52开始自带
*/
@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/",
        "*anno", "cn.jdworks.wechat",
        "*tx"})

@Modules(scanPackage = true)
public class MainModule {

}
