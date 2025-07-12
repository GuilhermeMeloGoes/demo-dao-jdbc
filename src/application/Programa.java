package application;

import model.dao.DaoFactory;
import model.dao.VendedorDao;

import model.entities.Departamento;
import model.entities.Vendedor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VendedorDao vendedorDao = DaoFactory.createVendedorDao();

        System.out.println("=== TESTE 1: Vendedor findById ===");
        Vendedor vendedor = vendedorDao.findById(3);
        System.out.println(vendedor);

        System.out.println("\n\n=== TESTE 2: Vendedor findByDepartamento ===");
        Departamento departamento = new Departamento(2, null);
        List<Vendedor> listaDepartamentos = vendedorDao.findByDepartamento(departamento);
        for (Vendedor v : listaDepartamentos) {
            System.out.println(v);
        }

        System.out.println("\n\n=== TESTE 3: Vendedor findAll ===");
        List<Vendedor> listaCompleta = vendedorDao.findAll();
        for (Vendedor v : listaCompleta) {
            System.out.println(v);
        }

        /* System.out.println("\n\n=== TESTE 4: Vendedor insert ===");
        Vendedor vendedor1 = new Vendedor(null, "Greg", "greg@gmail.com", LocalDate.now(), 4000.00, departamento);
        vendedorDao.insert(vendedor1);
        System.out.println("\n Vendedor inserido! Novo id = " + vendedor1.getId()); */

        System.out.println("\n\n=== TESTE 5: Vendedor update ===");
        Vendedor novoVendedor = vendedorDao.findById(1);
        novoVendedor.setNome("Guilherme");
        vendedorDao.update(novoVendedor);
        System.out.println("\n Update Completo!");

        System.out.println("\n\n=== TESTE 6: Vendedor delete ===");
        System.out.println("Digite o id que quer deletar? ");
        int id = sc.nextInt();
        vendedorDao.deletById(id);
        System.out.println("\n Vendedor deletado!");

        sc.close();
    }
}
