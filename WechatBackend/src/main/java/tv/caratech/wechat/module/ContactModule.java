package tv.caratech.wechat.module;

import java.util.LinkedList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import tv.caratech.wechat.bean.Contact;
import tv.caratech.wechat.bean.User;


@IocBean
@At("/contact")
@Ok("json")
@Fail("http:500")

public class ContactModule {
	
	@Inject Dao dao;
	
//	@At
//	public Object add(@Param("userid") int userId, @Param("contactid") int contactId) {
//		
//	}
	
//	public Object add(@Param("..") User user){
//		NutMap map = new NutMap();
//		String msg = checkUser(user, true);
//		if (msg != null) {
//			return map.setv("ok", false).setv("msg", msg);
//		}
//		
//		user = dao.insert(user);
//		return map.setv("ok", true).setv("msg", user);
//	}
	
	@At
	public Object list(@Param("userid") int userId) {
		List<Contact> contacts = dao.query(Contact.class, Cnd.where("userId", "=", userId));
		
		if (contacts != null) {
			List<NutMap> maps = new LinkedList<NutMap>();

			for (Contact contact : contacts) {
				int cId = contact.getContactId();
				User cUser = dao.fetch(User.class, Cnd.where("id", "=", cId));
				NutMap map = new NutMap();
				map.setv("userId", cId).setv("name", cUser.getName());
				maps.add(map);
			}
			return maps;
		}
		return null;
	}
	
	@At
	public Object count() {
		int count = dao.count(Contact.class);
		return count;
	}
}
