package org.example;
import Connection.ConnectionFactory;

import java.sql.Connection;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();

            if (conn != null) {
                System.out.println("Conexiune reusita la baza de date!");
            } else {
                System.out.println("Conexiune esuata!");
            }
        } finally {
            ConnectionFactory.close(conn);
        }
        }
    }
