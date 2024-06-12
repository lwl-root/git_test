package com.lwl.test;

import com.lwl.dao.UserDao;
import com.lwl.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppTest {
    @Test
    public void test() {
        try {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("classpath:mybatis-config.xml"));
            SqlSession sqlSession = sqlSessionFactory.openSession();
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            List<User> all = mapper.findAll();
            all.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");
        UserDao userDao = applicationContext.getBean(UserDao.class);
        List<User> all = userDao.findAll();
        all.forEach(System.out::println);
    }

    @Test
    public void test3() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date =sdf.parse("1900-01-01 08:00:00");
        System.out.print(sdf.format(date));
    }
}
