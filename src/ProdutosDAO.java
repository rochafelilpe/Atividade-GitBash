/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */


import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.SQLException; 
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    PreparedStatement ps = null;
    ResultSet rs = null; 
    
    public void cadastrarProduto (ProdutosDTO produto){
        conectaDAO conectaDAO = new conectaDAO();
        Connection conexao = conectaDAO.connectDB();
        
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?,?,?)";
        try(conexao; PreparedStatement prep = conexao.prepareStatement(sql)){
            
            //preenche os parâmetros do sql 
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            //rodando na base de dados
            int linhasAfetadas = prep.executeUpdate();
            
            if(linhasAfetadas > 0){
                JOptionPane.showMessageDialog(null, "Produto cadastrado!");
            }
            
            
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null, "Erro ao conectar no banco ou cadastrar o produto!" + sqle.getMessage());
        }
        
        
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        String sql = "SELECT id, nome, status, valor FROM produtos";
        //conecta
        conectaDAO conectaDAO = new conectaDAO();
        Connection conexao = conectaDAO.connectDB();
        
        listagem.clear();
        
        try{
            this.ps = conexao.prepareStatement(sql);
            this.rs = ps.executeQuery();

            //Percorre o banco e adiciona na tabela de produtos
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setStatus(rs.getString("status"));
                produto.setValor(rs.getInt("valor"));

                listagem.add(produto);
            }
        
        }catch (Exception e) {
            System.out.println("Erro ao carregar banco: " + e.getMessage());
        }finally{
            try{
                conexao.close();
            }catch(Exception e){
                
            }
        }
        
        return listagem;
    }
   
}

