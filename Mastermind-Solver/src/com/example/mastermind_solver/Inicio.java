package com.example.mastermind_solver;



import java.util.Random;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Inicio extends Activity {

    Resources mResources;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button b1= (Button) findViewById(R.id.newGame);
        mResources = getResources();
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ir_menu_principal = new Intent(getApplicationContext(), Mastermind.class);
				startActivity(ir_menu_principal);
			}
		});
		create_randompeck((ImageView) findViewById(R.id.rand1));
		create_randompeck((ImageView) findViewById(R.id.rand2));
		create_randompeck((ImageView) findViewById(R.id.rand3));
		create_randompeck((ImageView) findViewById(R.id.rand4));
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
}
