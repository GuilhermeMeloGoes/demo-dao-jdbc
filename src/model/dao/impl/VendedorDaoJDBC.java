package model.dao.impl;

import model.dao.VendedorDao;
import model.entities.Vendedor;

import java.util.List;

public class VendedorDaoJDBC implements VendedorDao {

    @Override
    public void insert(Vendedor vendedor) {

    }

    @Override
    public void update(Vendedor vendedor) {

    }

    @Override
    public void deletById(Integer id) {

    }

    @Override
    public Vendedor findById(Integer id) {
        return null;
    }

    @Override
    public List<Vendedor> findAll() {
        return List.of();
    }
}
