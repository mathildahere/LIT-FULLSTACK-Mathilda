package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;


public class Main {
    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            {
                Transaction tx = session.beginTransaction();

                Stock aapl =  new Stock("APPL","APPLE INC","Techo","CONSUMER Electornic" );
                aapl.setCurrentPrice(new BigDecimal("155.00"));
                aapl.setMarketCap(new BigDecimal("300000000000000000"));
                aapl.setPeRatio(28.3);
                aapl.setDividendYield(0.6f);

                session.persist(aapl);
                tx.commit();
                session.close();

            }

        }

    }
}