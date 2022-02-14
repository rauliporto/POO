package pt.iscte.dcti.poo.sokoban.starter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;
import java.util.Collections;
import java.io.PrintWriter;
import java.io.IOException;


public class Pontuacao {
	
	//Guarda pontuação por nível
	public static void pontuacaoNivel(int level, int score) {
		fileManagement("levels/Pontuacao_"+level+".txt", score);
	}
	
	//Guarda a pontuação geral do jogo inteiro
	public static void PontuacaoGeral(int score){
		fileManagement("levels/Pontuacao.txt", score);
	}
	
	//Consoante o caminho do ficheiro, adicionamos um novo score
	private static void fileManagement(String filePath, int score){
		Scanner analise;
		List<Integer> pontuacaoGeral=new ArrayList<Integer>();
		//adicionamos à lista um novo score :)
		pontuacaoGeral.add(score);
		
		try{
			//Se o caminho do ficheiro existir, não fará nada
			File pontuacao=new File(filePath);
			//Se não existir, irá criar
			try{
			pontuacao.createNewFile();
			}
			catch(IOException e){
				
			}
			//Criamos o leitor de um ficheiro
			Scanner aux=new Scanner(pontuacao);
			
			//Se existirem valores, irá ler os valores do ficheiro ( só os primeiros 5)
			if(aux.hasNextLine()){
				int x=0;
				while(aux.hasNextLine() && x<5){
					x++;
					analise = new Scanner(aux.nextLine());
				 int addToArray=analise.nextInt();
				 pontuacaoGeral.add(addToArray);
				
				}
				aux.close();
			}
			//Ordenamos os valores com um novo valor inserido
			 Collections.sort(pontuacaoGeral);
			 //Criamos um escritor de ficheiros
			 PrintWriter ficheiroDePontuacoes=new PrintWriter(new File(filePath));
			 //adicionamos os primeiros 5 scores no ficheiro
			 for(int i=0;i<pontuacaoGeral.size() && i<5;i++){
				 ficheiroDePontuacoes.println(pontuacaoGeral.get(i));
			 }
			 ficheiroDePontuacoes.close();
		}
		catch(FileNotFoundException e){
			System.err.println("Ficheiro n�o encontrado");
		}
	}
}
