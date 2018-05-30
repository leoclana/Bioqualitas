package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe que implementa os padroes de criptografia.
 *
 * @author v0rtex
 *
 */


public class Criptografia {

	/**
     * @author v0rtex - hmiranda[at]0fx66.com
     *
     * Metodo que realiza o calculo de hash da senha antes de ser persistida no
     * banco de dados.
     *
     * Utiliza o padrao SHA-256
     *
     * @param senha - String para calcular o hash
     * @return Resultado do calculo de hash.
     *
     */
    public static String encodePassword(String senha) {

            StringBuffer sb = null;

            try {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.update(senha.getBytes());

                    byte byteDate[] = md.digest();

                    sb = new StringBuffer();

                    for(int i = 0; i < byteDate.length; i++) {
                            sb.append(Integer.toString((byteDate[i] & 0xff) + 0x100).substring(1));
                    }

            }
            catch (NoSuchAlgorithmException e) {
                    System.out.println("Erro ao realizar o calculo de hash.");

            }

            return sb.toString();
    }
/*
    public static void main(String[] args){

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/bioqualitasdb", args[0], args[1]);

//            PreparedStatement stmt = connection.prepareStatement("insert into user (userId, ativo, nome, cpf, email, senha) values(1, true, 'Nome do usuario', 'cpf do usuario', 'email do usuario', 'senha do usuario')");
//            stmt.execute();
//            stmt = connection.prepareStatement("insert into Perfis (id_user, perfil) values (1, 1)");
//            stmt.execute();

            Map<Long, String> senhasCriptografadas = new LinkedHashMap<Long, String>();
            PreparedStatement stmt = connection.prepareStatement("select userId, senha from user");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String senha = rs.getString("senha");
                Long id = rs.getLong("userId");
                senhasCriptografadas.put(id, Criptografia.encodePassword(senha));
            }

            rs.close();
            stmt.close();

            stmt = connection.prepareStatement("update user set senha=? where userId=?");

            for(Map.Entry<Long, String> entry : senhasCriptografadas.entrySet()){
                stmt.setString(1, entry.getValue());
                stmt.setLong(2, entry.getKey());
                stmt.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    */
    
}
