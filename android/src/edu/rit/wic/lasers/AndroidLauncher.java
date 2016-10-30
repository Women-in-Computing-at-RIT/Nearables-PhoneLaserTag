package edu.rit.wic.lasers;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.orhanobut.logger.Logger;

import edu.rit.wic.lasers.bridge.AndroidNotifier;
import edu.rit.wic.lasers.bridge.Notifier;
import edu.rit.wic.lasers.logging.AndroidDebugLightRay;
import edu.rit.wic.lasers.logging.Beam;

/**
 * Android Launcher for Laser Tag Game
 *
 * @author Matthew Crocco
 */
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.useAccelerometer = true;
		config.useCompass = true;
		config.useGyroscope = true;
		config.useWakelock = true;

		config.useGLSurfaceView20API18 = true;

		Logger.init("LaserTag").hideThreadInfo().methodCount(3).methodOffset(4);
		Beam.BEAM.addRay(new AndroidDebugLightRay());

		Notifier notif = new AndroidNotifier(this);
		initialize(new LaserTagGame(notif), config);
	}
}
