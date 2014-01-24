package com.example.mastermind_solver;

import java.util.ArrayList;
import java.util.ListIterator;

import android.R.bool;
/**
 *
 * @author Juan Mite
 *
 */
final class Engine {
   private static final bool True = null;
private static final bool False = null;
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
	
	public int getScore(int feedback) {
		int score = 0;
		int greyPeks = feedback % 10;
		int blackPeks = feedback/10;
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
	
	public bool allBlack(int guess){
		if(getScore(guess)== 14)
			return True;
		else
			return False;
	}
	
	
	public int randomGuess (){
		int n = 0;
		n = (int)(Math.random()*poblacion.size());
		int guess = poblacion.get(n);
		return guess;
	}
	
	public ArrayList<Integer> exterminar(int feedback, int guess) {
		
		if (getScore(feedback) == 0){
			killAll(guess);
			
		}
		
		return poblacion;
	}
	public void killOne(int guess) {
		ListIterator<Integer> itera = poblacion.listIterator(poblacion.size());
		while (itera.hasPrevious()){
			int GUESS = itera.previous().intValue();
			if (GUESS == guess)
			itera.remove();
		}
	}
	
	public void killComplement(int guess) {
		int p1 =guess/1000;  
		int p2 =(guess/100)%10;
		int p3 =(guess/10) % 10; 
		int p4 = guess % 10;
		
		ListIterator<Integer> itera = poblacion.listIterator(poblacion.size());
		while (itera.hasPrevious()){
			int GUESS = itera.previous().intValue();
			int q1 =GUESS/1000;  
			int q2 =(GUESS/100)%10;
			int q3 =(GUESS/10) % 10; 
			int q4 = GUESS % 10;
			if (!(p1==q1||p1==q2||p1==q3||p1==q4)&&
				(p2==q1||p2==q2||p2==q3||p2==q4)&&
				(p3==q1||p3==q2||p3==q3||p3==q4)&&
				(p4==q1||p4==q2||p4==q3||p4==q4)){
				
				itera.remove();
			}
		}
		
	}
	
	public void killAll(int guess) {
		// TODO Auto-generated method stub
		int p1 =guess/1000;  
		int p2 =(guess/100)%10;
		int p3 =(guess/10) % 10; 
		int p4 = guess % 10;
		
		ListIterator<Integer> itera = poblacion.listIterator(poblacion.size());
		while (itera.hasPrevious()){
			int GUESS = itera.previous().intValue();
			int q1 =GUESS/1000;  
			int q2 =(GUESS/100)%10;
			int q3 =(GUESS/10) % 10; 
			int q4 = GUESS % 10;
			if (p1==q1||p1==q2||p1==q3||p1==q4||
				p2==q1||p2==q2||p2==q3||p2==q4||
				p3==q1||p3==q2||p3==q3||p3==q4||
				p4==q1||p4==q2||p4==q3||p4==q4){
				
				itera.remove();
			}
		}
		
	}

	public int bestGuess(int actualFeedback, int pastFeedback) {
		
		if (getScore(actualFeedback) < getScore(pastFeedback))
			return pastFeedback;
		else
			return actualFeedback;
	}
	
}
