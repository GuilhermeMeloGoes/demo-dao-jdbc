package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VendedorDaoJDBC implements VendedorDao {

    private Connection conn;

    public VendedorDaoJDBC(Connection conn) {
        this.conn = conn;
    }

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
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement(
                    "SELECT vendedor.*, departamento.Nome as DepNome "
                            + "FROM vendedor INNER JOIN departamento "
                            + "ON vendedor.DepartamentoId = departamento.Id "
                            + "WHERE vendedor.Id = ?");

            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            if (rs.next()) {
                Departamento dep = new Departamento();
                dep.setId(rs.getInt("DepartamentoId"));
                dep.setNome(rs.getString("DepNome"));
                Vendedor obj = new Vendedor();
                obj.setId(rs.getInt("Id"));
                obj.setNome(rs.getString("Nome"));
                obj.setEmail(rs.getString("Email"));
                obj.setDataNascimento(rs.getDate("DataAniversarrio").toLocalDate());
                obj.setSalarioBase(rs.getDouble("SalarioBase"));
                obj.setDepartamento(dep);
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.fecharStatement(pstm);
            DB.fecharResultSet(rs);
        }
    }

    @Override
    public List<Vendedor> findAll() {
        return List.of();
    }
}
