package tv.caratech.wechat.module;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import tv.caratech.wechat.bean.User;

@IocBean
@At("/user")
@Ok("json")
@Fail("http:500")

public class UserModule {
	
	@Inject Dao dao;
	
	@At
	public int count() {
		return dao.count(User.class);
	}
	
	
	@At
	public Object login(@Param("name") String name, @Param("password") String password) {
		User user = dao.fetch(User.class, Cnd.where("name", "=", name).and("password", "=", password));
		if (user == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@At
	public Object add(@Param("..") User user){
		NutMap map = new NutMap();
		String msg = checkUser(user, true);
		if (msg != null) {
			return map.setv("ok", false).setv("msg", msg);
		}
		
		user = dao.insert(user);
		return map.setv("ok", true).setv("msg", user);
	}
	
	protected String checkUser(User user, boolean create) {
        if (user == null) {
            return "空对象";
        }
        if (create) {
            if (Strings.isBlank(user.getName()) || Strings.isBlank(user.getPassword()))
                return "用户名/密码不能为空";
        } else {
            if (Strings.isBlank(user.getPassword()))
                return "密码不能为空";
        }
        String passwd = user.getPassword().trim();
        if (6 > passwd.length() || passwd.length() > 12) {
            return "密码长度错误";
        }
        user.setPassword(passwd);
        if (create) {
            int count = dao.count(User.class, Cnd.where("name", "=", user.getName()));
            if (count != 0) {
                return "用户名已经存在";
            }
        } else {
            if (user.getId() < 1) {
                return "用户Id非法";
            }
        }
        if (user.getName() != null)
            user.setName(user.getName().trim());
        return null;
    }

}
