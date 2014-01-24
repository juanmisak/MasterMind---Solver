package com.example.mastermind_solver;

import java.util.Arrays;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Mastermind extends Activity {
	public static final String PERCENT_CHAR = "%";
    public static final int MAX_PERCENT_RANDOM_VALUE = 100;
    private VerticalProgressBar progressBar;
    private Random random;
    private Button randomValueButton;
    private TextView progressValueTextView;    
    
    
	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;
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

    Engine mGame;
    int mGuess = 0;
    Resources mResources;
    // state[] shows which colour peg has been selected
    boolean[] mState = new boolean[Engine.TOTAL_NO_PEGS];
    
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
        
        map(sSlotPosition, pegSlots);
        map(sSmallSlotPosition, sSmallPegSlots);
    }

    private ImageView bluePeg;
    private ImageView greenPeg;
    private ImageView redPeg;
    private ImageView whitePeg;
    private ImageView yellowPeg;
    private ImageView purplePeg;
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
        Percent randomPercent = new Percent(random.nextInt(MAX_PERCENT_RANDOM_VALUE));
        updateViews(randomPercent);
        
        mResources = getResources();
        // Initialise the state array
        for (int i = 0; i < mState.length; i++) {
            mState[i] = false;
        } // for

        bluePeg = (ImageView) findViewById(R.id.bluePeg);
        greenPeg = (ImageView) findViewById(R.id.greenPeg);
        redPeg = (ImageView) findViewById(R.id.redPeg);
        whitePeg = (ImageView) findViewById(R.id.whitePeg);
        yellowPeg = (ImageView) findViewById(R.id.yellowPeg);
        purplePeg = (ImageView) findViewById(R.id.purplePeg);

        findViewById(R.id.confirm08).setClickable(false);
        

        TextView console = (TextView) findViewById(R.id.consola);
        String logconsole="";
        int j=0;
        ///Generando aletaorio en cada iteracion de los 8
        for (int i = 0; i < confirm.length; i++) {
	        /*ImageView view = (ImageView) findViewById(confirm[i]);	
	        view.setImageDrawable(mResources.getDrawable(R.drawable.confirm));*/
	        logconsole+="Combination guessing.... \n";
	        j++;
	        logconsole+="Round #"+j+" \n";
	        logconsole+="[color1, color2, color3, color4]\n";
	        
        }
        console.setText(logconsole);
        ///randoms de las fichas
        for (int i =0; i <4; i++) {
        	create_randompeck((ImageView) findViewById(sSlotPosition[i]));		
		}
        //Bloqueando clickable a las fichas generadas
        for (int i =0; i < sSlotPosition.length; i++) {
            ImageView view = (ImageView) findViewById(sSlotPosition[i]);
            view.setClickable(false);	
		}
        
       
        
        
        TOTAL_PEG_SLOTS=TOTAL_PEG_SLOTS-4;
        //bloqueando clickable a los feeds restantes
        for (int i = 0; i < TOTAL_PEG_SLOTS; i++) {
            ImageView view = (ImageView) findViewById(sSmallSlotPosition[i]);
            view.setClickable(false);
		}
        
        ImageView view = (ImageView) findViewById(confirm[7]);	
        view.setImageDrawable(mResources.getDrawable(R.drawable.confirm));
        view.setClickable(true);
        //findViewById(R.id.confirm08).setClickable(false);
        
        ///randoms de las feedbacks
        for (int i = 0; i < sSmallSlotPosition.length; i++) {

            ImageView view1 = (ImageView) findViewById(sSmallSlotPosition[i]);
            
            //view1.setImageDrawable(mResources.getDrawable(R.drawable.smallPeg));
            view1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
            
         	create_randompeck_feedback((ImageView) findViewById(sSmallSlotPosition[i]));			
 		}

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

public void create_randompeck(ImageView view){
	

	int randInt = new Random().nextInt(6) + 1;
	 switch (randInt) {
     case 1:
         view.setImageDrawable(mResources.getDrawable(R.drawable.bluepeg));
         break;
     case 2:
         view.setImageDrawable(mResources.getDrawable(R.drawable.greenpeg));
         break;
     case 3:
         view.setImageDrawable(mResources.getDrawable(R.drawable.redpeg));
         break;
     case 4:
         view.setImageDrawable(mResources.getDrawable(R.drawable.purplepeg));
         break;
     case 5:
         view.setImageDrawable(mResources.getDrawable(R.drawable.whitepeg));
         break;
     case 6:
         view.setImageDrawable(mResources.getDrawable(R.drawable.yellowpeg));
         break;
     default:
         break;
	 }
}
	
	/*   DE  AQUI PARA ABAJO ESTAS FUNCIONES SON COMPLEMENTARIAS PARA AYUDARME A JUGAR POR AHORA NO ME INTERESAN*/
    protected static final int MENU_NEW_GAME = Menu.FIRST;
    protected static final int MENU_INSTRUCTIONS = Menu.FIRST + 1;

    // Map the slotPositions to an index for each row on the board
    private static void map(final int[] array, final int[][] dArray) {
        int k = 0;
        for (int i = 0; i < dArray.length; i++) {
            for (int j = 0; j < dArray[i].length; j++) {
                dArray[i][j] = array[k];
                k++;
            } // for
        } // for
    } // map(int[], int[][])

    private void checkState() {
        ImageView view = null;
        for (int i = 0; i < mState.length; i++)
            if (mState[i]) {
                switch (i) {
                    case BLUE:
                        bluePeg.performClick();
                        break;
                    case GREEN:
                        greenPeg.performClick();
                        break;
                    case RED:
                        redPeg.performClick();
                        break;
                    case PURPLE:
                        purplePeg.performClick();
                        break;
                    case WHITE:
                        whitePeg.performClick();
                        break;
                    case YELLOW:
                        yellowPeg.performClick();
                        break;
                    default:
                        break;
                } // switch
                return;
            } // if
    } // checkState()

    private boolean checkGuess(final int viewID) {
        try {
            for (int i = 0; i < pegSlots[mGuess].length; i++) {
                if (pegSlots[mGuess][i] == viewID) { return true; } // if
            } // for
            return false;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        } // catch
    } // checkGuess(int)
    

    private void checkPegs() {
        boolean check = true;
        ImageView view = null;
        switch (mGuess) {
            case 0:
                for (int i = 0; i < 4; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm08);
                break;

            case 1:
                for (int i = 4; i < 8; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm07);
                break;

            case 2:
                for (int i = 8; i < 12; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm06);
                break;

            case 3:
                for (int i = 12; i < 16; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm05);
                break;

            case 4:
                for (int i = 16; i < 20; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm04);
                break;

            case 5:
                for (int i = 20; i < 24; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm03);
                break;

            case 6:
                for (int i = 24; i < 28; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm02);
                break;

            case 7:
                for (int i = 28; i < 32; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm01);
                break;

            default:
                break;
        } // switch
        if (check) {
            view.setImageDrawable(mResources.getDrawable(R.drawable.confirm));
            view.setClickable(true);
        } else {
            view.setImageDrawable(mResources.getDrawable(R.drawable.row));
            view.setClickable(false);
        } // else
    } // checkPegs()

    public final void select(final View view) {
        if (finished || !checkGuess(view.getId())) return;
        for (int i = 0; i < mState.length; i++) {
            if (mState[i]) {
                switch (i) {
                    case BLUE:
                        ((ImageView) view).setImageDrawable(mResources
                                .getDrawable(R.drawable.bluepeg));
                        view.setTag(R.drawable.bluepeg);
                        bluePeg.performClick();
                        break;
                    case RED:
                        ((ImageView) view).setImageDrawable(mResources
                                .getDrawable(R.drawable.redpeg));
                        view.setTag(R.drawable.redpeg);
                        redPeg.performClick();
                        break;
                    case WHITE:
                        ((ImageView) view).setImageDrawable(mResources
                                .getDrawable(R.drawable.whitepeg));
                        view.setTag(R.drawable.whitepeg);
                        whitePeg.performClick();
                        break;
                    case YELLOW:
                        ((ImageView) view).setImageDrawable(mResources
                                .getDrawable(R.drawable.yellowpeg));
                        view.setTag(R.drawable.yellowpeg);
                        yellowPeg.performClick();
                        break;
                    case GREEN:
                        ((ImageView) view).setImageDrawable(mResources
                                .getDrawable(R.drawable.greenpeg));
                        view.setTag(R.drawable.greenpeg);
                        greenPeg.performClick();
                        break;
                    case PURPLE:
                        ((ImageView) view).setImageDrawable(mResources
                                .getDrawable(R.drawable.purplepeg));
                        view.setTag(R.drawable.purplepeg);
                        purplePeg.performClick();
                        break;
                    default:
                        break;
                } // switch
                checkPegs();
                return;
            } // if
        } // for
        ((ImageView) view).setImageDrawable(null);
        view.setTag(R.drawable.pegslot);
        checkPegs();
    } // select(View)
    

    private int[] parseAttempt(int[] tags) {
        final int[] attempt = new int[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++) {
            if (tags[i] == sPegs[0]) {
                attempt[i] = sPegs[0];
            } else if (tags[i] == sPegs[1]) {
                attempt[i] = sPegs[1];
            } else if (tags[i] == sPegs[2]) {
                attempt[i] = sPegs[2];
            } else if (tags[i] == sPegs[3]) {
                attempt[i] = sPegs[3];
            } else if (tags[i] == sPegs[4]) {
                attempt[i] = sPegs[4];
            } else if (tags[i] == sPegs[5]) {
                attempt[i] = sPegs[5];
            } // else
        } // for
        return attempt;
    }// parseAttempt(int[])

    private int[] getTags() {
        final int[] tags = new int[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++) {
            tags[i] = (Integer) findViewById(pegSlots[mGuess][i]).getTag();
        } // for
        return tags;
    } // getTags()

    private static final int CORRECT_COLOUR_POSITION = 2;
    private static final int CORRECT_COLOUR_WRONG_POSITION = 1;

    private final boolean parseResponse(final int[] resp) {
        Arrays.sort(resp);
        for (int i = 0; i < MAX_PEGS; i++) {
            if (resp[i] == CORRECT_COLOUR_POSITION) {
                ((ImageView) findViewById(sSmallPegSlots[mGuess][i]))
                        .setImageResource(R.drawable.smallred);
            } else if (resp[i] == CORRECT_COLOUR_WRONG_POSITION) {
                ((ImageView) findViewById(sSmallPegSlots[mGuess][i]))
                        .setImageResource(R.drawable.smallwhite);
            } // else
        } // for
          // Sorted in ascending order, therefore if first element is correct peg
          // colour and position, all are correct
        return resp[0] == CORRECT_COLOUR_POSITION;
    } // parseResponse(int)

    private int[] makeResponse(final boolean[] pos, final boolean[] col) {
        final int[] resp = new int[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++) {
            // If in right position, return 2
            if (pos[i]) {
                resp[i] = CORRECT_COLOUR_POSITION;
            } else {
                // If in wrong position, return 1
                if (col[i]) {
                    resp[i] = CORRECT_COLOUR_WRONG_POSITION;
                } // if
            } // else
        } // for
        return resp;
    } // makeResponse(boolean[], boolean[])

    public void confirm(final View view) {
        view.setClickable(false);
        // Get the tags for the current guess and
        // Parse the combination provided by the user into an array
        final int[] attempt = parseAttempt(getTags());

        // Check the attempt against the solution
        // First check if a peg is the same colour and in the right position
        final boolean[] pos = new boolean[MAX_PEGS], col = new boolean[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++) {
            pos[i] = mGame.checkPos(attempt[i], i);
        } // for

        // Then check if the peg is in the wrong position
        for (int i = 0; i < MAX_PEGS; i++) {
            if (!pos[i]) {
                col[i] = mGame.checkPeg(attempt[i]);
            } // if
        } // for

        // Pass the result onto the user through use of the smaller pegs
        final boolean correct = parseResponse(makeResponse(pos, col));

        mGuess++;
        // If the attempt is correct, end the game
        if (correct) {
            endGame();
            finished = true;
        } else {
            // Lose
            if (mGuess == MAX_GUESSES) {
                loseGame();
                finished = true;
            } else {
                checkPegs();
                mGame.resetStates();
            } // else
        } // else
    } // confirm(View)

    private boolean finished;

    protected void loseGame() {
        final Intent again = new Intent(this, this.getClass());
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You failed to crack the code!").setCancelable(true)
                .setNeutralButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(again);
                        finish();
                    } // onClick(DialogInterface, int)
                }).setNegativeButton("Quit to Main Menu", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    } // onClick(DialogInterface, int)
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                    } // onClick(DialogInterface, int)
                });
        builder.create().show();
    } // loseGame()

    protected void endGame() {
        final Intent again = new Intent(this, Mastermind.class);
        new AlertDialog.Builder(this)
                .setMessage("Congratulations! You cracked the code in " + mGuess + " attempts!")
                .setCancelable(true)
                .setNeutralButton("Start Again", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(again);
                        finish();
                    } // onClick(DialogInterface, int)
                }).setNegativeButton("Quit to Main Menu", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    } // onClick(DialogInterface, int)
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                    } // onClick(DialogInterface, int)
                }).create().show();
    } // endGame()
    
    
    

    private void updateViews(Percent randomPercent) {
    	Log.i("entro al rando", ""+randomPercent);
        progressBar.setCurrentValue(randomPercent);
        progressValueTextView.setText(randomPercent.asIntValue() + PERCENT_CHAR);
    }
}
