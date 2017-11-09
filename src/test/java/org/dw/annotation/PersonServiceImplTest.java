package org.dw.annotation;

import org.dw.annotation.service.PersonService;
import org.junit.Test;

/**
 * Created by daiwei on 2017/11/9.
 */
public class PersonServiceImplTest {
    @Test
    public void testSave() {
        ClassPathXMLApplicationContext ctx = new ClassPathXMLApplicationContext("beans.xml");
        PersonService personService = (PersonService)ctx.getBean("personService");
        personService.save();
    }
}
