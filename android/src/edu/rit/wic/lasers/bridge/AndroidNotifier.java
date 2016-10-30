package edu.rit.wic.lasers.bridge;

import android.content.Context;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.sdsmdg.tastytoast.TastyToast;

import edu.rit.wic.lasers.logging.Beam;

/**
 * Android implementation of {@link Notifier} which uses {@link TastyToast} to show
 * various sweet Toast notifications. This is executed on the UI Thread and may not be
 * executed immediately... but should be executed pretty quickly!
 *
 * @author Matthew Crocco
 */
public class AndroidNotifier extends AbstractNotifier {

	private static final Beam logger = Beam.BEAM;
	private final AndroidApplication notifierContext;

	public AndroidNotifier(AndroidApplication ctxt) {
		this.notifierContext = ctxt;
	}

	@Override
	public void msg(final DurationHint duration, final NotifType type, final String message,
	                final Object... args) {

		Context ctxt = this.notifierContext;
		String msg = String.format(message, args);
		this.notifierContext.runOnUiThread(
			() -> TastyToast.makeText(
				ctxt,
				msg,
				translateDuration(duration),
				translateType(type)
			)
		);

	}

	private static int translateType(NotifType type) {
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
				logger.w("Unknown " + NotifType.class.getName() + " instance: " + type);
				return TastyToast.DEFAULT;
		}
	}

	private static int translateDuration(DurationHint duration) {
		switch(duration) {
			case SHORT:
				return TastyToast.LENGTH_SHORT;
			case LONG:
				return TastyToast.LENGTH_LONG;
			default:
				logger.w("Unknown " + DurationHint.class.getName() + " instance: " +
					duration);
			case NORMAL:
				return TastyToast.LENGTH_LONG;
		}
	}
}
