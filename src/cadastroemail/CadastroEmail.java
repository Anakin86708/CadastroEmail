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
    private ResultSet rs, rsValores, rsEmail;
    private Statement st;
    private String sqlString, Categoria, Nome, Email;
    private int IDPessoa, IDCategoria, IDEmail;
    private boolean NovoValor;
    
    //Variáveis da classe
    
    public boolean SetConexao(){
        String driver, enderecoDB, userDB, senhaDB;
        driver = "com.mysql.jdbc.Driver"; //Não esquecer de adicionar biblioteca do Driver MySQL
        enderecoDB = "jdbc:mysql://localhost/contato";
        userDB = "root";
        senhaDB = "";
        
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(enderecoDB, userDB, senhaDB);
            //msgErro = "Mensagem: Banco de Dados conectato com êxito!";
            return true;
        } catch (Exception e){
            //msgErro = "Mensagem: Erro ao conectar com o Banco de Dados: \n\t";
            return false;
        }
        
        //Não será necessário uma mensagem de erro
        //return msgErro;
    }
    
    public ResultSet GetCategoriasRS(){
        try{
            sqlString = "SELECT descricao, id FROM categoria ORDER BY descricao";
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
            //sqlString = "SELECT nome FROM pessoa";
            if ("TODOS".equals(Categoria)){
                sqlString = "SELECT nome, id FROM pessoa ORDER BY nome";
                //Uso de JOIN para exibir pessoas que tenham email em outra categoria
                //sqlString = "SELECT p.nome, p.id from pessoa as p LEFT JOIN email as e ON p.id = e.idPessoa";
                st = con.prepareStatement(sqlString);
                rs = st.executeQuery(sqlString);
            }else{
                sqlString = "SELECT p.nome, p.id from pessoa as p, email as e, categoria as c WHERE p.id = e.idPessoa and c.id = e.idCategoria and c.descricao = ? ORDER BY p.nome";
                ps = con.prepareStatement(sqlString);
                ps.setString(1,Categoria);
                rs = ps.executeQuery();
            }
            
            rs.first();
            if (!rs.first()) {
                rs = null;
            }
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
    
//    public ResultSet CadastroPessoa(){
//        try{
//            sqlString = "SELECT p.nome, c.descricao, e.email FROM pessoa as p, categoria as c, email as e WHERE p.id = e.idPessoa AND c.id = e.idCategoria AND p.id = ?";
//            ps = con.prepareStatement(sqlString);
//            ps.setInt(1, IDPessoa);
//            rs = ps.executeQuery();
//            rs.first();
//        } catch (Exception e){
//            rs = null;
//        }
//        return  rs;
//    }
    
    public String GetNomeString(){
        try {
            sqlString = "SELECT nome FROM pessoa WHERE id = ?";
            ps  = con.prepareStatement(sqlString);
            ps.setInt(1, IDPessoa);
            rs = ps.executeQuery();
            rs.first();
            return rs.getString(1);
            
        } catch (Exception e) {
            return "";
        }
    }
    
    public ResultSet GetEmailRS(){
        try {
            sqlString = "SELECT email, id FROM email WHERE idPessoa = ?";
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, IDPessoa);
            rsEmail = ps.executeQuery();
            rsEmail.first();
            return rsEmail;
        } catch (Exception e) {
            return null;
        }
    }
    
    public String GetEmailString(){
        try {
            sqlString = "SELECT email FROM email WHERE idPessoa = ? AND idCategoria = ?";
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, IDPessoa);
            ps.setInt(2, IDCategoria);
            rsEmail = ps.executeQuery();
            rsEmail.first();
            return rsEmail.getString(1);
        } catch (Exception e) {
            return null;
        }
    }
    
    public String Remover(){
        try {
            sqlString = "DELETE FROM email WHERE idCategoria = ? AND idPessoa = ?";
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, IDCategoria);
            ps.setInt(2, IDPessoa);
            ps.executeUpdate();
            return "Sucesso!";

        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
    
    
    public Integer GetCategoriaInteger(){
        try {
            sqlString = "SELECT c.id FROM categoria as c, email as e WHERE c.id = e.idCategoria AND e.idPessoa = ?";
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, IDPessoa);
            rs = ps.executeQuery();
            rs.first();
            return rs.getInt(1);
        } catch (Exception e) {
            return 0;
        }
    }
    
//    public String EditarContato(Integer idCategoria){
//        try {
//            sqlString = "INSERT INTO email (idPessoa, idCategoria, email) VALUES (?,?,?)";
//            ps = con.prepareStatement(sqlString);
//            ps.setInt(1, IDPessoa);
//            ps.setInt(2, idCategoria);
//            ps.setString(3, Email);
//            ps.executeUpdate();
//            return "Sucesso!";
//        } catch (Exception e) {
//            return "Erro ao editar contato!" + e.getMessage();
//        }
//    }

    /**
     * Inserir ou editar um dos cadastros.
     * Usa os valores de IdPessoa, IdCategoria e Email que estão encapsulados
     * @return
     * String de êxito ou falha
     */
    
    public String AlterarValorCadastro(){
        if (NovoValor) {
            try {
                sqlString = "INSERT INTO email (idPessoa, idCategoria, email) VALUES (?,?,?)";
                ps = con.prepareStatement(sqlString);
                ps.setInt(1, IDPessoa);
                ps.setInt(2, IDCategoria);
                ps.setString(3, Email);
                ps.executeUpdate();
                return "Sucesso!";
            } catch (Exception e) {
                return "Erro ao adicionar contato!" + e.getMessage();
            }
        } else {
            try {
                sqlString = "UPDATE email SET idPessoa = ?, idCategoria = ?, email = ? WHERE idCategoria = ?";
                ps = con.prepareStatement(sqlString);
                ps.setInt(1, IDPessoa);
                ps.setInt(2, IDCategoria);
                ps.setString(3, Email);
                ps.setInt(4, IDCategoria);
                ps.executeUpdate();
                return  "Sucesso!";
            } catch (Exception e) {
                 return "Erro ao editar contato!" + e.getMessage();
            }
        }
    }
    
    public void SetCategoria(String Categoria){
        this.Categoria = Categoria;
    }
    
    public void SetID(int IDPessoa){
        this.IDPessoa = IDPessoa;
    }
    
    public void SetNome(String Nome){
        this.Nome = Nome;
    }
    
    public void SetEmail(String Email){
        this.Email = Email;
        /*String emailTmp;
        try {
        rsEmail.first();
        do {
        emailTmp = rs.getString(1);
        if (emailTmp.equals(Email)) {
        NovoValor = false;
        break;
        } else {
        NovoValor = true;
        }
        } while (rs.next());
        } catch (Exception e) {
        }
        return NovoValor;*/
    }
    
    public void SetIDEmail(int IdEmail){
        this.IDEmail = IdEmail;
    }
    
    public void SetIDCategoria(int IDCategoria){
        this.IDCategoria = IDCategoria;
    }
    
    public void SetNovoValor(boolean NovoValor){
        this.NovoValor = NovoValor;
    }
    
    /* public void SeIDEmail(int IDEmail){
    
    }*/
    
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
