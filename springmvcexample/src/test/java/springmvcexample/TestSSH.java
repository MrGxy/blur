package springmvcexample;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.howtodoinjava.demo.model.EmployeeVO;

public class TestSSH {

	
	
	private ApplicationContext ctx=null;
	
	@Test
	public void testDataSource() throws SQLException{
		//测试数据源
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println(ctx);
		DataSource datasource =ctx.getBean(DataSource.class);
		System.out.println(datasource);
		System.out.println(datasource.getConnection().toString());
		//测试sessionfactory
		SessionFactory sessionfactory=ctx.getBean(SessionFactory.class);
		System.out.println(sessionfactory);
		
		
		Session session= sessionfactory.openSession();
		Transaction tx=session.beginTransaction();
		
		EmployeeVO evo=new EmployeeVO("2017","起飞");
		
		session.save(evo);
		tx.commit();
		session.close();
		
		
	}
}
