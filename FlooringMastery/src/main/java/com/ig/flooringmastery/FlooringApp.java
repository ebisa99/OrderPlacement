/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery;

import com.ig.flooringmastery.controller.FlooringController;
import com.ig.flooringmastery.dao.*;
import com.ig.flooringmastery.service.*;
import com.ig.flooringmastery.ui.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ebisa
 */
public class FlooringApp {

    public static void main(String[] args) throws OrderPersistenceException, TaxPersistenceException,
            ProductPersistenceException{
        /*
        UserIO io = new UserIOConsoleImpl();
        FlooringView view = new FlooringView(io);
        OrderDao orderDao = new OrderDaoFileImpl();
        ProductDao productDao = new ProductDaoFileImpl();
        TaxDao taxDao = new TaxDaoFileImpl();
        FlooringService service = new FlooringServiceImpl(orderDao, productDao, taxDao);
        FlooringController controller = new FlooringController(view, service);
        controller.run();
        */
        ApplicationContext ctx = 
           new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringController controller = 
           ctx.getBean("controller", FlooringController.class);
        controller.run();
    }
}
