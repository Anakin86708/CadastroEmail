/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroemail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author ariel
 */
public class CadastroEmail {

    /**
     * @param args the command line arguments
     */
    
    //Variáveis para o funcionamento do SQL
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs, rsValores;
    private Statement st;
    private String msgErro, sqlString, Categoria;
    
    //Variáveis da classe
    
    public void SetConexao(){
        String driver, enderecoDB, userDB, senhaDB;
        driver = "com.mysql.jdbc.Driver"; //Não esquecer de adicionar biblioteca do Driver MySQL
        enderecoDB = "jdbc:mysql://localhost/contato";
        userDB = "root";
        senhaDB = "";
        
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(enderecoDB, userDB, senhaDB);
            msgErro = "Mensagem: Banco de Dados conectato com êxito!";
        } catch (Exception e){
            msgErro = "Mensagem: Erro ao conectar com o Banco de Dados: \n\t";
        }
        
        //Não será necessário uma mensagem de erro
        //return msgErro;
    }
    
    public ResultSet GetCategoriasRS(){
        try{
            sqlString = "SELECT descricao FROM categoria";
            st = con.prepareStatement(sqlString);
            rs = st.executeQuery(sqlString);
            rs.first();
        } catch(Exception e){
            rs = null;
        }
        return rs;
    }
    
//    public int GetQtdCategoria(){
//        try {
//            sqlString = "SELECT COUNT(id) FROM categoria";
//            st = con.prepareStatement(sqlString);
//            rs = st.executeQuery(sqlString);
//            rs.first();
//            
//            return rs.getInt(1);
//        } catch (Exception e) {
//            return 0;
//        }
//    }
    
    public ResultSet GetPessoasRS(){
        try{
            sqlString = "SELECT nome FROM pessoa";
            st = con.prepareStatement(sqlString);
            rs = st.executeQuery(sqlString);
            rs.first();
        } catch(Exception e){
            rs = null;
        }
        return rs;
    }
    
//    public int GetQtdPessoa(){
//        try {
//            sqlString = "SELECT COUNT(id) FROM pessoa";
//            st = con.prepareStatement(sqlString);
//            rs = st.executeQuery(sqlString);
//            rs.first();
//            
//            return rs.getInt(1);
//        } catch (Exception e) {
//            return 0;
//        }
//    }
    
    public String AdicionarCategoria(String valor){
        try {
            sqlString = "INSERT INTO categoria (descricao) VALUES (?)";
            ps = con.prepareStatement(sqlString);
            ps.setString(1,valor);
            ps.executeUpdate();
            return "Sucesso!";
            
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
    
    public String AdicionarPessoa(String valor){
        try {
            sqlString = "INSERT INTO pessoa (nome) VALUES (?)";
            ps = con.prepareStatement(sqlString);
            ps.setString(1,valor);
            ps.executeUpdate();
            return "Sucesso!";
            
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
    
    public void SetCategoria(String Categoria){
        this.Categoria = Categoria;
    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
