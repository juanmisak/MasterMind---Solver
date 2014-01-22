package com.example.mastermind_solver;

import java.util.ArrayList;
/**
 *
 * @author Juan Mite
 *
 */
final class Engine {
   static int NUM_COLORES = 6;

public enum Colors {
	   AZUL,
	   VERDE,
	   ROJO,
	   BLANCO,
	   AMARILLO,
	   VIOLETA
	}	
	
	ArrayList<Integer> guesses = new ArrayList<Integer>();
	ArrayList<Integer> feedbacks = new ArrayList<Integer>();
	ArrayList<Integer> poblacion = new ArrayList<Integer>();
	ArrayList<Integer> pastGuesses = new ArrayList<Integer>();

	public ArrayList<Integer> crearPoblacion() {
		for(int h=0;h<NUM_COLORES;h++){						
			for(int k=0;k<NUM_COLORES;k++){
				for(int j=0;j<NUM_COLORES;j++){
					for(int i=0;i<NUM_COLORES;i++){
						poblacion.add((1000*h)+(100*k)+(j*10)+i);
						}
					}
				}
		}
		return poblacion;
	}
	
	public int getScore(int guess) {
		int score = 0;
		int greyPeks = guess % 10;
		int blackPeks = guess/10;
		int n=blackPeks+greyPeks;
		
		score = (n*(n+1))/2 + blackPeks;
		
		return score;
	}
	
	
	public Integer Mutation(int guess) {
		Integer guessMuted = 0;
		int p1 =guess/1000;  
		int p2 =(guess/100)%10;
		int p3 =(guess/10) % 10; 
		int p4 = guess % 10;
		
		int vector[]=new int [4];
		int i=0,j=0;
		vector[i]=(int)(Math.random()*4);
		for(i=1;i<10;i++){
			vector[i]=(int)(Math.random()*4);
			for(j=0;j<i;j++){
				if(vector[i]==vector[j])			{
					i--;
				}
			}
		}
		guessMuted = (int) (p1*Math.pow(10,vector[0])+
							p2*Math.pow(10,vector[1])+
							p3*Math.pow(10,vector[2]+
							p4*Math.pow(10,vector[3])));
			
		return guessMuted;
	}
	
	public ArrayList<Integer> Selection(int eliminateGuess){
		
		
		
		return poblacion;		
	}
	

}
