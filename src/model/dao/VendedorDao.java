package model.dao;

import model.entities.Vendedor;

import java.util.List;

public interface VendedorDao {
    void insert(Vendedor vendedor);

    void update(Vendedor vendedor);

    void deletById(Integer id);

    Vendedor findById(Integer id);

    List<Vendedor> findAll();
}
