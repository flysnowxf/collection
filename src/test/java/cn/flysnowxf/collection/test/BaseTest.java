package cn.flysnowxf.collection.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:app*.xml" })
public class BaseTest extends AbstractJUnit4SpringContextTests {
	
}
