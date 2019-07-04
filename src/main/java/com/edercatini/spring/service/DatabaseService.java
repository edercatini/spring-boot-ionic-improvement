package com.edercatini.spring.service;

import com.edercatini.spring.model.*;
import com.edercatini.spring.repository.*;
import com.edercatini.spring.utils.CryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.edercatini.spring.enums.CustomerTypes.PHYSICAL_PERSON;
import static com.edercatini.spring.enums.PaymentStatus.PAID;
import static com.edercatini.spring.enums.PaymentStatus.PENDING;
import static java.util.Arrays.asList;

@Service
public class DatabaseService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    public void run() throws ParseException {
        Category cat1 = new Category("Informática");
        Category cat2 = new Category("Escritório");
        Category cat3 = new Category("Cama mesa e banho");
        Category cat4 = new Category("Eletrônicos");
        Category cat5 = new Category("Jardinagem");
        Category cat6 = new Category("Decoração");
        Category cat7 = new Category("Perfumaria");

        Product p1 = new Product("Computador", 2000.00);
        Product p2 = new Product("Impressora", 800.00);
        Product p3 = new Product("Mouse", 80.00);
        Product p4 = new Product("Mesa de escritório", 300.00);
        Product p5 = new Product("Toalha", 50.00);
        Product p6 = new Product("Colcha", 200.00);
        Product p7 = new Product("TV true color", 1200.00);
        Product p8 = new Product("Roçadeira", 800.00);
        Product p9 = new Product("Abajour", 100.00);
        Product p10 = new Product("Pendente", 180.00);
        Product p11 = new Product("Shampoo", 90.00);

        cat1.getProducts().addAll(asList(p1, p2, p3));
        cat2.getProducts().addAll(asList(p2));

        p1.getCategories().addAll(asList(cat1));
        p2.getCategories().addAll(asList(cat1, cat2));
        p3.getCategories().addAll(asList(cat1));
        cat2.getProducts().addAll(asList(p2, p4));
        cat3.getProducts().addAll(asList(p5, p6));
        cat4.getProducts().addAll(asList(p1, p2, p3, p7));
        cat5.getProducts().addAll(asList(p8));
        cat6.getProducts().addAll(asList(p9, p10));
        cat7.getProducts().addAll(asList(p11));

        p1.getCategories().addAll(asList(cat1, cat4));
        p2.getCategories().addAll(asList(cat1, cat2, cat4));
        p3.getCategories().addAll(asList(cat1, cat4));
        p4.getCategories().addAll(asList(cat2));
        p5.getCategories().addAll(asList(cat3));
        p6.getCategories().addAll(asList(cat3));
        p7.getCategories().addAll(asList(cat4));
        p8.getCategories().addAll(asList(cat5));
        p9.getCategories().addAll(asList(cat6));
        p10.getCategories().addAll(asList(cat6));
        p11.getCategories().addAll(asList(cat7));

        categoryRepository.saveAll(asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        productRepository.saveAll(asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

        State est1 = new State("Minas Gerais");
        State est2 = new State("São Paulo");

        City c1 = new City("Uberlândia", est1);
        City c2 = new City("São Paulo", est2);
        City c3 = new City("Campinas", est2);

        est1.getCities().addAll(asList(c1));
        est2.getCities().addAll(asList(c2, c3));

        stateRepository.saveAll(asList(est1, est2));
        cityRepository.saveAll(asList(c1, c2, c3));

        Customer cli1 = new Customer("Maria Silva", "maria@gmail.com", "36378912377", PHYSICAL_PERSON, CryptUtils.encrypt("test"));

        cli1.getPhones().addAll(asList("27363323", "93838393"));

        Address e1 = new Address("Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
        Address e2 = new Address("Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

        cli1.getAddresses().addAll(asList(e1, e2));

        customerRepository.saveAll(asList(cli1));
        addressRepository.saveAll(asList(e1, e2));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Purchase ped1 = new Purchase(sdf.parse("2017-09-30 10:32"), e1, cli1);
        Purchase ped2 = new Purchase(sdf.parse("2017-10-10 19:35"), e2, cli1);

        Payment pagto1 = new CreditCardPayment(PAID, ped1, 6);
        ped1.setPayment(pagto1);

        Payment pagto2 = new BankSlipPayment(PENDING, ped2, sdf.parse("2017-10-20 00:00"), null);
        ped2.setPayment(pagto2);

        cli1.getPurchases().addAll(asList(ped1, ped2));

        purchaseRepository.saveAll(asList(ped1, ped2));
        paymentRepository.saveAll(asList(pagto1, pagto2));

        PurchaseItem ip1 = new PurchaseItem(ped1, p1, 0.00, 1, 2000.00);
        PurchaseItem ip2 = new PurchaseItem(ped1, p3, 0.00, 2, 80.00);
        PurchaseItem ip3 = new PurchaseItem(ped2, p2, 100.00, 1, 800.00);

        ped1.getItems().addAll(asList(ip1, ip2));
        ped2.getItems().addAll(asList(ip3));

        p1.getItems().addAll(asList(ip1));
        p2.getItems().addAll(asList(ip3));
        p3.getItems().addAll(asList(ip2));

        purchaseItemRepository.saveAll(asList(ip1, ip2, ip3));
    }
}