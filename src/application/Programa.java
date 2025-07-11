package application;

import model.entities.Departamento;
import model.entities.Vendedor;

import java.time.LocalDate;

public class Programa {
    public static void main(String[] args) {

        Departamento dep = new Departamento(1, "Livros");

        Vendedor ven = new Vendedor(1, "Guilherme ", "gui@gmail.com", LocalDate.now(), 3000.00, dep);

        System.out.println(dep);
        System.out.println(ven);
    }
}
