package tv.caratech.wechat.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_contact")
public class Contact {

	@Id
	private int id;
	@Column
	private int userId;
	@Column
	private int contactId;
	
}
