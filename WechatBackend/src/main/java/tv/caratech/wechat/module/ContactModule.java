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
import org.nutz.mvc.annotation.GET;
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
	
	@At
	public Object add(@Param("userid") int userId, @Param("contactid") int contactId) {
		NutMap map = new NutMap();
		String msg = checkContact(userId, contactId);
		if (msg != null) {
			return map.setv("ok", false).setv("msg", msg);
		}
		
		Contact contact = new Contact();
		contact.setUserId(userId);
		contact.setContactId(contactId);
		
		dao.insert(contact);
		return map.setv("ok", true).setv("msg", "");
	}
	
	private String checkContact(int userId, int contactId) {
		User cUser = dao.fetch(User.class, Cnd.where("id", "=", contactId));
		
		if (cUser != null) {
			Contact contact = dao.fetch(Contact.class, Cnd.
					where("userId", "=", userId).
					and("contactId", "=", contactId));
			if (contact == null) {
				return null;
			}else {
				return "好友已在联系人列表中";
			}
		}else {
			return "该好友用户id不存在";
		}
		
	}
	
//	@At("/?")
//	@GET
//	public Object getContacts(int userId) {
//		List<Contact> contacts = dao.query(Contact.class, Cnd.where("userId", "=", userId));
//		
//		if (contacts != null) {
//			List<NutMap> maps = new LinkedList<NutMap>();
//
//			for (Contact contact : contacts) {
//				int cId = contact.getContactId();
//				User cUser = dao.fetch(User.class, Cnd.where("id", "=", cId));
//				NutMap map = new NutMap();
//				map.setv("userId", cId).setv("name", cUser.getName());
//				maps.add(map);
//			}
//			return maps;
//		}
//		return null;
//	}
	
	@At
	public Object list(@Param("userid") int userId) {
		List<Contact> contacts = dao.query(Contact.class, Cnd.where("userId", "=", userId));
		
		if (contacts != null) {
			List<NutMap> mapList = new LinkedList<NutMap>();

			for (Contact contact : contacts) {
				int cId = contact.getContactId();
				User cUser = dao.fetch(User.class, Cnd.where("id", "=", cId));
				NutMap map = new NutMap();
				map.setv("userId", cId).setv("name", cUser.getName());
				mapList.add(map);
			}
			return mapList;
		}
		return null;
	}
	
}
