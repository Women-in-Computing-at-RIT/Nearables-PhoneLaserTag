package edu.rit.wic.lasers.bridge;

import android.content.Context;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.sdsmdg.tastytoast.TastyToast;

import edu.rit.wic.lasers.logging.Beam;

/**
 * Created by Matthew on 10/11/2016.
 */
public class AndroidNotifier extends AbstractNotifier {

	private final AndroidApplication notifierContext;

	public AndroidNotifier(AndroidApplication ctxt) {
		this.notifierContext = ctxt;
	}

	@Override
	public void msg(final Duration duration, final Type type, final String message,
	                final Object... args) {

		int len = translateDuration(duration);
		int realType = translateType(type);

		String msg = String.format(message, args);

		this.notifierContext.runOnUiThread(() -> TastyToast.makeText(this
			.notifierContext, msg, len, realType));

	}

	private static int translateType(Type type) {
		switch(type) {
			case SUCCESS:
				return TastyToast.SUCCESS;
			case WARNING:
				return TastyToast.WARNING;
			case ERROR:
				return TastyToast.ERROR;
			case INFO:
				return TastyToast.INFO;
			case SPECIAL:
				return TastyToast.CONFUSING;
			default:
				Beam.INSTANCE.w("Unknown " + Type.class.getName() + " instance: " + type);
				return TastyToast.DEFAULT;
		}
	}

	private static int translateDuration(Duration duration) {
		switch(duration) {
			case QUICK:
				return TastyToast.LENGTH_SHORT;
			default:
				Beam.INSTANCE.w("Unknown " + Duration.class.getName() + " instance: " +
					duration);
			case NORMAL:
				return TastyToast.LENGTH_LONG;
		}
	}
}
