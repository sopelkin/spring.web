package edu.sibinfo.spring.web.module03.service;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes=ClientServiceCustomContextTestConfig.class)
public class ClientServiceSearchTest {
	
	@Autowired
    private ClientService service;
    
	@Before
	public void init() {
		service.register("Luke", "Williams", "+79239889523");
		service.register("John", "Smith", "+79132354313");
		service.register("Sam", "Williams", "+79053495805");
		
		service.register("Joel", "Williams", "+79069409806");
		service.register("Lucas", "Williams", "+79074452307");
		service.register("Sawyer", "Smith", "+79089872308");
		
		service.register("Luke", "Smith", "+79239000023");
		service.register("John", "Williams", "+79132000013");
		service.register("Sam", "Smith", "+79053400005");
		
		service.register("Joel", "Smith", "+79069450006");
		service.register("Lucas", "Williams", "+79074470007");
		service.register("Sawyer", "Williams", "+79089860008");	
	}
	
    @Test
    public void searchAllSmiths() {
    	assertEquals(5, count(service.search("Smi")));
    }

    @Test
    public void searchSaSmiths() {
    	assertEquals(2, count(service.search("Smi Sa")));
    }

    @Test
    public void searchJos() {
    	assertEquals(4, count(service.search("i o")));
    }

    @Test
    public void searchOne() {
    	assertEquals(1, count(service.search("ms oe")));
    }

    @Test
    public void searchNoOne() {
    	assertEquals(0, count(service.search("th W")));
    }

	private int count(Iterable<?> result) {
		int cnt = 0;
		for (Iterator<?> iterator = result.iterator(); iterator.hasNext(); iterator.next()) {
			++cnt;
		}
		return cnt;
	}
}
