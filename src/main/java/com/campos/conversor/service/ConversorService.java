package com.campos.conversor.service;

import com.campos.conversor.Enum.CentenaPorExtensoEnum;
import com.campos.conversor.Enum.DezenaPorExtensoEnum;
import com.campos.conversor.Enum.MilharPorExtensoEnum;
import com.campos.conversor.Enum.UnidadePorExtensoEnum;
import com.campos.conversor.tools.Utilitarios;
import org.springframework.stereotype.Service;

/**
 * Created by cris on 14/03/2020.
 */
@Service
public class ConversorService {
    private final int VALOR_MINIMO = -99999;
    private final int VALOR_MAXIMO = 99999;
    private final int MIL = 1000;
    private final int CEM = 100;
    private final int VINTE = 20;
    /**
     * metodo responsável pela transcrição do @numeral passado por parametro para palavras em português.
     * @param numeral
     * @return
     */
    public String converteNumeralParaExtenso(int numeral){
        try{
            if (!Utilitarios.validaInteiroEntreLimites(numeral, VALOR_MINIMO, VALOR_MAXIMO))
                return "Número fora da faixa";

            if (numeral >= this.MIL){
                return montaMilhar(numeral);
            }else{
                return getValor(numeral);
            }


        }catch (Exception e){

        }
        return "";
    }

    private String montaUnidade(int numeral){
        String valorExtenso = "";
        for (UnidadePorExtensoEnum unidade : UnidadePorExtensoEnum.values()){
            if (unidade.valor == numeral){
                valorExtenso = unidade.extenso;
                break;
            }
        }
        return valorExtenso;
    }

    private String montaDezena(int numeral){
        String valorExtenso = "";
        for (DezenaPorExtensoEnum dezena : DezenaPorExtensoEnum.values()){
            if (dezena.valor <= numeral && dezena.valor + 10 > numeral){
                valorExtenso = dezena.extenso;
                valorExtenso += " e " + this.converteNumeralParaExtenso(numeral - dezena.valor);
             //   valorExtenso += " e " + montaUnidade(numeral - dezena.valor);
                break;
            }
        }
        return valorExtenso;
    }

    private String montaCentena(int numeral){
        String valorExtenso = "";
        for (CentenaPorExtensoEnum centena : CentenaPorExtensoEnum.values()){
            if (centena.valor <= numeral && centena.valor + 100 > numeral){
                valorExtenso = centena.extenso;
                valorExtenso += " e " + this.converteNumeralParaExtenso(numeral - centena.valor);
                break;
            }
        }
        return valorExtenso;
    }

    private String montaMilhar(int numeral){
        String valorExtenso = "";
        if (MilharPorExtensoEnum.MIL.valor <= numeral){
            int milha = Math.abs(numeral / 1000);
            int centena = numeral % 1000;
            valorExtenso = this.converteNumeralParaExtenso(milha) +" "+ MilharPorExtensoEnum.MIL.extenso + " " + this.getValor(centena);
        }
        return valorExtenso;
    }
    private String getValor(int numeral){
        if (numeral >= this.CEM && numeral <= this.MIL){
            return montaCentena(numeral);

        }else if (numeral >= this.VINTE && numeral < this.CEM){
            return montaDezena(numeral);
        }else {
            return montaUnidade(numeral);
        }
    }
}
