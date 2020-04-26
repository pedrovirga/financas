package caixaApp;

import java.math.BigDecimal;
import manipulate.*;
import java.io.*;

public class Calculations{

    BigDecimal valor, saldo;
    File log = new File("." , "log.txt");
    App manLog = new App(log);

    String debtexto = "O valor Debitado foi: ";
    String credtexto = "O valor Creditado foi: ";
    String saldotexto = "O Saldo e: ";

    Calculations(){
        String texto = "O Saldo e: ";
        String saldoTexto = manLog.readLineSaldo();
        if(saldoTexto == "0"){ /*o arquivo de log nao existe */
            this.saldo = new BigDecimal("0");
            manLog.writeTextoLog(saldotexto + "0" + "\n");
        }
        else{
            String tirarTexto = saldoTexto.substring(saldoTexto.indexOf(texto) + texto.length());
            String[] valorSaldo = tirarTexto.split(":");
            this.saldo = new BigDecimal(valorSaldo[0].replaceAll("(\\r|\\n)" , ""));
        }
    }

    public void addValor(String valor){
        this.valor = new BigDecimal(valor);
        this.saldo = this.saldo.add(this.valor);
        System.out.println(credtexto + this.valor);
        System.out.println(saldotexto + this.saldo);
        if(manLog.writeTextoLog(credtexto + this.valor + "\n"))
            System.out.println("Credito foi para o log.");
        if(manLog.writeTextoLog(saldotexto + this.saldo + "\n"))
            System.out.println("Atualizou Saldo log.");
    }

    public void removeValor(String valor){
        this.valor = new BigDecimal(valor);
        this.saldo = this.saldo.subtract(this.valor);
        System.out.println(debtexto + this.valor);
        System.out.println(saldotexto + this.saldo);
        if(manLog.writeTextoLog(debtexto + this.valor + "\n"))
            System.out.println("Debito foi para o log.");
        if(manLog.writeTextoLog(saldotexto + this.saldo + "\n"))
            System.out.println("Atualizou Saldo log.");
    }

    public String getSaldo(){
        return this.saldo.toString();
    }
}