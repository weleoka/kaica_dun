package kaica_dun;

import kaica_dun.Entities.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Deepak Kumar
 * Web: http://www.roseindia.net
 */
public class TestDb {
    public static void main(String[] args) throws Exception {

        SessionFactory sessFact = HibernateUtil.getSessionFactory();
        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();
        Employee emp = new Employee();
        emp.setEmpName("Deepak Kumar");
        emp.setEmpMobileNos("000000");
        emp.setEmpAddress("Delhi - India");
        session.save(emp);
        tr.commit();
        System.out.println("Successfully inserted");
        sessFact.close();
    }

}