package model.dao;

import model.entities.Departamento;

import java.util.List;

public interface DepartamentoDao {
    void insert(Departamento departamento);

    void update(Departamento departamento);

    void deletById(Integer id);

    Departamento findById(Integer id);

    List<Departamento> findAll();
}
