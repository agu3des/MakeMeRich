// Função	        Método	                    Descrição
// Hash de senha	hashPassword(String)	    Gera hash seguro para armazenar no banco.
// Verificação	    checkPass(String, String)	Compara senha digitada com o hash salvo.


package br.edu.ifpb.pweb2.makemerich.util;

import org.mindrot.jbcrypt.BCrypt;

public abstract class PasswordUtil {
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static boolean checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword))
            return true;
        else
            return false;
    }

    // Teste rápido para verificação local
    // public static void main(String[] args) {
    //     String senha = "123";
    //     System.out.println(PasswordUtil.hashPassword(senha));
    // }


}