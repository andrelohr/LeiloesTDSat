/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        conn = new conectaDAO().connectDB();

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.execute();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucessso.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Produto não cadastrado!\nErro ao cadastrar produto.");
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produtos";
        conn = new conectaDAO().connectDB();
        
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(resultset.getInt("id"));
                p.setNome(resultset.getString("nome"));
                p.setValor(resultset.getInt("valor"));
                p.setStatus(resultset.getString("status"));
                listagem.add(p);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
        return listagem;
    }
    
    public void venderProduto(int id) {
    String sql = "UPDATE produtos SET status = ? WHERE id = ?";
    conn = new conectaDAO().connectDB();
    int aVenda = aVenda(id);
    int vendido;

    try {
        prep = conn.prepareStatement(sql);
        prep.setString(1, "Vendido");
        prep.setInt(2, id);
        vendido = prep.executeUpdate();
        if (vendido == 1 && aVenda == 1) {
            JOptionPane.showMessageDialog(null, "Venda do produto realizada com sucesso.");
        } else if (vendido == 1 && aVenda == 0) {
            JOptionPane.showMessageDialog(null, "Venda não realizada!\nO produto informado já está vendido.");
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!\nNão há produtos cadastrados no ID informado.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro.\nErro durante a execução da função Vender.");
    }
}

public int aVenda(int id) {
    String sql = "SELECT COUNT(*) FROM produtos WHERE id = ? AND status = ?";
    conn = new conectaDAO().connectDB();

    try {
        prep = conn.prepareStatement(sql);
        prep.setInt(1, id);
        prep.setString(2, "A Venda");
        resultset = prep.executeQuery();
        resultset.next();
        return resultset.getInt("COUNT(*)");

    } catch (Exception e) {
        return 0;
    }
}

    
        
}

