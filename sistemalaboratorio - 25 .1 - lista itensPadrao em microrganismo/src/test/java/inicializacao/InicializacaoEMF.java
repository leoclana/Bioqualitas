/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package inicializacao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Component
public class InicializacaoEMF {

    //JPA public static EntityManagerFactory emf;
	public static SessionFactory sf;
	
    public InicializacaoEMF() {
    }

    @BeforeClass
    public static void setUpClass() {

        //JPA emf = Persistence.createEntityManagerFactory("bqPersistence");
        sf = (SessionFactory) InicializacaoSpring.ctx.getBean("mySessionFactory");
    	
    }

    @Test
    public void testeDeAcessoAoBanco() throws Exception {
        //JPA EntityManager em = emf.createEntityManager();
        
    	Session s = sf.openSession();
    	
        //JPA String nome = ""+ em
        //JPA         .createNativeQuery("select 1")
        //JPA         .getSingleResult();

        Query q = s.createQuery("Select 1");
        
        q.executeUpdate();
        
        ///JPA if (nome == null) throw new Exception();
        
    }

}