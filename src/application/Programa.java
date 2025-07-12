package application;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.dao.impl.VendedorDaoJDBC;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.time.LocalDate;

public class Programa {
    public static void main(String[] args) {

        VendedorDao vendedorDao = DaoFactory.createVendedorDao();

        System.out.println("=== TESTE 1: Vendedor findById ===");
        Vendedor vendedor = vendedorDao.findById(3);
        System.out.println(vendedor);
    }
}
