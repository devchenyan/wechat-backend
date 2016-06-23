package tv.caratech.wechat;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import tv.caratech.wechat.bean.Contact;
import tv.caratech.wechat.bean.User;

public class MainSetup implements Setup{
	public void init(NutConfig nc) {
		Ioc ioc = nc.getIoc();
		Dao dao = ioc.get(Dao.class);
		Daos.createTablesInPackage(dao, "tv.caratech.wechat.bean", false);
		
		// 初始化默认根用户
        if (dao.count(User.class) == 0) {
            User user = new User();
            user.setName("admin");
            user.setPassword("123456");
            dao.insert(user);
            
            user.setName("one");
            user.setPassword("123456");
            dao.insert(user);
            
            Contact contact = new Contact();
            contact.setUserId(1);
            contact.setContactId(2);
            dao.insert(contact);
        }
	}
	
	public void destroy(NutConfig nc) {
		// TODO Auto-generated method stub
		
	}
}
