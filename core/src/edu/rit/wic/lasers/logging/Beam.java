package edu.rit.wic.lasers.logging;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.kiwi.util.gdx.collection.GdxArrays;
import com.google.common.base.Preconditions;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p> A logging system inspired by Timber (Created by Jake Wharton). Following the same
 * theme of not being directly named, a Beam (Laser Beam) that is made up of many {@link
 * LightRay light rays}. </p> <p> Following the idiomatic Java Singleton structure, this
 * is an enum with a single instance representing the one-and-only instance of this class.
 * A LightRay can do anything from logging per-platform to logging out to an external
 * service or file, all {@link Beam} does is send out logging actions to all {@link
 * LightRay} instances registered. </p>
 *
 * @author Matthew Crocco
 */
public enum Beam {

	BEAM;

	/** Concurrency Lock for updating LIGHT_RAYS */
	private final Lock lightRayLock = new ReentrantLock();

	private final LightRay[] EMPTY_LIGHT_RAYS_ARRAY = new LightRay[0];
	private final Array<LightRay> LIGHT_RAYS = GdxArrays.newArray(LightRay.class);
	private volatile LightRay[] lightRaysArray = EMPTY_LIGHT_RAYS_ARRAY;
	/**
	 * Root {@link LightRay} instance of the logging tree. Logging actions are emitted in
	 * a Depth-First manner.
	 */
	private final AbstractLightRay COHERENT_BEAM = new AbstractLightRay(null) {

		@Override
		public void setTag(final String tag) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].setTag(tag);
			}
		}

		@Override
		public void v(final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].v(message, args);
			}
		}

		@Override
		public void v(final Throwable t, final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].v(t, message, args);
			}
		}

		@Override
		public void v(final Throwable t) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].v(t);
			}
		}

		@Override
		public void d(final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].d(message, args);
			}
		}

		@Override
		public void d(final Throwable t, final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].d(t, message, args);
			}
		}

		@Override
		public void d(final Throwable t) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].d(t);
			}
		}

		@Override
		public void i(final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].i(message, args);
			}
		}

		@Override
		public void i(final Throwable t, final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].i(t, message, args);
			}
		}

		@Override
		public void i(final Throwable t) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].i(t);
			}
		}

		@Override
		public void w(final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].w(message, args);
			}
		}

		@Override
		public void w(final Throwable t, final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].w(t, message, args);
			}
		}

		@Override
		public void w(final Throwable t) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].w(t);
			}
		}

		@Override
		public void e(final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].e(message, args);
			}
		}

		@Override
		public void e(final Throwable t, final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].e(t, message, args);
			}
		}

		@Override
		public void e(final Throwable t) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].e(t);
			}
		}

		@Override
		public void wtf(final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].wtf(message, args);
			}
		}

		@Override
		public void wtf(final Throwable t, final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].wtf(t, message, args);
			}
		}

		@Override
		public void wtf(final Throwable t) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].wtf(t);
			}
		}

		@Override
		public void log(final BeamPriority priority, final String message,
		                final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].log(priority, message, args);
			}
		}

		@Override
		public void log(final BeamPriority priority, final Throwable t,
		                final String message, final Object... args) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].log(priority, t, message, args);
			}
		}

		@Override
		public void log(final BeamPriority priority, final Throwable t) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < lightRaysArray.length; i++) {
				lightRaysArray[i].log(priority, t);
			}
		}

		@Override
		protected void log(final BeamPriority priority, final String tag,
		                   final String message, final Throwable t) {
			// These should never be called, is only called if not all of the methods
			// are properly overridden to emit to all other trees.
			throw new AssertionError("Missing override");
		}
	};

	public static void interceptUncaughtExceptions() {
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			String eType = e.getClass().getTypeName();
			Beam.BEAM.e(e, "Uncaught Exception of type '%s' in Thread: %s:%d", eType, t.getName(), t.getId());
		});
	}

	/** Log a verbose message with optional format args. */
	public void v(String message, Object... args) {
		COHERENT_BEAM.v(message, args);
	}

	/** Log a verbose exception and a message with optional format args. */
	public void v(Throwable t, String message, Object... args) {
		COHERENT_BEAM.v(t, message, args);
	}

	/** Log a verbose exception. */
	public void v(Throwable t) {
		COHERENT_BEAM.v(t);
	}

	/** Log a debug message with optional format args. */
	public void d(String message, Object... args) {
		COHERENT_BEAM.d(message, args);
	}

	/** Log a debug exception and a message with optional format args. */
	public void d(Throwable t, String message, Object... args) {
		COHERENT_BEAM.d(t, message, args);
	}

	/** Log a debug exception. */
	public void d(Throwable t) {
		COHERENT_BEAM.d(t);
	}

	/** Log an info message with optional format args. */
	public void i(String message, Object... args) {
		COHERENT_BEAM.i(message, args);
	}

	/** Log an info exception and a message with optional format args. */
	public void i(Throwable t, String message, Object... args) {
		COHERENT_BEAM.i(t, message, args);
	}

	/** Log an info exception. */
	public void i(Throwable t) {
		COHERENT_BEAM.i(t);
	}

	/** Log a warning message with optional format args. */
	public void w(String message, Object... args) {
		COHERENT_BEAM.w(message, args);
	}

	/** Log a warning exception and a message with optional format args. */
	public void w(Throwable t, String message, Object... args) {
		COHERENT_BEAM.w(t, message, args);
	}

	/** Log a warning exception. */
	public void w(Throwable t) {
		COHERENT_BEAM.w(t);
	}

	/** Log an error message with optional format args. */
	public void e(String message, Object... args) {
		COHERENT_BEAM.e(message, args);
	}

	/** Log an error exception and a message with optional format args. */
	public void e(Throwable t, String message, Object... args) {
		COHERENT_BEAM.e(t, message, args);
	}

	/** Log an error exception. */
	public void e(Throwable t) {
		COHERENT_BEAM.e(t);
	}

	/** Log an assert message with optional format args. */
	public void wtf(String message, Object... args) {
		COHERENT_BEAM.wtf(message, args);
	}

	/** Log an assert exception and a message with optional format args. */
	public void wtf(Throwable t, String message, Object... args) {
		COHERENT_BEAM.wtf(t, message, args);
	}

	/** Log an assert exception. */
	public void wtf(Throwable t) {
		COHERENT_BEAM.wtf(t);
	}

	/** Log at {@code priority} a message with optional format args. */
	public void log(BeamPriority priority, String message, Object... args) {
		COHERENT_BEAM.log(priority, message, args);
	}

	/** Log at {@code priority} an exception and a message with optional format args. */
	public void log(BeamPriority priority, Throwable t, String message, Object... args) {
		COHERENT_BEAM.log(priority, t, message, args);
	}

	/** Log at {@code priority} an exception. */
	public void log(BeamPriority priority, Throwable t) {
		COHERENT_BEAM.log(priority, t);
	}

	/**
	 * A view into Timber's planted LightRays as a LightRay itself. This can be used for
	 * injecting a logger instance rather than using methods or to facilitate testing.
	 */
	public LightRay asLightRay() {
		return COHERENT_BEAM;
	}

	/** Set a one-time tag for use on the next logging call. */
	public LightRay tag(String tag) {
		LightRay[] forest = lightRaysArray;
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0, count = forest.length; i < count; i++) {
			forest[i].setTag(tag);
		}
		return COHERENT_BEAM;
	}

	/**
	 * Adds a {@link LightRay} to the beam. Any logging actions will be sent to the
	 * ray as
	 * well.
	 *
	 * @param ray
	 * 	{@link LightRay} to add
	 *
	 * @throws NullPointerException
	 * 	Parameter is null
	 * @throws IllegalArgumentException
	 * 	Parameter is Root LightRay
	 */
	public void addRay(LightRay ray) {
		ray = Preconditions.checkNotNull(ray);
		Preconditions.checkArgument(ray != COHERENT_BEAM, "Beams inside beams are not "
			+ "allowed!");

		lightRayLock.lock();
		try {
			LIGHT_RAYS.add(ray);
			lightRaysArray = LIGHT_RAYS.toArray();
		} finally {
			lightRayLock.unlock();
		}
	}

	/**
	 * Adds {@link LightRay LightRays} to the beam. Any logging actions will be sent to
	 * the rays as well.
	 *
	 * @param rays
	 * 	All {@link LightRay LightRays} to add
	 *
	 * @throws NullPointerException
	 * 	Any {@link LightRay} in parameter is null
	 * @throws IllegalArgumentException
	 * 	Any {@link LightRay} in parameter is Root LightRay
	 */
	public void addRays(LightRay... rays) {
		rays = Preconditions.checkNotNull(rays);

		for (LightRay ray : rays) {
			Preconditions.checkNotNull(ray, "No null LightRays allowed");
			Preconditions.checkArgument(ray != COHERENT_BEAM, "Beams inside beams are "
				+ "not allowed!");
		}

		lightRayLock.lock();
		try {
			LIGHT_RAYS.addAll(rays);
			lightRaysArray = LIGHT_RAYS.toArray();
		} finally {
			lightRayLock.unlock();
		}
	}

	/**
	 * Removes a {@link LightRay} from the beam. No more logging actions will be sent to
	 * that LightRay from this Beam.
	 *
	 * @param ray
	 * 	{@link LightRay} to remove
	 *
	 * @throws NullPointerException
	 * 	Parameter is null
	 * @throws IllegalArgumentException
	 * 	LightRay has not been added
	 */
	public void removeRay(LightRay ray) {
		ray = Preconditions.checkNotNull(ray);

		lightRayLock.lock();
		try {
			Preconditions.checkArgument(LIGHT_RAYS.removeValue(ray, true), "Cannot "
				+ "remove LightRay that has not been added: " + String
				.valueOf(ray));
			lightRaysArray = LIGHT_RAYS.toArray();
		} finally {
			lightRayLock.unlock();
		}
	}

	/**
	 * Removes all {@link LightRay} instances from this Beam.
	 */
	public void removeAllRays() {
		lightRayLock.lock();
		try {
			LIGHT_RAYS.clear();
			lightRaysArray = EMPTY_LIGHT_RAYS_ARRAY;
		} finally {
			lightRayLock.unlock();
		}
	}

	/**
	 * Returns an {@link ImmutableArray} representing EVERY {@link LightRay} added to
	 * this
	 * {@link Beam}. Should only be iterated over, modify LightRay instances at your own
	 * risk.
	 *
	 * @return {@link ImmutableArray} of {@link LightRay LightRays} known by this {@link
	 * Beam}
	 */
	public Iterable<LightRay> getBeam() {
		lightRayLock.lock();
		try {
			return new ImmutableArray<>(this.LIGHT_RAYS);
		} finally {
			lightRayLock.unlock();
		}
	}

	/**
	 * @return Numer of {@link LightRay LightRays} added to this {@link Beam}
	 */
	public int rayCount() {
		lightRayLock.lock();
		try {
			return LIGHT_RAYS.size;
		} finally {
			lightRayLock.unlock();
		}
	}

}
