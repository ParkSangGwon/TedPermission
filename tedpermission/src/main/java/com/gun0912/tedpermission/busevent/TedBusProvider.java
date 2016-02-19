package com.gun0912.tedpermission.busevent;

/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Maintains a singleton instance for obtaining the bus. Ideally this would be
 * replaced with a more efficient means such as through injection directly into
 * interested classes.
 */
public final class TedBusProvider extends Bus{

	private static TedBusProvider instance;

	public static TedBusProvider getInstance() {

		if(instance==null)
			instance = new TedBusProvider();

		return instance;
	}


	private final Handler mHandler = new Handler(Looper.getMainLooper());

	@Override
	public void post(final Object event) {


		if (Looper.myLooper() == Looper.getMainLooper()) {
			super.post(event);
		} else {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					TedBusProvider.getInstance().post(event);
				}
			});
		}


	}
}
