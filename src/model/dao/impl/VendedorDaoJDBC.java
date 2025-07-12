package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendedorDaoJDBC implements VendedorDao {

    private Connection conn;

    public VendedorDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Vendedor vendedor) {

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(
                    "INSERT INTO vendedor " +
                            "(Nome, Email, DataAniversarrio, SalarioBase, DepartamentoId) " +
                            "VALUES " +
                            "(?,?,?,?,?); ",
                    Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, vendedor.getNome());
            pstm.setString(2, vendedor.getEmail());
            pstm.setDate(3, Date.valueOf(vendedor.getDataNascimento()));
            pstm.setDouble(4, vendedor.getSalarioBase());
            pstm.setInt(5, vendedor.getDepartamento().getId());

            int linhasAfetadas = pstm.executeUpdate();

            if (linhasAfetadas > 0) {
                ResultSet rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    vendedor.setId(id);
                }
                DB.fecharResultSet(rs);
            } else {
                throw new DbException("Erro ao inserir vendedor, nenhuma linha foi afetada.");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.fecharStatement(pstm);
        }

    }

    @Override
    public void update(Vendedor vendedor) {
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(
                    "UPDATE vendedor SET Nome=?, Email= ?, DataAniversarrio= ?, SalarioBase= ?, DepartamentoId= ? WHERE Id = ?;");

            pstm.setString(1, vendedor.getNome());
            pstm.setString(2, vendedor.getEmail());
            pstm.setDate(3, Date.valueOf(vendedor.getDataNascimento()));
            pstm.setDouble(4, vendedor.getSalarioBase());
            pstm.setInt(5, vendedor.getDepartamento().getId());
            pstm.setInt(6, vendedor.getId());

            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.fecharStatement(pstm);
        }
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
                Departamento dep = instanciarDepartamento(rs);
                return instanciarVendedor(rs, dep);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.fecharStatement(pstm);
            DB.fecharResultSet(rs);
        }
    }

    public Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
        Departamento dep = new Departamento();
        dep.setId(rs.getInt("DepartamentoId"));
        dep.setNome(rs.getString("DepNome"));
        return dep;
    }

    public Vendedor instanciarVendedor(ResultSet rs, Departamento dep) throws SQLException {
        Vendedor obj = new Vendedor();
        obj.setId(rs.getInt("Id"));
        obj.setNome(rs.getString("Nome"));
        obj.setEmail(rs.getString("Email"));
        obj.setDataNascimento(rs.getDate("DataAniversarrio").toLocalDate());
        obj.setSalarioBase(rs.getDouble("SalarioBase"));
        obj.setDepartamento(dep);
        return obj;
    }


    @Override
    public List<Vendedor> findAll() {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement(
                    "SELECT vendedor.*, departamento.nome as DepNome " +
                            "FROM vendedor INNER JOIN departamento " +
                            "ON vendedor.DepartamentoId = departamento.Id " +
                            "ORDER BY Nome;"
            );

            rs = pstm.executeQuery();

            List<Vendedor> listaVendedores = new ArrayList<>();
            Map<Integer, Departamento> map = new HashMap<>();

            while (rs.next()) {

                Departamento dep = map.get(rs.getInt("DepartamentoId"));

                if (dep == null) {
                    dep = instanciarDepartamento(rs);
                    map.put(rs.getInt("DepartamentoId"), dep);
                }

                Vendedor obj = instanciarVendedor(rs, dep);
                listaVendedores.add(obj);
            }
            return listaVendedores;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.fecharStatement(pstm);
            DB.fecharResultSet(rs);
        }
    }

    @Override
    public List<Vendedor> findByDepartamento(Departamento departamento) {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement(
                    "SELECT vendedor.*, departamento.nome as DepNome " +
                            "FROM vendedor INNER JOIN departamento " +
                            "ON vendedor.DepartamentoId = departamento.Id " +
                            "WHERE DepartamentoId = ? " +
                            "ORDER BY Nome;"
            );

            pstm.setInt(1, departamento.getId());
            rs = pstm.executeQuery();

            List<Vendedor> listaVendedores = new ArrayList<>();
            Map<Integer, Departamento> map = new HashMap<>();

            while (rs.next()) {

                Departamento dep = map.get(rs.getInt("DepartamentoId"));

                if (dep == null) {
                    dep = instanciarDepartamento(rs);
                    map.put(rs.getInt("DepartamentoId"), dep);
                }

                Vendedor obj = instanciarVendedor(rs, dep);
                listaVendedores.add(obj);
            }
            return listaVendedores;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.fecharStatement(pstm);
            DB.fecharResultSet(rs);
        }
    }
}
