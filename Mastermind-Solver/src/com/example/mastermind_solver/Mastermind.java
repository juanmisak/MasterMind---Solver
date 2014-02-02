package com.example.mastermind_solver;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

import android.R.integer;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*  ===========================================
 *  Combinacion colores FICHAS a enteros:
 *  ===========================================
 *  celeste 	= 0
 *  verde 		= 1
 *  rojo 		= 2
 *  blanco 		= 3
 *  amarillo 	= 4
 *  morado 		= 5
 *  [abcd,abcd,abcd,abcd,abcd,abcd,abcd,abcd]*/
 /*
 *  ============================================
 *  Combinacion colores FEEDBACKS a enteros:
 *  ============================================
 *  negra 		= a  
 *  gris 		= b
 *  [ab,ab,ab,ab,ab,ab,ab,ab]
 */

public class Mastermind extends Activity {
	public static final String PERCENT_CHAR = "%";
    public static final int MAX_PERCENT_RANDOM_VALUE = 100;
    private VerticalProgressBar progressBar;
    private Random random;
    private Button randomValueButton;
    private TextView progressValueTextView;    
    
    int ituca=0;
    int jtuca=0;
    int index=0;
    int ktuca=0;

    int contador_guesses=0; //variable para contar los feedback
    int contador_feedbacks=0; //variable para contar los feedback
    
    int confirt=7;
    TextView console;
    String logconsole="";
	// Splash screen timer
	private static int SPLASH_TIME_OUT = 30;
    static final int BLUE = 0, GREEN = 1, RED = 2, WHITE = 3, YELLOW = 4, PURPLE = 5;
	static int TOTAL_PEG_SLOTS = 32;
	static final int MAX_PEGS = 4;
	static final int MAX_GUESSES = 8;
    static final int[] sPegs = { R.drawable.bluepeg, R.drawable.greenpeg, R.drawable.redpeg,
            R.drawable.whitepeg, R.drawable.yellowpeg, R.drawable.purplepeg };
    // slotPosition[] maps peg slots to view IDs
    protected static final int[] sSlotPosition = new int[TOTAL_PEG_SLOTS];
    protected static final int[] sSmallSlotPosition = new int[TOTAL_PEG_SLOTS];
    protected static final int[][] pegSlots = new int[MAX_GUESSES][MAX_PEGS];
    protected static final int[][] sSmallPegSlots = new int[MAX_GUESSES][MAX_PEGS];
    public int[] confirm= new int[8];
    public int[] guesses= new int[8];
    public int[] feedbacks= new int[8];


    Engine mGame = new Engine();
    int mGuess = 0;
    ArrayList<Integer> poblacion = mGame.crearPoblacion();
    int poblacion_inicial = mGame.poblacion.size();
    
    Resources mResources;
    // state[] shows which colour peg has been selected
    boolean[] mState = new boolean[Engine.NUM_COLORES];
    
    // Map the ImageView id's to an array index for simplicity when referencing later
    // Very inefficient, needs to be fixed.
    // Map the peg slots on the board to an array index before creating an object of the class
    static {
        sSlotPosition[3] = R.id.peg32;
        sSlotPosition[2] = R.id.peg31;
        sSlotPosition[1] = R.id.peg30;
        sSlotPosition[0] = R.id.peg29;
        
        sSlotPosition[7] = R.id.peg28;
        sSlotPosition[6] = R.id.peg27;
        sSlotPosition[5] = R.id.peg26;
        sSlotPosition[4] = R.id.peg25;
        
        sSlotPosition[11] = R.id.peg24;
        sSlotPosition[10] = R.id.peg23;
        sSlotPosition[9] = R.id.peg22;
        sSlotPosition[8] = R.id.peg21;
        
        sSlotPosition[15] = R.id.peg20;
        sSlotPosition[14] = R.id.peg19;
        sSlotPosition[13] = R.id.peg18;
        sSlotPosition[12] = R.id.peg17;
        
        sSlotPosition[19] = R.id.peg16;
        sSlotPosition[18] = R.id.peg15;
        sSlotPosition[17] = R.id.peg14;
        sSlotPosition[16] = R.id.peg13;
        
        sSlotPosition[23] = R.id.peg12;
        sSlotPosition[22] = R.id.peg11;
        sSlotPosition[21] = R.id.peg10;
        sSlotPosition[20] = R.id.peg09;
        
        sSlotPosition[27] = R.id.peg08;
        sSlotPosition[26] = R.id.peg07;
        sSlotPosition[25] = R.id.peg06;
        sSlotPosition[24] = R.id.peg05;
        
        sSlotPosition[31] = R.id.peg04;
        sSlotPosition[30] = R.id.peg03;
        sSlotPosition[29] = R.id.peg02;
        sSlotPosition[28] = R.id.peg01;

   
        sSmallSlotPosition[0] = R.id.smallPeg32;
        sSmallSlotPosition[1] = R.id.smallPeg31;
        sSmallSlotPosition[2] = R.id.smallPeg30;
        sSmallSlotPosition[3] = R.id.smallPeg29;
        sSmallSlotPosition[4] = R.id.smallPeg28;
        sSmallSlotPosition[5] = R.id.smallPeg27;
        sSmallSlotPosition[6] = R.id.smallPeg26;
        sSmallSlotPosition[7] = R.id.smallPeg25;
        sSmallSlotPosition[8] = R.id.smallPeg24;
        sSmallSlotPosition[9] = R.id.smallPeg23;
        sSmallSlotPosition[10] = R.id.smallPeg22;
        sSmallSlotPosition[11] = R.id.smallPeg21;
        sSmallSlotPosition[12] = R.id.smallPeg20;
        sSmallSlotPosition[13] = R.id.smallPeg19;
        sSmallSlotPosition[14] = R.id.smallPeg18;
        sSmallSlotPosition[15] = R.id.smallPeg17;
        sSmallSlotPosition[16] = R.id.smallPeg16;
        sSmallSlotPosition[17] = R.id.smallPeg15;
        sSmallSlotPosition[18] = R.id.smallPeg14;
        sSmallSlotPosition[19] = R.id.smallPeg13;
        sSmallSlotPosition[20] = R.id.smallPeg12;
        sSmallSlotPosition[21] = R.id.smallPeg11;
        sSmallSlotPosition[22] = R.id.smallPeg10;
        sSmallSlotPosition[23] = R.id.smallPeg9;
        sSmallSlotPosition[24] = R.id.smallPeg8;
        sSmallSlotPosition[25] = R.id.smallPeg7;
        sSmallSlotPosition[26] = R.id.smallPeg6;
        sSmallSlotPosition[27] = R.id.smallPeg5;
        sSmallSlotPosition[28] = R.id.smallPeg4;
        sSmallSlotPosition[29] = R.id.smallPeg3;
        sSmallSlotPosition[30] = R.id.smallPeg2;
        sSmallSlotPosition[31] = R.id.smallPeg1;
        /*
        map(sSlotPosition, pegSlots);
        map(sSmallSlotPosition, sSmallPegSlots);*/
    }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mastermind);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        confirm[0] = R.id.confirm01;
        confirm[1] = R.id.confirm02;
        confirm[2] = R.id.confirm03;
        confirm[3] = R.id.confirm04;
        confirm[4] = R.id.confirm05;
        confirm[5] = R.id.confirm06;
        confirm[6] = R.id.confirm07;
        confirm[7] = R.id.confirm08;

        random = new Random();
        
        progressBar = (VerticalProgressBar) findViewById(R.id.acd_id_proress_bar);
        progressValueTextView = (TextView) findViewById(R.id.acd_id_proress_value);
        
        
        mResources = getResources();
        // Initialise the state array
        for (int i = 0; i < mState.length; i++) {
            mState[i] = false;
        } // for



        findViewById(R.id.confirm08).setClickable(false);

        console = (TextView) findViewById(R.id.consola);
        ///Generando aletaorio en cada iteracion de los 8

        jtuca++;
        logconsole+="Combination guessing.... \n";   	
        logconsole+="Round #"+jtuca+" \n";

		guesses[contador_guesses] = generar_sigFichas();
		console.setText(logconsole);
        contador_guesses++;
        
        
        //Bloqueando clickable a las fichas generadas
        for (int i =0; i < sSlotPosition.length; i++) {
            ImageView view = (ImageView) findViewById(sSlotPosition[i]);
            view.setClickable(false);	
            ImageView view2= (ImageView) findViewById(sSmallSlotPosition[i]);
            view2.setClickable(true);
		}
      
        TOTAL_PEG_SLOTS=TOTAL_PEG_SLOTS-4;
        //bloqueando clickable a los feeds restantes
        for (int i = 0; i < TOTAL_PEG_SLOTS; i++) {
            ImageView view = (ImageView) findViewById(sSmallSlotPosition[i]);
            view.setClickable(false);
		}

        ImageView view = (ImageView) findViewById(confirm[confirt]);
        confirt--;
        view.setImageDrawable(mResources.getDrawable(R.drawable.confirm));
        view.setClickable(true);
        
        
        ///randoms de las feedbacks
        for ( int i = 0; i < sSmallSlotPosition.length; i++) {
            ImageView view1 = (ImageView) findViewById(sSmallSlotPosition[i]);
            //view1.setImageDrawable(mResources.getDrawable(R.drawable.smallPeg));
            view1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageView v1 = (ImageView)v;
					
					Drawable d1=v1.getDrawable();
					
					if(v1.getDrawable()== null){			//comparo si el feed esta vacio lo cambia a rojo
						v1.setImageDrawable(mResources.getDrawable(R.drawable.black_peck));								
					}else if(d1.getConstantState().equals( mResources.getDrawable(R.drawable.black_peck).getConstantState())){	//comparo si el feed esta rojo lo cambia a blanco				
							v1.setImageDrawable(mResources.getDrawable(R.drawable.grey_peck));							
						}else if(d1.getConstantState().equals( mResources.getDrawable(R.drawable.grey_peck).getConstantState())){  //comparo si el feed esta blanco lo cambia a vacio
								v1.setImageDrawable(null);
						}
					}
				});
            
 		}

        for (int i = 0; i < 8; i++) {
            ImageView view3 = (ImageView) findViewById(confirm[i]);
            //view3.setClickable(false);
            view3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					v.setClickable(false);
		
					if(confirt>=0){			
					   			    	
						jtuca++;
				        logconsole+="Combination guessing.... \n";   	

				        logconsole+="Round # "+jtuca+" \n";
				        if (contador_feedbacks==0)
						guesses[contador_guesses] = generar_sigFichas();
						

						ImageView v1 = (ImageView) findViewById(confirm[confirt]);
						confirt--;
						v1.setImageDrawable(mResources.getDrawable(R.drawable.confirm));
						v1.setClickable(true);
						
					    int negras=0;
					    int grises=0;
					    for (int l =0; l <4; l++) {
					    	ImageView ficha= (ImageView) findViewById(sSmallSlotPosition[ktuca]);
					    	Drawable drficha=ficha.getDrawable();
					    	if(drficha!=null){
					    		if(drficha.getConstantState().equals( mResources.getDrawable(R.drawable.black_peck).getConstantState())){
						    		negras++;
						    	}else if(drficha.getConstantState().equals( mResources.getDrawable(R.drawable.grey_peck).getConstantState())){
						    		grises++;
						    	}
					    	}
					    	
					    	ktuca++;
						}
  
					    String feedback= String.valueOf(negras)+""+String.valueOf(grises);
					    feedbacks[contador_feedbacks-1]=Integer.parseInt(feedback);

					    logconsole+=  guesses[0]+"-"+guesses[1]+"-"+guesses[2]+"-"+guesses[3]+"-"+
					  			  guesses[4]+"-"+guesses[5]+"-"+guesses[6]+"-"+guesses[7]+"\n"+
					  		      feedbacks[0]+"-"	+feedbacks[1]+"-"+feedbacks[2]+"-"+feedbacks[3]+"-"+feedbacks[4]+"-"+
					  		      feedbacks[5]+"-"+feedbacks[6]+"-"+feedbacks[7]+"\n"+ "Hay: "+poblacion.size()+" individuos en la población"+"\n------->"
					  		      +contador_feedbacks+"\n";
					    
						
						if((feedbacks[contador_feedbacks-1])==40){
						        Toast toa = Toast.makeText(getApplicationContext(),
					                    "Fiiiiiiin", Toast.LENGTH_SHORT);
					        	toa.show();}
						else mGame.killOne(guesses[contador_feedbacks-1]);
						
						
					    if((feedbacks[contador_feedbacks-1])==0)
							 mGame.killAll(guesses[contador_feedbacks-1]);
						
					    else if ((feedbacks[contador_feedbacks-1])>=14){
            				mGame.killComplement(guesses[contador_feedbacks-1]);
            			}
						    						    
						
					    
						console.setText(logconsole);
						
				
							guesses[contador_guesses] = generar_sigFichas();
							
						
				    	contador_guesses++;
				    	contador_feedbacks++;
				    	

					}
					}
				});
		}

     	//create_randompeck_feedback((ImageView) findViewById	(sSmallSlotPosition[i]));			
}

	
	
public int generar_sigFichas(){
		double x = ((double) poblacion.size() / (double) poblacion_inicial);
		Percent randomPercent = new Percent((int) (x * 100));
		String m = Integer.toString(poblacion.size());
		console.setText(m);
		String t = Integer.toString(poblacion_inicial);
		console.setText(t);
		if (jtuca == 1) {
			updateViews(new Percent(100));
		} else {
			updateViews(randomPercent);
		}
		updateViews(randomPercent);
		String[] acolor = new String[4];
				
		int individuo = mGame.getIndividuo();
		int fichas_gen=individuo;
		
		// / parte el enteros en digitos
		int[] digitos = { 0, 0, 0, 0 };
		int contadortotal = 3;
		

		while (individuo > 0) {
			digitos[contadortotal--] = individuo % 10;
			individuo /= 10;
		}

		// /randoms de las fichas
		for (int i = 0; i < 4; i++) {
			String Color = create_randompeck(
					(ImageView) findViewById(sSlotPosition[ituca]), digitos[i]);
			// String Color=insert_randompeck((ImageView)
			// findViewById(sSlotPosition[ituca]));
			acolor[i] = Color;
			ituca++;
		}
if (jtuca==1){
	
	
		logconsole+=  guesses[0]+"-"+guesses[1]+"-"+guesses[2]+"-"+guesses[3]+"-"+
	  			  guesses[4]+"-"+guesses[5]+"-"+guesses[6]+"-"+guesses[7]+"\n"+
	  		      feedbacks[0]+"-"+feedbacks[1]+"-"+feedbacks[2]+"-"+feedbacks[3]+"-"+feedbacks[4]+"-"+
	  		      feedbacks[5]+"-"+feedbacks[6]+"-"+feedbacks[7]+"\n"+ "Hay: "+poblacion.size()+" individuos en la población"+"\n------->"
	  		      +contador_feedbacks+"\n"; 
		
				  contador_feedbacks++;
				   
}



    return fichas_gen;
}

public String insert_randompeck(ImageView view){
	
	String color="";
	int randInt = new Random().nextInt(5);
	 switch (randInt) {
     case 0:
         view.setImageDrawable(mResources.getDrawable(R.drawable.bluepeg));
         color="blue";
         break;
     case 1:
         view.setImageDrawable(mResources.getDrawable(R.drawable.greenpeg));
         color="green";
         break;
     case 2:
         view.setImageDrawable(mResources.getDrawable(R.drawable.redpeg));
         color="red";
         break;
     case 3:
         view.setImageDrawable(mResources.getDrawable(R.drawable.whitepeg));
         color="white";
         break;
     case 4:
         view.setImageDrawable(mResources.getDrawable(R.drawable.yellowpeg));
         color="yellow";
         break;
     case 5:
         view.setImageDrawable(mResources.getDrawable(R.drawable.purplepeg));
         color="purple";
         break;
     default:
         break;
	 }
	 
	 return color;
}


public void create_randompeck_feedback(ImageView view){
	

	int randInt = new Random().nextInt(2) + 1;
	 switch (randInt) {
     case 1:
         view.setImageDrawable(mResources.getDrawable(R.drawable.grey_peck));
         break;
     case 2:
         view.setImageDrawable(mResources.getDrawable(R.drawable.black_peck));
         break;
     default:
         break;
	 }
}

public String create_randompeck(ImageView view,Integer randInt){
	
	String color="";
	 switch (randInt) {
     case 0:
         view.setImageDrawable(mResources.getDrawable(R.drawable.bluepeg));
         color="blue";
         break;
     case 1:
         view.setImageDrawable(mResources.getDrawable(R.drawable.greenpeg));
         color="green";
         break;
     case 2:
         view.setImageDrawable(mResources.getDrawable(R.drawable.redpeg));
         color="red";
         break;
     case 3:
         view.setImageDrawable(mResources.getDrawable(R.drawable.whitepeg));
         color="white";
         break;
     case 4:
         view.setImageDrawable(mResources.getDrawable(R.drawable.yellowpeg));
         color="yellow";
         break;
     case 5:
         view.setImageDrawable(mResources.getDrawable(R.drawable.purplepeg));
         color="purple";
         break;
     default:
         break;
	 }
	 
	 return color;
}
	
    

    private void updateViews(Percent randomPercent) {
    	Log.i("entro al rando", ""+randomPercent);
        progressBar.setCurrentValue(randomPercent);
        progressValueTextView.setText(randomPercent.asIntValue() + PERCENT_CHAR);
    }
    
    public boolean compareDrawable(Drawable d1, Drawable d2){
        try{
            Bitmap bitmap1 = ((BitmapDrawable)d1).getBitmap();
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
            stream1.flush();
            byte[] bitmapdata1 = stream1.toByteArray();
            stream1.close();

            Bitmap bitmap2 = ((BitmapDrawable)d2).getBitmap();
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
            stream2.flush();
            byte[] bitmapdata2 = stream2.toByteArray();
            stream2.close();

            return bitmapdata1.equals(bitmapdata2);
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }
}

